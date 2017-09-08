package com.db.javaschool.server.storage;

public interface Storage {
    void dump();

    void loadChunk(int chunkNumber);
}
