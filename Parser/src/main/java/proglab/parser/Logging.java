package proglab.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logging {
	public static void Log(String log) {
		System.out.println(log);

		try {
			
			FileWriter fw = new FileWriter("log.txt",true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(fw);
			
			pw.println(log);
			pw.close();
		} catch (IOException e) {
			System.out.println("Error while writing log: " + e.getMessage());
		}
	}
}
