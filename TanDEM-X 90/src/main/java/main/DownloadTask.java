package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.apache.commons.io.FilenameUtils;

/**
 * Download Task
 *
 * Downloading the zip file and start the decompress thread
 *
 * @author crypto
 *
 */
public class DownloadTask implements Runnable {

  private final String inputList;
  private final File   outputDir;
  private final String username;
  private final String passwd;

  /**
   * @param line
   * @param outputDir
   * @param username
   * @param passwd
   */
  public DownloadTask(String line, File outputDir, String username, String passwd) {
    this.inputList = line;
    this.outputDir = outputDir;
    this.username = username;
    this.passwd = passwd;
  }

  @Override
  public void run() {
    downloadFile(inputList);
  }

  /**
   * Download the file from param and starte the ZipHandler as an thread
   *
   * @param webUrl
   */
  public void downloadFile(String webUrl) {
    InputStream inputStream = null;
    FileOutputStream outputStream = null;
    HttpURLConnection urlConnection = null;
    String saveFilePath = null;

    try {
      URL url = new URL(webUrl);
      urlConnection = (HttpURLConnection) url.openConnection();
      urlConnection.setRequestMethod("GET");

      // generate savename
      saveFilePath = outputDir + File.separator + FilenameUtils.getName(url.getPath());

      // Building username and pass for login
      String userpass = username + ":" + passwd;
      String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userpass.getBytes());
      urlConnection.setRequestProperty("Authorization", basicAuth);

      // opens input stream from the HTTP connection
      inputStream = urlConnection.getInputStream();

      // opens an output stream to save into file
      outputStream = new FileOutputStream(new File(saveFilePath));

      // Save data with blocksize 4096
      int bytesRead = -1;
      byte[] buffer = new byte[4096];
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, bytesRead);
      }

      //Close all resources
      urlConnection.disconnect();
      inputStream.close();
      outputStream.close();

      // Start decompress Thread
      new Thread(new ZipHandler(new File(saveFilePath), outputDir)).start();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
    }

  }
}
