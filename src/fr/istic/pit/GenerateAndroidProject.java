package fr.istic.pit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import sun.tools.jar.CommandLine; 
 
public class GenerateAndroidProject
{
  public static void main(String[] args) 
  throws IOException, InterruptedException
  {
	  
	  String toolsPath ="/home/ayari/android-sdk-linux/tools";
	  generateAndroidProject(toolsPath);
  }
  
  
  public static void generateAndroidProject(String toolsPath){

	  try{  

		  ExecutorService executor = Executors.newSingleThreadExecutor();
		    executor.submit(() -> {

		       //------------------------------------Form windows --------------------------------//
		       // ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", projectPath + "/gradlew assembleRelease -p" + projectPath, "--info");
		    	
		    	//------------------------------------Form unix-----------------------------------//
		    	ProcessBuilder builder = new ProcessBuilder("/bin/bash" ,"-c",  toolsPath + "/android create project -g -v 1.2.2 --target 1 --name rtest --path rtest --activity MainActivity --package com.mydomain.rtest");
		        builder.redirectErrorStream(true);
		        try {
		            Process p = builder.start();
		            BufferedReader stdout = new BufferedReader(
		                    new InputStreamReader(p.getInputStream()));

		            System.out.println("log -- " + stdout.readLine());
		            String s = "";
		            while (  (s=stdout.readLine())  != null) {
		                System.out.println("log -- " + s);
		                
		                Platform.runLater(() -> {
		                    //if you change the UI, do it here 
		                });
		                
		                
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