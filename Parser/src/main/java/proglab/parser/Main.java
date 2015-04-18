package proglab.parser;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

	public static void main(String[] args) {

		//test1();
		test2();
	}
	
	public static void test2()
	{
		Crawler c = new Crawler();
		c.Crawl();
	}

	public static void test1() {
		// String url =
		// "http://mobil.derstandard.at/forum/1/2000012787886?_=1426507619442";
		
		//String url = "http://mobil.derstandard.at/Forum/Postings?ForumKey.ForumKeyId=2000012787886&ForumKey.ForumKeyType=1&SelectedSortTypeForDropdown=0&Filter.SelectedFilterType=0&CurrentPage=3&SelectedPostingId=&X-Requested-With=XMLHttpRequest&_=0";
		String url = Downloader.GenerateUrlToComments(2000012787886L,1);
		String html="";
		try {
			html = Downloader.Download(url).getHtml();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ParseComments(html);
	}
	
	

	private static void ParseComments(String html) {
		Document doc = Jsoup.parse(html);

		// System.out.println(doc.getElementById("sorter"));

		/*HtmlParser parser = new HtmlParser(html,0);
		System.out.println("Page count: " + parser.GetPagecount());

		for (Element posting : doc.getElementsByClass("posting")) {
			int timestamp;

			// Getting timestamp
			Element date = posting.getElementsByClass("timestamp").first();
			timestamp = Integer.parseInt(date.attr("data-livestamp"));

			// @PK: get more elements!
		}*/
	}
}
