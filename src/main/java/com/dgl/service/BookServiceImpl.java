package com.dgl.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.dgl.common.Enum.EnumBookType;
import com.dgl.common.Enum.EnumPress;
import com.dgl.common.Enum.EnumYesOrNo;
import com.dgl.common.utils.DGLUtils;
import com.dgl.config.excel.BookReadListener;
import com.dgl.dao.es.BookRepository;
import com.dgl.model.dto.BookDTO;
import com.dgl.model.entity.es.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private RestHighLevelClient client;

    @Override
    public Integer save(List<BookDTO> list) {
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(bookDTO -> {
//                BookDTO dto = BookDTO.builder().author(DGLUtils.getChineseName()).bookImage("").
//                        bookName("<<" + DGLUtils.getChineseName() + DGLUtils.getChineseName() + ">>").
//                        bookPages(RandomUtil.randomInt(10, 50000)).
//                        bookType(EnumBookType.getByRandom().getType()).
//                        press(EnumPress.getByRandom().getValue()).
//                        price(String.valueOf(RandomUtil.randomInt(1, 10000))).build();
                //log.info("=================当前书籍:{}==================", JSON.toJSONString(dto));
            });
        }
        return 0;
    }

    @Override
    public Integer importBook(MultipartFile file) {
        List<BookDTO> studentList = new ArrayList();
        String fileName = file.getOriginalFilename();
        File excelFile = new File("E:\\excelImport\\"+fileName);
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
    public Integer test() {
        List<Book> list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            BookDTO dto = BookDTO.builder().author(DGLUtils.getChineseName()).bookImage("").
                    bookName("<<" + DGLUtils.getChineseName() + DGLUtils.getChineseName() + ">>").
                    bookPages(RandomUtil.randomInt(10, 50000)).
                    bookType(EnumBookType.getByRandom().getType()).
                    press(EnumPress.getByRandom().getValue()).
                    price(String.valueOf(RandomUtil.randomInt(1, 10000))).build();
            Book book = new Book(dto);
            list.add(book);
            log.info("当前书籍:{}", JSON.toJSONString(dto));
        }
        bookRepo.saveAll(list);
        return list.size();
    }

    @Override
    public List<BookDTO> findAll() {
        List<BookDTO> list=new ArrayList<>();
         bookRepo.findAll().forEach(book -> {
             BookDTO bookDTO = new BookDTO(book);
             list.add(bookDTO);
         });
        return list;
    }

    @Override
    public Integer deleteAll() {
        bookRepo.deleteAll();
        return EnumYesOrNo.YES.getCode();
    }
}
