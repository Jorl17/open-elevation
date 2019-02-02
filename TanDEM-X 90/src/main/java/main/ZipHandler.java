package main;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import org.apache.commons.io.FileUtils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class ZipHandler implements Runnable {

  private File input;
  private File output;

  /**
   * @param input
   * @param output
   */
  public ZipHandler(File input, File output) {
    this.input = input;
    this.output = output;
  }

  /**
   * Copy DEM file from zip to the output dir and delete extracted dir and zip
   * file
   * @throws IOException 
   */
  private void copyDEMtoRoot() throws IOException {
    // Get extracted File name
    java.util.zip.ZipFile zip = new java.util.zip.ZipFile(input);
    Enumeration<?> entries = zip.entries();
    File extreactedFile = new File(output.getPath() + File.separator + entries.nextElement());
    zip.close();

    // DEM File location
    File tifLocation = new File(extreactedFile.getPath() + File.separator + "DEM");
    for (String tifFilePath : tifLocation.list()) {
      // Rename (mv tif to output dir)
      File tif = new File(tifLocation + File.separator + tifFilePath);
      tif.renameTo(new File(output.getPath() + File.separator + tif.getName()));
    }

    // Delete extracted dir
    FileUtils.deleteDirectory(extreactedFile);
    // Delete zip
    FileUtils.forceDelete(input);
  }

  @Override
  public void run() {
    try {
      unzip();
      copyDEMtoRoot();

      // Adding progressbar ++ to show progress
      ProgressDialog.getInstance().addOne();
    } catch (ZipException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Unzip file
   * 
   * @throws ZipException
   */
  private void unzip() throws ZipException {
    ZipFile zipFile = new ZipFile(input);
    zipFile.extractAll(output.getPath());
  }
}
