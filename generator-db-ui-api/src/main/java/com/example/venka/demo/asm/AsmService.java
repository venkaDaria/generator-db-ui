package com.example.venka.demo.asm;

import org.springframework.asm.ClassWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

import static com.example.venka.demo.app.GeneratorFiles.getPackageDir;

@Component
public class AsmService {

    // create files with property .name
    // см. последнюю редакцию ДБ
    public void createFiles(final File directory, final Map<String, String> body) {
        final String packageName = getPackageDir(body);
        final File file = new File(directory.getPath() + packageName);


        // get entities, bounds
        ClassWriter cw = new ClassWriter(0);

        byte[] bytecode = cw.toByteArray();
    }
}
