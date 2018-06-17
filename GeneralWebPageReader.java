// For grabbing a webpage's texts
// Author: Wiwi Samsul

import java.net.*;
import java.io.*;

public class GeneralWebPageReader {
	
	public static String[] symbolExtractor(String input)
	{
		String[] outputHold = new String[1000];
		int outputCounter = 0;
		char[] charHold = new char[8];
		
		for(int i = 0; i < (input.length()-8); i++)
		{
			if(input.charAt(i)=='s')
				if(input.charAt(i+1)=='y')
					if(input.charAt(i+2)=='m')
						if(input.charAt(i+3)=='b')
							if(input.charAt(i+4)=='o')
								if(input.charAt(i+5)=='l')
									if(input.charAt(i+6)=='=')
										if(input.charAt(i+7)=='"')
										{
											int k = 0;
											while(input.charAt(i+8+k)!='"')
											{
												charHold[k] = input.charAt(i+8+k);
												k++;
											}
											
											char[] updatedCharHold = new char[k];
											for(int l = 0; l < k; l++)
											{
												updatedCharHold[l] = charHold[l];
											}
											
											if((outputCounter > 0) && !((String.valueOf(updatedCharHold)).equals(outputHold[outputCounter-1])))
											{
												outputHold[outputCounter] = String.valueOf(updatedCharHold);
												outputCounter++;
											}
											
											if((outputCounter == 0))
											{
												outputHold[outputCounter] = String.valueOf(updatedCharHold);
												outputCounter++;
											}
										}
		}
		String[] output = new String[outputCounter];
		for(int i = 0; i < output.length; i++)
		{
			output[i] = outputHold[i];
		}
		return output;
	}
	
	public static String[] IndustryIDNumberExtractor(String input) {
		String[] outputHold = new String[1000];
		int outputCounter = 0;
		char[] charHold = new char[8];

		for (int i = 0; i < (input.length() - 9); i++) {
			if (input.charAt(i) == 'i')
				if (input.charAt(i + 1) == 'n')
					if (input.charAt(i + 2) == 'd')
						if (input.charAt(i + 3) == 'u')
							if (input.charAt(i + 4) == 's')
								if (input.charAt(i + 5) == 't')
									if (input.charAt(i + 6) == 'r')
										if (input.charAt(i + 7) == 'y')
											if (input.charAt(i + 8) == '=') {
												int k = 0;
												while (input.charAt(i + 9 + k) != '"') {
													charHold[k] = input.charAt(i + 9 + k);
													k++;
												}

												char[] updatedCharHold = new char[k];
												for (int l = 0; l < k; l++) {
													updatedCharHold[l] = charHold[l];
												}

												if ((outputCounter > 0) && !((String.valueOf(updatedCharHold))
														.equals(outputHold[outputCounter - 1]))) {
													outputHold[outputCounter] = String.valueOf(updatedCharHold);
													outputCounter++;
												}

												if ((outputCounter == 0)) {
													outputHold[outputCounter] = String.valueOf(updatedCharHold);
													outputCounter++;
												}
											}
		}
		String[] output = new String[outputCounter];
		for (int i = 0; i < output.length; i++) {
			output[i] = outputHold[i];
		}
		return output;
	}
	
	
//	public static String[] WebContentToStringArray(URL input) throws Exception
//	{
//		BufferedReader in = new BufferedReader(new InputStreamReader(input.openStream()));
//		String inputLine; String[] output = new String[0];
//		
//		int webpageLineCounter = 0; int webpageWordCounter = 0;
//        while ((inputLine = in.readLine()) != null){
//        	webpageLineCounter++;
//        	output = WebpageReader.wordSplitter(inputLine);
//        	if(output.length > 0)
//        		for(int i = 0; i < output.length; i++)
//        		{}
//        }
//		return output;
//	}
	
    public static void main(String[] args) throws Exception {

    	// input below here:
        URL oracle = new URL("https://research.tradeking.com/research/quotes/price-quote.asp?mcsymbol=LLY");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        String inputLine; String[] symbols;
        while ((inputLine = in.readLine()) != null){
            System.out.println(inputLine); // prints all the content of the webpage
            
//          commands needed for symbolExtractor starts here
//        	symbols = symbolExtractor(inputLine);
//        	for(int i = 0; i < symbols.length; i++)
//        	{
//        		System.out.println("https://research.tradeking.com/research/quotes/stock-news.asp?mcsymbol=" + symbols[i]);
//        	}
//        	commands needed for symbolExtractor ends here

            // commands needed for IndustryIDNumberExtractor starts here
//            symbols = IndustryIDNumberExtractor(inputLine);
//        	for(int i = 0; i < symbols.length; i++)
//        	{
//        		System.out.println("https://research.tradeking.com/research/markets/sectors.asp?industry=" + symbols[i]);
//        	}
            // commands needed for IndustryIDNumberExtractor ends here
            
//            commands for printing out the content of the webpage, split word for word starts here
//            String[] splittedWords = WebpageReader.wordSplitter(inputLine);
//            System.out.println(splittedWords.length);
//            for(int i = 0; i < splittedWords.length; i++){
//            	System.out.println(splittedWords[i]);
//            }
//            commands for printing out the content of the webpage, split word for word ends here
            
        }
        in.close();
    }
}
