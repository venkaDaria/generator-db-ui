package com.example.venka.demo.service.res;

import com.example.venka.demo.service.res.sub.ModelService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.example.venka.demo.service.GeneratorFiles.getPackageDir;
import static com.example.venka.demo.utils.Paths.MAIN_PACKAGE;

@Service
public class JavaCreatorService {

    private final ModelService modelService;

    public JavaCreatorService(final ModelService modelService) {
        this.modelService = modelService;
    }

    // TODO: model (create files with property .name?)
    // TODO: repo
    // TODO: controller
    // TODO: check work
    // см. последнюю редакцию ДБ
    public void createFiles(final File source, final Map<String, Object> body) throws IOException {
        final String packageName = getPackageDir(body);
        final File directory = new File(source.getName() + MAIN_PACKAGE + packageName);

        modelService.execute(directory, body);
    }
}
