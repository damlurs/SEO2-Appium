package com.ntt.acoe.framework.selenium.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */
public class TextFileUtilities {
	public static String getFileContent(String filePath) throws IOException {
		String fileContent = "";

		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null) {
			fileContent = fileContent + "\n" + s;
		}
		br.close();
		return fileContent;
	}

	public static int getLineCount(String filePath) throws IOException {
		int lineCount = 0;
		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		while (br.readLine() != null) {

			lineCount = (lineCount + 1);
		}
		br.close();
		return lineCount;
	}

	public static String getHeaderLine(String filePath) throws IOException {
		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s;
		s = br.readLine();
		br.close();
		return s;
	}

	public static ArrayList<String> getDetailLines(String filePath) throws IOException {
		ArrayList<String> al = new ArrayList<String>();
		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s;
		br.readLine();
		while ((s = br.readLine()) != null) {
			al.add(s);
		}
		al.remove(al.size() - 1);
		br.close();
		return (al);
	}

	public static String getTrailerLine(String filePath) throws IOException {
		String trailerContent = "";

		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String s;
		while ((s = br.readLine()) != null) {
			trailerContent = s;
		}
		br.close();
		return trailerContent;
	}

	public static void printFileContent(String filePath) throws IOException {
		System.out.println(getFileContent(filePath));
	}

	public static int getCharCount(String filePath) throws IOException {
		int charCount = 0;
		File f = new File(filePath);
		FileReader fr = new FileReader(f);
		while (fr.read() != -1) {
			charCount = (charCount + 1);
		}
		fr.close();
		return charCount;
	}
}
