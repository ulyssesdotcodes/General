//Graphic calculater co-authored by Ulysses Popple and Andrew Flockhart

package Calculator.backend;
import java.util.LinkedList;
import java.util.Stack;

public class CalcBackend{

  public static void main(String[] args){
    
  }


  public static LinkedList<MyNode> tokenize(String theString){
    LinkedList<MyNode> toReturn = new LinkedList<MyNode>();
    String doubleString= "";
    MyNode toAdd= new MyNode('0', 0);
    for(int i= 0; i<theString.length(); i++){
      switch(theString.charAt(i)){
        case ' ':
         break;
        case '+':
        case 'l':
        case 'r':
        case 's':
        case '*':
        case '/':
        case '(':
        case ')':
        case '^':
        toReturn.add(new MyNode(theString.charAt(i))); break;
        case '-':
          if(i != theString.length() -1)
            if(theString.charAt(i+1) == ' ')//is an operator
             toReturn.add(new MyNode('-'));
            else// is part of a double value
              doubleString = doubleString + '-';
          else//must be an operator at the end of the String
            toReturn.add(new MyNode('-'));

          break;
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case '.':
          doubleString = doubleString +theString.charAt(i);
          if( i != theString.length() - 1){
            if((theString.charAt(i+1)== ' ')||(i == theString.length()-1)){
              toReturn.add(new MyNode('0', doubleString));
              doubleString = "";
             }
          }
          else//last character in String
            toReturn.add(new MyNode( '0', doubleString));
          
          break;
        default: break;
      }
    
    }
    
      return toReturn;
  }

  public static LinkedList<MyNode> convert(LinkedList<MyNode> inFixExp){
    LinkedList<MyNode> toReturn =new LinkedList<MyNode>();
    MyNode thisNode;
    MyNode topOperator;
    Stack theStack = new Stack();
    for(MyNode t : inFixExp){
      thisNode = t; 
      if(thisNode.getOp() == '0')//Node is a double
        toReturn.add(thisNode);
      else{//Node is an operator
        if(thisNode.getOp() =='(')
          theStack.push(thisNode);
        else if(thisNode.getOp() ==')'){//Node is right parens
          while(((MyNode)theStack.peek()).getOp() != '('){
            toReturn.add((MyNode)theStack.pop());
          }
          theStack.pop();
        }
        else if(theStack.empty())
          theStack.push(thisNode);
         else{
          topOperator =(MyNode) theStack.peek();
          if (hasPrecedence(thisNode.getOp(), topOperator.getOp())){//operator has precedence over top operator of stack
            theStack.push(thisNode);
          } 
          else{//operator does not have precedence over top operator of stack
            toReturn.add((MyNode)theStack.pop());
            theStack.push(thisNode);
          }
         }
       }
      }
    //all items have been seen
    while(!theStack.empty()){
    toReturn.add((MyNode)theStack.pop());
    }
    return toReturn;
  }


  public static Double eval(LinkedList<MyNode> postfixExp){
    Stack theStack = new Stack();
    Double temp;
    Double newVal=0.0;
    Double under;
    char op;
    String evaluate;
    MyNode thisNode;

    for( MyNode t : postfixExp){
      thisNode = t;
      if(thisNode.getOp() == '0')
        theStack.push(thisNode.getValue());
      else {
          //want to check if r/l/s, if thats the case, just pull top, apply operator, push
        if(thisNode.getOp() == 'r' || thisNode.getOp() == 'l'||thisNode.getOp() == 's'){
          op = thisNode.getOp();
          temp = (Double) theStack.pop();
          switch(op){
            case 'l':newVal = Math.log(temp); break;
            case 'r':newVal = (Double)(1/temp); break; 
            case 's':newVal = Math.sqrt(temp); break; 
          }
          theStack.push(newVal);

        }
        else{
        temp =(Double) theStack.pop();
        under =(Double) theStack.pop();
        op= thisNode.getOp();
       switch(op){
        case '+':newVal = under+temp; break;
        case '-':newVal = under-temp; break;
        case '/':newVal = under/temp; break;
        case '*':newVal = under*temp; break;
        case '^':newVal = Math.pow(under,temp); break;
        default: break;
       }
        theStack.push(newVal);
      }
     }
    }
      Double toReturn = (Double) theStack.pop();
      return toReturn;
  
  }


  public static boolean hasPrecedence( char theNewGuy, char topOperator){
  int newGuy = -1;
  int topOp = -1;
  char theChar;
  boolean first = true;
  for(int i = 0; i<2; i++){
    if( i==0)
      theChar = theNewGuy;
    else{
      theChar = topOperator;
      first = !first;
    }
    switch(theChar){
      case '+':
      case '-':
        if(first)
          newGuy = 1;
        else
          topOp =1;
        break;
      case '*':
      case '/':
        if(first)
          newGuy = 2;
        else
          topOp =2;
        break;
      case '^': 
        if(first)
          newGuy = 3;
        else
          topOp = 3;
      case 'r':
      case 's':
      case 'l':
        if(first)
          newGuy = 4;
        else
          topOp =4;
        break; 
      case '(':
        if(first)
          newGuy = -4;
        else
          topOp= -4;
        break;
      default: break;
    }
  }
  return(newGuy>topOp);
  }

}

  class MyNode{
   
   public MyNode(char c){
    theOperator= c;
   }
   public MyNode(char c, String s){
    theOperator = c;
    theValue = Double.parseDouble(s);
   }
   public MyNode(double d){
    theValue= d;
    theOperator = '0';
   }
   public MyNode(char c, double d){
    theValue = d; 

    theOperator = c;
   }
    double theValue;
    char theOperator;

    public double getValue(){
      return this.theValue;
    }
    public char getOp(){
      return this.theOperator;
    }
    public void setVal(double d){
      theValue= d;
    }
    public void setOp(char c){
      theOperator = c;
    }
    public void set(char c, double d){
      theOperator = c;
      theValue = d;
    }
    public void set(char c, String s){
      theOperator = c;
      theValue = Double.parseDouble(s);
    }
  }
