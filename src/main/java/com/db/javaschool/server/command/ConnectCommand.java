package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.ConnectRequest;
import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.protocol.request.SendRequest;
import com.db.javaschool.server.Context;
import com.db.javaschool.server.entity.User;
import org.json.JSONObject;

import java.net.Socket;

public class ConnectCommand extends ServerCommand {
    public ConnectCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(Object... objects) {
        ConnectRequest request = (ConnectRequest) objects[0];
        Socket socket = (Socket) objects[1];

        try {
            context.lockConnections().add(new User(request.getUsername(), socket));
        } finally {
            context.releaseConnections();
        }
    }
}
