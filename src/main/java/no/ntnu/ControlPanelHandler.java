package no.ntnu;

import java.net.*;
import java.util.concurrent.*;
public class ControlPanelHandler {

    private ConcurrentHashMap<Integer, Socket> ControlPanels = new ConcurrentHashMap<>();

    public void addControlPanel(int id, Socket socket){
        ControlPanels.put(id, socket);
    }
}