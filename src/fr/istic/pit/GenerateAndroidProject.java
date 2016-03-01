package fr.istic.pit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream; 
import java.io.PrintWriter;

public class GenerateAndroidProject
{

  public static OsValidator OS ;
  public static ProcessBuilder builder;
  
  public GenerateAndroidProject() { 
	 OS = new OsValidator() ;
	}

  
  public static void main(String[] args)
			 throws IOException, InterruptedException
			 {
				 OS = new OsValidator(); 
			 generateAndroidProject("/home/ayari/android-sdk-linux/tools","1.2.2",1,"rtest1","/home/ayari/Bureau/rtest1","MainActivity","com.mydomain.rtest");
			  
			 }
  
  public static void generateAndroidProject(String toolsPath,String gradleVersion,int targetNumber, String nameProject,String pathOfProject,String nameActivity,String namePackage){

	  try{  

		  ExecutorService executor = Executors.newSingleThreadExecutor();
		    executor.submit(() -> {
		    	
		    	
                if(OS.isWindows()){
		           builder = new ProcessBuilder("cmd.exe", "/C",  toolsPath + "/android create project -g -v "+gradleVersion+" --target "+targetNumber+" --name "+nameProject+" --path "+pathOfProject+" --activity "+nameActivity+" --package "+namePackage);
                } else if (OS.isUnix()) {
				
		    	   builder = new ProcessBuilder("/bin/bash" ,"-c",  toolsPath + "/android create project -g -v "+gradleVersion+" --target "+targetNumber+" --name "+nameProject+" --path "+pathOfProject+" --activity "+nameActivity+" --package "+namePackage);
		         }
                
                
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
		            
		            replaceFile("/home/ayari/Bureau/rtest1/gradle/wrapper/gradle-wrapper.properties",
		            		"/home/ayari/Bureau/rtest/gradle/wrapper/gradle-wrapper.properties");
		            
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
  
   
  public static void replaceFile(String source,String dest) throws IOException{
	        
	  File fin = new File(source);
	    FileInputStream fis = new FileInputStream(fin);
	    BufferedReader in = new BufferedReader(new InputStreamReader(fis));

	    FileWriter fstream = new FileWriter(dest, true);
	    BufferedWriter out = new BufferedWriter(fstream);

	    String aLine = null; 
	    
	    PrintWriter pw = new PrintWriter(new File(dest));
	    pw.write("");
	    pw.flush();
	    
	    
	    while ((aLine = in.readLine()) != null) { 
	        out.write(aLine);
	        out.newLine();
	    }
 
	    in.close();
        out.close();
	     }
	}
  
 