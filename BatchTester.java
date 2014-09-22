import java.io.*;
import java.util.*;

public class BatchTester
{
  public static void main(String [] args)
  {
//  initialize student and class configuration data
    int studentNumber = 0;
    String studentName = "blank";
    String studentHandle = "000000";
    String className = "297D/"; 
    String configFileName = "./configBatch.txt";
//  System.out.println("configFileName: " + configFileName);
    
//  set fixed paths and file names:
    String path = "C:/Program Files/Java/jdk1.7.0_25/bin";
    String sourcePath = "./src";
    String testDataPath = sourcePath;
    String argsFileName = testDataPath + "/args.txt";
    String testInputFileName = testDataPath + "/TestInput.txt";
/*  make sure set correctly
    System.out.println("path: " + path);
    System.out.println("sourcePath: " + sourcePath);
    System.out.println("testDataPath: " + testDataPath);
    System.out.println("argsFileName: " + argsFileName);
    System.out.println("testInputFileName: " + testInputFileName);
*/
    try
    {
//    config file has list of ordinal student number,
//    student name, and random handle
      File configFile = new File(configFileName);
      Scanner in = new Scanner(configFile);
      int runNumber = 1;
      
      while(in.hasNextLine())
      {
        String line  = in.nextLine();
        
        Scanner inLine = new Scanner(line);
//      debug code - print out scanned config info
//      System.out.print("scanned config info: ");
        while(inLine.hasNext())
        {
          studentNumber = inLine.nextInt();
          studentName = inLine.next();
          studentHandle = inLine.next();
        }

//      set paths and file names:
        String classPath = "/java/bin/" + className + studentName;
        String studentPath = sourcePath + "/" + studentName;
        String inputFileStub = studentPath + "/input";
        String outputFileName = studentPath + "/output-" + studentName + ".txt";
/*      make sure set correctly
        System.out.println("classPath: " + classPath);
        System.out.println("studentPath: " + studentPath);
        System.out.println("inputFileStub: " + inputFileStub);
        System.out.println("outputFileName: " + outputFileName);
*/
      
        System.out.println("run #: " + runNumber + " ; studentNumber: " + studentNumber + 
        "; Name: " + studentName + "; Handle: " + studentHandle);
        System.out.println("Output goes to: " + outputFileName);
        
//      run javac compiler - returns 0 on success
//      Compiler Constructor:
//      public Compiler(int numbr, String nme, String hndl, String pth, String clsPath, 
//      String srcPath, String stdPath, String outFileName)
        Compiler c = new Compiler(runNumber, studentName, studentHandle, path, classPath, sourcePath,studentPath, outputFileName);
        int success = c.compileJava();
        
//      Print whether or not compile successful
        if(success == 0) 
        {
          System.out.println("Compiled Successfully");
        }
        else
        {
          System.out.println("Compile Failed");
        }
      
//      Run the test cases
//      TestRunner consructor:
//      public TestRunner(int numbr, String nme, String hndl, String pth, String clsPath, 
//      String srcPath, String stdPath, String tstDataPath, String argFileName, 
//      String tstInputFileName, String inputFileStub, String outFileName)
        TestRunner r = new TestRunner(runNumber, studentName, studentHandle, path, classPath, sourcePath, studentPath, testDataPath, argsFileName, testInputFileName, inputFileStub, outputFileName);
        r.runJava();
        runNumber++;
        System.out.println();
      }
    } catch(IOException ioe)
    {
      System.out.println("main IOException");
    }
  }
}
