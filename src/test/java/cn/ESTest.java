//package cn;
//
//import lombok.SneakyThrows;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
///**
// * @ClassName ESTest
// * @Description TODO
// * @Author DGL
// * @Date 2021/5/30  19:21
// **/
//@SpringBootTest
//public class ESTest {
//
//    @Autowired
//    RestHighLevelClient restHighLevelClient;
//
//    @Test
//    public void testCreateIndex() throws IOException {
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("index_movie");
//        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        System.err.println(createIndexResponse);
//    }
//}
