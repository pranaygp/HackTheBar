package com.hackthebar.pranaygp.drinkup;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by pranaygp on 9/25/15.
 */
public class NetClient {

    /**
     * Maximum size of buffer
     */
    public static final int BUFFER_SIZE = 2048;
    private static int portIncrementCounter;
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private String host = null;
    private String macAddress = null;
    private int port = 7999;


    /**
     * Constructor with Host, Port and MAC Address
     * @param host
     * @param port
     * @param macAddress
     */
    public NetClient(String host, int port, String macAddress) {
        this.host = host;
        this.port = port;
        this.macAddress = macAddress;
        portIncrementCounter = 0;
    }

    private void connectWithServer() {
        try {
            if (socket == null) {

                InetAddress ip = null;
                try {
                    ip = InetAddress.getByName(this.host);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                byte[] bytes = ip.getAddress();

                socket = new Socket(InetAddress.getByAddress(bytes), this.port);
//                socket.setSoLinger(true, 0);
                socket.setSoTimeout(5);
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Log.d("NC", "connectWithServer : on PORT " + socket.getPort());
            }
        } catch (IOException e) {
//            e.printStackTrace();
            disConnectWithServer();
            this.port++;
            portIncrementCounter++;
            if (portIncrementCounter % 4 == 0)
                this.port -= 4;

            Log.e("nc", "connectWithServer: " + e.getMessage());
            connectWithServer();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void disConnectWithServer() {
        if (socket != null) {
            if (socket.isConnected()) {
                try {
                    in.close();
                    out.close();
                    socket.close();
                    socket = null;
                    Log.d("NC", "socket closed");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendDataWithString(String message) {
        if (message != null) {
            connectWithServer();
            out.write(message);
            out.flush();
        }
    }

    public String receiveDataFromServer() {
        try {
            connectWithServer();
            String message = "";
            int charsRead = 0;
            char[] buffer = new char[BUFFER_SIZE];

            while ((charsRead = in.read(buffer)) > 0) {
                Log.d("NC", "receiveDataFromServer : " + charsRead);
                message += new String(buffer).substring(0, charsRead);
                Log.d("NC", "receiveDataFromServer : " + message);

                if(message == null){
                    disConnectWithServer();
                    this.port++;
                    portIncrementCounter++;
                    if (portIncrementCounter % 4 == 0)
                        this.port -= 4;
                }
                break;
            }
            Log.d("NC", "receiveDataFromServer : Left while loop");

//            disConnectWithServer(); // disconnect server
            return message;
        } catch (IOException e) {
//                return e.getMessage();
            disConnectWithServer();
            this.port++;
            portIncrementCounter++;
            if (portIncrementCounter % 4 == 0)
                this.port -= 4;
            return "Switched port: " + this.port;
        }
    }


}