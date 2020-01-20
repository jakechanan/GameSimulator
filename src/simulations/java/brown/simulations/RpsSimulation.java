package brown.simulations;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class RpsSimulation extends AbsUserSimulation {

    public RpsSimulation(List<String> agentClass, String inputJSON,
                         String outFile, boolean writeToFile) {
        super(agentClass, inputJSON, outFile, writeToFile);
    }

    public void run() throws InterruptedException {

        ServerRunnable sr = new ServerRunnable();
        AgentRunnable ar = new AgentRunnable(agentClass.get(0), "myAgent");
        AgentRunnable ar2 = new AgentRunnable(agentClass.get(1), "opponent");

        Thread st = new Thread(sr);
        Thread at = new Thread(ar);
        Thread atTwo = new Thread(ar2);

        st.start();
        if (agentClass != null) {
            at.start();
            atTwo.start();
        }

        while (true) {
            if (!st.isAlive()) {
                at.interrupt();
                atTwo.interrupt();
                break;
            }
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	
        List<String> agentList = new LinkedList<String>();
        agentList.add("brown.user.agent.library.BasicRPSAgent");
        agentList.add("brown.user.agent.library.BasicRPSAgent");
        RpsSimulation basicSim = new RpsSimulation(agentList,
                "input_configs/rock_paper_scissors_quick.json", "outfile", false);
        basicSim.run();
        
    }

}