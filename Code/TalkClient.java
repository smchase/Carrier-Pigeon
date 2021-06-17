import java.io.*;
import java.net.*;
import java.util.*;

public class TalkClient {
	public static void main(String[] args) throws Exception {
		Scanner fromUser = new Scanner(System.in);
		System.out.print("What's the port number of the server you want to join?\n> ");
		int port = Integer.parseInt(fromUser.nextLine());
		System.out.print("What nickname do you want to go by?\n> ");
		String nick = fromUser.nextLine();

		try (Socket socket = new Socket("localhost", port)) {
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			System.out.printf("\n***Talking to server on port %d as %s***\n(use '.leave' to leave)\n\n> ", port, nick);
			toServer.println("T"+nick);

			while (true) {
				String message = fromUser.nextLine();
				if (message.equals(".leave")) break;
				else System.out.print("> ");
				toServer.println(message);
			}
		}
	}
}