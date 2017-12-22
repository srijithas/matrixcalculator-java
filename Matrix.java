//Matrix.java
//Srijitha Somangili 
//ssomangi
//Matrix ADT

public class Matrix{
   private class Entry{
      int column;
      double value;

      //creates a new Entry with c and v
      Entry(int column, double value){
          this.column = column;
          this.value = value;
      }
      public String toString(){
          return "(" + column + ", " + value+ ")";
      }
      //returns T => two entries of c and v are equal
      public boolean equals(Object x){
           boolean equ = false;
           Entry that;
           if ( x instanceof Entry){
               that = (Entry) x;
               equ =(this.column == that.column&&this.value == that.value);
           }
           return equ;
      }
   }
   List[] row;
   
   //Constructor
   // Makes a new n x n zero Matrix. pre: n>=1
   Matrix(int n){
      if(n<1){
        throw new RuntimeException("Matrix Error: Matrix() called with a negative column count");
      }
      row = new List[n+1];
      for(int i =1; i <n+1; ++i){
         row[i] = new List();
      }
   }

   //Access functions

   //getSize
   //Returns n, the number of rows and columns of this Matrix
   int getSize(){
      return row.length -1;
   }

   //getNNZ
   //Returns the number of non-zero entries in this Matrix
   int getNNZ(){
      int num = 0;
      for(int i =1; i<= getSize(); ++i){
         num += row[i].length();
      }
      return num;
   }

   //equals
   //overrides Object's equals() method
   public boolean equals(Object x){
      if( x instanceof Matrix){
         Matrix tht =  (Matrix) x;
      
      if ( getSize() != tht.getSize()){
         return false;
      }
      for (int i = 1; i <= getSize(); ++i){
         if( !(row[i].equals(tht.row[i]))){
             return false;
         }
      }
   }
      return true;
   }

   //Manipulation Procedures

   //makeZero
   // sets this Matrix to the zero state 
   void makeZero(){
      for( int i = 1; i <= getSize(); ++i){
        row[i] = new List();
      }
   }

   //copy
   //returns a new Matrix having the same entries as this Matrix 
   Matrix copy(){
      Matrix temp = new Matrix(getSize());
      for(int i =1 ; i <= getSize(); ++i){
         row[i].moveFront();
         while(row[i].index() >= 0){
         Entry filtemp = (Entry) row[i].get();
         temp.changeEntry(i, filtemp.column, filtemp.value);
         row[i].moveNext();
         }
      }
      return temp;
   }

   //changeEntry
   //changes ith row, jth column of this Matrix to x
   //pre: 1<=i<=getSize(), 1<=j<=getSize()
   void changeEntry(int i, int j, double x){
      if( i < 1 || i > getSize()){
          throw new RuntimeException("changeEntry() called Matrix invalid place on ith row, jth column");
      }
      if( j < 1 || j > getSize()){
          throw new RuntimeException("changeEntry() called Matrix invalid place on ith row, jth column");
      }
     boolean find = false;
     row[i].moveFront();
     while(row[i].index() >= 0){
         Entry E = (Entry) row[i].get();
         if( E.column == j){
             find = true;
         } else { find = false;}
         if(find){
            if(x == 0.0){
               row[i].delete();
               return;
            }else{
               E.value = x;
               return;
            }
        }
        row[i].moveNext();
     }
     if( !find && x != 0.0){
         row[i].moveFront();
         if(row[i].index() == -1){
            row[i].append(new Entry(j,x));
            return;
         }else{
            while(row[i].index() > -1 && ((Entry) row[i].get()).column <j){
               row[i].moveNext();
            }
            if(row[i].index() > -1){
               row[i].insertBefore(new Entry(j,x));
            }else{ //when cursor goes off the List and index = -1 again
               row[i].append( new Entry(j,x));
            }
         } 
     }
   }
      
   //scalarMult
   // returns a new Matrix that is the scalar product of this Matrix with x 
   Matrix scalarMult(double x){
      Matrix newM = this.copy();
      for(int i = 1; i <= newM.getSize(); ++i){
          newM.row[i].moveFront();
          while( newM.row[i].index() >= 0){
              Entry tem = (Entry) newM.row[i].get();
              newM.changeEntry(i, tem.column, x * tem.value);
              newM.row[i].moveNext();
          }
      }
      return newM;
   }

   //add
   //returns a new Matrix that is the sum of this Matrix with M
   //pre: getSize()==M.getSize()
   Matrix add(Matrix M){ 
      if(getSize() != M.getSize()){
         throw new RuntimeException("add() called on two matrices w/ different sizes");
      }
      if ( this == M){
         return this.copy().scalarMult(2);
      }
      Matrix AD = new Matrix(getSize());
      for(int i =1; i <= getSize(); ++i){
         AD.row[i] = adoper(row[i], M.row[i]);
      }
      return AD;
   }   
   //subtract
   //returns a new Matrix that is the difference of this Matrix with M
   //pre: getSize()==M.getSize()
   Matrix sub(Matrix M){
      if(getSize() != M.getSize()){
         throw new RuntimeException("sub() called on two matrices w/ different sizes");
      }
      if ( this == M){
         return new Matrix(getSize());
      }
      Matrix SUB = new Matrix(getSize());
     for(int i =1; i <= getSize(); ++i){
         SUB.row[i] = suboper(row[i], M.row[i]);
      }
      return SUB;
   }

