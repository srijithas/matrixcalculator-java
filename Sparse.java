//Sparse.java
//Srijitha Somangili
//PA3
//file that executes matrix operations

import java.io.*;
import java.util.Scanner;

public class Sparse{
   public static void main(String[] args) throws IOException{
      Scanner in = null;
      PrintWriter out = null;
      String line = null;
      String[] token = null;
      int i, n, lineNumber = 0;
 
      if(args.length < 2){
         System.err.println("Usage: FileIO infile outfile");
         System.exit(1);
      }
      
      in = new Scanner(new File(args[0]));
      int lineA;
      int lineB;
      
      line = in.nextLine() + " ";
      token = line.split("\\s+");

      Matrix A = new Matrix(Integer.parseInt(token[0]));
      Matrix B = new Matrix(Integer.parseInt(token[0]));
      lineA = Integer.parseInt(token[1]) +2;
      lineB = Integer.parseInt(token[2]) +lineA+1;

      in = new Scanner(new File(args[0]));
      out = new PrintWriter(new FileWriter(args[1]));    

      while(in.hasNextLine()){
         lineNumber++;
         line = in.nextLine()+" ";
         token = line.split("\\s+");
        if(lineNumber > 2 && lineNumber <= lineA){
          A.changeEntry(Integer.parseInt(token[0]),Integer.parseInt(token[1]),Double.parseDouble(token[2]));
         }
        if(lineNumber > (lineA +1)  && lineNumber <= lineB){
          B.changeEntry(Integer.parseInt(token[0]),Integer.parseInt(token[1]),Double.parseDouble(token[2]));
         }
      }
 
      out.println("A has " + A.getNNZ()+" non-zero entries:");
      out.println(A);
      out.println("B has " + B.getNNZ()+" non-zero entries:");
      out.println(B);

      out.println("(1.5)*A =");
      out.println(A.scalarMult(1.5));

      out.println("A+B =");
      out.println(A.add(B));

      out.println("A+A =");
      out.println(A.add(A));
 
     out.println("B-A =");
     out.println(B.sub(A));

     out.println("A-A =");
     out.println(A.sub(A));

     out.println("Transpose(A) =");
     out.println(A.transpose());

     out.println("A*B =");
     out.println(A.mult(B));
   
     out.println("B*B =");
     out.print(B.mult(B));

     in.close();
     out.close();
   }
}
