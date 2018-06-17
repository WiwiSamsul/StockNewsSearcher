// For detecting certain words in stock news
// Author: Wiwi Samsul

import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class WebpageReader {

	public static boolean todaysDateFilter(String input)
	{
		Date today = Calendar.getInstance().getTime();
		SimpleDateFormat today2 = new SimpleDateFormat("yyyyMMdd");
		String dateInString = today2.format(today);
		String yearInString = new String(); String monthInString = new String(); String dayInString = new String();
		for(int i = 0; i < 4; i++)
			yearInString += dateInString.charAt(i);
		for(int i = 0; i < 2; i++)
			monthInString += dateInString.charAt(4+i);
		for(int i = 0; i < 2; i++)
			dayInString += dateInString.charAt(6+i);
		
		int firstDigitOfMonth = (int) monthInString.charAt(0);
		String updateMonth = new String();
		
		if (firstDigitOfMonth == 48) {
			updateMonth = "" + monthInString.charAt(1);
		} else {
			updateMonth = monthInString;
		}
		
		String dateForCompare = updateMonth + "/" + dayInString + "/" + yearInString;
		boolean output = false;
		if(dateForCompare.equals(input))
			output = true;
		return output;
	}
	
	/**
	 * Feed a line from news page, if "ET" detected, then retrieve the date. Output is a String
	 */
	public static String dateRetriever(String input)
	{
		String output = null;
		char[] date = new char[9]; char[] date2 = new char[10];
		String[] splittedWords = wordSplitter(input);
		int n = splittedWords.length;
		for(int l = 0; l < (n-1); l++)
		{

			if (splittedWords[l].equals("ET")) {
				if (n < 2) break;
				
				if(splittedWords[l+1].length() < 7) break;
				
				System.out.println(splittedWords[l+1]);
				
				char firstChar = splittedWords[l+1].charAt(0); char secondChar = splittedWords[l+1].charAt(1);
				// System.out.println(firstChar);
				int month = (int) firstChar; int nextCharOfMonth = (int) secondChar;
				// Note: conversion from char to int, char "9" equals
				// number 57, char "10" equals number 58
				// System.out.println(month);
				if (nextCharOfMonth == 47) // if the next char is "/", then the month is single digit
				{
					for (int i = 0; i < 9; i++) {
						date[i] = splittedWords[l+1].charAt(i); // grab 9
															// next
															// characters
					}
					output = new String(date);
				} else {
					for (int i = 0; i < 10; i++) {
						date2[i] = splittedWords[l+1].charAt(i); // otherwise,
															// grab 10
															// next
															// characters
					}
					output = new String(date2);
				}
			}
		}
		return output;
	}
	
	/**
	 * Feed a String, return a String[] array containing individual words
	 * @param String
	 * @return String[]
	 */
	public static String[] wordSplitter(String input) {
		String[] splittedWords = input.split("\\s+");
		return splittedWords;
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
				splittedWords = wordSplitter(inputLine);
				int n = splittedWords.length; // System.out.println(n);

				if (n > 0) { // to make sure that the entry line is not blank. Sometimes webpage has empty lines
					for (int i = 0; i < n; i++) { // checking for all words contained in the page
						if (splittedWords[i].equals("Positive") || splittedWords[i].equals("positive")) {
							String date = dateRetriever(inputLine); // grab the latest date
							boolean dateCheck = todaysDateFilter(date);
							if(dateCheck)
							{
								System.out.println("Detected!"); // once
								// "Positive"
								// is
								// detected,...
								System.out.print("Stock: ");

								for (int l = 71; l < o; l++) // the stock name's
																// first char is
																// at 71st char
								{
									System.out.print(retrievedURL.charAt(l));
								}
								System.out.println();

								System.out.println(date);
								System.out.println("========");
							}
						}
					}
				}
				

				// int m = inputLine.length(); System.out.println(m);

				// for (int i = 0; i < 0; i++) {
				// if (splittedWords[i] == "Announces"){
				// System.out.println("Detected!");
				// }
				// System.out.println(splittedWords[i]);
				// }
//				 System.out.println(inputLine);
				// System.out.println(splittedWords[0]);
				// System.out.println("=====");
			}
			in.close();
		}
		scan.close();
	}
}
