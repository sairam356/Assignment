package com.userservice.configuration;

import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoConfig {

    @Value("${spring.data.mongodb.uri}")
    private String primaryMongoUri;

    @Value("${spring.data.mongodb.database}")
    private String primaryDatabaseName;

    @Value("${spring.data.mongodb.secondary.uri}")
    private String secondaryMongoUri;

    @Value("${spring.data.mongodb.secondary.database}")
    private String secondaryDatabaseName;

    // Primary MongoDB (for user data)
    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate primaryMongoTemplate() {
        return new MongoTemplate(primaryMongoDatabaseFactory());
    }

    @Primary
    @Bean
    public MongoDatabaseFactory primaryMongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(primaryMongoUri), primaryDatabaseName);
    }

    // Secondary MongoDB (for other data)
    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() {
        return new MongoTemplate(secondaryMongoDatabaseFactory());
    }

    @Bean
    public MongoDatabaseFactory secondaryMongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(MongoClients.create(secondaryMongoUri), secondaryDatabaseName);
    }

    // Default MongoTemplate bean (linked to the primary MongoDB)
    @Bean(name = "mongoTemplate")
    public MongoTemplate defaultMongoTemplate() {
        return primaryMongoTemplate();
    }
}
