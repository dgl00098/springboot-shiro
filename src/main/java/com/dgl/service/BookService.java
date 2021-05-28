package com.dgl.service;

import com.dgl.model.dto.BaseQO;
import com.dgl.model.dto.BookDTO;
import com.dgl.model.entity.Role;
import com.dgl.model.vo.RespEntity;
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
    Integer save(List<BookDTO> list);

    /**
     * 批量导入图书
     * @param file
     * @return
     */
    Integer importBook(MultipartFile file);
}
