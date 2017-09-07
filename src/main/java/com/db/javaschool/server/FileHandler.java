package com.db.javaschool.server;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class FileHandler {

    private String folder;
    private int fileNumber;

    public FileHandler(String folder) {
        this.folder = folder;
        fileNumber = new File(folder).listFiles().length;
    }

    public void dumpFile(List<String> messages) {
        File file = new File(folder, fileNumber + "_chatek.txt");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            //messages.forEach(m -> writer.write(m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dumpFile(JSONObject object) {
        File file = new File(folder, fileNumber + "_chatek.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
