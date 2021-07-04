import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ListenClient {
	public static void main(String[] args) throws Exception {
		Scanner fromUser = new Scanner(System.in);
		System.out.print("What's the port number of the server you want to join?\n> ");
		int port = Integer.parseInt(fromUser.nextLine());

		try (Socket socket = new Socket("localhost", port)) {
			Scanner fromServer = new Scanner(socket.getInputStream());
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			System.out.printf("\n***Listening to server on port %d***\n(press ENTER to exit)", port);
			toServer.println("L");

			ExecutorService thread = Executors.newSingleThreadExecutor();
			thread.submit(new GetMessages(fromServer));

			while (!fromUser.nextLine().equals("")) {}
			System.out.print("***Listener exitedb***\n");
			System.exit(0); //test
		}
	}

	private static class GetMessages implements Runnable {
		private static Scanner fromServer;

		public GetMessages(Scanner fromServer) {
			this.fromServer = fromServer;
		}

		@Override
		public void run() {
			try {
				String message = fromServer.nextLine();
				System.out.printf("\n\n%s", message);
				while (true) {
					message = fromServer.nextLine();
					System.out.printf("\n%s", message);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}