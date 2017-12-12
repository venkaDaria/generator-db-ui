package com.example.venka.demo.utils;

import org.springframework.asm.Type;

public final class Types {

    public Types() {
        // empty
    }

    public static final String STR_SUPPLIER = Type.getMethodDescriptor(Type.getType(String.class));
}
