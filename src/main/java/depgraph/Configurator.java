package depgraph;

import java.io.File;
import java.nio.file.Files;

public class Configurator {
	String nameOfFile;
	String nameOfDirectory;

	public Configurator() {
		//nameOfFile = "";
		//nameOfDirectory = "";
	}
	public void manageCmdLineArguments(String [] args){
	
		if(args.length == 0){
			System.out.println("No arguments passed.");
		}
		if(args.length !=0){
			System.out.println("Processing arguments now ... \n");

			for(int i = 0; i < args.length; i++ ){
				if(args[i].contains("-h")){
					this.help();
				}
				else if(args[i].contains("-s")){
					i++;
					//System.out.println("Name of file passed to the program: " + args[i]);
					this.processSingleFile(args[i]);
				}else if(args[i].contains("-d")){
					i++;
					//System.out.println("Path of directory passed to the program : " + args[i]);
					this.processDirectory(args[i]);
				}else{
					System.out.println("Could not interpret arguments");
				}
			}

		} 
	}
	private void help(){
		System.out.println("Welcome to the C Dependency Graph Tool.\n");
		System.out.println("Command line argument syntax is as follows:");
		System.out.println("gradle run --args=\" [arguments] \"\n");
		System.out.println("Possible arguments include -h for help,");
		System.out.println("-s for processing a single file,");		
		System.out.println("-d for processing a directory.");
	}

	//source directory is SeniorDesign\c-dependency-graph
	private void processSingleFile(String fileName){

		File singleFile = new File(fileName);
		
		if(singleFile.isFile()){
			this.nameOfFile = fileName;
		}else{
			System.out.println("File name provided cannot resolve to a file");
		}

	}
	private void processDirectory(String directoryName){
		File singleDirectory = new File(directoryName);
		if(singleDirectory.isDirectory()){
			this.nameOfDirectory = directoryName;
		}else{
			System.out.println("Directory name provided cannot resolve to a directory");
		}

	}
	// getters 
	public String getFileName(){
		return nameOfFile;
	}

	public String getDirectoryName(){
		return nameOfDirectory;
	}



}
