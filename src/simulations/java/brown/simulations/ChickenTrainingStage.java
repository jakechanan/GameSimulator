package brown.simulations;

import java.util.LinkedList;
import java.util.List;

public class ChickenTrainingStage extends AbsUserSimulation {

  public ChickenTrainingStage(List<String> agentClass, String inputJSON, int port,
      String outFile, boolean writeToFile) {
    super(agentClass, inputJSON, port, outFile, writeToFile);
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

}