package com.example.venka.demo.service.asm;

import com.google.gson.internal.LinkedTreeMap;
import org.springframework.asm.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.venka.demo.utils.Types.STR_SUPPLIER;
import static org.springframework.asm.Opcodes.*;

@Service
public class AsmService {

    private final AsmBoundService asmBoundService;

    public AsmService(final AsmBoundService asmBoundService) {
        this.asmBoundService = asmBoundService;
    }

    public byte[] createModel(final String name, final String packageName,
                              final List<LinkedTreeMap<String, Object>> fields,
                              final List<LinkedTreeMap<String, Object>> bounds) {
        final ClassWriter cw = new ClassWriter(0);
        asmBoundService.applyClassWriter(cw);

        final String className = StringUtils.capitalize(name);
        final String fullClassName = packageName + "/" + className;

        cw.visit(V1_8, ACC_PUBLIC, fullClassName, "",
                 packageName.replace("impl", "") + "/BaseEntity;", null);

        cw.visitAnnotation("Llombok/Data;", true);
        cw.visitAnnotation("Ljavax/persistence/Entity;", true);

        final FieldVisitor fv = cw.visitField(ACC_PRIVATE, "id", Type.getDescriptor(long.class), null, null);
        fv.visitAnnotation("Ljavax/persistence/Id;", true);
        fv.visitAnnotation("Ljavax/persistence/GeneratedValue;", true)
                .visitEnum("strategy", "Ljavax/persistence/GenerationType;", "AUTO");
        fv.visitEnd();

        fields.forEach(field -> executeField(cw, fullClassName, field));

        bounds.forEach(bound -> asmBoundService.createField(className, bound));

        cw.visitEnd();
        return cw.toByteArray();
    }

    private void executeField(final ClassWriter cw, final String fullClassName, final LinkedTreeMap<String, Object> field) {
        cw.visitField(ACC_PRIVATE, field.get("name").toString(), getDescription(field),
                null, null).visitEnd();

        final Object isMain = field.get("isMain");
        if (isMain != null && Boolean.valueOf(isMain.toString())) {
            createMethods(cw, fullClassName, field);
        }
    }

    private void createMethods(final ClassWriter cw, final String fullClassName, final LinkedTreeMap<String, Object> field) {
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "getName", STR_SUPPLIER, null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, fullClassName, field.get("name").toString(), getDescription(field));
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        mv = cw.visitMethod(ACC_PUBLIC, "toString", STR_SUPPLIER, null, null);
        mv.visitAnnotation("Ljava/lang/annotation/Override;", true);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitFieldInsn(GETFIELD, fullClassName, field.get("name").toString(), getDescription(field));
        mv.visitInsn(IRETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
    }

    private String getDescription(final LinkedTreeMap<String, Object> field) {
        switch (field.get("dataType").toString()) {
            case "Integer":
                return Type.getDescriptor(int.class);
            case "Double":
                return Type.getDescriptor(double.class);
            case "LocalDate":
                return Type.getDescriptor(LocalDate.class);
            case "LocalDateTime":
                return Type.getDescriptor(LocalDateTime.class);
            case "String":
            default:
                return Type.getDescriptor(String.class);
        }
    }
}
