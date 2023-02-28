import java.net.*;
import java.io.*;

public class RunnableThread extends Thread implements Runnable {

	private String text;
	private String hostname;
	private int port;
	public long turn;

	public RunnableThread(String text, String hostname, int port) {
		this.text = text;
		this.hostname = hostname;
		this.port = port;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		try (Socket socket = new Socket(hostname, port)) {

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(text);

			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String out;

			// System.out.println("thread check 1");

			while ((out = reader.readLine()) != null) {

				System.out.println(out);
			}

			// System.out.println("thread check 2");

			socket.close();

			// System.out.println("thread check 3");

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());

		}
		// System.out.println("the end");
		long endTime = System.currentTimeMillis();
		this.turn = (endTime - startTime);
		//System.out.println("Thread turn-around time: " + turn + "ms");
	}

}
