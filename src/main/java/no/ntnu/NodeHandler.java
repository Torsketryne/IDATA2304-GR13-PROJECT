package no.ntnu;

import java.net.*;
import java.util.concurrent.*;
public class NodeHandler {

    private ConcurrentHashMap<Integer, Socket> Nodes = new ConcurrentHashMap<>();

    public void addNode(int id, Socket socket){
        Nodes.put(id, socket);
    }

}
