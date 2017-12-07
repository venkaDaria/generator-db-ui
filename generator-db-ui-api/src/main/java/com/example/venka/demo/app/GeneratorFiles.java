package com.example.venka.demo.app;

import com.example.venka.demo.utils.DataBaseUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class GeneratorFiles {

    private final DataBaseUtils dataBaseUtils;

    public GeneratorFiles(final DataBaseUtils dataBaseUtils) {
        this.dataBaseUtils = dataBaseUtils;
    }

    public File get(final Map<String, String> body) throws IOException {
        final File source = new File("generator-db-ui-template");
        final File target = createTargetDirectory(source);

        setMetadata(target, body);
        // change templates
        // create files

        return target;
    }

    @NotNull
    private static File createTargetDirectory(File source) throws IOException {
        Files.deleteIfExists(Paths.get(source.getName() + "-temp"));
        final File target =  Files.createDirectory(Paths.get("generator-db-ui-template-temp")).toFile();
        FileUtils.copyDirectory(source, target);
        return target;
    }

    private void setMetadata(final File directory, final Map<String, String> body) throws IOException {
        final String groupId = body.get("groupId");
        final String artifactId = body.get("artifactId");
        final String name = body.get("name");
        final String packageName = body.get("packageName");
        final String javaVersion = body.get("javaVersion");
        final String userName = body.get("userName");
        final String password = body.get("password");
        final String dataBase = body.get("dataBase");

        final File file = new File(directory.getPath() + "/src/main/resources/application.properties");
        final Path path = file.toPath();
        replace(path, "\\[username\\]", userName);
        replace(path, "\\[password\\]", password);
        replace(path, "\\[url\\]", dataBaseUtils.getUrl(dataBase, name));
        replace(path, "\\[platform\\]", dataBaseUtils.getPlatform(dataBase));
    }

    private void replace(final Path path, final String regex, final String replacement) throws IOException {
        String content = new String(Files.readAllBytes(path));
        content = content.replaceAll(regex, replacement);
        Files.write(path, content.getBytes());
    }

    public String getName(final Map<String, String> body) {
        return body.get("name");
    }
}
