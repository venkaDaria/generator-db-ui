package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.service.asm.AsmService;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.venka.demo.service.GeneratorFiles.getPackageDir;
import static com.example.venka.demo.utils.FileUtils.writeTo;

@Service
public class RepositoryService implements ServiceExecutor {

    private final AsmService asmService;

    public RepositoryService(final AsmService asmService) {
        this.asmService = asmService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(final File directory, final Map<String, Object> body) throws IOException {
        final File repositories = new File(directory.getPath() + "/repositories");

        final List<LinkedTreeMap<String, Object>> entities =
                (ArrayList<LinkedTreeMap<String, Object>>) body.get("entities");

        entities.forEach(entity -> {
            final String entityName = entity.get("name").toString();
            final byte[] byteCode = asmService.createRepository(entityName, getPackageDir(body));

            try {
                writeTo(repositories, entityName + "Repository", byteCode);
            } catch (final IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
