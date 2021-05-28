package com.dgl.dao;

import com.dgl.model.entity.Book;
import com.dgl.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 书籍
 */
@Service
public interface BookRepository extends JpaRepository<Book, Integer> {


}
