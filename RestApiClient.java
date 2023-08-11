package com.java.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner14;

import org.json.JSONObject;

import com.java.pojo.Currency;

public class RestApiClient {

	public static void main(String[] args) throws IOException{
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Welcome to the Currency Info Command Line Editor.");
		System.out.println("Do you want to get or set a Currency's info?");
		System.out.println("(Type 'get' or 'set' now.)");
		
		String getOrSet = scanner.nextLine();
		
		if("get".equalsIgnoreCase(getOrSet)){
			System.out.println("Whose info do you want to get?");
			System.out.println("(Type a currency id now.)");
			String name = scanner.nextLine();
			int currToFind = Integer.parseInt(name);
			
			String jsonString = getCurrencyData(currToFind);
			JSONObject jsonObject = new JSONObject(jsonString);

			String sourceCurrency = jsonObject.getString("sourceCurrency");
			String targetCurrency = jsonObject.getString("targetCurrency");
			float loadFactor = 		jsonObject.getFloat("loadFactor");

			System.out.println("Source currency : "+sourceCurrency);
			System.out.println("Target currency : "+targetCurrency);
			System.out.println("Load Factor     : "+loadFactor);
					
			
		}
		else if("set".equalsIgnoreCase(getOrSet)){
			System.out.println("Whose info do you want to set?");
			System.out.println("(Type a currency details now.)");
			
			Currency currency = new Currency();

			Scanner scanner1 = new Scanner(System.in);
			Scanner scanner2 = new Scanner(System.in);
			Scanner scanner3 = new Scanner(System.in);
			Scanner scanner4 = new Scanner(System.in);
				
			System.out.println("Enter currency id : ");
			currency.setCurrencyId(scanner1.nextInt());

			System.out.println("Enter source Currency : ");
			currency.setSourceCurrency(scanner2.next());

			System.out.println("Enter target Currency : ");
			currency.setTargetCurrency(scanner3.next());

			
			System.out.println("Enter load factor : ");
			currency.setLoadFactor(scanner4.nextFloat());
	
			setCurrencyData(currency);
		}
		
		scanner.close();
		
		System.out.println("Thanks for using Rest Client");
		
	}
	
	public static String getCurrencyData(int currencyId) throws IOException{

		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/MyRestAPI2/rest/currencies/" + currencyId).openConnection();
		
		connection.setRequestMethod("GET"); //POST, PUT, DELETE

		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			String response = "";
			Scanner scanner = new Scanner(connection.getInputStream());
			while(scanner.hasNextLine()){
				response += scanner.nextLine();
				response += "\n";
			}
			scanner.close();

			return response;
		}
		
		// an error happened
		return null;
	}

	public static void setCurrencyData(Currency currency) throws IOException{
		
		HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/MyRestAPI2/rest/currencies/add/").openConnection();

		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");


		String jsonInputString = "{ \"currencyId\":"+currency.getCurrencyId()+",";
		 jsonInputString+= "\"sourceCurrency\":"+"\""+currency.getSourceCurrency()+"\",";
		 jsonInputString+= "\"targetCurrency\":"+"\""+currency.getTargetCurrency()+"\",";
		 jsonInputString+= "\"loadFactor\":"+currency.getLoadFactor()+"}";
		
		System.out.println("post data : "+jsonInputString);

		
		connection.setDoOutput(true);
		
		//send json content the server
		connection.setRequestProperty("Accept", "application/json");

		//receive json content from the server
		//connection.setRequestProperty("Content", "application/json");
		
        OutputStream os = connection.getOutputStream();
        os.write(jsonInputString.getBytes("UTF-8")); //convert json to byte array
        os.close();
        
			
		int responseCode = connection.getResponseCode();
		if(responseCode == 200){
			System.out.println("POST was successful.");
		}
		else if(responseCode == 401){
			System.out.println("Wrong password.");
		}
	}
}