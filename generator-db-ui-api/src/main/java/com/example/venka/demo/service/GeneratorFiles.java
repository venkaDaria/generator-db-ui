package com.example.venka.demo.service;

import com.example.venka.demo.service.res.JavaCreatorService;
import com.example.venka.demo.service.res.TemplateService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.example.venka.demo.utils.FileUtils.createTargetDirectory;

@Service
public class GeneratorFiles {

    private final TemplateService templateService;
    private final JavaCreatorService javaCreatorService;

    public GeneratorFiles(final TemplateService templateService, final JavaCreatorService javaCreatorService) {
        this.templateService = templateService;
        this.javaCreatorService = javaCreatorService;
    }

    public static String getPackageDir(Map<String, Object> body) {
        return "/" + body.get("packageName").toString().replace(".", "/");
    }

    public File get(final Map<String, Object> body) throws IOException {
        final File file = createTargetDirectory(new File("generator-db-ui-template"));

        templateService.changeFiles(file, body);
        javaCreatorService.createFiles(file, body);

        return file;
    }
}
