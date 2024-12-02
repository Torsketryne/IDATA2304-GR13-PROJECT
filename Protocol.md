# Communication protocol

This document describes the protocol used for communication between the different nodes of the
distributed application.

## Terminology

* Sensor - a device which senses the environment and describes it with a value (an integer value in
  the context of this project). Examples: temperature sensor, humidity sensor.
* Actuator - a device which can influence the environment. Examples: a fan, a window opener/closer,
  door opener/closer, heater.
* Sensor and actuator node - a computer which has direct access to a set of sensors, a set of
  actuators and is connected to the Internet.
* Control-panel node - a device connected to the Internet which visualizes status of sensor and
  actuator nodes and sends control commands to them.
* Graphical User Interface (GUI) - A graphical interface where users of the system can interact with
  it.
* metadata - Key or identifier. Metadata defines what its associated data means. 
For example temperature could have the metadata Celsius and data 23

## The underlying transport protocol

The transport-layer protocol being used is TCP. It is a protocol that fits the need for reliability, robustness and control.

Port number 1313 for the interconnecting server

## The architecture

The network consists a server and two types of nodes, capable of connecting to the server 
and communicating through the server to other nodes from the other type.

The network will be client-server. Each node will be a client connecting to a server with the use of sockets

There is only one server. There can be multiple of each types of nodes.
The two types of nodes are SensorActuatorNodes and ControlPanel

When a node connects to the server, a new thread with a client handler will be created to handle 
the node while the server keeps scouting for new nodes

## The flow of information and events

A node can connect to the server and send a command for creating a relationship between itself
and a different node of the other type. A node can then send commands to the server, which will in turn
interpret them and run a corresponding function on the paired node

Commands are given in a specific form always starting with MessageType:<Type of message>;
The semicolon separate values with their metadata

## Connection and state

The communication protocol is connection-oriented and stateful.

## Types, constants

All commands start with MessageType. When the command processor gets a command it will try 
to understand the command by first checking what type of command it is.
Different message types illicit different actions

## Message format

A message in general will be a value as data accompanied by a tag as metadata. For example "Temperature:" 20

### Error messages

IOException.

## An example scenario

Server starts. It opens a socket for listening on port 1313. Control panel start and attempts to connect to server.
Connection between server and control panel is passed to a client handler on a new thread. Control panel
immediately shares its unique id with server. Server stores the id along with the clientHandler handling the panel

SensorActuatorNode attempts to connect to server. Connection between server and SensorActuatorNode 
is passed to another client handler on a new thread. The node immediately shares its unique id with server. 
Server stores the id along with the clientHandler. 

SensorActuatorNode attempts to connect with control panel. 
The node sends a command which the message processor process. If a connection can be made the client handler
will update a Hashmap containing all client relationships, which all handlers have access to.

## Reliability and security

It doesn't do everything a user may expect it to do.
System is reliable when ran in the correct sequence. 
If something goes wrong there are few attempts of resolving the issues withing the program.