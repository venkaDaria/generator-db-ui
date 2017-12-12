package com.example.venka.demo.service.asm;

import com.google.gson.internal.LinkedTreeMap;
import jdk.internal.org.objectweb.asm.Type;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.FieldVisitor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static org.springframework.asm.Opcodes.ACC_PRIVATE;

@Service
public class AsmBoundService {

    public static final String ID = "_id";
    private ClassWriter cw;

    private String optionName;

    public void applyClassWriter(final ClassWriter cw) {
        this.cw = cw;
    }

    public boolean applyOption(String className, LinkedTreeMap<String, Object> bound) {
        if (Objects.equals(bound.get("option1"), className)) {
            optionName = bound.get("option2").toString();
            return true;
        }
        if (Objects.equals(bound.get("option2"), className)) {
            optionName = bound.get("option1").toString();
            return true;
        }
        return false;
    }

    public void createField(final LinkedTreeMap<String, Object> bound) {
        switch (bound.get("bind").toString()) {
            case "0":
                oneToOneCreate(bound);
                break;
            case "1":
                oneToManyCreate(bound);
                break;
            case "2":
                manyToOneCreate(bound);
                break;
            case "3":
                manyToManyCreate(bound);
                break;
        }
    }

    // TODO:

    private void manyToManyCreate(final LinkedTreeMap<String, Object> bound) {
        final FieldVisitor fv;

        if (Objects.equals(bound.get("option2"), optionName)) {
            fv = cw.visitField(ACC_PRIVATE, optionName + "Set", Type.getDescriptor(HashSet.class),
                    StringUtils.capitalize(optionName), null);
            fv.visitAnnotation("Ljavax/persistence/ManyToMany;", true)
                    .visit("mappedBy", bound.get("option1") + "Set");
        } else {
            fv = cw.visitField(ACC_PRIVATE, optionName, StringUtils.capitalize(optionName),
                    null, null);
            fv.visitAnnotation("Ljavax/persistence/ManyToOne;", true);
            AnnotationVisitor av = fv.visitAnnotation("Ljavax/persistence/JoinTable;", true);
            av.visit("name", optionName + bound.get("option2"));
            av.visitAnnotation("joinColumns", "Ljavax/persistence/JoinColumn;")
                    .visit("name", optionName + ID);
            av.visitAnnotation("inverseJoinColumns", "Ljavax/persistence/JoinColumn;")
                    .visit("name", bound.get("option2") + ID);
        }

        fv.visitEnd();
    }

    private void oneToManyCreate(final LinkedTreeMap<String, Object> bound) {
        oneToManyCreate(bound, x -> x);
    }

    private void manyToOneCreate(final LinkedTreeMap<String, Object> bound) {
        oneToManyCreate(bound, x -> !x);
    }

    private void oneToManyCreate(final LinkedTreeMap<String, Object> bound, final Predicate<Boolean> reversed) {
        final FieldVisitor fv;

        if (reversed.test(Objects.equals(bound.get("option2"), optionName))) {
            fv = cw.visitField(ACC_PRIVATE, optionName + "Set", Type.getDescriptor(HashSet.class),
                    StringUtils.capitalize(optionName), null);
            fv.visitAnnotation("Ljavax/persistence/OneToMany;", true)
                    .visit("mappedBy", bound.get("option1"));
        } else {
            fv = cw.visitField(ACC_PRIVATE, optionName, StringUtils.capitalize(optionName),
                    null, null);
            fv.visitAnnotation("Ljavax/persistence/ManyToOne;", true)
                    .visitEnum("fetch", "Ljavax/persistence/FetchType;", "LAZY");
            fv.visitAnnotation("Ljavax/persistence/JoinColumn;", true)
                    .visit("name", optionName + ID);
        }

        fv.visitEnd();
    }

    private void oneToOneCreate(final LinkedTreeMap<String, Object> bound) {
        final FieldVisitor fv = cw.visitField(ACC_PRIVATE, optionName, StringUtils.capitalize(optionName),
                null, null);
        final AnnotationVisitor av = fv.visitAnnotation("Ljavax/persistence/OneToOne;", true);

        if (Objects.equals(bound.get("option1"), optionName)) {
            av.visitEnum("cascade", "Ljavax/persistence/CascadeType;", "ALL");
        }

        fv.visitEnd();
    }
}
