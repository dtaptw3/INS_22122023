package com.app.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public final class ArithmeticUtil {

	final static Logger log = Logger.getLogger(ArithmeticUtil.class);

	public static final BigDecimal calcPercentValue(final BigDecimal percent, final BigDecimal amount) {

		try {
			if (!amount.equals(BigDecimal.ZERO)) {
				final DecimalFormat df = new DecimalFormat("0.000000#");

				final BigDecimal percentValue = new BigDecimal(
						df.format((amount.divide(new BigDecimal(100), 6, RoundingMode.HALF_UP)).multiply(percent)));

				return percentValue.setScale(6, RoundingMode.CEILING);

			}
		} catch (Exception e) {

			log.info(e.toString());
		}

		return BigDecimal.ZERO;
	}

	public static final BigDecimal calcPercent(final BigDecimal percentValue, final BigDecimal amount) {

		// MathContext mc = new MathContext(8);
		if (!amount.equals(BigDecimal.ZERO) || percentValue.equals(BigDecimal.ZERO)) {
			final DecimalFormat df = new DecimalFormat("0.000000#");
			final BigDecimal percent = new BigDecimal(
					df.format((percentValue.multiply(new BigDecimal(100))).divide(amount, 6, RoundingMode.HALF_UP)));
			return percent;
		} else {

			return BigDecimal.ZERO;
		}
	}

	// ADDED 19112020
	public static final String validateNumericOverFlow(String bignumber) throws InconsistencyException {

		if (bignumber.contains("E") || bignumber.substring(0, bignumber.indexOf(".")).length() > 18) {
			throw new InconsistencyException("Arithematic overflow");
		}

		/*
		 * if (bignumber.contains("E") || bignumber.substring(bignumber.indexOf("."),
		 * bignumber.length()).length() > 3) { throw new
		 * InconsistencyException("Arithematic overflow"); }
		 */

		return "valid";

	}

	String unitsMap[] = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	String tensMap[] = { "zero", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };

	public String NumberToWords(int number) {
		if (number == 0)
			return "zero";

		if (number < 0)
			return "minus " + NumberToWords(Math.abs(number));

		String words = "";

		if ((number / 1000000000) > 0) {
			words += NumberToWords(number / 1000000000) + " billion ";
			number %= 1000000000;
		}

		if ((number / 1000000) > 0) {
			words += NumberToWords(number / 1000000) + " million ";
			number %= 1000000;
		}

		if ((number / 1000) > 0) {
			words += NumberToWords(number / 1000) + " thousand ";
			number %= 1000;
		}

		if ((number / 100) > 0) {
			words += NumberToWords(number / 100) + " hundred ";
			number %= 100;
		}

		if (number > 0) {
			if (number < 20)
				words += unitsMap[number];
			else {
				words += tensMap[number / 10];
				if ((number % 10) > 0)
					words += "-" + unitsMap[number % 10];
			}
		}

		return words;
	}
	
	
	
	
	/// 
	

	private static String input;
	
	private static String[] units = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight",
			" Nine" };
	private static String[] teen = { " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen",
			" Seventeen", " Eighteen", " Nineteen" };
	private static String[] tens = { " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty",
			" Ninety" };
	private static String[] maxs = { "", "", " Hundred", " Thousand", " Lakh", " Crore" };

	public static String convertNumberToWords(int n) {
		input = numToString(n);
		String converted = "";
		int pos = 1;
		boolean hun = false;
		while (input.length() > 0) {
			if (pos == 1) // TENS AND UNIT POSITION
			{
				if (input.length() >= 2) // TWO DIGIT NUMBERS
				{
					String temp = input.substring(input.length() - 2, input.length());
					input = input.substring(0, input.length() - 2);
					converted += digits(temp);
				} else if (input.length() == 1) // 1 DIGIT NUMBER
				{
					converted += digits(input);
					input = "";
				}
				pos++;
			} else if (pos == 2) // HUNDRED POSITION
			{
				String temp = input.substring(input.length() - 1, input.length());
				input = input.substring(0, input.length() - 1);
				if (converted.length() > 0 && digits(temp) != "") {
					converted = (digits(temp) + maxs[pos] + " and") + converted;
					hun = true;
				} else {
					if (digits(temp) == "")
						;
					else
						converted = (digits(temp) + maxs[pos]) + converted;
					hun = true;
				}
				pos++;
			} else if (pos > 2) // REMAINING NUMBERS PAIRED BY TWO
			{
				if (input.length() >= 2) // EXTRACT 2 DIGITS
				{
					String temp = input.substring(input.length() - 2, input.length());
					input = input.substring(0, input.length() - 2);
					if (!hun && converted.length() > 0)
						converted = digits(temp) + maxs[pos] + " and" + converted;
					else {
						if (digits(temp) == "")
							;
						else
							converted = digits(temp) + maxs[pos] + converted;
					}
				} else if (input.length() == 1) // EXTRACT 1 DIGIT
				{
					if (!hun && converted.length() > 0)
						converted = digits(input) + maxs[pos] + " and" + converted;
					else {
						if (digits(input) == "")
							;
						else
							converted = digits(input) + maxs[pos] + converted;
						input = "";
					}
				}
				pos++;
			}
		}
		return converted;
	}

	private static String digits(String temp) // TO RETURN SELECTED NUMBERS IN WORDS
	{
		String converted = "";
		for (int i = temp.length() - 1; i >= 0; i--) {
			int ch = temp.charAt(i) - 48;
			if (i == 0 && ch > 1 && temp.length() > 1)
				converted = tens[ch - 2] + converted; // IF TENS DIGIT STARTS WITH 2 OR MORE IT FALLS UNDER TENS
			else if (i == 0 && ch == 1 && temp.length() == 2) // IF TENS DIGIT STARTS WITH 1 IT FALLS UNDER TEENS
			{
				int sum = 0;
				for (int j = 0; j < 2; j++)
					sum = (sum * 10) + (temp.charAt(j) - 48);
				return teen[sum - 10];
			} else {
				if (ch > 0)
					converted = units[ch] + converted;
			} // IF SINGLE DIGIT PROVIDED
		}
		return converted;
	}

	private static String numToString(int x) // CONVERT THE NUMBER TO STRING
	{
		String num = "";
		while (x != 0) {
			num = ((char) ((x % 10) + 48)) + num;
			x /= 10;
		}
		return num;
	}

}
