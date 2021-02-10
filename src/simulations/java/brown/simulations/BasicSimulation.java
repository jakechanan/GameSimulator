package brown.simulations;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class BasicSimulation extends AbsUserSimulation {
	private List<String> names;

    public BasicSimulation(List<String> agentClass, List<String> names, String inputJSON, int port,
                         String outFile, boolean writeToFile) {
        super(agentClass, inputJSON, port, outFile, writeToFile);
        this.names = names;
        assert (names.size() == agentClass.size());
    }

    public void run() throws InterruptedException {
        ServerRunnable serverRunnable = new ServerRunnable();
        
        List<AgentRunnable> agentRunnables = new ArrayList<>(this.names.size());
        for (int i = 0; i < this.names.size(); i++) {
        	agentRunnables.add(new AgentRunnable(agentClass.get(i), this.names.get(i)));
        }
        
        Thread serverThread = new Thread(serverRunnable);
        
        List<Thread> agentThreads = new ArrayList<>(agentRunnables.size());
        for (AgentRunnable ar : agentRunnables) {
        	agentThreads.add(new Thread(ar));
        }

        serverThread.start();
        for (Thread t : agentThreads) {
        	t.start();
        }

        while (true) {
            if (!serverThread.isAlive()) {
            	for (Thread t : agentThreads) {
                	t.interrupt();
                }
                break;
            }
            Thread.sleep(1000);
        }
    }

}