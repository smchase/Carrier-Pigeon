# Carrier Pigeon

A terminal-based messaging application using sockets built with **Java**.

Server class:
- Opens a server socket on a given local port
- Uses multithreading to handle multiple multiple connected clients

Client classes:
- Two seperate types: one to send messages to a given port, and one to recieve them
- Connects to a given local port if a server is open on it
