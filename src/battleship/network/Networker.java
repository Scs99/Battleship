/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.network;

import battleship.IGameCanStart;
import battleship.IHitRequestReceived;
import battleship.IHitResponseReceived;
import battleship.IStartGameRequest;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Maï
 */
public class Networker implements INetworker, IGui {

    private final ArrayList<IHitResponseReceived> hitResponseReceivedListeners = new ArrayList<>();
    private final ArrayList<IHitRequestReceived> hitRequestReceivedListeners = new ArrayList<>();
    private final ArrayList<IStartGameRequest> startGameRequestReceivedListeners = new ArrayList<>();
    private final ArrayList<IGameCanStart> gameCanStartListeners = new ArrayList<>();

    private Socket socket;
    private ObjectOutputStream writer;

    public final String myName;
    private int myPort;
    private String myIp;

    public Networker(String myName) {
        this.myName = myName;
    }

    public boolean askPlayerToJoin(String ipAddress, String hostname) {
        return true;     // vorläufig zum compilieren
    }

    public String getMyIP() {
        return myIp;
    }

    public int getMyPort() {
        return myPort;
    }

    /**
     * Verbindet sich als Client mit einem Server.
     *
     * @param ip Die IP-Adresse des Servers.
     * @param port Der Port des Servers.
     */
    public void connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            //writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer = new ObjectOutputStream(socket.getOutputStream());

            if (ip.equalsIgnoreCase("Localhost")) {
                myIp = ip;
            } else {
                InetAddress ipAddress = InetAddress.getLocalHost();
                myIp = ipAddress.getHostAddress();
            }
            send(new NetworkPackage(new ConnectionDetails(myIp, myPort)));

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Versendet ein Netzwerkpaket zum Gegenspieler.
     *
     * @param netPackage Das zu versendende Packet.
     */
    public void send(NetworkPackage netPackage) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            writer.writeObject(netPackage);
            writer.flush();
            System.out.println(timeStamp + " " + myName + " Client: Sending " + netPackage.typeOfObject);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Erstellt einen neuen Server der einkommende Packete empfängt.
     *
     * @param port der Port des Servers.
     */
    public void startServer() {

        // Thread als anonyme innere Klasse.
        Thread t = new Thread() {
            private Socket s;
            private ObjectInputStream in;

            @Override
            public void run() {
                try {
                    //ServerSocket ss = new ServerSocket(port);
                    ServerSocket ss = new ServerSocket(0);
                    myPort = ss.getLocalPort();
                    InetAddress ipAddress = InetAddress.getLocalHost();
                    myIp = ipAddress.getHostAddress();
                    s = ss.accept();
                    in = new ObjectInputStream(s.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true) {
                    try {
                        Object object = (NetworkPackage) in.readObject();
                        NetworkPackage netPackage = (NetworkPackage) object;
                        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                        if (HitRequest.class.getSimpleName().equals(netPackage.typeOfObject)) {
                            HitRequest hitRequest = (HitRequest) netPackage.object;
                            System.out.println(timeStamp + " " + myName + " Server: I received a HitRequest @ " + hitRequest.x + " " + hitRequest.y);
                            receivedHitRequest(hitRequest);
                        } else if (HitResponse.class.getSimpleName().equals(netPackage.typeOfObject)) {
                            HitResponse hitResponse = (HitResponse) netPackage.object;
                            System.out.println(timeStamp + " " + myName + " Server: I received a HitResponse @ " + hitResponse.x + " " + hitResponse.y);
                            receivedHitResponse(hitResponse);
                        } else if (StartGameRequest.class.getSimpleName().equals(netPackage.typeOfObject)) {         
                            StartGameRequest startGameRequest = (StartGameRequest) netPackage.object;
                            System.out.println(timeStamp + " " + myName + " Start game request received.");
                            receivedStartGameRequest(startGameRequest);
                        } else if (ConnectionDetails.class.getSimpleName().equals(netPackage.typeOfObject)) {
                            ConnectionDetails connectionDetails = (ConnectionDetails) netPackage.object;
                            System.out.println(timeStamp + " " + myName + " Server: I received connection details " + connectionDetails.ip + " " + connectionDetails.port);
                            if (socket == null) {
                                connect(connectionDetails.ip, connectionDetails.port);
                                System.out.println(timeStamp + " " + myName + " Server: Autoconnect successful.");
                                gameCanStart();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

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

    @Override
    public void registerGameCanStart(IGameCanStart receiver) {
        gameCanStartListeners.add(receiver);
    }

    public void gameCanStart() {
        for (IGameCanStart receiver : gameCanStartListeners) {
            receiver.onGameCanStart();
        }
    }

    @Override
    public void registerStartGameRequest(IStartGameRequest receiver) {
        startGameRequestReceivedListeners.add(receiver);
    }

    public void receivedStartGameRequest(StartGameRequest startGameRequest){
        for(IStartGameRequest receiver : startGameRequestReceivedListeners){
            receiver.onStartGameRequestReceived(startGameRequest);
        }
    }
}
