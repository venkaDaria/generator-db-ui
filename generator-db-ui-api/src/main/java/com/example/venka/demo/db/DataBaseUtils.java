package com.example.venka.demo.db;

public interface DataBaseUtils {

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
}
