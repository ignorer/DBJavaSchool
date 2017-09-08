package com.db.javaschool.server.storage;

import com.db.javaschool.server.entity.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.json.XMLTokener.entity;

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
            writer.write(new JSONObject(messageList).toString());
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
        JSONArray array = new JSONArray(jsonString);
        List<Message> list = new ArrayList<Message>();
        for(int i = 0; i < array.length(); i++){
            //list.add(new Message());

//            /
            //Message)(array.getJSONObject(i)

        }
        return new LinkedList<Message>();
    }
}
