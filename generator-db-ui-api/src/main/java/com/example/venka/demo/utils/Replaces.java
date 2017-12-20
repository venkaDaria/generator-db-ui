package com.example.venka.demo.utils;

public final class Replaces {

    // UI
    public static final String TITLE = "\\[title\\]";
    public static final String PATHS = "\\[paths\\]";
    public static final String FOOTER = "\\[footer\\]";
    public static final String HEADER = "\\[header\\]";
    public static final String USERNAME = "\\[username\\]";
    public static final String PASSWORD = "\\[password\\]";
    public static final String URL = "\\[url\\]";
    public static final String PLATFORM = "\\[platform\\]";
    public static final String GROUP = "\\[group\\]";
    public static final String COMPILE = "\\[compile\\]";

    // controller
    public static final String FIELDS = "\\[fields\\]";
    public static final String CONSTRUCTOR = "\\[constructor\\]";
    public static final String CONSTRUCTOR_FIELDS = "\\[constructor\\-fields\\]";
    public static final String PARAMS = "\\[params\\]";
    public static final String PARAMS_CREATE = "\\[params\\-create\\]";
    public static final String DEPS_PARAMS = "\\[deps-params\\]";
    public static final String CREATE = "\\[create\\]";
    public static final String DEPS_STREAM = "\\[deps\\-stream\\]";
    public static final String DEPS = "\\[deps\\]";
    public static final String IMPORT = "\\[import\\]";
    public static final String IMPORT_2 = "\\[import2\\]";

    // punctuation
    public static final String TAB = "    ";
    public static final String START_TAB = TAB + TAB;
    public static final String SPACE = " ";
    public static final String EQUAL = " = ";
    public static final String STOP = ";";
    public static final String POINT = ".";

    // keywords
    public static final String FINAL = ", final ";

    private Replaces() {
        // empty
    }
}
