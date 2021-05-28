package com.dgl.controller;

import com.dgl.model.dto.BookDTO;
import com.dgl.service.BookService;
import com.dgl.service.RoleService;
import com.dgl.model.entity.Role;
import com.dgl.model.vo.RespEntity;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@RequestMapping(value = "/book")
@RestController
@Api(tags = {"书籍管理"})
@ApiSupport(order = 3, author = "杜光磊")
public class BookController {

    @Autowired
    BookService bookService;


    @ApiOperation(value = "新增")
    @PostMapping(value = "/save")
    public RespEntity save(BookDTO bookDTO) {
        List<BookDTO> list=new ArrayList<>();
        list.add(bookDTO);
        Integer result=bookService.save(list);
        return new RespEntity(result);
    }

    @ApiOperation(value = "批量导入图书",notes = "阿里的easyExcel导入")
    @PostMapping(value = "/importBook",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public RespEntity importBook(@RequestParam("file") MultipartFile file) {
        Integer result=bookService.importBook(file);
        return new RespEntity(result);
    }


}
