package com.example.venka.demo.service.asm;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.FieldVisitor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.springframework.asm.Opcodes.ACC_PRIVATE;
import static org.springframework.asm.Opcodes.ACC_PUBLIC;
import static org.springframework.asm.Opcodes.V1_8;

@Service
public class AsmService {

    public byte[] createModel(final String name, final String packageName,
                              final List<LinkedTreeMap<String, Object>> fields,
                              final List<LinkedTreeMap<String, Object>> bounds) {
        final String className = StringUtils.capitalize(name);

        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC, packageName + "/" + className, "",
                 packageName.replace("impl", "") + "/BaseEntity;", null);

        cw.visitAnnotation("Llombok/Data;", true);
        cw.visitAnnotation("Ljavax/persistence/Entity;", true);

        FieldVisitor fv = cw.visitField(ACC_PRIVATE, "id", "J", null, null);
        fv.visitAnnotation("Ljavax/persistence/Id;", true);
        fv.visitAnnotation("Ljavax/persistence/GeneratedValue;", true)
                .visitEnum("strategy", "Ljavax/persistence/GenerationType;", "AUTO");
        fv.visitEnd();

        cw.visitEnd();
        return cw.toByteArray();
    }
}
