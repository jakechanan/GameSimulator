package brown.system.setup.library;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import com.esotericsoftware.kryo.Kryo;

import brown.logging.library.ErrorLogging;
import brown.simulations.RPSSimulation;
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
	String FILEPATH = "src/main/java/";
    String JARPATH = "brown";
    try {
      kryo.register(HashMap.class);
      kryo.register(LinkedList.class);
      List<String> classesToReflect = getJavaFiles(FILEPATH);
      classesToReflect.addAll(getJavaFilesFromJAR(JARPATH));
      for (String className : classesToReflect) {
        Class<?> tpClass = Class.forName(className);
        kryo.register(tpClass);
      } 
      return true;
    } catch (IOException a) {
      ErrorLogging.log("ERROR: java startup: " + a.toString());
    } catch (ClassNotFoundException b) {
      ErrorLogging.log("ERROR: java startup: " + b.toString());
    } catch (URISyntaxException c) {
      ErrorLogging.log("ERROR: java startup: " + c.toString());
    }
    return false;
  }

  /**
   * helper that returns every java class starting at a path
   * 
   * @param path the starting path for the search
   * @return every java class starting at path
   * @throws IOException
 * @throws URISyntaxException 
   */
  public static List<String> getJavaFiles(String path) throws IOException, URISyntaxException {
	List<String> output = new LinkedList<String>();
	  
	if (!new File(path).exists()) {
    	return output;
    }
    
    Files.walk(Paths.get(path)).filter(Files::isRegularFile).filter(s -> s.toString().endsWith(".java"))
        .forEach(s -> output.add(s.toString().replaceAll(path, "")
            .replaceAll(".java", "").replaceAll("/", ".")));
    return output;
  }
  
  /**
   * helper that returns every java class starting at a path
   * 
   * @param path the starting path for the search
   * @return every java class starting at path
   * @throws IOException
 * @throws URISyntaxException 
   */
  public static synchronized List<String> getJavaFilesFromJAR(String path) throws IOException, URISyntaxException {
    List<String> output = new LinkedList<String>();
	  
	URL url = Setup.class.getClassLoader().getResource(path);
	if (url == null || !url.toURI().getScheme().equals("jar")) {
		return output;
	}
	
	FileSystem fileSystem = FileSystems.newFileSystem(url.toURI(), Collections.<String, Object>emptyMap());
  	Files.walk(fileSystem.getPath(path)).filter(Files::isRegularFile)
  		.filter(s -> s.toString().endsWith(".class")).forEach(s -> output.add(s.toString().substring(1)
			.replaceAll(".class", "").replaceAll("/", ".")));
    fileSystem.close();
    return output;
  }
  
  

}
