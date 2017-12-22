package com.example.venka.demo.utils.db;

public interface DataBaseUtils {
    // gradle
    String JPA = "compile('org.springframework.boot:spring-boot-starter-data-jpa')";

    static DataBaseUtils getInstance(final String dataBase) {
        switch (dataBase) {
            case "MySQL":
                return new MySQLDataBaseUtils();
            case "MongoDB":
                return new MongoDataBaseUtils();
            default:
                return null;
        }
    }

    String getUrl(final String dataBase, final String name);

    String getPlatform(final String dataBase);

    String getDependencies(final String dataBase);

    default String replaceDataJpa() {
        return JPA;
    }
}
