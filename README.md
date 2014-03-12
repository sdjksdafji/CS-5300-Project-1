CS 5300 Project 1a

This project is a java servlet for session management. The following framework are used in the project:

Spring

JSF

Primefaces.

Cookie format: key = "CS5300PROJ1SESSIONBYSW773" value = sessionId + "_" + version + "_" + expiration + "_"	+ metadata

Session table is a hash map of <key = sesion id, value = message, version, expiration timestamp>

The way I delete time-out session is to run a thread every 45 seconds. The thread go through the whole session table, compares every session content's expiration timestamp to the current time. If the timestamp is before the current time, it will be removed from session table.

SessionDemoBean is the jsf bean class.
SessionContent is a pojo storing the session information.
SessionCookieService deals with the logic of operating cookie and session.
SessionStateTableManager deal with the logic of maintaining the session table.
VersionManager generates version number by incrementing by 1 on each request.
