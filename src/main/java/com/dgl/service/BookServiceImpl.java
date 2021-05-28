package com.dgl.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import com.dgl.config.excel.BookReadListener;
import com.dgl.dao.BookRepository;
import com.dgl.model.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

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
    BookRepository bookRepository;

    @Override
    public Integer save(List<BookDTO> list) {
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(bookDTO -> {
                log.info("=================当前书籍:{}==================", JSON.toJSONString(bookDTO));
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
}
