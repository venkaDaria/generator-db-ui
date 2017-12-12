package com.example.venka.demo.service.res;

import com.example.venka.demo.service.res.sub.ModelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static com.example.venka.demo.service.GeneratorFiles.getPackageDir;
import static com.example.venka.demo.utils.FileUtils.replace;
import static com.example.venka.demo.utils.Paths.MAIN_PACKAGE;
import static com.example.venka.demo.utils.Paths.OLD_PACKAGE;

@Service
public class JavaCreatorService {

    private final ModelService modelService;

    private final RepositoryService repositoryService;

    private final ControllerService controllerService;

    public JavaCreatorService(
            final ModelService modelService,
            final RepositoryService repositoryService,
            final ControllerService controllerService) {
        this.modelService = modelService;
        this.repositoryService = repositoryService;
        this.controllerService = controllerService;
    }

    // TODO: model + repo
    // TODO: controller (not asm?)
    // TODO: check work
    // см. последнюю редакцию ДБ
    public void createFiles(final File source, final Map<String, Object> body) throws IOException {
        final String packageName = getPackageDir(body);
        final File directory = new File(source.getName() + MAIN_PACKAGE + packageName);

        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .forEach(path -> replacePackage(path, packageName));

        modelService.execute(directory, body);
    }

    private void replacePackage(final Path path, final String packageName) {
        try {
            replace(path, toName(OLD_PACKAGE), toName(packageName));
        } catch (final IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String toName(final String packageName) {
        return packageName.substring(1).replace("/", ".");
    }
}
