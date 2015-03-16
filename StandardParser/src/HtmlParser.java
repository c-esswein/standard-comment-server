import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HtmlParser {
	
	private Document doc;
	
	public HtmlParser(String html)
	{
		doc = Jsoup.parse(html);
	}
	
	public int GetPagecount()
	{	
		Element buttonLast = doc.select("button.std-button.last").first();
		//buttonLast = doc.getElementsByClass("paging").first();
		
		if(buttonLast == null)
			throw new IllegalArgumentException("Unable to get pagecount. HTML element 'std-button last' not fount in HTML.");
		
		
		return Integer.parseInt(buttonLast.attr("data-pagenumber"));
	}
	
	public int GetCommentcount()
	{	
		Elements elements = doc.getElementsByClass("posting-container");
		//buttonLast = doc.getElementsByClass("paging").first();
		
		if(elements == null)
			throw new IllegalArgumentException("Unable to get Comment Count. HTML element 'posting-container' not fount in HTML.");
		
		
		return elements.size() ;
	}
	
	
}
