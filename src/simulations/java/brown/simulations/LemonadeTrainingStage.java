package brown.simulations;

import java.util.LinkedList;
import java.util.List;

public class LemonadeTrainingStage extends AbsUserSimulation {

  public LemonadeTrainingStage(List<String> agentClass, String inputJSON, int port,
      String outFile, boolean writeToFile) {
    super(agentClass, inputJSON, port, outFile, writeToFile);
  }

  public void run() throws InterruptedException {

    ServerRunnable sr = new ServerRunnable();
    AgentRunnable ar = new AgentRunnable(agentClass.get(0), "myAgent");
    AgentRunnable ar2 = new AgentRunnable(agentClass.get(1), "opponent1");
    AgentRunnable ar3 = new AgentRunnable(agentClass.get(2), "opponent2");

    Thread st = new Thread(sr);
    Thread at = new Thread(ar);
    Thread atTwo = new Thread(ar2);
    Thread atThree = new Thread(ar3);

    st.start();
    if (agentClass != null) {
      at.start();
      atTwo.start();
      atThree.start();
    }

    while (true) {
      if (!st.isAlive()) {
        at.interrupt();
        atTwo.interrupt();
        atThree.interrupt();
        break;
      }
      Thread.sleep(1000);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    List<String> agentList = new LinkedList<String>();
    agentList.add("brown.user.agent.library.BasicLemonadeAgent");
    agentList.add("brown.user.agent.library.BasicLemonadeAgent");
    agentList.add("brown.user.agent.library.BasicLemonadeAgent");
    LemonadeTrainingStage basicSim = new LemonadeTrainingStage(agentList,
        "input_configs/lemonade_game_training.json", 2121, "outfile", false);
    basicSim.run();
  }

}