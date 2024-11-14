package no.ntnu.run;

public class CommandLineGreenhouse {

    public static void main(String[] args) {
        new CommandLineGreenhouse().run(args);
    }

    /**
     * Runs the greenhouse simulator based on command-line input.
     *
     * @param args Command line arguments; if the first argument is "simulate", use simulated data.
     */
    private void run(String[] args) {
        System.out.println("Running greenhouse simulator in command line (without GUI)...");

        boolean isSimulationMode = checkSimulationMode(args);
        if (isSimulationMode) {
            System.out.println("Using simulated data.");
        }

        /**
         * Used template code so should not be used in the final implementation.
         */
        // GreenhouseSimulator simulator = new GreenhouseSimulator(isSimulationMode);
        // simulator.initialize();
        // simulator.start();

    }

    /**
     * Checks if the simulator should run in simulation mode.
     */
    private boolean checkSimulationMode(String[] args) {
        return true;
    }
}
