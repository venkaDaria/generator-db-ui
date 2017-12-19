package com.example.venka.demo.utils;

public final class Paths {

    public static final String PROPERTIES = "/src/main/resources/application.properties";
    /* packages */
    public static final String MAIN_PACKAGE = "/src/main/java";
    public static final String TEMP_NAME = "generator-db-ui-template-temp";
    public static final String OLD_PACKAGE_FIRST = "/com";
    public static final String OLD_PACKAGE = OLD_PACKAGE_FIRST + "/example/venka/lab5";
    public static final String OLD_PACKAGE_POINT = "com.example.venka.lab5";
    public static final String MODEL = "/model/impl";
    public static final String CRUD_REPOSITORY = "org/springframework/data/repository/CrudRepository";
    public static final String REPOSITORIES = "/repositories";
    public static final String CONTROLLERS = "/controllers/impl";
    public static final String EXAMPLE_CONTROLLER = CONTROLLERS + "/ExampleController.java";
    /* UI */
    private static final String TEMPLATES = "/src/main/resources/templates/fragments";
    public static final String FOOTER = TEMPLATES + "/footer.html";
    public static final String HEADER = TEMPLATES + "/header.html";

    private Paths() {
        // empty
    }
}
