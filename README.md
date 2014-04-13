CS 5300 Project 1a

This project is a java servlet for session management. The following framework are used in the project:

Spring

JSF

Primefaces.

Cookie format: key = "CS5300PROJ1SESSIONBYSW773" value = sessionId (including local session num + server ip) + "_" + version + "_" + expiration + "_"	+ metadata (includes 2 ip address in our case)

The whole project consists of five parts: Web Controller, SessionDAO, RPC, LocalSessionTable, and GOSSIP.

Web Controller is mainly classes "jsfbean.*" and "service.SessionCookieService". The purpose of web controller is to handler the http request from user, return the correct session message, and do the proper cookie operation.

SessionDAO is the highest abstract data model; it is under package "dao". The purpose of this class is to add, update, retreive, and delete the session through a series of RPC calls. Thus, developer only need to call the functions under SessionDAO to access all these sessions, which are stored distributedly on the server. This interface provides developer a local-like interface.

RPC consists of two parts: server and client. All the source code about RPC Service is under the package "rpc". Each instance is a server of RPC as well as a client. The RPC client and server is built upon the classical server and client model using UDP protocol. Every request and response is encoded in a single UDP packet.




local session table is a hash map of <key = sessionId (including local session num + server ip), value = message, version, expiration timestamp>


The way I delete time-out session is to run a thread every 45 seconds. The thread go through the whole session table, compares every session content's expiration timestamp to the current time. If the timestamp is before the current time, it will be removed from session table.

