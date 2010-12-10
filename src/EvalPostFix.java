import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;


public class EvalPostFix {
	
	public static LinkedList<Token> tokenizer(String expr){
		LinkedList<Token> tokenList = new LinkedList<Token>();
		char currentChar, addChar;
		int addInt;
		String integerKeep = "";
		
		for(int i=0; i < expr.length(); i++){
			currentChar = expr.charAt(i);
			addChar = '0';addInt = 0;
			
			switch(currentChar){
			case '+': case '*': addChar = currentChar; break;
			case ' ': continue;
			case '-': if((expr.charAt(i+1)==' ')) { addChar='-'; break;}
			default: integerKeep += currentChar; if(expr.charAt(i+1)==' ') {addInt = Integer.parseInt(integerKeep); integerKeep="";} else continue;
			}
			
			tokenList.add(new EvalPostFix().new Token(addInt,addChar));
		}
		
		for(Token token : tokenList)
			System.out.println(token.getOperand() + " " + token.getOperator());
		
		return tokenList;
	}
	
	public static int eval(LinkedList<Token> tokenList){
		int currentOperand; char currentOperator;
		
		Stack<Integer> stack = new Stack<Integer>();
		
		for(Token token : tokenList){
			currentOperand = token.getOperand(); currentOperator = token.getOperator();
			
			if(currentOperator == '0')
				stack.push(currentOperand);
			else
				stack.push(operate(stack.pop(),stack.pop(),currentOperator));
		}
		
		return stack.pop();
	}
	
	public static int operate(int a, int b, char c){
		switch(c){
		case '+': return b + a;
		case '-': return b - a;
		case '*': return b * a;
		default: return 0;
		}
	}
	
	public static LinkedList<Token> convert(String s){
		LinkedList<Token> tokenList = new LinkedList<Token>();
		Stack<Character> opStack = new Stack<Character>();
		char currentChar, addChar;
		int addInt;
		String integerKeep = "";
		
		for(int i=0; i < s.length(); i++){
			currentChar = s.charAt(i);
			addChar = '0';addInt = 0;

			char c;
			switch(currentChar){
			case '+':
				while(opStack.size() > 0){
					c = opStack.pop();
					if(c == '('){
						opStack.push('(');
						break;
					}
					tokenList.add(new EvalPostFix().new Token(addInt, c));
				}
				opStack.push(currentChar);
				continue;
			case '*':
				opStack.push(currentChar); continue;
			case ' ': continue;
			case '-': 
				if(s.charAt(i+1)==' ') {
					while(opStack.size() > 0){
						c = opStack.pop();
						if(c == '('){
							opStack.push(c);
							break;
						}
						tokenList.add(new EvalPostFix().new Token(addInt, c));
					}
					opStack.push(currentChar);
					continue;
				}
			case '(': opStack.add(currentChar);continue;
			case ')':
				while(opStack.size() > 0){
					c = opStack.pop();
					if(c == '(') break;
					tokenList.add(new EvalPostFix().new Token(addInt, c));
				}
				continue;
			default: integerKeep += currentChar; if(s.length() <= i + 1 || (s.charAt(i+1)==' ' || s.charAt(i+1)==')')) {addInt = Integer.parseInt(integerKeep); integerKeep="";} else continue;
			}

			tokenList.add(new EvalPostFix().new Token(addInt,addChar));
		}
		while(opStack.size() > 0){
			tokenList.add(new EvalPostFix().new Token(0, opStack.pop()));
		}
		
		for(Token token : tokenList)
			System.out.println(token.getOperand() + " " + token.getOperator());
		
		return tokenList;
	}
	
	
	public static void main(String[] args){
		String inFix = "2 + 3 * (9 - 3) * 4 * 3 + 5";
		
		if(args.length > 0)
			inFix = args[0];
		
		if(inFix.equals("readInput")){
			Scanner scanner = new Scanner(System.in);
			inFix = scanner.nextLine();
			
			while (!inFix.equals("end")){
				System.out.println(eval(convert(inFix)));
				inFix = scanner.nextLine();
			}
		
		} else {
			System.out.println(eval(convert(inFix)));
		}
	}
	
	class Token{
		int operand;
		char operator;
		
		public Token(int operand, char operator) {
			this.operand = operand;
			this.operator = operator;
		}
		
		public int getOperand() {
			return operand;
		}
		public void setOperand(int operand) {
			this.operand = operand;
		}
		public char getOperator() {
			return operator;
		}
		public void setOperator(char operator) {
			this.operator = operator;
		}
	}
}
