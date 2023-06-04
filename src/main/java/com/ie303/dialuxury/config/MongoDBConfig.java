package com.ie303.dialuxury.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories (basePackages = "com.ie303.dialuxury.repository")
public class MongoDBConfig {
}
