package com.db.javaschool.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FileHandler {

    private String folder;
    private int fileNumber = 0;

    public FileHandler(String folder) {
        this.folder = folder;
    }

    public int getFileCount() {
        return new File(folder).listFiles().length;
    }

    public void dumpFile(List<String> messages) {
        File file = new File(folder, fileNumber + "_chatek.txt");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true);
            //messages.forEach(m -> writer.write(m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
