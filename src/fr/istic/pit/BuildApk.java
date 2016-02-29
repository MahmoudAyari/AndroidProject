package fr.istic.pit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import sun.tools.jar.CommandLine; 
 
public class BuildApk
{
  public static void main(String[] args) 
  throws IOException, InterruptedException
  {
	  
	  String projectPath ="/home/ayari/AndroidStudioProjects/Tp3";
	  buildApk(projectPath);
  }
  
  
  public static void buildApk(String projectPath){

	  try{  

		  ExecutorService executor = Executors.newSingleThreadExecutor();
		    executor.submit(() -> {

		       //------------------------------------Form windows --------------------------------//
		       // ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", projectPath + "/gradlew assembleRelease -p" + projectPath, "--info");
		    	
		    	//------------------------------------Form unix-----------------------------------//
		    	ProcessBuilder builder = new ProcessBuilder("/bin/bash" ,"-c",  projectPath + "/gradlew assembleDebug -p" + projectPath, "--info");
		        builder.redirectErrorStream(true);
		        try {
		            Process p = builder.start();
		            BufferedReader stdout = new BufferedReader(
		                    new InputStreamReader(p.getInputStream()));

		            System.out.println("outputString:: " + stdout.readLine());
		            String s = "";
		            while (  (s=stdout.readLine())  != null) {
		                System.out.println("outputString:: " + s);
		                
		                Platform.runLater(() -> {
		                    //if you change the UI, do it here 
		                });
		                
		                if (stdout.readLine().contains("BUILD SUCCESSFUL")) {
		                    break;
		                }
		            }
		            p.getInputStream().close();
		            p.getOutputStream().close();
		            p.getErrorStream().close();
		            p.destroy();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    });
		    
	  }catch( Exception e){
		   }
  }
}