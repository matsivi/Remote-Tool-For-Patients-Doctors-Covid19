package com.e.covid19new;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class Server extends Service{

public static final String START_SERVER = "startserver";
public static final String STOP_SERVER = "stopserver";
public static final int SERVERPORT = 8086;
public final String NOTIFICATION="com.e.covid19new";
    //Content content ;


       private Thread serverThread;
        private ServerSocket serverSocket;

    {
        try {
            serverSocket = new ServerSocket(SERVERPORT);
            Log.e("MSC_RSS", "Success");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public Server() {

        }

//called when the services starts
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
        //action set by setAction() in activity
    Log.e("MSC_RSS", "Start command");
        String action = intent.getAction();
        if (action.equals(START_SERVER)) {
        //start your server thread from here
        this.serverThread = new Thread(new ServerThread());
        this.serverThread.start();
        }
        if (action.equals(STOP_SERVER)) {
        //stop server
        if (serverSocket != null) {
        try {
        serverSocket.close();
        } catch (IOException ignored) {}
        }
        }

        //configures behaviour if service is killed by system, see documentation
        return START_REDELIVER_INTENT;
        }

@Override
public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

        }

class ServerThread implements Runnable {

    public void run() {
        Socket socket;
        try {
            serverSocket = new ServerSocket(SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!Thread.currentThread().isInterrupted()) {

            try {

                socket = serverSocket.accept();

                CommunicationThread commThread = new CommunicationThread(socket);
                new Thread(commThread).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class CommunicationThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader input;
    public CommunicationThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String read = input.readLine();
               Intent intent = new Intent("customEvent");
                intent.putExtra("action",read);
                LocalBroadcastManager.getInstance(Server.this).sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
}

