package com.cbaeza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

/**
 * Mongo DB config
 */
@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "pictures";
  }

  @Override
  public Mongo mongo() throws Exception {
    return new MongoClient("127.0.0.1", 27017);
  }
}
