/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import battleship.IHitRequestReceived;
import battleship.IHitResponseReceived;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Maï
 */
public class Networker implements INetworker {

    private final ArrayList<IHitResponseReceived> hitResponseReceivedListeners = new ArrayList<>();
    private final ArrayList<IHitRequestReceived> hitRequestReceivedListeners = new ArrayList<>();
    //public static Networker instance;
    private boolean isServer;
    private int port;
    private String host;

    private Socket socket;
    //private BufferedWriter writer;
    private ObjectOutputStream writer;

    public Networker() {
    }

    /*
    public static void initialize() {
        instance = new Networker();
    }
     */
    public boolean askPlayerToJoin(String ipAddress, String hostname) {
        return true;     // vorläufig zum compilieren
    }

    public void connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            //writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer = new ObjectOutputStream(socket.getOutputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    public void send(String message) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            System.out.println(timeStamp + " Client: " + message);
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     */
    public void send(NetworkPackage netPackage) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            System.out.println(timeStamp + " Client: " + netPackage.toString());
            writer.writeObject(netPackage);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(int port) {
        (new Thread() {
            @Override
            public void run() {
                ServerSocket ss;
                try {
                    ss = new ServerSocket(port);

                    Socket s = ss.accept();

                    //BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                    try {
                        Object object = (NetworkPackage) in.readObject();
                        NetworkPackage netPackage = (NetworkPackage) object;
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                        System.out.println(timeStamp + " Server: " + netPackage.toString());
                        System.out.println(timeStamp + " Server: " + netPackage.typeOfObject);

                        if ("HitRequest".equals(netPackage.typeOfObject)) {
                            HitRequest hitRequest = (HitRequest) netPackage.object;
                            System.out.println(timeStamp + " Server: I received a HitRequest @ " + hitRequest.x + " " + hitRequest.y);
                            receivedHitRequest(hitRequest);

                        } else if ("HitResponse".equals(netPackage.typeOfObject)) {
                            HitResponse hitResponse = (HitResponse) netPackage.object;
                            System.out.println(timeStamp + " Server: I received a HitResponse @ " + hitResponse.x + " " + hitResponse.y);
                            receivedHitResponse(hitResponse);
                        }

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void checkObjectType(NetworkPackage netPackage) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    /*  
    public void startServer(int port) {
        (new Thread() {
            @Override
            public void run() {
                ServerSocket ss;
                try {
                    ss = new ServerSocket(port);

                    Socket s = ss.accept();

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(s.getInputStream()));
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                        System.out.println(timeStamp + " Server: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
     */
    @Override
    public void registerHitResponse(IHitResponseReceived receiver) {
        hitResponseReceivedListeners.add(receiver);
    }

    @Override
    public void registerHitRequest(IHitRequestReceived receiver) {
        hitRequestReceivedListeners.add(receiver);
    }

    public void receivedHitResponse(HitResponse hitResponse) {
        for (IHitResponseReceived receiver : hitResponseReceivedListeners) {
            receiver.onHitResponseReceived(hitResponse);
        }
    }

    public void receivedHitRequest(HitRequest hitRequest) {
        for (IHitRequestReceived receiver : hitRequestReceivedListeners) {
            receiver.onHitRequestReceived(hitRequest);
        }
    }

}
