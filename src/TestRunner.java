import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder.Redirect;

public class TestRunner
{
  private int number;
  private String name;
  private String handle;
  private String path;
  private String classPath;
  private String sourcePath;
  private String studentPath;
  private String testDataPath;
  private String argsFileName;
  private String testInputFileName;
  private String inputFileStub;
  private String inputFileName;
  private String outputFileName;
  private int success;
    
  public TestRunner(int numbr, String nme, String hndl, String pth, String clsPath, String srcPath, String stdPath, String tstDataPath, String argFileName, String tstInputFileName, String inFileStub,String outFileName)
  {
    number = numbr;
    name = nme;
    handle = hndl;
    path = pth;
    classPath = clsPath;
    sourcePath = srcPath;
    studentPath = stdPath;
    testDataPath = tstDataPath;
    argsFileName = argFileName;
    testInputFileName = tstInputFileName;
    inputFileStub = inFileStub;
    outputFileName = outFileName;
    success = 1;  // Outcome of compilation, success = 0
  }

  public void runJava()
  {
/*  timeout variables
    long startTime;
    long currentTime;
    long timeOut = 60000000000L;
*/
     
    try
    {
//    set up input files
//    TestInput.txt has inputs for each test on a single line
      File testInputFile = new File(testInputFileName);
      Scanner testInputs = new Scanner(testInputFile);
//    input.txt has inputs for a single run each on a separate line
//    and is created immediately before each test run from TestInput.txt
    
//    instantiate output file    
      File outputFile = new File(outputFileName);

//    instantiate command-line arguments file
      File argsFile = new File(argsFileName);
      
//    instantiate argument Scanner
      Scanner argsInput = new Scanner(argsFile);
      int run = 0;
      
      synchronized(outputFile)
      {
        while(argsInput.hasNextLine())
        {
          run++;
//        declare arg ArrayList for java ProcessBuilder
          List<String> arg = new ArrayList<String>();
          String argsLine = argsInput.nextLine();
//        parse argsLine via TestTools.parseLine
          arg = TestTools.parseLine(argsLine);
          arg.add(0, "java");
//        System.out.println(arg); 

//        scan TestInput.txt
          String testInputLine = testInputs.nextLine();
//        System.out.println(testInputLine);

//        create input file for current run
          List<String> inputs = new ArrayList<String>();          
          inputs = TestTools.parseLine(testInputLine);
          System.out.println("System.in inputs: " + inputs);
          inputFileName = inputFileStub + run + ".txt";
          PrintWriter writeTests = new PrintWriter(inputFileName);
          for(String element : inputs)
          {
            writeTests.println(element);
          }
          writeTests.close();          
          File inputFile = new File(inputFileName);
          

//        create new java ProcessBuilder using arg ArrayList
          ProcessBuilder pb = new ProcessBuilder(arg);

//        Create environment map and set environmental variables 
          Map<String, String> env = pb.environment();
          env.clear();
          env.put("PATH", path);
          env.put("CLASSPATH", classPath);
      
//        Determine current working directory
          File cwd = pb.directory(); 
//        NB - ProcessBuilder default is to return a null  
//        pointer for the abstract path to indicate that it 
//        is using System.Properties "user.dir", i.e., the 
//        current system working directory; hence the
//        critical need to handle a NullPointerException.
//        Also returns a null pointer if the directory
//        doesn't exist.

//        compute new abstract working directory path = studentPath
          File nwd = TestTools.cd(cwd, studentPath);
      
/*        debug code - to confirm correct new path 
          String nwdPath = nwd.getAbsolutePath();
          System.out.println("new cwd path: " + nwdPath);      
          TestTools.dir(nwd);
*/
     
//        set ProcessBuilder working directory to new abstract path
          pb.directory(nwd);  
//        System.out.println("new working directory: "  + pb.directory().getAbsolutePath()); 
          
//        redirect standard input, error, and output files; print process arguments      
          pb.redirectInput(Redirect.from(inputFile));
          pb.redirectErrorStream(true);
          pb.redirectOutput(Redirect.appendTo(outputFile));
          System.out.println("java process arguments: " + pb.command());
        
//        start java process    
          Process p = pb.start();

//        want processes to run sequentially to keep output in order         
//        basically joins thread to process to force sequential execution
//        need to be careful - if any process hangs, whole run hangs
          p.waitFor();
          
//        alternately, can get it sequential most times by sleeping a bit
//        Thread.sleep(1000);
 
/*        Timeout code to deal with missing Scanner input
//        Fixed by just redirecting standard.input to input.txt
          startTime = System.nanoTime();
          currentTime = System.nanoTime();
          while(currentTime - startTime < timeOut & p.exitValue() != 0)
          {        
            Thread.sleep(1000);
            currentTime = System.nanoTime();
//          System.out.println("Time:" + currentTime + "; success = " + success);
          }
*/
          assert pb.redirectInput() == Redirect.PIPE;
          assert pb.redirectOutput().file() == outputFile;
          assert p.getInputStream().read() == -1;
/*          
          if(currentTime - startTime > timeOut) 
          {
            p.destroy();
          }
*/
        }
      return;
      }
    }
    catch(java.lang.IllegalThreadStateException itse){}
    catch(java.util.NoSuchElementException nsee)
    {
      System.out.println("java.util.NoSuchElementException: Probably Scanner error");
    }
    catch(IOException ioe)
    {
      System.out.println("Runtime IOException");
    } 

    catch(InterruptedException ie)
    {
      System.out.println("Runtime InterruptedException");     
    }
/*
    catch(Exception e)
    {
      System.out.println("General Runtime Exception");
    }
*/    
  }
}