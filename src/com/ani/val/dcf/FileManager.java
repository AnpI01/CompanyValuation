package com.ani.val.dcf;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class FileManager {

	public static String readOneRecFromFile(String inFileNme){
		File file = new File(inFileNme);
		Scanner sc = null;;

		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		sc.useDelimiter("\\Z");

		String line =  sc.next();
		sc.close();
		return line;
	}
}
