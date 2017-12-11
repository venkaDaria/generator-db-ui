package com.example.venka.demo.example;

import com.example.venka.demo.utils.DecompilerUtils;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.asm.Opcodes.ACC_PUBLIC;
import static org.springframework.asm.Opcodes.ACC_STATIC;
import static org.springframework.asm.Opcodes.ACC_SUPER;
import static org.springframework.asm.Opcodes.ALOAD;
import static org.springframework.asm.Opcodes.GETSTATIC;
import static org.springframework.asm.Opcodes.INVOKESPECIAL;
import static org.springframework.asm.Opcodes.INVOKEVIRTUAL;
import static org.springframework.asm.Opcodes.RETURN;
import static org.springframework.asm.Opcodes.V1_8;

public class HelloClass {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);

        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, "sample/HelloGen", null, "java/lang/Object", null);

        //default constructor
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0); //load the first local variable: this
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        //main method
        {
            MethodVisitor mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
            mv.visitCode();
            mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;"); //put System.out to operand stack
            mv.visitLdcInsn("Hello, world!"); //load const "Hello" from const_pool, and put onto the operand stack
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(2, 1);
            mv.visitEnd();
        }
        cw.visitEnd();

        //save bytecode into disk
        final String className = "temp/sample/HelloGen.class";

        Files.createDirectories(Paths.get("temp/sample/"));
        Files.createFile(Paths.get(className));

        FileOutputStream out = new FileOutputStream(className);
        out.write(cw.toByteArray());
        out.close();

        // decompile
        DecompilerUtils.execute(new File(className), new File("temp/sample/HelloGen.java"));
    }
}