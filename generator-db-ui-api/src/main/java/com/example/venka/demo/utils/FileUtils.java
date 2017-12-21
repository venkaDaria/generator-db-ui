package com.example.venka.demo.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class FileUtils {

    private FileUtils() {
        // empty
    }

    public static void replace(final Path path, final String regex, final String replacement) throws IOException {
        String content = new String(Files.readAllBytes(path));
        content = content.replaceAll(regex, replacement);
        Files.write(path, content.getBytes());
    }

    @NotNull
    public static File createTargetDirectory(final File source, final String name) throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(source.getName() + "-temp").toFile());
        org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(name).toFile());

        final File target = Files.createDirectory(Paths.get(name)).toFile();

        org.apache.commons.io.FileUtils.copyDirectory(source, target);
        return target;
    }

    public static void writeTo(final File directory, final String name, final byte[] bytes) throws IOException {
        final Path source = Files.createFile(Paths.get(directory.getPath() + getClassName(name)));
        Files.write(source, bytes);

        final Path target = Files.createFile(Paths.get(directory.getPath() + getJavaName(name)));
        DecompilerUtils.execute(source.toFile(), target.toFile());

        final String nonPackage = Arrays.stream(directory.getPath().split("/"))
                .limit(4).collect(Collectors.joining("/"));
        replace(target, nonPackage, "");

        Files.deleteIfExists(source);
    }

    public static String getJavaName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".java";
    }

    private static String getClassName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".class";
    }
}
