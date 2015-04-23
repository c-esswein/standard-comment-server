package proglab.parser;

public class Main {

	public static void main(String[] args) {

		Logging.Log("Crawler started.");
		try {
			Crawler c = new Crawler();
			c.Crawl();
		} catch (Exception e) {
			Logging.Log("General Exception: " + e.getMessage());
		}
		Logging.Log("Crawler finished.");
	}
}
