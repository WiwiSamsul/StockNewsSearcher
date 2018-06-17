// For generating stock links based on research.TradeKing.com
// Author: Wiwi Samsul

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class StockListGenerator {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(new File("data/TradeKingIndustryList")); // scan from text file containing stocks URLs

		String retrievedURL; // prepares a variable to hold string value
		
		while (scan.hasNext()) {
			
			retrievedURL = scan.next(); int o = retrievedURL.length();
//			System.out.println(o);

			URL oracle = new URL(retrievedURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine; // initialization
			String[] splittedWords; // initialization
			String[] symbols; // initialization

			while ((inputLine = in.readLine()) != null) {
//	          commands needed for symbolExtractor starts here
	        	symbols = GeneralWebPageReader.symbolExtractor(inputLine);
	        	for(int i = 0; i < symbols.length; i++)
	        	{
	        		System.out.println("https://research.tradeking.com/research/quotes/stock-news.asp?mcsymbol=" + symbols[i]);
	        	}
//	        	commands needed for symbolExtractor ends here
			}
	}

}
}
