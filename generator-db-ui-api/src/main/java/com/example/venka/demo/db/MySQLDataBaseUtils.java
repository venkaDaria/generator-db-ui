package com.example.venka.demo.db;

public class MySQLDataBaseUtils implements DataBaseUtils {

    @Override
    public String getUrl(final String dataBase, final String name) {
        return "spring.datasource.url=jdbc:mysql://localhost/" + name;
    }

    @Override
    public String getPlatform(final String dataBase) {
        return "spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect";
    }

    @Override
    public String getDependencies(final String dataBase) {
        return "compile group: 'mysql', name: 'mysql-connector-java', version: '5.1.6'";
    }
}
