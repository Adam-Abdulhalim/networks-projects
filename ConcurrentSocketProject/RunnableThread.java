import java.net.*;
import java.io.*;

public class RunnableThread extends Thread implements Runnable {

	private String text;
	private String hostname;
	private int port;
	public long turn;
	String strArr[];
	int index;

	public RunnableThread(String text, String hostname, int port, String[] strArr, int index) {
		this.text = text;
		this.hostname = hostname;
		this.port = port;
		this.strArr = strArr;
		this.index = index;
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

			StringBuilder threadBuffer = new StringBuilder();
			
			while ((out = reader.readLine()) != null) {

				threadBuffer.append(out);
				threadBuffer.append(System.getProperty("line.separator"));
				
				//System.out.println(out);
			}
			
			long endTime = System.currentTimeMillis();
			this.turn = (endTime - startTime);
			
			threadBuffer.append(System.getProperty("line.separator"));
			threadBuffer.append("Thread turn-around time: " + turn + "ms");
			threadBuffer.append(System.getProperty("line.separator"));
			
			strArr[index] = threadBuffer.toString();
			
			// System.out.println("thread check 2");

			socket.close();

			// System.out.println("thread check 3");

		} catch (UnknownHostException ex) {

			System.out.println("Server not found: " + ex.getMessage());

		} catch (IOException ex) {

			System.out.println("I/O error: " + ex.getMessage());

		}
		// System.out.println("the end");
		
	}

}
