import java.io.*;
import java.util.*;

public class SingleTester
{
  public static void main(String [] args)
  {
//  initialize student and class configuration data    
    int studentNumber = 0;
    int runNumber = 1; 
    String studentName = "blank";
    String studentHandle = "000000";
    String className = "412/"; 
    String configFileName = "./configSingle.txt";
    System.out.println("configFileName: " + configFileName);
    String path = "C:/Program Files/Java/jdk1.7.0_25/bin";
    
//  set fixed paths and file names:
    String sourcePath = "./src";
    String testDataPath = sourcePath;
    String argsFileName = testDataPath + "/args.txt";
    String testInputFileName = testDataPath + "/TestInput.txt";
/*  make sure set correctly
    System.out.println("sourcePath: " + sourcePath);
    System.out.println("testDataPath: " + testDataPath);
    System.out.println("argsFileName: " + argsFileName);
    System.out.println("testInputFileName: " + testInputFileName);
*/
    try
    {
/*    config file contains:
      - path to Java jdk (enclosed in quotes) on first line
        e.g. - "C:/Program Files/Java/jdk1.7.0_25/bin"
      - student name (lowercase lastnamefm) on second line
        e.g. - smithjq for John Q. Smith
      - random 6-digit handle on third line
        e.g. - 543890 - use the one given to you in class
*/
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

//    set paths and file names:
      String classPath = "/java/bin/" + className + studentName;
      String studentPath = sourcePath + "/" + studentName;
      String inputFileStub = studentPath + "/input";
      String outputFileName = studentPath + "/output-" + studentName + ".txt";
/*    make sure set correctly
      System.out.println("classPath: " + classPath);
      System.out.println("studentPath: " + studentPath);
      System.out.println("inputFileStub: " + inputFileStub);
      System.out.println("outputFileName: " + outputFileName);
*/
      
      System.out.println("run #: " + runNumber + " ; studentNumber: " + studentNumber + 
      "; Name: " + studentName + "; Handle: " + studentHandle);
      System.out.println("Output goes to: " + outputFileName);
        
//    run javac compiler - returns 0 on success
//    Compiler Constructor:
//    public Compiler(int numbr, String nme, String hndl, String pth, String clsPath, 
//    String srcPath, String stdPath, String outFileName)
      Compiler c = new Compiler(runNumber, studentName, studentHandle, path, classPath, sourcePath,studentPath, outputFileName);
      int success = c.compileJava();
        
//    Print whether or not compile successful
      if(success == 0) 
      {
        System.out.println("Compiled Successfully");
      }
      else
      {
        System.out.println("Compile Failed");
      }
      
//    Run the test cases
//    TestRunner consructor:
//    public TestRunner(int numbr, String nme, String hndl, String pth, String clsPath, 
//    String srcPath, String stdPath, String tstDataPath, String argFileName, 
//    String tstInputFileName, String inputFileName, String outFileName)
      TestRunner r = new TestRunner(runNumber, studentName, studentHandle, path, classPath, 
      sourcePath, studentPath, testDataPath, argsFileName, testInputFileName, inputFileStub,
      outputFileName);
      r.runJava();
      runNumber++;
      System.out.println();
    } catch(IOException ioe)
    {
      System.out.println("main IOException");
    }
  }
}
