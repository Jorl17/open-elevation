package main;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Start class managing the cmd options
 * 
 * @author crypto
 *
 */
public class Start {

  public static void main(String[] args) throws ParseException {
    //Adding options
    Options options = new Options();
    options.addOption("i", "input-urllist", true, "Specify the input url list")
        .addOption("o", "output", true, "Specify a output for extracted tiff file")
        .addOption("u", "username", true, "Your https://geoservice.dlr.de login username (=email)")
        .addOption("p", "password", true, "Your https://geoservice.dlr.de password")
        .addOption("n","number-of-threads", true, "Insert a number of download threads default is 4");

    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("TanDEM-X-90-Downloader", null, options,
        "Sample ustage:\njava -jar <<yourFile>> -i=C:\\ -o=C:\\out -u=test@test.test -p=pass");

    // Parsed vars
    File inputList = null;
    File outputDir = null;
    String username = null;
    String passwd = null;
    int threads = 4;

    // Create a parser
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    // Get input
    if (cmd.hasOption("i")) {
      inputList = new File(cmd.getOptionValue("i"));
    } else {
      error("Plase specify a input file for the url list");
    }
    // Get output
    if (cmd.hasOption("o")) {
      outputDir = new File(cmd.getOptionValue("o"));
      //Create folderstructure if not exists
      if (!outputDir.exists()) {
        outputDir.mkdirs();
      }
    } else {
      error("Plase specify a output dir");
    }
    // Get usernmae
    if (cmd.hasOption("u")) {
      username = cmd.getOptionValue("u");
    } else {
      error("Plase specify a username");
    }
    // Get passwd
    if (cmd.hasOption("p")) {
      passwd = cmd.getOptionValue("p");
    } else {
      error("Plase specify a passwd");
    }
    // Get Threads
    if (cmd.hasOption("n")) {
      threads = Integer.parseInt(cmd.getOptionValue("n"));
    }
    
    //Give args to Mainclass
    new Main(inputList, outputDir, username, passwd, threads);
  }

  /**
   * Errormessage will be shown as error and system is shutdown
   * 
   * @param msg
   */
  private static void error(String msg) {
    System.err.println("\n\nERROR: " + msg);
    System.exit(0);
  }
}
