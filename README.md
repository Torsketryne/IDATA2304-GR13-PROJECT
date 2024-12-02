# Project

Course project for the course [IDATA2304 Computer Communication and Network Programming (2023)](https://www.ntnu.edu/studies/courses/IDATA2304/2023).

## Overview

This project implements a distributed smart greenhouse application, consisting of:

- **Sensor-Actuator Nodes**
- **Visualization Nodes**

For a detailed description of the communication protocol, refer to [protocol.md](protocol.md).

---

## Getting Started

### Run Order

To ensure proper functionality, run the files in the following order:

1. **Server** 
    ```bash
    java Server
    ```
3. **ControlPanelStarter**
    ```bash
    java ControlPanelStarter
    ```
3. **GreenhouseSimulator**
    ```bash
   java GreenhouseSimulator
    ```

---

### Running the Greenhouse

There are two versions of the greenhouse application:

1. **Command-Line Interface (CLI):**  
   Execute the `main` method in the `CommandLineGreenhouse` class.

2. **Graphical User Interface (GUI):**  
   Execute the `main` method in the `GreenhouseGuiStarter` class.  
   **Note:** Do not run the `GreenhouseApplication` class directly, as JavaFX requires additional modules that may not load properly.

---

### Running the Control Panel

The control panel is only available as a GUI version:

- Execute the `main` method in the `ControlPanelStarter` class.
- To simulate control panel operations in "fake" mode, set the `fake` variable in the main method to `true`.

---

## Simulating Events

To simulate periodic events for testing purposes:

1. Run both the greenhouse and the control panel with the `fake` command-line parameter.
2. For additional details, refer to the classes in the [`no.ntnu.run` package](src/main/java/no/ntnu/run).


To add run instructions for the three classes (`Server`, `ControlPanelStarter`, and `GreenhouseSimulator`) to the `README.md` file, you can update the **Getting Started** section like this:

