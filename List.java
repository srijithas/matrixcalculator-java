//List.java
//Srijitha Somangili 
//ssomangi
//List ADT

public class List{
  
    //contains the private class nodes for the information for the nodes
    private class Node{
      Node previous;
      Node next;
      Object  data;
    
      Node(Object data){
         previous = null;
         next = null;
         this.data = data;
      }
   
      public boolean equals(Object x){
         boolean equ = false;
         Node that;
         if(x instanceof Node){
            that = (Node) x;
            equ = (this.data.equals(that.data));
         }
         return equ;
      }

      public String toString(){
          return String.valueOf(data);
      }
    }

    private Node back;
    private Node front;
    private Node cursor;
    private int length;
    private int index;

    ///creates a new empty list
    List(){
      back = null;
      front = null;
      cursor = null;
      length = 0;
      index = -1;
    }

    //returns the number of element in this list
    int length(){
       return length;
    }

    //if cursor is defined, returns the index of the cursor element if not -1 
    int index(){
       return index;
    }

    //returns front element
    Object front(){
       if(length <= 0){
         throw new RuntimeException("List Error: front() called on empty List");
       }
       return front.data;
    }

    //returns back element
    Object back(){
       if(length <=0){
         throw new RuntimeException("List Error: back() called on empty List");
       } 
       return back.data;
    }

    //returns cursor element
    Object get(){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(index<0){
          throw new RuntimeException("List Error: get() called an unassigned index in List");
       }
       return cursor.data;
    }
 
    //returns true if this List and L are the same integer sequence
    public boolean equals(Object x){
      // boolean flag = true;
      if( x instanceof List){
        List L = (List) x;
         if(L.length != this.length){
            return false;
         }
         Node lfront = (Node) L.front;
         Node temp = this.front;
         while(lfront != null ){
             if(!(lfront.data.equals(temp.data))){
                return false;
             }
             lfront = lfront.next;
             temp = temp.next;
         }
       }
       return true;
    }
   
    //Manipulation procedures
    //resets this List to its original empty state
    void clear(){
       front = null;
       back = null;
       cursor = null;
       length = 0;
       index = -1;
    }

    //places the cursor under the front element  
    void moveFront(){
       if(length >= 1){
            cursor = front;
            index =0;
        }
    }

    //places the cursor under the back element
    void moveBack(){
       if(length >= 1){
            cursor = back;
            index = length -1;
       }
    }

    //if cursor is not at front moves one closer to the front
    //if cursor is at the front becomes undefined
    void movePrev(){
       if( index != 0  && cursor != null){
            cursor = cursor.previous;
            index = index -1;
       }else if( index == 0 && cursor!= null){
            cursor = null;
            index = -1;
       }
    }

    //if cursor is not at the back moves 1 closer to back
    //if cursor is at the back becomes undefined
    void moveNext(){
       if( index != length -1 && cursor != null){
            cursor = cursor.next;
            index = index + 1;
       }else if( index == length -1 && cursor != null){
            cursor = null;
            index = -1;
       }       
    }

    //insert new element into this List before front 
    void prepend(Object data){
       Node N = new Node(data);
       if(length==0){
            back = N;
            front = N ; 
       }else{
            front.previous = N;
            N.next = front;
            front = N;
       }
       length++;
    } 
  
    //insert new element after back
    void append(Object data){
       Node N = new Node(data);
       if(length == 0){
            back = N;
            front = N;
       }else{
            back.next =N;
            N.previous = back;
            back = N;
       }
       length++;
    }

    //insert new element before cursor
    void insertBefore(Object data){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(index<0){
          throw new RuntimeException("List Error: get() called an unassigned index in List");
       }
       if( index == 0){
           prepend(data);
           index++;
       }else{
           Node N = new Node(data);
           N.next = cursor;
           N.previous = cursor.previous;
           cursor.previous.next = N;
           cursor.previous = N;
           length++;
           index++;
       }
    }

    //inserts new element after cursor
    void insertAfter(Object data){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(index<0){
          throw new RuntimeException("List Error: get() called an unassigned index in List");
       }
       if(index == length -1){
           append(data);
       }else{
           Node N = new Node(data);
           N.previous = cursor;
           N.next = cursor.next;
           cursor.next.previous = N;
           cursor.next = N;
           length++;
       }
    }

    //deletes the front element
    void deleteFront(){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(length == 1){
          front = back = null;
          cursor= null; 
          length--;
       }else{
          front = front.next;
          front.previous = null;
          length--;
       }
       if( index>=0){ index--;}
    }

    //deletes the back element
    void deleteBack(){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(length == 1){
          front = back = null;
          cursor= null; 
          length--;
       }else{
          back = back.previous;
          back.next = null;
          length--;
       }
       if( index>=0){ index--;}       
    }

    //deletes the cursor element, becomes undefined
    void delete(){
       if(length <=0){
          throw new RuntimeException("List Error: get() called on empty List");
       }
       if(index<0){
          throw new RuntimeException("List Error: get() called an unassigned index in List");
       }
       if(cursor == front){
          deleteFront();
       }else if(cursor == back){
          deleteBack();
       }else{
            cursor.previous.next = cursor.next;
            cursor.next.previous = cursor.previous; 
            length--;
       }
       cursor = null;
       index = -1;  
    }

    //OTHER METHODS
    //Overrides Object's toString method. Returns a String representaion of this List.
    public String toString(){
       String space = "";
       Node N = front;
       while( N != null){
            space += " " +N.toString();
            N = N.next;
       }
       space.trim();       
       return space;
    }

    //Returns a new List representing the same int sequence as this List
    //cursor in the new List is undefined
  /*  List copy(){
       List L = new List();
       Node N = front;
       while ( N != null){ 
            L.append(N.data);
            N = N.next;
       }
       return L;   
    }  

    //Returns a new List which is the concatenation of this list followed by L
    //cursor in the new List is undefined
    List concat(List L){
       List temp = this.copy();
       Node N = L.front;
       while( N != null){
              temp.append(N.data);
              N = N.next;
       }
       return temp;
    } */
} 
