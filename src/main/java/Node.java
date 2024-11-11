import java.util.concurrent.*;
public class Node {

    private ConcurrentHashMap<Integer, Socket> Nodes = new ConcurrentHashMap<>();

    public void addNode(int id, Socket socket){
        Nodes.put(id, socket);
    }

}
