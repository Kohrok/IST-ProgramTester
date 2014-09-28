
public class MainTester {

	public static void main(String[] args){
		
		RunInfo testInfo = new RunInfo();
		testInfo.singleSetup();
		Compiler c = new Compiler(testInfo.runNumber, testInfo.studentName, testInfo.studentHandle, testInfo.path, testInfo.classPath, testInfo.sourcePath,testInfo.studentPath, testInfo.outputFileName);
	      int success = c.compileJava();
	        
//	    Print whether or not compile successful
	      if(success == 0) 
	      {
	        System.out.println("Compiled Successfully");
	      }
	      else
	      {
	        System.out.println("Compile Failed");
	      }
	      
//	    Run the test cases
//	    TestRunner consructor:
//	    public TestRunner(int numbr, String nme, String hndl, String pth, String clsPath, 
//	    String srcPath, String stdPath, String tstDataPath, String argFileName, 
//	    String tstInputFileName, String inputFileName, String outFileName)
	      TestRunner r = new TestRunner(testInfo.runNumber, testInfo.studentName, testInfo.studentHandle, testInfo.path, testInfo.classPath, 
	    		  testInfo.sourcePath, testInfo.studentPath, testInfo.testDataPath, testInfo.argsFileName, testInfo.testInputFileName, testInfo.inputFileStub,
	    		  testInfo.outputFileName);
	      r.runJava();
	      testInfo.runNumber++;
	}
}
