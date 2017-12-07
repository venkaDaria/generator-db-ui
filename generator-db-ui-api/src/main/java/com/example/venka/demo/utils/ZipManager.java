package com.example.venka.demo.utils;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipManager {

    private final GeneratorFiles generatorFiles;

    public ZipManager(final GeneratorFiles generatorFiles) {
        this.generatorFiles = generatorFiles;
    }

    public byte[] create(final Map<String, String> body) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        final ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        final File directory = generatorFiles.get(body);
        packDirectory(zipOutputStream, directory, generatorFiles.getName(body));

        zipOutputStream.finish();
        zipOutputStream.flush();

        IOUtils.closeQuietly(zipOutputStream);
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    private void packDirectory(final ZipOutputStream zipOutputStream, final File file, final String fileName) throws IOException {
        if (file.isHidden()) {
            return;
        } if (file.isDirectory()) {
            File[] children = file.listFiles();

            if (children != null) {
                for (File childFile : children) {
                    packDirectory(zipOutputStream, childFile, fileName + "/" + childFile.getName());
                }
            }
        } else {
            packFile(zipOutputStream, file, fileName);
        }
    }

    private void packFile(final ZipOutputStream zipOutputStream, final File file, final String fileName) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        FileInputStream fileInputStream = new FileInputStream(file);

        IOUtils.copy(fileInputStream, zipOutputStream);

        fileInputStream.close();
        zipOutputStream.closeEntry();
    }
}
