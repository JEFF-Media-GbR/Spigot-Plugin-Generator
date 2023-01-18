package com.jeff_media.maven_spigot_plugin_gui.utils;

import com.jeff_media.maven_spigot_plugin_gui.Logger;
import com.jeff_media.maven_spigot_plugin_gui.SpigotPluginGenerator;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class FileDownloader {

    private static final Logger LOGGER = new Logger(FileDownloader.class);

    private final String url;
    private final File file;

    public FileDownloader(String url, File file) {
        this.url = url;
        this.file = file;
    }

    public CompletableFuture<File> startDownload() {
        LOGGER.debug("FileDownloader: Starting download of " + url + " to " + file.getAbsolutePath());
        return CompletableFuture.supplyAsync(() -> {
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                return file;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
    }

}
