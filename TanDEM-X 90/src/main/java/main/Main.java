package main;

import java.io.File;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
  private final File   inputList;
  private final File   outputDir;
  private final String username;
  private final String passwd;
  private final int    threads;

  /**
   * @param inputList
   * @param outputDir
   * @param username
   * @param passwd
   */
  public Main(File inputList, File outputDir, String username, String passwd, int threads) {
    this.inputList = inputList;
    this.outputDir = outputDir;
    this.username = username;
    this.passwd = passwd;
    this.threads = threads;

    parseInputFile();
  }

  /**
   * Reads all lines from the urllist and add them to an executer service to
   * progress in parallel threads
   */
  private void parseInputFile() {
    try {
      ExecutorService pool = Executors.newFixedThreadPool(threads);
      URI uri = inputList.toURI();

      // Read all lines to list
      List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

      // Set Number of rows to progressDialog to clac time to go
      ProgressDialog dialog = ProgressDialog.getInstance();
      dialog.setMax(lines.size());

      // adding jobs
      for (String line : lines) {
        pool.submit(new DownloadTask(line, outputDir, username, passwd));
      }

      pool.shutdown();
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
      // all tasks have now finished (unless an exception is thrown above)
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}