   //transpose
   //returns a new Matrix that is the transpose of this Matrix
   Matrix transpose(){
     Matrix temp = new Matrix(getSize());
     for(int i = 1; i <= getSize(); ++i){
         row[i].moveFront();
         while(row[i].index() >= 0){
            temp.changeEntry(((Entry) row[i].get()).column, i, ((Entry) row[i].get()).value);
            row[i].moveNext();
         }
      }
      return temp;
   }
   //mult
   //returns a new Matrix that is the product of this Matrix with M
   // pre: getSize()==M.getSize()
   Matrix mult(Matrix M){
      if(getSize() != M.getSize()){
         throw new RuntimeException("sub() called on two matrices w/ different sizes");
      }
      Matrix temp = new Matrix(getSize());
      Matrix Mtrp = M.transpose();
      for(int i = 1 ; i <= getSize(); ++i){
          if(row[i].length() == 0){ 
             continue;
          }
          for( int k = 1; k <= getSize(); ++k){
             if(Mtrp.row[k].length() == 0){
               continue;
             }
             temp.changeEntry(i,k, dot(row[i], Mtrp.row[k]));
           }
      }
      return temp;
   } 
   //Other functions

   //overrides Object's toString() method
   public String toString(){
       String output= "";
       for(int i = 1; i <= getSize(); ++i){
          if(row[i].length() > 0){
             row[i].moveFront();
             output += (i + ":" + row[i]+ "\n");
          }
       }
       output.trim();
       return output;
   } 
  
   //helper function to multiply two Lists together
   private static double dot(List P, List Q){
      double prod = 0.0;
      P.moveFront();
      Q.moveFront();
      while(P.index() >= 0 && Q.index() >= 0){
         Entry ptemp = (Entry) P.get();
         Entry qtemp = (Entry) Q.get();
         if(ptemp.column > qtemp.column){
            Q.moveNext();
         }else if( qtemp.column > ptemp.column){
            P.moveNext();
         }else{
            prod += ptemp.value * qtemp.value;
            P.moveNext();
            Q.moveNext();
         }
      }
      return prod;  
   }

   //Helps add two Lists together
   private List adoper(List R, List S){
      List L = new List();
      R.moveFront();
      S.moveFront();
      while( R.index() >= 0 || S.index() >= 0){
         if(R.index() >= 0 && S.index() >= 0){
            Entry rtemp = (Entry) R.get();
            Entry stemp = (Entry) S.get();
            if(rtemp.column > stemp.column){
                L.append(new Entry(stemp.column, stemp.value));
                S.moveNext();
            }else if(rtemp.column == stemp.column){
                if( rtemp.value + stemp.value != 0){
                   L.append(new Entry(rtemp.column, rtemp.value + stemp.value));
                 }
                 R.moveNext();
                 S.moveNext();
            }else if ( rtemp.column < stemp.column){
                L.append(new Entry(rtemp.column,rtemp.value));
                R.moveNext();
            }
         }else if(R.index() >= 0){
            Entry rtemp = (Entry) R.get();
            L.append(new Entry(rtemp.column, rtemp.value));
            R.moveNext();
         }else{
            Entry stemp = (Entry) S.get();
            L.append(new Entry(stemp.column, stemp.value));
            S.moveNext();
         }
      }
      return L;
  }

  //Helps subtract Two lists 
  private List suboper(List R, List S){
      List L = new List();
      R.moveFront();
      S.moveFront();
      while( R.index() >= 0 || S.index() >= 0){
         if(R.index() >= 0 && S.index() >= 0){
            Entry rtemp = (Entry) R.get();
            Entry stemp = (Entry) S.get();
            if(rtemp.column > stemp.column){
                L.append(new Entry(stemp.column,(-1.0)* stemp.value));
                S.moveNext();
            }else if(rtemp.column == stemp.column){
                if( rtemp.value - stemp.value != 0){
                   L.append(new Entry(rtemp.column, rtemp.value - stemp.value));
                 }
                 R.moveNext();
                 S.moveNext();
            }else if ( rtemp.column < stemp.column){
                L.append(new Entry(rtemp.column,rtemp.value));
                R.moveNext();
            }
         }else if(R.index() >= 0){
            Entry rtemp = (Entry) R.get();
            L.append(new Entry(rtemp.column, rtemp.value));
            R.moveNext();
         }else{
            Entry stemp = (Entry) S.get();
            L.append(new Entry(stemp.column, (-1.0) * stemp.value));
            S.moveNext();
         }
      }
      return L;
  }
}
