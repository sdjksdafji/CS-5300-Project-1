CS 5300 Project 1a

This project is a java servlet for session management. The following framework are used in the project:

Spring

JSF

Primefaces.

Cookie format: key = "CS5300PROJ1SESSIONBYSW773" value = sessionId (including local session num + server ip) + "_" + version + "_" + expiration + "_"	+ metadata (includes 2 ip address in our case)

local session table is a hash map of <key = sessionId (including local session num + server ip), value = message, version, expiration timestamp>

The way I delete time-out session is to run a thread every 45 seconds. The thread go through the whole session table, compares every session content's expiration timestamp to the current time. If the timestamp is before the current time, it will be removed from session table.

