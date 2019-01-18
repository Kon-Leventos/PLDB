package testing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

final class TestParser {

	public static void main(String arguments[]) {
		readTestFile("test.txt");
	}

	public static byte[] readTestFile(String fileName) {
		try {
			File testFile = new File(fileName);
			long fileSize = testFile.length();
			FileInputStream inputStream = new FileInputStream(testFile);
			byte[] allTestData = new byte[(int) fileSize];
			BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
			bufferedStream.read(allTestData);
			bufferedStream.close();
			return allTestData;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Possible missing file: " + fileName);
			return null;
		}
	}

}
