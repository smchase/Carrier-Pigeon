import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
	private static HashSet<PrintWriter> listenerList = new HashSet<PrintWriter>();
	private static ExecutorService pool = Executors.newCachedThreadPool();

	public static void main(String[] args) throws Exception {
		Scanner fromUser = new Scanner(System.in);
		System.out.print("What port should this instance of Carrier Pigeon use?\n> ");
		int port = Integer.parseInt(fromUser.nextLine());

		try (ServerSocket gatekeeper = new ServerSocket(port)) {
			System.out.printf("\n***Server is running on port %d***\n(press ENTER to close)", port);
			pool.submit(new Waiter(gatekeeper));

			while (!fromUser.nextLine().equals("")) {}
			System.out.print("***Server closed***\n");
			System.exit(0);
		}
	}

	private static class Waiter implements Runnable {
		private static ServerSocket gatekeeper;

		public Waiter(ServerSocket gatekeeper) {
			this.gatekeeper = gatekeeper;
		}

		@Override
		public void run() {
			try {
				while (true) {
					Socket socket = gatekeeper.accept();
					Scanner fromClient = new Scanner(socket.getInputStream());
					String firstMessage = fromClient.nextLine();
					switch (firstMessage.charAt(0)) {
						case 'T': pool.submit(new Talker(socket, firstMessage.substring(1))); break;
						case 'L': listenerList.add(new PrintWriter(socket.getOutputStream(), true)); break;
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private static class Talker implements Runnable {
		private Socket socket;
		private String nick;

		public Talker(Socket socket, String nick) {
			this.socket = socket;
			this.nick = nick;
		}

		@Override
		public void run() {
			try {
				Scanner fromClient = new Scanner(socket.getInputStream());
				for (PrintWriter toClient: listenerList) {
					toClient.printf("[+] %s has joined\n", nick);
				}

				while (fromClient.hasNextLine()) {
					String message = fromClient.nextLine();
					for (PrintWriter toClient: listenerList) {
						toClient.printf("%s: %s\n", nick, message);
					}
				}

				for (PrintWriter toClient: listenerList) {
					toClient.printf("[-] %s has left\n", nick);
				}
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}