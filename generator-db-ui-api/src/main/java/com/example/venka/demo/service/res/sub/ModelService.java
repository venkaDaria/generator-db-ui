package com.example.venka.demo.service.res.sub;

import com.example.venka.demo.service.asm.AsmService;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.venka.demo.utils.FileUtils.writeTo;
import static com.example.venka.demo.utils.JsonMapper.filterBounds;
import static com.example.venka.demo.utils.JsonMapper.filterFields;
import static com.example.venka.demo.utils.Paths.MODEL;

@Service
public class ModelService implements ServiceExecutor {

    private final AsmService asmService;

    public ModelService(final AsmService asmService) {
        this.asmService = asmService;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(File directory, Map<String, Object> body) throws IOException {
        final File model = new File(directory.getPath() + MODEL);

        final List<LinkedTreeMap<String, Object>> entities = (ArrayList<LinkedTreeMap<String, Object>>) body.get("entities");

        entities.forEach(entity -> {
            final String entityName = entity.get("name").toString();
            byte[] byteCode = asmService.createModel(entityName, model.getPath(), filterFields(body, entityName),
                    filterBounds(body, entityName));

            try {
                writeTo(model, entityName, byteCode);
            } catch (final IOException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }
}
