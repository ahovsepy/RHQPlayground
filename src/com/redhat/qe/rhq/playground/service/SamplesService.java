package com.redhat.qe.rhq.playground.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.w3c.dom.Entity;

import com.redhat.qe.jon.clitest.base.CliEngine;
import com.redhat.qe.jon.clitest.tasks.CliTasks;
import com.redhat.qe.jon.clitest.tasks.CliTasksException;

public class SamplesService {

	private static String rhqServerHost;
	private static String rhqCLIUser;
	private static String rhqCLIPassword;
	private static String rhqCLIHost;

	public static HashMap<String, String> listSamples(String path) {

		// String path = "cliSamples";
		HashMap<String, String> results = new HashMap<String, String>();
//		System.out.println(new File(path).getAbsolutePath());
		File[] files = new File(path).listFiles();

		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().endsWith(".js")) {
					results.put(file.getName(),file.getPath());
//					System.out.println(file.getName());
				}
				
			} else {
				String newPath = path + File.separator + file.getName();
				
				results.putAll(listSamples(newPath));
			}
		}

		return results;
	}
	
	private static void loadProperties(){
		Properties prop = new Properties();
		 
    	try {
               //load a properties file from class path, inside static method
    		prop.load(SamplesService.class.getClassLoader().getResourceAsStream("config.properties"));
    		rhqCLIHost = prop.getProperty("rhq.cli.host");
    		rhqCLIUser = prop.getProperty("rhq.cli.user");
    		rhqCLIPassword = prop.getProperty("rhq.cli.password");
    		rhqServerHost = prop.getProperty("rhq.server.host");
               
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
 
	}
	public static String runSample() throws CliTasksException{
		loadProperties();
		CliTasks.getCliTasks().initialize(rhqCLIHost, rhqCLIUser, rhqCLIPassword);
		//initialize(null, null, null);
		CliEngine engine = new CliEngine();
		engine.initialize(rhqServerHost, rhqCLIHost, rhqCLIUser, rhqCLIPassword, null, null);
		String result = engine.createJSRunner("test.js").dependsOn("rhqapi.js").run();
		
		return result;
	}
	

	public static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

	public static boolean writeFile(String fileContent) throws IOException {
		 BufferedWriter writer = null;
	        try {
	          	writer = new BufferedWriter(new FileWriter("test.js"));
	            writer.write(fileContent);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            	return false;
	            }
	        }
	        return true;
	}
	
	public static void main(String[] args) {
		HashMap<String, String> maps = listSamples("WebContent"+File.separator + "WEB-INF" + File.separator + "cliSamples");
		
		for (Entry<String, String> map : maps.entrySet()) {
			System.out.println(map.getKey());
			System.out.println(map.getValue());

	}
}
}
