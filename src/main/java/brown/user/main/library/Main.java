package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import brown.user.main.IJsonParser;
import brown.user.main.ISimulationConfig;

/**
 * the main class of TradingPlatform. Start a simulation by running this class. 
 * 
 * @author acoggins
 *
 */
public class Main {
	
	public static Integer PORT = null;
 
/**
 * 
 * @param args
 * @throws ClassNotFoundException
 * @throws NoSuchMethodException
 * @throws InstantiationException
 * @throws IllegalAccessException
 * @throws InvocationTargetException
 * @throws IllegalArgumentException
 * @throws InterruptedException
 * @throws FileNotFoundException
 * @throws IOException
 * @throws ParseException
 */
  public static void main(String[] args) throws ClassNotFoundException,
      NoSuchMethodException, InstantiationException, IllegalAccessException,
      InvocationTargetException, IllegalArgumentException, InterruptedException,
      FileNotFoundException, IOException, ParseException {

    List<ISimulationConfig> configs = new LinkedList<>();

    String fileName = args[0];
    IJsonParser jsonParser = new JsonParser();
    List<ISimulationConfig> runConfig = jsonParser.parseJSON(fileName);
    configs.addAll(runConfig);
    Map<String, Integer> outerParams =
        jsonParser.parseJSONOuterParameters(fileName);
    Map<String, Double> doubleParams =
        jsonParser.parseJSONDoubleParameters(fileName);
    Integer startingDelayTime = outerParams.get("startingDelayTime");
    Integer numTotalRuns = outerParams.get("numTotalRuns");
    Integer serverPort = (PORT == null) ? outerParams.get("serverPort") : PORT;
    if (args.length > 1) {
    	serverPort = Integer.parseInt(args[1]);
    }
    Double simulationDelayTime = doubleParams.get("simulationDelayTime"); 
    
    ConfigRun configRun = new ConfigRun(configs);
    configRun.run(startingDelayTime, simulationDelayTime, numTotalRuns, serverPort);
  }
}
