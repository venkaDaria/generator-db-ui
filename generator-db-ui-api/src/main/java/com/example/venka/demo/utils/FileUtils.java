package com.example.venka.demo.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    public static File createTargetDirectory(File source) throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(java.nio.file.Paths.get(source.getName() + "-temp").toFile());
        final File target = Files.createDirectory(Paths.get(source.getName() + "-temp")).toFile();
        org.apache.commons.io.FileUtils.copyDirectory(source, target);
        return target;
    }
}
