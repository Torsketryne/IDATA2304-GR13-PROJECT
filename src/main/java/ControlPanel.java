import java.util.concurrent.*;
public class ControlPanel {

    private ConcurrentHashMap<Integer, Socket> ControlPanels = new ConcurrentHashMap<>();

    public void addControlPanel(int id, Socket socket){
        ControlPanels.put(id, socket);
    }
}