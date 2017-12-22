package com.example.venka.demo.service.res;

import com.example.venka.demo.utils.Replaces;
import com.example.venka.demo.utils.db.DataBaseUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.venka.demo.service.GeneratorFiles.getPackageDir;
import static com.example.venka.demo.utils.FileUtils.replace;
import static com.example.venka.demo.utils.Paths.FOOTER;
import static com.example.venka.demo.utils.Paths.HEADER;
import static com.example.venka.demo.utils.Paths.MAIN_PACKAGE;
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE;
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE_FIRST;
import static com.example.venka.demo.utils.Paths.PROPERTIES;

@Service
public class TemplateService {
    private DataBaseUtils dataBaseUtils;

    private Map<String, Object> body;
    private File resultFile;

    public void changeFiles(final File resultFile, final Map<String, Object> body) throws IOException {
        this.resultFile = resultFile;
        this.body = body;

        dataBaseUtils = DataBaseUtils.getInstance(body.get("dataBase").toString());

        setMetadata();
        changeTemplates();
        changeDirectories();
    }

    private void changeDirectories() throws IOException {
        final String sourcePackage = resultFile.getName() + MAIN_PACKAGE;
        final File temporary = new File("temp");

        FileUtils.copyDirectory(new File(sourcePackage + OLD_PACKAGE), temporary);
        deleteOldPackage(sourcePackage);

        final String packageName = getPackageDir(body);
        final File target = Files.createDirectories(Paths.get(sourcePackage + packageName)).toFile();

        FileUtils.copyDirectory(temporary, target);
        FileUtils.deleteDirectory(temporary);
    }

    private void deleteOldPackage(final String sourcePackage) throws IOException {
        FileUtils.deleteDirectory(new File(sourcePackage + OLD_PACKAGE_FIRST));
    }

    private void changeTemplates() throws IOException {
        final String footer = String.format("(c) %s, %s", body.get("userName"), LocalDate.now().getYear());
        final String header = body.get("name") + " DB UI";

        final File fileFooter = new File(resultFile.getPath() + FOOTER);
        final File fileHeader = new File(resultFile.getPath() + HEADER);

        replace(fileFooter.toPath(), Replaces.FOOTER, footer);
        replace(fileHeader.toPath(), Replaces.HEADER, header);
    }

    private void setMetadata() throws IOException {
        final String groupId = body.get("groupId").toString();
        final String artifactId = body.get("artifactId").toString();
        final String userName = body.get("userName").toString();
        final String password = body.get("password").toString();
        final String dataBase = body.get("dataBase").toString();

        final File file = new File(resultFile.getPath() + PROPERTIES);
        final Path path = file.toPath();
        replace(path, Replaces.USERNAME, userName);
        replace(path, Replaces.PASSWORD, password);
        replace(path, Replaces.URL, dataBaseUtils.getUrl(dataBase, artifactId));
        replace(path, Replaces.PLATFORM, dataBaseUtils.getPlatform(dataBase));

        final Path pathGradle = new File(resultFile.getPath() + "/build.gradle").toPath();
        replace(pathGradle, Replaces.GROUP, "'" + groupId + "'");
        replace(pathGradle, Replaces.COMPILE, dataBaseUtils.getDependencies(dataBase));
        replace(pathGradle, Pattern.quote(DataBaseUtils.JPA), dataBaseUtils.replaceDataJpa());
    }
}
