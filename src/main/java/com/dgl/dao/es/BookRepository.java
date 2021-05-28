package com.dgl.dao.es;

import com.dgl.model.entity.es.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;



/**
 * 书籍
 */
public interface BookRepository extends ElasticsearchRepository<Book, String> {


}
