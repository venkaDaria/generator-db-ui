package com.example.venka.demo.utils;

import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.DecompilerSettings;
import com.strobel.decompiler.PlainTextOutput;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public final class DecompilerUtils {

    private static final DecompilerSettings settings = DecompilerSettings.javaDefaults();

    private DecompilerUtils() {
        // empty
    }

    public static void execute(final File source, final File target) {
        try (final FileOutputStream stream = new FileOutputStream(target);
             final OutputStreamWriter writer = new OutputStreamWriter(stream)) {

            Decompiler.decompile(
                    source.getPath().replace(".class", ""),
                    new PlainTextOutput(writer),
                    settings
            );
        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
