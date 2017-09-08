package com.db.javaschool.server.storage;

import com.db.javaschool.server.entity.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FileSystemStorage implements Storage {
    private int numberOfPages;
    private File file;

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public FileSystemStorage(File file) throws IOException {
        //this.path = file.getAbsolutePath();
        this.file = file;
        if (file.exists() && file.isDirectory()) {
            numberOfPages = file.listFiles().length;
        } else {
            file.mkdir();
            numberOfPages = 0;
        }
    }


    @Override
    public void store(List<Message> messageList) throws IOException {
        //File file = new File(path, numberOfPages++ + "_chatek.txt");
        String path = this.file.toPath().toString() + System.lineSeparator() + numberOfPages++ + "_chatek.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(new JSONArray(messageList).toString());
        }
    }

    @Override
    public List<Message> getData(int fileNumber) throws IOException {
        String jsonString = "";
        String path = this.file.toPath().toString() + "\\" + fileNumber + "_chatek.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                jsonString += line;
            }
        }
        List<Object> array = new JSONArray(jsonString).toList();

        List<Message> list = new ArrayList<>();
        for (Object object : array) {
            list.add(new Message(
                    (int) ((HashMap) object).get("timeStamp"),
                    (String) ((HashMap) object).get("message"),
                    (String) ((HashMap) object).get("userName")
            ));
        }
        return list;
    }
}
