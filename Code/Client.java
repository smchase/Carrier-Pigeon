import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("localhost", 1111);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("server says "+in.readLine());
	}
}