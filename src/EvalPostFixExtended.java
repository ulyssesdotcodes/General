import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class EvalPostFixExtended{
	public static LinkedList<Token> tokenizer(String expr){
		LinkedList<Token> tokenList = new LinkedList<Token>();
		char currentChar, addChar;
		double addDouble;
		String doubleKeep = "";
		
		for(int i=0; i < expr.length(); i++){
			currentChar = expr.charAt(i);
			addChar = '0';addDouble = 0;
			
			switch(currentChar){
			case '+': case '*': case '÷': case '/': case '^': addChar = currentChar; break;
			case ' ': continue;
			case '-': if((expr.charAt(i+1)==' ')) { addChar='-'; break;}
			default: doubleKeep += currentChar; if(expr.charAt(i+1)==' ') {addDouble = Double.parseDouble(doubleKeep); doubleKeep="";} else continue;
			}
			
			tokenList.add(new EvalPostFixExtended().new Token(addDouble,addChar));
		}
		
		for(Token token : tokenList)
			System.out.println(token.getOperand() + " " + token.getOperator());
		
		return tokenList;
	}
	
	public static double eval(LinkedList<Token> tokenList){
		double currentOperand; char currentOperator;
		
		Stack<Double> stack = new Stack<Double>();
		
		for(Token token : tokenList){
			currentOperand = token.getOperand(); currentOperator = token.getOperator();
			
			if(currentOperator == '0')
				stack.push(currentOperand);
			else
				stack.push(operate(stack.pop(),stack.pop(),currentOperator));
		}
		
		return stack.pop();
	}
	
	public static LinkedList<Token> convert(String s){
		LinkedList<Token> tokenList = new LinkedList<Token>();
		Stack<Character> opStack = new Stack<Character>();
		char currentChar, addChar;
		double addDouble;
		String doubleKeep = "";
		
		for(int i=0; i < s.length(); i++){
			currentChar = s.charAt(i);
			addChar = '0';addDouble = 0;

			char c;
			switch(currentChar){
			case '+':
				while(opStack.size() > 0){
					c = opStack.pop();
					if(c == '(' || c == '-' || c == '+'){
						opStack.push(c);
						break;
					}
					tokenList.add(new EvalPostFixExtended().new Token(addDouble, c));
				}
				opStack.push(currentChar);
				continue;
			case '*':
				if(opStack.size() < 1){
					opStack.push(currentChar); continue;
				} else if (opStack.peek() == '÷' || opStack.peek() == '^'){
					while(opStack.size() > 0){
						c = opStack.pop();
						if(c == '(' || c == '-' || c == '+'){
							opStack.push(c);
							break;
						}
						tokenList.add(new EvalPostFixExtended().new Token(addDouble, c));
					}
					opStack.push(currentChar);
					continue;
				} else {
					opStack.push(currentChar); continue;
				}
			case '÷':case '/': 
				if(opStack.size() < 1){
					opStack.push(currentChar); continue;
				} else if (opStack.peek() == '*' || opStack.peek() == '^'){
					while(opStack.size() > 0){
						c = opStack.pop();
						if(c == '(' || c == '-' || c == '+'){
							opStack.push(c);
							break;
						}
						tokenList.add(new EvalPostFixExtended().new Token(addDouble, c));
					}
					opStack.push(currentChar);
					continue;
				} else {
					opStack.push(currentChar); continue;
				}
			case '^':
				opStack.push(currentChar); continue;
			case ' ': continue;
			case '-': 
				if((s.charAt(i+1)==' ')) {
					while(opStack.size() > 0){
						c = opStack.pop();
						if(c == '('){
							opStack.push('(');
							break;
						}
						tokenList.add(new EvalPostFixExtended().new Token(addDouble, c));
					}
					opStack.push(currentChar);
					continue;
				}
			case '(': opStack.add(currentChar);continue;
			case ')':
				while(opStack.size() > 0){
					c = opStack.pop();
					if(c == '(') break;
					tokenList.add(new EvalPostFixExtended().new Token(addDouble, c));
				}
				continue;
			default: doubleKeep += currentChar; if(s.length() <= i + 1 || (s.charAt(i+1)==' ' || s.charAt(i+1)==')')) {addDouble = Double.parseDouble(doubleKeep); doubleKeep="";} else continue;
			}
			
			tokenList.add(new EvalPostFixExtended().new Token(addDouble,addChar));
		}
		
		while(opStack.size() > 0){
			tokenList.add(new EvalPostFixExtended().new Token(0, opStack.pop()));
		}
		
		for(Token token : tokenList)
			System.out.println(token.getOperand() + " " + token.getOperator());
		
		return tokenList;
	}
	
	public static double operate(double a, double b, char c){
		switch(c){
		case '+': return b + a;
		case '-': return b - a;
		case '*': return b * a;
		case '÷':case '/': return b / a;
		case '^': return Math.pow(b, a);
		default: return 0;
		}
	}
	
	
	public static void main(String[] args){
		String s = "2 3 ^";
		
		String expr = "readInput";
		
		if(args.length > 0)
			expr = args[0];
		
		if(expr.equals("readInput")){
			Scanner scanner = new Scanner(System.in);
			expr = scanner.nextLine();
			
			while (!expr.equals("end")){
				System.out.println(eval(convert(expr)));
				expr = scanner.nextLine();
			}
			
		} else {
			System.out.println(eval(convert(expr)));
		}
	}
	
	class Token{
		double operand;
		char operator;
		
		public Token(double operand, char operator) {
			this.operand = operand;
			this.operator = operator;
		}
		
		public double getOperand() {
			return operand;
		}
		public void setOperand(double operand) {
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
