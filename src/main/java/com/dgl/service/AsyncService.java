package com.dgl.service;

import com.dgl.model.dto.BookDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 专门处理异步业务
 */
@Async
public interface AsyncService {

    void batchInsert();

}
