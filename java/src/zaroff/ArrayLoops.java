import java.util.Scanner;
public class ArrayLoops
{
  public static void main(String [] args)
  {
    Scanner input = new Scanner(System.in);
    System.out.println("Please enter an integer n (array size limit)");   
    int n = input.nextInt();
    System.out.println("Please enter an integer range for the random numbers"); 
    int range = input.nextInt();
    
    int [] numbers = new int[n];
    int sum  = 0;
    int sumsq = 0;
    
    for(int k = 0; k < n;  k++)
    {
      numbers[k] = (int) (Math.random() * range + 1);
      System.out.println(numbers[k]);
      sum += numbers[k];
      sumsq += numbers[k]*numbers[k];
    }
    
    double average = ((double)sum)/n;
    double stdDev = Math.sqrt(( ((double)sumsq) / n) - average*average);
    
    System.out.println("n = " + n + "; range = " + range + "; average = " + average + "; stdDev = " + stdDev);
  }
} 