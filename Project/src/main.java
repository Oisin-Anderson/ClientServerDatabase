import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import myApp.JDBCMainWindow;

public class main {
	
	public static void Request() throws XmlPullParserException, IOException {
		CloseableHttpResponse httpResponse;
    	CloseableHttpClient httpClient;
    	String line = "";
    	try {
    		URI uri = new URIBuilder()
    				.setScheme("http")
    				.setHost("localhost")
    				.setPort(8080)
    				.setPath("/Systems/rest/houses")
    				.build();
    		
    		HttpGet getRequest = new HttpGet(uri);
    		getRequest.setHeader("Accept", "application/json");
    		
    		httpClient = HttpClients.createDefault();
    		httpResponse = httpClient.execute(getRequest);
    		
    		HttpEntity entity = httpResponse.getEntity();
    		Scanner in = new Scanner(entity.getContent());
    		
    		
    		while(in.hasNextLine()) {
    			line = in.nextLine();
    		}

            System.out.println("GetHouses");
    		System.out.println(line);
    		
    		in.close();
    	} catch (Exception e) {
    		System.err.println(e.getMessage());
    		e.printStackTrace();
    	}
    	
    	
    	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        xpp.setInput( new StringReader ( line ) );

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
         if(eventType == XmlPullParser.START_DOCUMENT) {
             System.out.println("Start document");
         } else if(eventType == XmlPullParser.START_TAG) {
             System.out.println("Start tag "+xpp.getName());
         } else if(eventType == XmlPullParser.END_TAG) {
             System.out.println("End tag "+xpp.getName());
         } else if(eventType == XmlPullParser.TEXT) {
             System.out.println("Text "+xpp.getText());
         }
         eventType = xpp.next();
        }

        System.out.println("End document");
	}

	public static void main(String[] args) throws XmlPullParserException, IOException{
		//Request();
		 new JDBCMainWindow();    	
	}

}
