package com.example.venka.demo.service.asm;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.asm.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static jdk.internal.org.objectweb.asm.Opcodes.GETFIELD;
import static org.springframework.asm.Opcodes.ACC_PRIVATE;
import static org.springframework.asm.Opcodes.ACC_PUBLIC;
import static org.springframework.asm.Opcodes.RETURN;
import static org.springframework.asm.Opcodes.V1_8;

@Service
public class AsmService {

    public byte[] createModel(final String name, final String packageName,
                              final List<LinkedTreeMap<String, Object>> fields,
                              final List<LinkedTreeMap<String, Object>> bounds) {
        final String className = StringUtils.capitalize(name);

        final ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC, packageName + "/" + className, "",
                 packageName.replace("impl", "") + "/BaseEntity;", null);

        cw.visitAnnotation("Llombok/Data;", true);
        cw.visitAnnotation("Ljavax/persistence/Entity;", true);

        final FieldVisitor fv = cw.visitField(ACC_PRIVATE, "id", "J", null, null);
        fv.visitAnnotation("Ljavax/persistence/Id;", true);
        fv.visitAnnotation("Ljavax/persistence/GeneratedValue;", true)
                .visitEnum("strategy", "Ljavax/persistence/GenerationType;", "AUTO");
        fv.visitEnd();

        fields.forEach(field -> {
            cw.visitField(ACC_PRIVATE, field.get("name").toString(), getDescription(field),
                    null, null).visitEnd();

            if (Boolean.valueOf(field.get("mainField").toString())) {
                createMethods(packageName, cw, field);
            }
        });

        cw.visitEnd();
        return cw.toByteArray();
    }

    private void createMethods(final String packageName, final ClassWriter cw, final LinkedTreeMap<String, Object> field) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getName", "(V;)Ljava/lang/String;",
                null, null);
        mv.visitCode();
        mv.visitInsn(RETURN);
        mv.visitFieldInsn(GETFIELD, packageName, field.get("name").toString(), getDescription(field));
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC, "toString", "(V;)Ljava/lang/String;",
                null, null);
        mv.visitAnnotation("Ljava/lang/annotationOverride;", true);
        mv.visitCode();
        mv.visitInsn(RETURN);
        mv.visitFieldInsn(GETFIELD, packageName, field.get("name").toString(), getDescription(field));
        mv.visitEnd();
    }

    private String getDescription(final LinkedTreeMap<String, Object> field) {
        switch (field.get("dataType").toString()) {
            case "Integer":
                return "I";
            case "Double":
                return "D";
            case "LocalDate":
                return "Ljava/time/LocalDate;";
            case "LocalDateTime":
                return "Ljava/time/LocalDateTime;";
            case "String":
            default:
                return "Ljava/lang/String;";
        }
    }
}
