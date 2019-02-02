package main;

import me.tongfei.progressbar.ProgressBar;

/**
 * Progressbar Singleton
 * 
 * @author crypto
 *
 */
public class ProgressDialog {
  private static ProgressDialog instance;
  private ProgressBar           progressBar = null;

  public synchronized static ProgressDialog getInstance() {
    if (ProgressDialog.instance == null) {
      ProgressDialog.instance = new ProgressDialog();
    }
    return ProgressDialog.instance;
  }

  // Create Progressbar
  private ProgressDialog() {
    progressBar = new ProgressBar("Downloading...", 0);
  }

  /**
   * Add +1 to progressbar
   */
  public synchronized void addOne() {
    progressBar.step();
  }

  /**
   * Set Max progress to progressBar
   * 
   * @param i
   */
  public synchronized void setMax(int i) {
    progressBar.maxHint(i);
  }
}