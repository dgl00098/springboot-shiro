package com.dgl.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.dgl.common.Enum.CustomException;
import com.dgl.common.Enum.EnumErrorMsg;
import com.dgl.common.Enum.EnumYesOrNo;
import com.dgl.config.excel.BookReadListener;
import com.dgl.dao.es.BookRepository;
import com.dgl.model.dto.BookDTO;
import com.dgl.model.dto.BookListDTO;
import com.dgl.model.entity.es.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName BookServiceImpl
 * @Description TODO
 * @Author DGL
 * @Date 2021/5/28  11:55
 **/
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepo;

    @Autowired
    AsyncService asyncService;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public String save(List<BookDTO> list) {
        AtomicReference<String> result= new AtomicReference<>("");
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(bookDTO -> {
                BookDTO dto = BookDTO.builder().
                        id(bookDTO.getId()).
                        author(bookDTO.getAuthor()).
                        description(bookDTO.getDescription()).
                        bookImage(bookDTO.getBookImage()).
                        bookName(bookDTO.getBookName()).
                        bookPages(bookDTO.getBookPages()).
                        bookType(bookDTO.getBookType()).
                        press(bookDTO.getPress()).
                        price(bookDTO.getPrice()).build();
                log.info("=================当前书籍:{}==================", JSON.toJSONString(dto));
                Book book = new Book(dto);
                Book save = bookRepo.save(book);
                result.set(save.getId());
            });
        }
        return result.get();
    }

    @Override
    public Integer importBook(MultipartFile file) {
        List<BookDTO> studentList = new ArrayList();
        String fileName = file.getOriginalFilename();
        File excelFile = new File("E:\\excelImport\\" + fileName);
        try {
            file.transferTo(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(excelFile.getPath(), BookDTO.class, new BookReadListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
        return 10;
    }

    @Override
    public Integer testBatchInsert() {
        asyncService.batchInsert();
        return EnumYesOrNo.YES.getCode();
    }


    @Override
    public Integer deleteAll() {
        bookRepo.deleteAll();
        return EnumYesOrNo.YES.getCode();
    }

    @Override
    public Integer countAll() {
        int all = bookRepo.countAllByStatus(EnumYesOrNo.NO.getCode());
        return all;
    }

    @Override
    public Page<BookDTO> bookList(BookListDTO dto) {
        // 构建分页
        Pageable page = PageRequest.of(dto.getCurrentPage() - 1, dto.getPageSize());
        BoolQueryBuilder boolQuery = getBoolQuery(dto);
        SortBuilder scoreSortBuilder = SortBuilders.scoreSort();
        if (StringUtils.isNotEmpty(dto.getOrderField()) && StringUtils.isNotEmpty(dto.getOrderType())) {
            scoreSortBuilder = SortBuilders.fieldSort(dto.getOrderField());
            scoreSortBuilder.order(dto.getOrderType().equals("desc") ? SortOrder.DESC : SortOrder.ASC);
        }
        //创建请求体
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().
                withQuery(boolQuery).
                withPageable(page).
                withSort(scoreSortBuilder).
                build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(searchQuery, Book.class);
        long count = elasticsearchRestTemplate.count(searchQuery, Book.class);
        List<BookDTO> list=new ArrayList<>();
        search.forEach(bookSearchHit -> {
            Book book = bookSearchHit.getContent();
            BookDTO bookDTO = new BookDTO(book);
            list.add(bookDTO);
        });
        return new PageImpl<>(list,page,count);
    }

    @Override
    public BookDTO findById(String id) {
        Book book = bookRepo.findById(id).orElseThrow(() -> new CustomException(EnumErrorMsg.DATA_NOT_EXIST));
        BookDTO bookDTO = new BookDTO(book);
        return bookDTO;
    }

    @Override
    public Integer deleteById(String id) {
        bookRepo.deleteById(id);
        return EnumYesOrNo.YES.getCode();
    }

    /**
     * 构建查询语句
     *
     * @return
     */
    private BoolQueryBuilder getBoolQuery(BookListDTO dto) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //范围查询
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("price");
        if (StringUtils.isNotEmpty(dto.getMinPrice())) {
            rangeQueryBuilder.gte(dto.getMinPrice());
        }
        if (StringUtils.isNotEmpty(dto.getMaxPrice())) {
            rangeQueryBuilder.gte(dto.getMaxPrice());
        }
        //条件查询
        if (StringUtils.isNotEmpty(dto.getBookName())) {
            boolQueryBuilder.must(new MatchQueryBuilder("bookName", dto.getBookName()));
        }
        if (StringUtils.isNotEmpty(dto.getBookType())) {
            boolQueryBuilder.must(new MatchQueryBuilder("bookType", dto.getBookType()));
        }
        if (StringUtils.isNotEmpty(dto.getAuthor())) {
            boolQueryBuilder.must(new MatchQueryBuilder("author", dto.getAuthor()));
        }
        boolQueryBuilder.must(rangeQueryBuilder);
        return boolQueryBuilder;
    }
}
