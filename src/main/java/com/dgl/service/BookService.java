package com.dgl.service;

import com.dgl.model.dto.BookDTO;
import com.dgl.model.dto.BookListDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 书籍管理
 */
public interface BookService {


    /**
     * 添加书籍
     * @param list
     * @return
     */
    String save(List<BookDTO> list);

    /**
     * 批量导入图书
     * @param file
     * @return
     */
    Integer importBook(MultipartFile file);

    /**
     * 测试插入数据
     * @return
     */
    Integer testBatchInsert();

    /**
     * 删除所有
     * @return
     */
    Integer deleteAll();

    /**
     * 查询总条数
     * @return
     */
    Integer countAll();

    /**
     * 书籍列表
     * @param bookListDTO
     * @return
     */
    Page<BookDTO> bookList(BookListDTO bookListDTO);

    /**
     * 书籍详情
     * @param id
     * @return
     */
    BookDTO findById(String id);

    /**
     * 删除图书
     * @param id
     * @return
     */
    Integer deleteById(String id);
}
