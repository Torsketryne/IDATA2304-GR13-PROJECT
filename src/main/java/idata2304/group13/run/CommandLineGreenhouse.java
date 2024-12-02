package idata2304.group13.run;

import idata2304.group13.greenhouse.GreenhouseSimulator;
import idata2304.group13.tools.Logger;

// Note: javadoc documentation was enhanced with the assistance of ChatGPT and Github Copilot for clarity and completeness.


/**
 * Run a greenhouse simulation using command-line interface (no GUI).
 * This class initializes and starts the greenhouse simulator with the specified configuration.
 *
 * <ul>
 *     <li>When the command line argument is "fake", the simulator will emulate fake events.</li>
 *     <li>When the command line argument is something else or not present, the simulator will use real socket communication.</li>
 * </ul>
 *
 * <p> The main method of this class is executed as the application entrypoint.</p>
 */
public class CommandLineGreenhouse {
  /**
   * Application entrypoint for the command-line version of the simulator.
   *
   * @param args Command line arguments, only the first one of them used: when it is "fake",
   *             emulate fake events, when it is either something else or not present,
   *             use real socket communication.
   */
  public static void main(String[] args) {
    Logger.info("Running greenhouse simulator in command line (without GUI)...");
    boolean fake = false;
    if (args.length == 1 && "fake".equals(args[0])) {
      fake = false; // Use fake events
      Logger.info("Using FAKE events");
    }
    // Create and start the greenhouse simulator
    GreenhouseSimulator simulator = new GreenhouseSimulator(fake);
    simulator.initialize();
    simulator.start();
  }
}
