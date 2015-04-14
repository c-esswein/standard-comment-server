package proglab.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {

	/**
	 * Gets the HTML content of a website, using modified headers to make the
	 * server believe the request was like AJAX
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static DownloadResponse Download(String url)
			throws MalformedURLException, IOException {
		StringBuffer response = new StringBuffer();

		HttpURLConnection con = (HttpURLConnection) (new URL(url))
				.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header. Required. Otherwise would lead to 404 Error,
		con.setRequestProperty("X-Requested-With", "XMLHttpRequest");

		int responseCode = con.getResponseCode();
		if (responseCode != 200)
			throw new IllegalStateException("Response Code: " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));

		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine + "\n");
		}
		in.close();
				


		PrintWriter pw = new PrintWriter("C:\\temp\\out.html");
		pw.print(response.toString());
		pw.close();

		DownloadResponse r = new DownloadResponse();
		r.setHtml(response.toString());
		r.setUrl(con.getURL().toString());

		return r;
	}

	public static String GenerateUrlToMain() {
		return "http://www.derstandard.at";
	}

	public static String GenerateUrlToCategory(String category) {
		return "http://www.derstandard.at" + category + "?_chron=t";
	}

	public static String GenerateUrlToArticle(long ArticleId) {
		return "http://www.derstandard.at/" + ArticleId;
	}

	/**
	 * Generates a link to the comments of the first page (paginator of the
	 * comments)
	 * 
	 * @param forumKeyId
	 * @return
	 */
	public static String GenerateUrlToComments(long forumKeyId) {
		return GenerateUrlToComments(forumKeyId, 1);
	}

	/**
	 * Generates a link to the comments of one page (paginator of the comments)
	 * 
	 * @param forumKeyId
	 * @param page
	 * @return
	 */
	public static String GenerateUrlToComments(long forumKeyId, int page) {
		String url = "http://mobil.derstandard.at/Forum/Postings?";
		url += "ForumKey.ForumKeyId=" + forumKeyId;
		url += "&ForumKey.ForumKeyType=1&SelectedSortTypeForDropdown=0&Filter.SelectedFilterType=0&";
		url += "CurrentPage=" + page;
		url += "&SelectedPostingId=&X-Requested-With=XMLHttpRequest&_=0";

		return url;
	}
}
