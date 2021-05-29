package com.dgl.controller;

import com.dgl.model.dto.BookDTO;
import com.dgl.model.dto.BookListDTO;
import com.dgl.service.BookService;
import com.dgl.service.RoleService;
import com.dgl.model.entity.Role;
import com.dgl.model.vo.RespEntity;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "测试插入数据")
    @PostMapping(value = "/testBatchInsert")
    public RespEntity testBatchInsert() {
        Integer result=bookService.testBatchInsert();
        return new RespEntity(result);
    }


    @ApiOperation(value = "新增/编辑图书")
    @PostMapping(value = "/save")
    public RespEntity save(BookDTO bookDTO) {
        List<BookDTO> list=new ArrayList<>();
        list.add(bookDTO);
        String result=bookService.save(list);
        return new RespEntity(result);
    }

    @ApiOperation(value = "删除图书")
    @DeleteMapping(value = "/deleteById")
    @ApiImplicitParam(name = "id",value = "图书id",required = true)
    public RespEntity deleteById(String id) {
        Integer result=bookService.deleteById(id);
        return new RespEntity(result);
    }

    @ApiOperation(value = "书籍详情")
    @GetMapping(value = "/findById")
    @ApiImplicitParam(name = "id",value = "图书id",required = true)
    public RespEntity<BookDTO> findById(String id) {
        BookDTO result=bookService.findById(id);
        return new RespEntity(result);
    }

    @ApiOperation(value = "书籍列表-单字符串全文查询")
    @GetMapping(value = "/bookList1")
    public RespEntity<Page<BookDTO>> bookList1(BookListDTO bookListDTO) {
        Page<BookDTO> result=bookService.bookList(bookListDTO);
        return new RespEntity(result);
    }

//    @ApiOperation(value = "书籍列表-指定字段模糊查询")
//    @GetMapping(value = "/bookList2")
//    public RespEntity<Page<BookDTO>> bookList2(BookListDTO bookListDTO) {
//        Page<BookDTO> result=bookService.bookList2(bookListDTO);
//        return new RespEntity(result);
//    }
//
//    @ApiOperation(value = "书籍列表-指定字段短语匹配")
//    @GetMapping(value = "/bookList3")
//    public RespEntity<Page<BookDTO>> bookList3(BookListDTO bookListDTO) {
//        Page<BookDTO> result=bookService.bookList3(bookListDTO);
//        return new RespEntity(result);
//    }
//
//    @ApiOperation(value = "书籍列表-完全匹配查询")
//    @GetMapping(value = "/bookList4")
//    public RespEntity<Page<BookDTO>> bookList4(BookListDTO bookListDTO) {
//        Page<BookDTO> result=bookService.bookList4(bookListDTO);
//        return new RespEntity(result);
//    }
//
//    @ApiOperation(value = "书籍列表-多字段匹配某一个值")
//    @GetMapping(value = "/bookList5")
//    public RespEntity<Page<BookDTO>> bookList5(BookListDTO bookListDTO) {
//        Page<BookDTO> result=bookService.bookList5(bookListDTO);
//        return new RespEntity(result);
//    }

    @ApiOperation(value = "删除所有")
    @DeleteMapping(value = "/deleteAll")
    public RespEntity<Integer> deleteAll() {
        Integer result=bookService.deleteAll();
        return new RespEntity(result);
    }

    @ApiOperation(value = "获取总记录数")
    @GetMapping(value = "/countAll")
    public RespEntity<Integer> countAll() {
        Integer result=bookService.countAll();
        return new RespEntity(result);
    }

    @ApiOperation(value = "批量导入图书",notes = "阿里的easyExcel导入")
    @PostMapping(value = "/importBook",consumes = "multipart/*", headers = "content-type=multipart/form-data")
    public RespEntity importBook(@RequestParam("file") MultipartFile file) {
        Integer result=bookService.importBook(file);
        return new RespEntity(result);
    }


}
