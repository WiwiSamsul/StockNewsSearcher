// For parsing companies' revenue and loss figures
// Author: Wiwi Samsul

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class StockRevLossNewsFilter {

	/**
	 * Feed a String, take away the dollar sign then return the value
	 * @param String
	 * @return double
	 */
	public static double DollarSignAndNumberExtractor(String input) {
		double output = 0; // initialization
		char[] hold = new char[input.length() - 1];
		String hold2 = null;
		for (int i = 0; i < (input.length() - 1); i++) {
			if((input.length() > 2) && ((input.charAt(i + 1) == '.') && ((input.charAt(i + 2) == '.'))))
				break; // of double dots detected, skip
			if ((input.charAt(i + 1) == ',') || (input.charAt(i + 1) == 'M') || (input.charAt(i + 1) == '<') || (input.charAt(i + 1) == '/') || (input.charAt(i + 1) == ';') || (input.charAt(i + 1) == '%') || (input.charAt(i + 1) == 'K') || (input.charAt(i + 1) == '-'))
				break; // if comma or "M" or "<" detected, then it's the end of the number
			hold[i] = input.charAt(i + 1);
		}
		hold2 = String.valueOf(hold);
		output = Double.parseDouble(hold2);
		return output;
	}
	
	/**
	 * Feed a String, return a boolean true/false. If Loss >= Est Loss, then return true
	 * @param String
	 * @return true if loss >= estimated loss
	 */
	public static boolean LossVsEstLossChecker(String input) {
		String[] splittedWords = WebpageReader.wordSplitter(input);
		double loss = 0;
		double lossEst = 0;
		boolean output = false; // initialization

		for (int i = 0; i < (splittedWords.length - 1); i++) // check for all words in a line, one by one
		{
			// System.out.println(splittedWords[i]);
			if ((splittedWords[i].equals("Loss")) && (splittedWords[i + 1].charAt(0) == '$') && (loss == 0.0)) {
				// if "loss" is 0, then the first one must be loss, not the estimated loss
				loss = DollarSignAndNumberExtractor(splittedWords[i + 1]);
			}
			if ((splittedWords[i].equals("Loss")) && (splittedWords[i + 1].charAt(0) == '$') && (loss > 0)) {
				// if "loss" is greater than 0, then the next money is estimated loss
				lossEst = DollarSignAndNumberExtractor(splittedWords[i + 1]);
			}
		}
//		System.out.println(loss);
//		System.out.println(lossEst);
		if (loss >= lossEst) {
			output = true;
//			System.out.println("Bad news, loss >= estimated loss.");
		} else {
			output = false;
//			System.out.println("Good news! Loss < estimated loss.");
		}

		return output;
	}
	
	/**
	 * Feed a String, return a boolean true/false. If Rev >= Est Rev, then return true
	 * @param String
	 * @return true if revenue >= estimated revenue
	 */
	public static boolean RevVsEstRevChecker(String input)
	{
		String[] splittedWords = WebpageReader.wordSplitter(input);
		double rev = 0;
		double revEst = 0;
		boolean output = false; // initialization
		boolean revFlag = true;

		for (int i = 0; i < (splittedWords.length - 1); i++) // check for all words in a line, one by one
		{
			// System.out.println(splittedWords[i]);
			if ((splittedWords[i].equals("Rev.")) && (splittedWords[i + 1].charAt(0) == '$') && (rev == 0.0)) {
				// if "rev" is 0, then the first one must be rev, not the estimated rev
//				System.out.println(splittedWords[i + 1]);
				rev = DollarSignAndNumberExtractor(splittedWords[i + 1]);
				revFlag = false;
			}
			if ((splittedWords[i].equals("Est.")) && (splittedWords[i + 1].charAt(0) == '$') && (rev > 0) && !(revFlag)) {
				// if "rev" is greater than 0, then the next money is estimated rev
//				System.out.println(splittedWords[i + 1]);
				revEst = DollarSignAndNumberExtractor(splittedWords[i + 1]);
			}
		}
//		System.out.println(rev);
//		System.out.println(revEst);
		if (rev > revEst) {
			output = true;
//			System.out.println("Good news! Rev >= estimated rev.");
		} else {
			output = false;
//			System.out.println("Bad news. Rev < estimated rev.");
		}

		return output;
	}
	
	/**
	 * Feed a String, return a boolean true/false. If Sales >= Est Sales, then return true
	 * @param String
	 * @return true if sales >= estimated sales
	 */
	public static boolean SalesVsEstSalesChecker(String input)
	{
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(new File("data/TradeKingStocksDataPages.txt")); // scan from text file containing stocks URLs
		
		String retrievedURL; // prepares a variable to hold string value
		
		while (scan.hasNext()) {
			retrievedURL = scan.next(); int o = retrievedURL.length();
//			System.out.println(o);

			URL oracle = new URL(retrievedURL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

			String inputLine; // initialization
			String[] splittedWords; // initialization

			while ((inputLine = in.readLine()) != null) {
//				System.out.println(inputLine); // print out all the content of the page, "inputLine" is a string
				splittedWords = WebpageReader.wordSplitter(inputLine);
				int n = splittedWords.length; // System.out.println(n);

				if (n > 0) { // to make sure that the entry line is not blank. Sometimes webpage has empty lines
					for (int i = 0; i < n; i++) { // checking for all words contained in the page
						
						// for debugging process: printing stock output starts here
//						for (int l = 71; l < o; l++) // the stock name's
//						// first char is
//						// at 71st char
//						{
//							System.out.print(retrievedURL.charAt(l));
//						}
//						System.out.println();
//						System.out.println("========");
						// for debugging process: printing stock output ends here

						// if the word "Loss" is detected, then send the whole line to loss checking program
//						if(splittedWords[i].equals("Loss"))
//						{
////							String date = WebpageReader.dateRetriever(inputLine); // grab the latest date
////							System.out.println(date);
////							boolean dateCheck = WebpageReader.todaysDateFilter(date);
//							boolean lossCheck;
//							lossCheck = LossVsEstLossChecker(inputLine);
//							if(!lossCheck)
//							{
//								System.out.println("Loss Check Detected!");
//								System.out.print("Stock: ");
//
//								for (int l = 71; l < o; l++) // the stock name's
//																// first char is
//																// at 71st char
//								{
//									System.out.print(retrievedURL.charAt(l));
//								}
//								System.out.println();
//								System.out.println("========");
//							}
//								
//						}
						
						// if the word "Rev" is detected, then send the whole line to revenue checking program
						if(splittedWords[i].equals("Rev"))
						{
//							String date = WebpageReader.dateRetriever(inputLine); // grab the latest date
//							boolean dateCheck = WebpageReader.todaysDateFilter(date);
							boolean revCheck;
							revCheck = RevVsEstRevChecker(inputLine);
							if(revCheck)
							{
								System.out.println("Rev Check Detected!"); // once
								System.out.print("Stock: ");

								for (int l = 71; l < o; l++) // the stock name's
																// first char is
																// at 71st char
								{
									System.out.print(retrievedURL.charAt(l));
								}
								System.out.println();
								System.out.println("========");
							}
						}
						
//						// if the word "Sales" is detected, then send the whole line to sales checking program
//						if(splittedWords[i].equals("Sales"))
//						{
//							boolean salesCheck;
//							salesCheck = SalesVsEstSalesChecker(inputLine);
//						}
						
						
					}
				}//...
			}
		}

	}

}
