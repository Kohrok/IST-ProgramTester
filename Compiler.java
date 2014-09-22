import java.io.*;
import java.util.*;
import java.lang.ProcessBuilder.Redirect;

public class Compiler
{
  private int number;
  private String name;
  private String handle;
  private String path;
  private String classPath;
  private String sourcePath;
  private String studentPath;
  private String outputFileName;
  private int success;
    
  public Compiler(int numbr, String nme, String hndl, String pth, String clsPath, String srcPath, String stdPath, String outFileName)
  {
    number = numbr;
    name = nme;
    handle = hndl;
    path = pth;
    classPath = clsPath;
    sourcePath = srcPath;
    studentPath = stdPath;
    outputFileName = outFileName;
    success = 1;  // Outcome of compilation, success = 0
  }
    
  public int compileJava()
  {
    try
    {
//    create new bin directory
      boolean createBin = new File(classPath).mkdir();

//    create new javac ProcessBuilder        
      ProcessBuilder pb =
      new ProcessBuilder("javac", "-d", classPath, "./" + studentPath + "/*.java");

//    Create environment map and set environmental variables         
      Map<String, String> env = pb.environment();       
      env.clear();
      env.put("PATH", path);
      env.put("CLASSPATH", classPath);
//    env.put("SOURCEPATH", sourcePath);
//    env.remove("OTHERVAR");

//    Determine current working directory
      File cwd = pb.directory();
//    NB - ProcessBuilder default is to return a null  
//    pointer for the abstract path to indicate that it 
//    is using System.Properties "user.dir", i.e., the 
//    current system working directory; hence the
//    critical need to handle a NullPointerException.
//    Also returns a null pointer if the directory
//    doesn't exist.

//    debug code - to confirm correct directory       
//    TestTools.dir(cwd);

//    set up output file      
      File outputFile = new File(outputFileName);
//    System.out.println(outputFileName);
      outputFile.delete();
      pb.redirectErrorStream(true);
      pb.redirectOutput(Redirect.appendTo(outputFile));

//    start javac process        
      Process p = pb.start();
        
//    need other processes to wait for compilation to finish
//    basically joins the thread to the javac process to force sequential
//    execution - need to be careful - if any process hangs, whole run hangs
      success = p.waitFor();

      assert pb.redirectInput() == Redirect.PIPE;
      assert pb.redirectOutput().file() == outputFile;
      assert p.getInputStream().read() == -1;
        
//    code to change working directory (not used in this instance)
/*    File nwd = TestTools.cd(cwd, studentPath);
      String nwdPath = nwd.getAbsolutePath();
//    System.out.println("new cwd path: " + nwdPath);
      TestTools.dir(nwd);
*/      
    } catch(Exception e)
    {
        System.out.println("Compile Exception");
    }
    return success;
  }
}