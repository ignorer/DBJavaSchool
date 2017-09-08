package com.db.javaschool.server.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesystemStorage implements Storage {
    private int numberOfChunks;

    public FilesystemStorage(String s) throws IOException {
        File storageDirectory = new File(s).getAbsoluteFile();
        if (!storageDirectory.exists() || !storageDirectory.isDirectory()) {
            Files.createDirectory(storageDirectory.toPath());
            // TODO: add format info file, add cache file
            return;
        }
        // TODO: add directory check
        numberOfChunks = storageDirectory.listFiles().length;
    }

    @Override
    public void dump() {

    }
}
