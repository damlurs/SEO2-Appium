package com.ntt.acoe.framework.selenium.util;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class LineUtilities {

	public static String[] getTokens(String line, String delimiter) {
		String[] tokens = line.split("\\" + delimiter, -1);
		for (int i = 0; i < tokens.length; i++) {
			// System.out.println(tokens[i]);
		}
		return (tokens);
	}

	public static String getSubString(String line, int startingPosition, int endingPosition) {
		return (line.substring(startingPosition, endingPosition));
	}

	public static String[] getTokensForPosition(String line, String positions) {
		String[] positionTokens = positions.split("\\,");
		String[] tokens = new String[positionTokens.length];

		for (int i = 0; i < positionTokens.length; i++) {
			String[] indexTokens = positionTokens[i].split("\\-");
			tokens[i] = line.substring(Integer.valueOf(indexTokens[0]), Integer.valueOf(indexTokens[1]));
			// System.out.println(indexTokens[0]+": "+tokens[i]);
		}
		return tokens;
	}
}
