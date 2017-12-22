package com.example.venka.demo.utils.db;

public class MongoDataBaseUtils implements DataBaseUtils {

    public String getUrl(final String dataBase, final String name) {
        return "\n" +
                "spring.data.mongodb.host=localhost\n" +
                "spring.data.mongodb.port=27017\n" +
                "spring.data.mongodb.database=" + name;
    }

    public String getPlatform(final String dataBase) {
        return "";
    }

    public String getDependencies(final String dataBase) {
        return "compile('org.springframework.boot:spring-boot-starter-data-mongodb')";
    }

    @Override
    public String replaceDataJpa() {
        return "compile group: 'org.hibernate', name: 'hibernate-core', version: '5.2.12.Final'";
    }
}
