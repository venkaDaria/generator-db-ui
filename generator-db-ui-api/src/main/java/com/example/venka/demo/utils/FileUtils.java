package com.example.venka.demo.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {

    private static final String PATH_TO_PACKAGE = "generator-db-ui-template-temp.src.main.java.";

    private FileUtils() {
        // empty
    }

    public static void replace(final Path path, final String regex, final String replacement) throws IOException {
        String content = new String(Files.readAllBytes(path));
        content = content.replaceAll(regex, replacement);
        Files.write(path, content.getBytes());
    }

    @NotNull
    public static File createTargetDirectory(File source) throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(java.nio.file.Paths.get(source.getName() + "-temp").toFile());
        final File target = Files.createDirectory(Paths.get(source.getName() + "-temp")).toFile();
        org.apache.commons.io.FileUtils.copyDirectory(source, target);
        return target;
    }

    public static void writeTo(final File directory, final String name, byte[] bytes) throws IOException {
        final Path source = Files.createFile(Paths.get(directory.getPath() + getClassName(name)));
        Files.write(source, bytes);

        final Path target = Files.createFile(Paths.get(directory.getPath() + getJavaName(name)));
        DecompilerUtils.execute(source.toFile(), target.toFile());

        replace(target, PATH_TO_PACKAGE, "");

        Files.deleteIfExists(source);
    }

    public static String getJavaName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".java";
    }

    private static String getClassName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".class";
    }
}
