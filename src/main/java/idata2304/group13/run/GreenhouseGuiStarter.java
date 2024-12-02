package idata2304.group13.run;

import idata2304.group13.gui.greenhouse.GreenhouseApplication;
import idata2304.group13.tools.Logger;

// Note: javadoc documentation was enhanced with the assistance of ChatGPT and Github Copilot for clarity and completeness.

/**
 * Starter for GUI version of the greenhouse simulator.
 * <p>
 * This class serves as the entry point for lunching the Greenhouse GUI application.
 * It determines whether to use fake events or real socket communication.
 * based on the command line arguments. the fake mode simulates the events, while the real mode communicates with the
 * server.
 * </p>
 *
 * @author Girst
 */
public class GreenhouseGuiStarter {
  /**
   * Entrypoint gor the Greenhouse GUI application.
   *
   * @param args Command line arguments, only the first one of them used: when it is "fake",
   *             emulate fake events, when it is either something else or not present,
   *             use real socket communication.
   */
  public static void main(String[] args) {
    boolean fake = false;
    if (args.length == 1 && "fake".equals(args[0])) {
      fake = true;
      Logger.info("Using FAKE events");
    }
    GreenhouseApplication.startApp(fake);
  }
}
