package com.example.venka.demo.utils.zip;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipManager {

    public byte[] create(final File directory) throws IOException {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        final ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        packDirectory(zipOutputStream, directory, directory.getName());

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
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) {
                return;
            }

            for (File childFile : children) {
                packDirectory(zipOutputStream, childFile, fileName + "/" + childFile.getName());
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
