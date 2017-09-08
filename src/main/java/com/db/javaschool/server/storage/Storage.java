package com.db.javaschool.server.storage;

import com.db.javaschool.server.entity.Message;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface Storage {
    void store(List<Message> messageList) throws IOException;

    List<Message> getData(int pageNumber) throws IOException;
}
