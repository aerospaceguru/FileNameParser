
// Programme to convert ISO 19650 structured file names in a directory to a CSV file

package info.martinoconnell;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileNameParser {

	static String location;
	static String csvFileName;

	public FileNameParser() {

		System.out.print("Enter directory: ");
		Scanner sc1 = new Scanner(System.in);
		location = sc1.nextLine();

		System.out.print("Enter new file name: ");
		Scanner sc2 = new Scanner(System.in);
		csvFileName = sc2.nextLine();
	}

	public static void main(String[] args) throws IOException {

		String str1 = "";
		ArrayList<String> FileNames1 = new ArrayList<String>();
		ArrayList<String> FileNames2 = new ArrayList<String>();
		ArrayList<String> FileNames3 = new ArrayList<String>();
		ArrayList<String> FileNames4 = new ArrayList<String>();

		new FileNameParser();

		// Locate the directory and create an array of the files
		File dir = new File(location);
		File[] directoryListing = dir.listFiles();

		// Converts the file names into a String array list 
		if(directoryListing != null) {
			for(File name : directoryListing) {				
				FileNames1.add(name.toString());
			}
		}

		// Looping in reverse order, this block truncates the file name when it reaches a backslash
		for(int i=0; i<FileNames1.size(); i++) {
			String str = FileNames1.get(i);

			for(int j=str.length()-1; j>=-1; j--) {	
				if(str.charAt(j) == '\\') {
					str1 = str.substring(j+1, str.length());
					break; // This ensures that the loop ends when the FIRST backslash is reached in reverse order
				}
			}
			FileNames2.add(str1);
		}

		// This loop ensures the filename is truncated when the first 'dot' is reached
		for(int i=0; i<FileNames2.size(); i++) {
			String str = FileNames2.get(i);

			for(int j=0; j<str.length()-1; j++) {	
				if(str.charAt(j) == '.') {
					str1 = str.substring(0, j);
					break;
				}
			}
			FileNames3.add(str1);
		}

		// This replaces each of the hyphens in the resulting file name with a comma
		for(int i=0; i<FileNames3.size(); i++) {
			String str = FileNames3.get(i);
			String str2 = str.replace('-', ',');
			FileNames4.add(str2);
		}

		// This loop cycles through the FileNames4 String array list and prints each entry to a new line in a CSV
		File csvFile = new File(location + "\\" + csvFileName);
		try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile));){
			for(String item : FileNames4){
				csvWriter.println(item);
			}
		} catch (IOException e) {
			System.out.println("An I/O error has occurred");
			e.printStackTrace();
		}
		System.out.println("Success! CSV file created.");
	}

}

