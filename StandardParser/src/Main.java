import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Main {

	public static void main(String[] args) {
		
		
		try {
			Document doc = Jsoup.connect("http://www.orf.at").get();
			System.out.println(doc.getElementById("nnav"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finish");
	}

}
