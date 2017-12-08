package com.example.venka.demo.app;

import com.example.venka.demo.asm.AsmService;
import com.example.venka.demo.db.DataBaseUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import static com.example.venka.demo.utils.Paths.FOOTER;
import static com.example.venka.demo.utils.Paths.HEADER;
import static com.example.venka.demo.utils.Paths.PROPERTIES;

@Component
public class GeneratorFiles {

    private DataBaseUtils dataBaseUtils;

    private final AsmService asmService;

    public GeneratorFiles(final AsmService asmService) {
        this.asmService = asmService;
    }

    @NotNull
    private static File createTargetDirectory(File source) throws IOException {
        FileUtils.deleteDirectory(Paths.get(source.getName() + "-temp").toFile());
        final File target = Files.createDirectory(Paths.get("generator-db-ui-template-temp")).toFile();
        FileUtils.copyDirectory(source, target);
        return target;
    }

    public File get(final Map<String, String> body) throws IOException {
        final File source = new File("generator-db-ui-template");
        final File target = createTargetDirectory(source);

        dataBaseUtils = DataBaseUtils.getInstance(body.get("dataBase"));

        setMetadata(target, body);
        changeTemplates(target, body);

        asmService.createFiles(target, body);

        return target;
    }

    private void changeTemplates(File directory, Map<String, String> body) throws IOException {
        final String footer = String.format("(c) %s, %s", body.get("userName"), LocalDate.now().getYear());
        final String header = body.get("name") + " DB UI";

        final File fileFooter = new File(directory.getPath() + FOOTER);
        final File fileHeader = new File(directory.getPath() + HEADER);

        replace(fileFooter.toPath(), "\\[footer\\]", footer);
        replace(fileHeader.toPath(), "\\[header\\]", header);
    }

    private void setMetadata(final File directory, final Map<String, String> body) throws IOException {
        final String groupId = body.get("groupId");
        final String artifactId = body.get("artifactId");
        final String userName = body.get("userName");
        final String password = body.get("password");
        final String dataBase = body.get("dataBase");

        final File file = new File(directory.getPath() + PROPERTIES);
        final Path path = file.toPath();
        replace(path, "\\[username\\]", userName);
        replace(path, "\\[password\\]", password);
        replace(path, "\\[url\\]", dataBaseUtils.getUrl(dataBase, artifactId));
        replace(path, "\\[platform\\]", dataBaseUtils.getPlatform(dataBase));

        final Path pathGradle = new File(directory.getPath() + "/build.gradle").toPath();
        replace(pathGradle, "\\[group\\]", "'" + groupId + "'");
        replace(pathGradle, "\\[compile\\]", dataBaseUtils.getDependencies(dataBase));
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
