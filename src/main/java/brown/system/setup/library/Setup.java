package brown.system.setup.library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import com.esotericsoftware.kryo.Kryo;

import brown.logging.library.ErrorLogging;
import brown.simulations.RpsSimulation;
import brown.system.setup.ISetup;

public final class Setup implements ISetup {
  @Override
  public void setup(Kryo kryo) {
	start(kryo);
  }
	
  /**
   * helper that registers all classes with kryo
   * 
   * @param kryo the Kryo object
   * @return
   */
  public static boolean start(Kryo kryo) {
    kryo.setRegistrationRequired(false);
    return true;
  }

  /**
   * helper that returns every java class starting at a path
   * 
   * @param path the starting path for the search
   * @return every java class starting at path
   * @throws IOException
 * @throws URISyntaxException 
   */
  public static List<String> getJavaFiles(String path) throws IOException {
	List<String> output = new ArrayList<String>();
	  
	if (!new File(path).exists()) {
    	return output;
    }
    
    Files.walk(Paths.get(path)).filter(Files::isRegularFile)
    	.filter(s -> s.toString().endsWith(".java"))
        .forEach(s -> output.add(s.toString().replaceAll(path, "")
            .replaceAll(".java", "").replaceAll("/", ".")));
    return output;
  }
}
