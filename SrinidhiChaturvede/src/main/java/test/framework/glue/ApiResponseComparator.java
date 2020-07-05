/**
 * @author Srinidhi Chaturvede
 *
 */
package test.framework.glue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cucumber.api.java.en.Given;

/**
 * This class will compare two API responses present in two different file and
 * return the output with meaningful message.
 */
public class ApiResponseComparator {

	String fileName1 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "filedata" + File.separator + "file1.txt";
	String fileName2 = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
			+ File.separator + "resources" + File.separator + "filedata" + File.separator + "file2.txt";
	private static String url1Response = "";
	private static String url2Response = "";
	private static String url1 = " ";
	private static String url2 = " ";

	/**
	 * This method is used to read api url from text files and comparing the
	 * responses
	 */
	@Given("^Reading and comparing the API URL's from two files and parsing the response body and printing the output in console$")
	public void FileInitiliazation() {
		List<String> file1UrlList = readFileData(fileName1);
		List<String> file2UrlList = readFileData(fileName2);
		compareUrlResponse(file1UrlList, file2UrlList);

	}

	/**
	 * This method is used to read api url from text file
	 */
	public static List<String> readFileData(String fileName) {
		List<String> urlList = new ArrayList<String>();
		File file = new File(fileName);
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println("No File found with url data");
		}
		BufferedReader br = new BufferedReader(fr);
		String url;
		try {
			while ((url = br.readLine()) != null) {
				urlList.add(url);
			}
			br.close();
		} catch (Exception e) {
			System.out.println("error while reading url data from file");
		}
		return urlList;
	}

	/**
	 * This method is used to compare response of Api URL
	 */
	public static void compareUrlResponse(List<String> file1UrlList, List<String> file2UrlList) {
		Iterator<String> file1UrlListIterator = file1UrlList.iterator();
		Iterator<String> file2UrlListIterator = file2UrlList.iterator();
		System.out.println("===================Start of API test file comparision============================");
		while (file1UrlListIterator.hasNext() && file2UrlListIterator.hasNext()) {
			url1 = file1UrlListIterator.next();
			url2 = file2UrlListIterator.next();
			url1Response = getApiData(url1);
			url2Response = getApiData(url2);
			if ((null != url1 && !("".equals(url1))) || (null != url2 && !("".equals(url2)))) {
				if (url1Response.equals(url2Response)) {

					System.out.println(url1 + "   Equal   " + url2);
					System.out.println("=========================================================================");
				} else {

					System.out.println(url1 + "  Not Equal  " + url2);
					System.out.println("=========================================================================");
				}
			} else {
				System.out.println("No Api Url Found on both the files");
				System.out.println("=============================================================================");
			}
		}
		System.out.println("=========================END of API Test case================================");
	}

	/**
	 * This method is used to call API URl and parsing response body
	 */
	public static String getApiData(String url) {
		StringBuilder sb = new StringBuilder();
		if (null != url && !("".equals(url))) {
			try {

				URLConnection connection = new URL(url).openConnection();
				connection.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.connect();

				BufferedReader r = new BufferedReader(
						new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

				String line;
				while ((line = r.readLine()) != null) {
					sb.append(line);
				}

			} catch (Exception e) {
				// System.out.println("Response not found on one of the API
				// request");
			}
		}
		return sb.toString();
	}

}
