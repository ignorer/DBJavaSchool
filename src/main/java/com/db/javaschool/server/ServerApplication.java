package com.db.javaschool.server;

import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.server.command.*;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.entity.User;
import com.db.javaschool.server.exception.RequestParsingException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerApplication {
    private final AtomicBoolean timeToExit = new AtomicBoolean(false);
    private final Thread inputThread = new Thread(this::inputThreadLifecycle);
    private final Thread outputThread = new Thread(this::outputThreadLifecycle);
    private final int port;
    private final Context context;
    private final Map<Request.RequestType, ServerCommand> commands;
    private final Object systemErrMonitor = new Object();

    public ServerApplication(int port) throws IOException {
        context = new Context();

        this.port = port;

        Map<Request.RequestType, ServerCommand> tempMap = new HashMap<>();
        tempMap.put(Request.RequestType.CONNECT, new ConnectCommand(context));
        tempMap.put(Request.RequestType.SEND, new SendCommand(context));
        tempMap.put(Request.RequestType.HISTORY, new HistoryCommand(context));
        tempMap.put(Request.RequestType.HISTORY_INFO, new HistoryInfoCommand(context));
        commands = Collections.unmodifiableMap(tempMap);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        inputThread.run();
        outputThread.run();

        while (!timeToExit.get()) {
            String input = scanner.next();
            if (input.equals("stop")) {
                timeToExit.set(true);
            }
        }
        try {
            inputThread.join();
            outputThread.join();
        } catch (InterruptedException e) {
            inputThread.stop();
            outputThread.stop();
        }
    }

    private void inputThreadLifecycle() {
        try (ServerSocket socket = new ServerSocket(port)) {
            while (!timeToExit.get()) {
                try  {
                    Socket inputSocket = socket.accept();
                    DataInputStream in = new DataInputStream(inputSocket.getInputStream());
                    DataOutputStream out = new DataOutputStream(inputSocket.getOutputStream());

                    String input = in.readUTF();
                    System.out.println("I've received " + input);
                    Request request = RequestParser.parseRequest(input);

                    ServerCommand command = commands.get(request.getType());
                    if (command == null) {
                        System.err.println("warning: couldn't get ");
                        continue;
                    }
                    command.execute(request, inputSocket);
                } catch (RequestParsingException e) {
                    synchronized (systemErrMonitor) {
                        System.err.println(String.format("warning: request is not valid.\nreason: %s\brequest: %s",
                            e.getMessage(), e.getRequestText()));
                    }
                }
            }
        } catch (IOException e) {
            synchronized (systemErrMonitor) {
                System.err.println("fatal error: couldn't run receiving thread. shutdown");
            }
            timeToExit.set(true);
        }
    }

    private void outputThreadLifecycle() {
        while (!timeToExit.get()) {
            try {
                Message message = context.getPool().getMessageFromDeque();

                Collection<User> connections = context.lockConnections();

                connections.forEach(user -> {
                    try {
                        DataOutputStream out = new DataOutputStream(user.getSocket().getOutputStream());
                        System.out.println("Sending message " + message + " to user " + user.getUsername());
                        out.writeUTF(message.toJSON());
                    } catch (IOException e) {
                        // ignore it
                    }
                });
            } catch (IOException e) {
                synchronized (systemErrMonitor) {
                    System.err.println("cannot save to file");
                }
            } finally {
                context.releaseConnections();
            }
        }
    }
}
