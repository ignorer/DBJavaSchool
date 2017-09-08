package com.db.javaschool.server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            ServerApplication application = null;
            application = new ServerApplication(6666);
            application.run();
        } catch (IOException e) {
            System.err.println("couldn't start application. cause:" + System.lineSeparator() + e.getCause().toString());
        }
    }
}
