package proglab.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logging {
	public static void Log(String log) {
		System.out.println(log);

		try {

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String filename = df.format(date) + ".txt";

			FileWriter fw = new FileWriter(filename, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(fw);

			pw.println(new Date().toString() + ": " + log);
			pw.close();
		} catch (IOException e) {
			System.out.println("Error while writing log: " + e.getMessage());
		}
	}
}
