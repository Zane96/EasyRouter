package me.zane.easyrouter_merge.framework;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

public class JarWriter implements Closeable{

    private final JarOutputStream jos;

    public JarWriter(File targetFile) throws IOException {
        this.jos = new JarOutputStream(
                new BufferedOutputStream(new FileOutputStream(targetFile)));
    }

    public void write(String relativePath, byte[] bytes) throws IOException {
        ZipEntry entry = new ZipEntry(relativePath);
        jos.putNextEntry(entry);
        jos.write(bytes);
    }

    public void close() throws IOException {
        jos.close();
    }
}