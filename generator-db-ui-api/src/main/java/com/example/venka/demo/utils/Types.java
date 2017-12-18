package com.example.venka.demo.utils;

import org.springframework.asm.Type;

import java.util.HashSet;

public final class Types {

    public static final String STR_SUPPLIER = Type.getMethodDescriptor(Type.getType(String.class));
    public static final String SET = Type.getDescriptor(HashSet.class);

    public Types() {
        // empty
    }
}
