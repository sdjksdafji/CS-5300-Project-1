CS 5300 Project 1a

This project is a java servlet for session management. The following framework are used in the project:

Spring

JSF

Primefaces.

Architecture:

The whole project consists of five parts: Web Controller, SessionDAO, RPC, LocalSessionTable, and GOSSIP.

Web Controller is mainly classes "jsfbean.*" and "service.SessionCookieService". The purpose of web controller is to handler the http request from user, return the correct session message, and do the proper cookie operation.

SessionDAO is the highest abstract data model; it is under package "dao". The purpose of this class is to add, update, retreive, and delete the session through a series of RPC calls. Thus, developer only need to call the functions under SessionDAO to access all these sessions, which are stored distributedly on the server. This interface provides developer a local-like interface.

RPC consists of two parts: server and client. All the source code about RPC Service is under the package "rpc". Each instance is a server of RPC as well as a client. The RPC client and server is built upon the classical server and client model using UDP protocol. Every request and response is encoded in a single UDP packet.

Local session table is the table which stores a hash map locally, mapping from session id to session content. The class which implements these functionality is "service.LocalSessionTableManger".

GOSSIP is the communication model in this project. All the file about GOSSIP protocol is under the pacakage "gossip". It basically does two things. In a fixed interval, which is 45s, it will choose a node randomly in its view and try to get the view of the remote node. In a random interval, which is in average 60s, it will update the bootstrap view in order to let new menber quickly catching up.


local session table is a hash map of [key = sessionId (including local session num + server ip), value = message, version, expiration timestamp]


The way I delete time-out session is to run a thread every 45 seconds. The thread go through the whole session table, compares every session content's expiration timestamp to the current time. If the timestamp is before the current time, it will be removed from session table.

Format:

Cookie format: key = "CS5300PROJ1SESSIONBYSW773" value = sessionId (including local session num + server ip) + "_" + version + "_" + expiration + "_"	+ metadata (includes 2 ip address in our case)

RPC format: All rpc request and response are formatted in a single UDP packet. The details about the udp buffer is in the file "RPC format.pdf"

Beanstalk Configuration:
In AWS EC2, we set the rules in the instance's security group, and open the ports we may use in the project. In AWS Elastic Beanstalk, we set the server to tomcat and upload our war file. 


Special notes for TA to deploy: you need to change the bootstrap content in the simpleDB to one of the public IP of your ec2 instance (any valid server IP is ok) format: "/"+ip address
