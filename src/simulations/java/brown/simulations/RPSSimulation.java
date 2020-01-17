package brown.simulations;

import java.util.LinkedList;
import java.util.List;

public class RPSSimulation extends AbsUserSimulation {

    public RPSSimulation(List<String> agentClass, String inputJSON,
                         String outFile, boolean writeToFile) {
        super(agentClass, inputJSON, outFile, writeToFile);
    }

    public void run() throws InterruptedException {

        ServerRunnable sr = new ServerRunnable();
        AgentRunnable ar = new AgentRunnable(agentClass.get(0), "alice");
        AgentRunnable ar2 = new AgentRunnable(agentClass.get(1), "bob");

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
        agentList.add("brown.user.agent.library.ExponentialWeightsAgent");
        agentList.add("brown.user.agent.library.FictitiousPlayAgent");
        RPSSimulation basicSim = new RPSSimulation(agentList,
                "input_configs/rock_paper_scissors.json", "outfile", false);
        basicSim.run();
    }

}