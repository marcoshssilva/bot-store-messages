package br.com.marcoshssilva.botstoremessages;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class MongoDbIntegrationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void test() {
        DBObject data = BasicDBObjectBuilder.start().add("data", "value").get();
        mongoTemplate.save(data, "collection");
        assertThat(mongoTemplate.findAll(DBObject.class, "collection")).extracting("data").containsOnly("value");
    }

}
