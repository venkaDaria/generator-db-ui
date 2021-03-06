package com.example.venka.demo.service.asm;

import com.example.venka.demo.utils.Types;
import com.google.gson.internal.LinkedTreeMap;
import jdk.internal.org.objectweb.asm.signature.SignatureVisitor;
import jdk.internal.org.objectweb.asm.signature.SignatureWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.FieldVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import static com.example.venka.demo.utils.Types.STR_SUPPLIER;
import static org.springframework.asm.Opcodes.ACC_PRIVATE;
import static org.springframework.asm.Opcodes.ACC_PUBLIC;
import static org.springframework.asm.Opcodes.ALOAD;
import static org.springframework.asm.Opcodes.ARETURN;
import static org.springframework.asm.Opcodes.DUP;
import static org.springframework.asm.Opcodes.GETFIELD;
import static org.springframework.asm.Opcodes.INVOKESPECIAL;
import static org.springframework.asm.Opcodes.INVOKESTATIC;
import static org.springframework.asm.Opcodes.NEW;
import static org.springframework.asm.Opcodes.PUTFIELD;
import static org.springframework.asm.Opcodes.PUTSTATIC;
import static org.springframework.asm.Opcodes.RETURN;
import static org.springframework.asm.Type.getDescriptor;
import static org.springframework.asm.Type.getInternalName;

@Service
public class AsmBoundService {

    private static final String ID = "_id";
    private final Set<String> options = new HashSet<>();
    private ClassWriter cw;
    private String optionName;
    private String className;

    private static void setMainInBound(final Object optionName, final AnnotationVisitor av) {
        av.visit("mappedBy", optionName);
        av.visitEnum("cascade", "Ljavax/persistence/CascadeType;", "ALL");
    }

    private static String toDescription(final String className) {
        return "L" + StringUtils.capitalize(className) + ";";
    }

    public static String applyOption(final String className, final LinkedTreeMap<String, Object> bound) {
        if (Objects.equals(bound.get("option1"), className)) {
            return bound.get("option2").toString();
        }
        if (Objects.equals(bound.get("option2"), className)) {
            return bound.get("option1").toString();
        }
        return "";
    }

    public void applyClassWriter(final ClassWriter cw) {
        this.cw = cw;
    }

    public Set<String> getOptions() {
        return options;
    }

    public void clearOptions() {
        options.clear();
    }

    public void createField(final String className, final LinkedTreeMap<String, Object> bound) {
        optionName = applyOption(className.toLowerCase(), bound);
        this.className = className;

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

        options.add(optionName);
    }

    private void manyToManyCreate(final LinkedTreeMap<String, Object> bound) {
        final FieldVisitor fv = cw.visitField(ACC_PRIVATE, optionName + "Set",
                Types.SET, getSignature(optionName), null);
        initialize(optionName, className);

        final AnnotationVisitor avBound = fv.visitAnnotation("Ljavax/persistence/ManyToMany;", true);

        if (Objects.equals(bound.get("option2"), optionName)) {
            avBound.visit("mappedBy", bound.get("option1") + "Set");
        } else {
            initialize(optionName, bound.get("option2").toString());

            final AnnotationVisitor av = fv.visitAnnotation("Ljavax/persistence/JoinTable;", true);
            av.visit("name", bound.get("option2") + "_" + bound.get("option1"));

            final AnnotationVisitor subAv = av.visitAnnotation("joinColumns", "Ljavax/persistence/JoinColumn;");
            subAv.visit("name", optionName + ID);
            subAv.visitEnd();

            av.visitAnnotation("inverseJoinColumns", "Ljavax/persistence/JoinColumn;")
                    .visit("name", bound.get("option2") + ID);
            av.visitEnd();
        }

        optionName = optionName + "Set";
        fv.visitEnd();
    }

    private void initialize(final String type, final String thisClass) {
        final MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitTypeInsn(NEW, getInternalName(HashSet.class));
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, getInternalName(HashSet.class), "<init>", "()V", false);
        mv.visitFieldInsn(PUTFIELD, thisClass, type + "Set", getSignature(type));
        mv.visitInsn(RETURN);
        mv.visitMaxs(4, 2);
        mv.visitEnd();
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
            fv = cw.visitField(ACC_PRIVATE, optionName + "Set", Types.SET,
                    getSignature(optionName), null);
            initialize(optionName, className);
            optionName = optionName + "Set";

            final AnnotationVisitor av = fv.visitAnnotation("Ljavax/persistence/OneToMany;", true);
            setMainInBound(reversed.test(true) ? bound.get("option1") : bound.get("option2"), av);
        } else {
            fv = cw.visitField(ACC_PRIVATE, optionName, toDescription(optionName),
                    null, null);
            fv.visitAnnotation("Ljavax/persistence/ManyToOne;", true)
                    .visitEnum("fetch", "Ljavax/persistence/FetchType;", "LAZY");
            fv.visitAnnotation("Ljavax/persistence/JoinColumn;", true)
                    .visit("name", optionName + ID);
        }

        fv.visitEnd();
    }

    @NotNull
    private static String getSignature(final String name) {
        final SignatureWriter signature = new SignatureWriter();

        final SignatureVisitor superclassSignature = signature.visitSuperclass();
        superclassSignature.visitClassType("java/util/Set");

        final SignatureVisitor genericSignature = superclassSignature.visitTypeArgument('=');
        genericSignature.visitClassType(StringUtils.capitalize(name));
        genericSignature.visitEnd();

        superclassSignature.visitEnd();

        return signature.toString();
    }

    private void oneToOneCreate(final LinkedTreeMap<String, Object> bound) {
        final FieldVisitor fv = cw.visitField(ACC_PRIVATE, optionName, toDescription(optionName),
                null, null);
        final AnnotationVisitor av = fv.visitAnnotation("Ljavax/persistence/OneToOne;", true);

        if (Objects.equals(bound.get("option2"), optionName)) {
            setMainInBound(bound.get("option1"), av);
        } else {
            fv.visitAnnotation("Ljavax/persistence/JoinColumn;", true)
                    .visit("name", optionName + ID);
        }

        fv.visitEnd();
    }
}
