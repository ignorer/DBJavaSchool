package com.db.javaschool.server;

import org.json.JSONObject;

import java.io.*;
import java.util.Date;
import java.util.List;

public class FileHandler {

    private String folder;
    private int fileNumber;

    public FileHandler(String folder) {
        this.folder = folder;
        fileNumber = new File(folder).listFiles().length;
    }

    public int getFileNumber() {
        return fileNumber;
    }

    public void dumpFile(JSONObject object) {
        File file = new File(folder, ++fileNumber + "_chatek.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(int numberToRead) {
        String returnString = "";
        File file = new File(folder, numberToRead + "_chatek.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                returnString += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
