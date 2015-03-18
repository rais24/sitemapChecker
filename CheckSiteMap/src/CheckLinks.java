
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CheckLinks {

	public static void main(String[] args){

		String sitemapURL = args[0];
		URL url = null;
		try {
			url = new URL(sitemapURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> SiteMapURLS=parseXML(url);

		ValidateURLS(SiteMapURLS);
	}


	private static List<String> parseXML(URL sitemap) {
		//List<String> urlList = new ArrayList<String>();
		List<String> URLList= new ArrayList<String>();
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {

			InputStream input = (sitemap).openStream();

			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(input);

			String url = null;
			int event = xmlStreamReader.getEventType();
			while(true){
				switch(event) {
				case XMLStreamConstants.START_ELEMENT:
					if(xmlStreamReader.getLocalName().equals("url"))
					{
						xmlStreamReader.nextTag();
						URLList.add(xmlStreamReader.getElementText());
					}


				case XMLStreamConstants.END_ELEMENT:
					if(xmlStreamReader.getLocalName().equals("url"))
					{
					//	URLList.add(url);
					}
					break;
				}
				if (!xmlStreamReader.hasNext())
					break;

				event = xmlStreamReader.next();
			}

		} catch (Exception  ex) {
			ex.printStackTrace();
		}
		return URLList;
	}

	public static void  ValidateURLS(List<String> SiteMapURLS)
	{
		try{

			for (int i=0 ; i<SiteMapURLS.size();i++)
			{
				String urlString= SiteMapURLS.get(i);
				
				URL	url = new URL(urlString);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.connect();

				int httpStatusCode = connection.getResponseCode();
				
				System.out.println("URL :"+ urlString+ " Status: " +httpStatusCode);
			}
		}
		catch(Exception ex)
		{
		//	ex.printStackTrace();	
		}
		//return true;
	}
}

