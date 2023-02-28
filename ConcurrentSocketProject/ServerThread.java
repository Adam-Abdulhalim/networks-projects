import java.io.*;
import java.net.*;

public class ServerThread extends Thread {

	private Socket socket;

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try {

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			String text;
			String command = null;

			text = reader.readLine();
			/*
			 * if (text == null) { break; }
			 */
			if (text.equals("1")) {

				command = "date";

			} else if (text.equals("2")) {

				command = "uptime";

			} else if (text.equals("3")) {

				command = "free";

			} else if (text.equals("4")) {

				command = "netstat -all";

			} else if (text.equals("5")) {

				command = "who";

			} else if (text.equals("6")) {

				command = "ps -aux";

			}

			Process p = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line;

			while ((line = stdInput.readLine()) != null) {

				writer.println(line);
			}

			socket.close();

		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
