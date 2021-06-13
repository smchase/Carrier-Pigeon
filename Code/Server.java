import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket listener = new ServerSocket(1111);
		Socket socket = listener.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println("hi!");
	}
}