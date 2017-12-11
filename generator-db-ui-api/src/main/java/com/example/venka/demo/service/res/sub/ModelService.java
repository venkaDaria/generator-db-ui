package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.service.asm.AsmService;
import com.example.venka.demo.utils.DecompilerUtils;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.venka.demo.utils.FileUtils.replace;

@Service
public class ModelService {

    private final AsmService asmService;

    public ModelService(final AsmService asmService) {
        this.asmService = asmService;
    }

    @SuppressWarnings("unchecked")
    public void execute(File directory, Map<String, Object> body) throws IOException {
        final File model = new File(directory.getPath() + "/model/impl");

        final List<LinkedTreeMap<String, Object>> entities = (ArrayList<LinkedTreeMap<String, Object>>) body.get("entities");

        entities.forEach(entity -> {
            final String entityName = entity.get("name").toString();
            byte[] bytecode = asmService.createModel(
                    entityName,
                    model.getPath(),
                    filterFields(body, entityName),
                    filterBounds(body, entityName)
            );

            try {
                writeToModel(model, entityName, bytecode);
            } catch (final IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    private void writeToModel(final File model, final String name, byte[] bytes) throws IOException {
        final Path source = Files.createFile(Paths.get(model.getPath() + getClassName(name)));
        Files.write(source, bytes);

        final Path target = Files.createFile(Paths.get(model.getPath() + getJavaName(name)));
        DecompilerUtils.execute(source.toFile(), target.toFile());

        replace(target, "generator-db-ui-template-temp.src.main.java.", "");

        Files.deleteIfExists(source);
    }

    private String getJavaName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".java";
    }

    private String getClassName(final String name) {
        return "/" + StringUtils.capitalize(name) + ".class";
    }

    @SuppressWarnings("unchecked")
    private List<LinkedTreeMap<String, Object>> filterBounds(final Map<String, Object> body, final String name) {
        return ((ArrayList<LinkedTreeMap<String, Object>>) body.get("bounds"))
                .stream()
                .filter(field -> field.get("option1").equals(name) || field.get("option2").equals(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @SuppressWarnings("unchecked")
    private ArrayList<LinkedTreeMap<String, Object>> filterFields(final Map<String, Object> body, final String name) {
        return ((ArrayList<LinkedTreeMap<String, Object>>) body.get("fields"))
                .stream()
                .filter(field -> field.get("parent").equals(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
