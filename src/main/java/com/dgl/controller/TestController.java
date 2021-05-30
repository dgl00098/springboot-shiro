package com.dgl.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.dgl.model.entity.es.People;
import com.dgl.model.vo.RespEntity;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping(value = "/esTest")
@RestController
@Api(tags = {"ES高级客户端测试"})
@ApiSupport(order = 4, author = "杜光磊")
public class TestController {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @ApiOperation(value = "创建索引")
    @PostMapping(value = "/createIndex")
    @ApiImplicitParam(name = "index",value = "索引名字",required = true)
    public RespEntity createIndex(String index) throws IOException {
        //创建索引请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.err.println(createIndexResponse);
        return new RespEntity(createIndexResponse.toString());
    }

    @ApiOperation(value = "判断索引是否存在")
    @GetMapping(value = "/isExist")
    @ApiImplicitParam(name = "index",value = "索引名字",required = true)
    public RespEntity isExist(String index) throws IOException {
        //创建索引请求
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        System.err.println(exists);
        return new RespEntity(exists);
    }

    @ApiOperation(value = "删除索引")
    @DeleteMapping(value = "/deleteIndex")
    @ApiImplicitParam(name = "index",value = "索引名字",required = true)
    public RespEntity deleteIndex(String index) throws IOException {
        //创建索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.err.println(delete);
        return new RespEntity(delete);
    }

    @ApiOperation(value = "创建文档")
    @PostMapping(value = "/createDocument")
    @ApiImplicitParam(name = "index",value = "索引名字",required = true)
    public RespEntity createDocument(String index) throws IOException {
        //创建对象
        People people = new People("张三",30);
        //创建请求
        IndexRequest request = new IndexRequest(index);
        //将数据转换成json,放入请求
        request.source(JSON.toJSONString(people), XContentType.JSON);
        //客户端发送请求,得到相应结果
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return new RespEntity(indexResponse);
    }

    @ApiOperation(value = "查询文档详情")
    @GetMapping(value = "/findDocumentById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index",value = "索引名字",required = true),
            @ApiImplicitParam(name = "id",value = "文档id",required = true)
    })
    public RespEntity findDocumentById(String index,String id) throws IOException {
        //创建索引请求
        GetRequest request = new GetRequest(index,id);
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.err.println(getResponse.getSource());
        return new RespEntity(getResponse.getSource());
    }

    @ApiOperation(value = "更新文档")
    @PostMapping(value = "/updateDocumentById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index",value = "索引名字",required = true),
            @ApiImplicitParam(name = "id",value = "文档id",required = true)
    })
    public RespEntity updateDocumentById(String index,String id) throws IOException {
        //创建索引请求
        UpdateRequest request = new UpdateRequest(index,id);
        People people = new People("杜光磊",18);
        request.doc(JSON.toJSONString(people),XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.err.println(updateResponse);
        return new RespEntity(updateResponse);
    }

    @ApiOperation(value = "删除文档")
    @DeleteMapping(value = "/deleteDocument")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index",value = "索引名字",required = true),
            @ApiImplicitParam(name = "id",value = "文档id",required = true)
    })
    public RespEntity deleteDocument(String index,String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index,id);
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return new RespEntity(deleteResponse);
    }


    @ApiOperation(value = "批量插入文档")
    @PostMapping(value = "/batchInsertDocument")
    @ApiImplicitParam(name = "index",value = "索引名字",required = true)
    public RespEntity batchInsertDocument(String index) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 100; i++) {
            //创建对象
            String name1="张三";
            String name2="李四";
            People people = new People((i % 2==0)?name1+(i+1):name2+(i+1), RandomUtil.randomInt(10,100));
            bulkRequest.add(new IndexRequest(index).id(""+(i+1)).source(JSON.toJSONString(people),XContentType.JSON));
        }
        //客户端发送请求,得到相应结果
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return new RespEntity(bulkResponse);
    }

    @ApiOperation(value = "查询文档")
    @GetMapping(value = "/findDocument")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index",value = "索引名字",required = true),
            @ApiImplicitParam(name = "keyword",value = "关键字",required = true)
    })
    public RespEntity findDocument(String index,String keyword) throws IOException {
        //创建搜索请求
        SearchRequest searchRequest = new SearchRequest(index);
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //TermQueryBuilder queryTermBuilder = QueryBuilders.termQuery("name", keyword);//分词精确查询
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("name", keyword);//分词匹配查询
        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        //执行查询
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = search.getHits();
        return new RespEntity(hits.getHits());
    }


}
