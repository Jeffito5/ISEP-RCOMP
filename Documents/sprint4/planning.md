RCOMP 2020-2021 Project - Sprint 4 planning
===========================================
### Sprint master: 1190827 ###

# 1. Sprint's backlog #

This sprint focus on data transaction between applications using TCP protocol. A data transaction always comprises
sending a request message, followed by receiving a reply message.

# 2. Technical decisions and coordination #
Each request or response consists of sending a sequence of bytes according to the same general format of message:

## Code: ##
    - 0- Test request with no effect other than returning a response with code 2. This request does not carry data.
    - 1- End of connection request. The server must return a response with code 2, after which both applications must close the TCP connection.
    - 2- Empty response (does not carry data) that acknowledges the receipt of a request. It is sent in response to orders with code 0 and code 1, but can be used in other contexts.
    - 3- Send files.

# 3. Subtasks assignment #

  * Task 4 - Danilton Lopes(1191240) -
  Implement communications with the Distribution Center.

  * Task 2 - Luís Araújo (1190827) -
  Implement communications with the Host applications. Manage the list of hosts and always there is a file to upload, proceed with sending it.

  * Task 1 - Marisa Pereira (1191596) -
  Implement the HTTP server and make available through it the predicted dashboard.
