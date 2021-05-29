package com.dgl.service;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.dgl.common.Enum.EnumBookType;
import com.dgl.common.Enum.EnumPress;
import com.dgl.common.utils.DGLUtils;
import com.dgl.dao.es.BookRepository;
import com.dgl.model.dto.BookDTO;
import com.dgl.model.entity.es.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName AsyncServiceImpl
 * @Description TODO
 * @Author DGL
 * @Date 2021/5/28  21:27
 **/
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    BookRepository bookRepo;

    @Override
    public void batchInsert() {
        List<Book> list = new LinkedList<>();
        for (int i = 0; i < 5000000; i++) {
            BookDTO dto = BookDTO.builder().author(DGLUtils.getChineseName()).bookImage("").
                    bookName("<<" + DGLUtils.getChineseName() + DGLUtils.getChineseName() + ">>").
                    bookPages(RandomUtil.randomInt(10, 50000)).
                    bookType(EnumBookType.getByRandom().getType()).
                    press(EnumPress.getByRandom().getValue()).
                    price(String.valueOf(RandomUtil.randomInt(1, 10000))).build();
            Book book = new Book(dto);
            list.add(book);
            log.info("当前书籍:{}", JSON.toJSONString(dto));
            if (list.size()==500){
                bookRepo.saveAll(list);
                list.clear();
                continue;
            }
            if (list.size()>0){
                bookRepo.saveAll(list);
                list.clear();
            }
        }
    }
}
