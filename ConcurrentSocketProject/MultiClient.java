import java.io.Console;

public class MultiClient {

	public static void main(String[] args) {
		if (args.length < 2)
			return;

		String hostname = args[0];
		int port = Integer.parseInt(args[1]);

		Console console = System.console();
		String text;
		int clientnum;
		

		do {
			long totalTurn = 0;
			text = console.readLine("Enter one of the following commands: \n"
					+ "1 - Host Current Date/Time\n"
					+ "2 - Host Uptime\n"
					+ "3 - Host Memory Use\n"
					+ "4 - Host Netstat\n"
					+ "5 - Host Current Users\n"
					+ "6 - Host Running Processes\n"
					+ "7 - Exit\n");

			if (text.equals("7")) {

				break;
			}

			String clientn = console.readLine("Enter amount of clients: ");
			clientnum = Integer.parseInt(clientn);

			RunnableThread myThreads[] = new RunnableThread[clientnum];
			String myStrArr[] = new String[clientnum];

			//long startTime = System.currentTimeMillis();
			
			System.out.println("\nServer: \n");
			for (int i = 0; i < clientnum; i++) {

				myThreads[i] = new RunnableThread(text, hostname, port, myStrArr, i);
				myThreads[i].start();
				
				// System.out.println("Thread starting....");

			}

			for (int j = 0; j < clientnum; j++) {
				try {

					// System.out.println("Thread closing....");
					myThreads[j].join();
					totalTurn = totalTurn + myThreads[j].turn;
					
				} catch (InterruptedException e) {
				}
			}
			
			for (int k = 0; k < clientnum; k++) {
				
				System.out.println(myStrArr[k]);
			}
			
			//long endTime = System.currentTimeMillis();
			
			//long totalTime = (endTime - startTime);
			
			System.out.println("\nAverage turn-around time: " + (totalTurn / clientnum) + "ms\n");
			System.out.println("Total turn-around time for " + clientnum + " clients: " + totalTurn + "ms\n");
			//System.out.println("Total turn-around time is " + totalTime);

		} while (!text.equals("7"));

	}
}