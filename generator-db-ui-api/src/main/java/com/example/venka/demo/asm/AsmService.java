package com.example.venka.demo.asm;

import org.objectweb.asm.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

@Component
public class AsmService {

    // create files with property .name
    // rename packages-directories
    // см. последнюю редакцию ДБ
    public void createFiles(final File directory, final Map<String, String> body) {
        final String packageName = body.get("packageName");

        // get entities, bounds
        ClassWriter cw = new ClassWriter(0);

        byte[] bytecode = cw.toByteArray();
    }
}
