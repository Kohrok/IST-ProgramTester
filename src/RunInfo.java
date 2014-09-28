import java.io.*;
import java.util.Scanner;


/**
 * Setup of the information related to running the program.
 * @author Steve
 *
 */
public class RunInfo {
//Basic information; Defaults to this info if object is created without args.
	String studentName = "blank";
    String studentHandle = "000000";
    String className = "297D/"; 
    String configFileName = "./configBatch.txt";    
    String path = "C:/Program Files/Java/jdk1.7.0_25/bin"; //?!!?!!!??????!?!???!!!??!??!?
    String sourcePath = "./src";
    String testDataPath = sourcePath;
    String argsFileName = testDataPath + "/args.txt";
    String testInputFileName = testDataPath + "/TestInput.txt";
// Compiler information   
    String classPath;
    String studentPath;
    String inputFileStub;
    String outputFileName;
//Batch/Single info
    int studentNumber = 0;
    int runNumber = 1;

    public RunInfo (){
    	
    }//RunInfo()
    public RunInfo (String sName,String sHandle,String cName,String cFileName,String path,String sPath,String tDataPath,String aFileName,String tInputFileName){
    	this.studentName = sName;
    	this.studentHandle = sHandle;
    	this.className = cName;
    	this.configFileName = cFileName;
    	this.path = path;
    	this.sourcePath = sPath;
    	this.argsFileName = aFileName;
    	this.testInputFileName = tInputFileName;
    }//RunInfo(args)
/*
 * Set up of the information for a batch test. Also compiles and runs the test, unless there is a better way.(Multiple Runs).
 */
    public void batchSetup(){	
    	try
        {
//        config file has list of ordinal student number,
//        student name, and random handle
          File configFile = new File(configFileName);
          Scanner in = new Scanner(configFile);
          int runNumber = 1;
          
          while(in.hasNextLine())
          {
            String line  = in.nextLine();
            
            Scanner inLine = new Scanner(line);
//          debug code - print out scanned config info
//          System.out.print("scanned config info: ");
            while(inLine.hasNext())
            {
              studentNumber = inLine.nextInt();
              studentName = inLine.next();
              studentHandle = inLine.next();
            }

//          set paths and file names:
            String classPath = "/java/bin/" + className + studentName;
            String studentPath = sourcePath + "/" + studentName;
            String inputFileStub = studentPath + "/input";
            String outputFileName = studentPath + "/output-" + studentName + ".txt";
            
//COMPILER NEEDS TO BE CALLED HERE BATCH IS SPECIAL, AS THE COMPILE RUNS THEN HAS ANOTHER LINE INPUT.
            
            
            
          }//while:hasNextLine
          }catch(IOException ioe){
        	  System.out.println("Batch IO Exception");
          }
        
    	
//This ends the prep for the object to be sent to the compiler and test runner.
    }//batchSetup
/*
 * Setup the information for the single test. Does not need to compile and run inside of RunInfo.    
 */
    public void singleSetup(){
//Reads the config file
    	try{
    	File configFile = new File(configFileName);
        Scanner in = new Scanner(configFile);
        String line  = in.nextLine();
        Scanner inLine = new Scanner(line); 
        path = inLine.next();
        line = in.nextLine();
        inLine = new Scanner(line);
        studentName = inLine.next();
        line = in.nextLine();
        inLine = new Scanner(line);
        studentHandle = inLine.next();    
// Setup names for compiler        
        classPath = "/java/bin/" + studentName; //changed, removed class path from between bin and student.
        studentPath = sourcePath + "/" + studentName;
        inputFileStub = studentPath + "/input";
        outputFileName = studentPath + "/output-" + studentName + ".txt";
    	} catch(IOException ioe){
    		System.out.println("SingleTest IOException");
    	}// catch
// Debug    	
/*    make sure set correctly
        System.out.println("classPath: " + classPath);
        System.out.println("studentPath: " + studentPath);
        System.out.println("inputFileStub: " + inputFileStub);
        System.out.println("outputFileName: " + outputFileName);
*/	
//This ends the prep for the object to be sent to the compiler and test runner.
    }//singleSetup()
}//RunInfo