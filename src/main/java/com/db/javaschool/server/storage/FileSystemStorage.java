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
    private String path;

    public FileSystemStorage(String path) throws IOException {
        this.path = path;
        File storageDirectory = new File(path).getAbsoluteFile();

        if (storageDirectory.exists() && storageDirectory.isDirectory()) {
            numberOfPages = storageDirectory.listFiles().length;
        } else {
            Files.createDirectory(storageDirectory.toPath());
            numberOfPages = 0;
        }
    }


    @Override
    public void store(List<Message> messageList) throws IOException {
        File file = new File(path, numberOfPages++ + "_chatek.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(new JSONArray(messageList).toString());
        }
    }

    @Override
    public List<Message> getData(int fileNumber) throws IOException {
        String jsonString = "";
        File file = new File(path, fileNumber + "_chatek.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
