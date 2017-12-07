package com.example.venka.demo.utils;

import org.springframework.stereotype.Component;

@Component
public class DataBaseUtils {

    public String getUrl(final String dataBase, final String name) {
        switch (dataBase) {
            case "MySQL":
                return "spring.datasource.url=jdbc:mysql://localhost/" + name;
            case "MongoDB":
                return "\n" +
                        "spring.data.mongodb.host=localhost\n" +
                        "spring.data.mongodb.port=27017\n" +
                        "spring.data.mongodb.database=" + name;
            default:
                return "";
        }
    }

    public String getPlatform(final String dataBase) {
        switch (dataBase) {
            case "MySQL":
                return "spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect";
            case "MongoDB":
            default:
                return "";
        }
    }

    public String getDependencies(final String dataBase) {
        switch (dataBase) {
            case "MySQL":
                return "compile group: 'mysql', name: 'mysql-connector-java', version: '5.1.6'";
            case "MongoDB":
                return "compile('org.springframework.boot:spring-boot-starter-data-mongodb')";
            default:
                return "";
        }
    }
}
