//Graphic calculater co-authored by Ulysses Popple and Andrew Flockhart

package Calculator.gui;


import java.applet.Applet;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Stack;
import Calculator.backend.CalcBackend;



public class CalcGUI extends Applet {

	private static final long serialVersionUID = -2428698862253362923L;

	private Button[] numbers;
	private LinkedList<Button> allButtons; 
	private Button C, CE, leftParen, rightParen, ln, sqrt, powerOf, mult, divide, add, subtract, equals, dec, recip, M, A, X, E, L, R, S;
	private LinkedList<TextField> allTextFields;
	private TextField expression, eval, regM, regX, regL;
	private LinkedList<Component> allComponents;
	private Label errorBox;
	private Choice eList;
	private String name; 
	private TextField currentEdit;




	private boolean store = false;
	private boolean reload =false;
	private int rightParensCount = 0;
	private int leftParensCount = 0;

	private GridBagLayout gridBag;
	private GridBagConstraints gbC;


	public void init(){
		//		Resize the applet to make it look nice. This will change with the HTML file probably.
		this.setSize(450,250);


		/*	
		Create a linked list of all the components
		 */	


		allComponents = new LinkedList<Component>();

		numbers = new Button[10];

		for(int i=0;i<=9;i++)
			numbers[i] = new Button(""+i);


		allButtons = new LinkedList<Button>();

		//		Top row
		allComponents.add(expression = new TextField());
		allComponents.getLast().setName("expression");
		allComponents.add(E = new Button("E"));
		allComponents.getLast().setName("E");

		//		ExpressionList selecter
		eList = new Choice();
		eList.insert("Expression List", 0);
		eList.setName("eList");
		allComponents.add(eList);
		allComponents.add(L = new Button("L"));
		allComponents.getLast().setName("L");

		//		Second row
		allComponents.add(eval = new TextField());
		allComponents.getLast().setName("eval");
		//		allComponents.add(A = new Button("A"));
		//		allComponents.getLast().setName("A");

		//		Third Row
		allComponents.add(C = new Button("C"));
		allComponents.getLast().setName("C");
		allComponents.add(CE = new Button("CE"));
		allComponents.getLast().setName("CE");
		allComponents.add(leftParen = new Button("("));
		allComponents.getLast().setName(" ( ");
		allComponents.add(rightParen = new Button(")"));
		allComponents.getLast().setName(" ) ");
		allComponents.add(ln = new Button("ln"));
		allComponents.getLast().setName("l ( ");
		allComponents.add(regM = new TextField(20));
		allComponents.getLast().setName("regM");
		allComponents.add(M = new Button("M"));
		allComponents.getLast().setName("M");

		//		Fourth row
		allComponents.add(numbers[7]);
		allComponents.getLast().setName("7");
		allComponents.add(numbers[8]);
		allComponents.getLast().setName("8");
		allComponents.add(numbers[9]);
		allComponents.getLast().setName("9");
		allComponents.add(sqrt = new Button("sqrt"));
		allComponents.getLast().setName("s ( ");
		allComponents.add(powerOf = new Button("^"));
		allComponents.getLast().setName(" ^ ");
		allComponents.add(regX = new TextField(20));
		allComponents.getLast().setName("regX");
		allComponents.add(X = new Button("X"));
		allComponents.getLast().setName("X");

		//		Fifth row
		allComponents.add(numbers[4]);
		allComponents.getLast().setName("4");
		allComponents.add(numbers[5]);
		allComponents.getLast().setName("5");
		allComponents.add(numbers[6]);
		allComponents.getLast().setName("6");
		allComponents.add(mult = new Button("*"));
		allComponents.getLast().setName(" * ");
		allComponents.add(divide = new Button("/"));
		allComponents.getLast().setName(" / ");

		//		Sixth row
		allComponents.add(numbers[1]);
		allComponents.getLast().setName("1");
		allComponents.add(numbers[2]);
		allComponents.getLast().setName("2");
		allComponents.add(numbers[3]);
		allComponents.getLast().setName("3");
		allComponents.add(add = new Button("+"));
		allComponents.getLast().setName(" + ");
		allComponents.add(subtract = new Button(" -"));
		allComponents.getLast().setName(" - ");

		//		Seventh row
		allComponents.add(numbers[0]);
		allComponents.getLast().setName("0");
		allComponents.add(dec = new Button("."));
		allComponents.getLast().setName(".");
		allComponents.add(equals = new Button("="));
		allComponents.getLast().setName("=");
		allComponents.add(recip = new Button("recip"));
		allComponents.getLast().setName("r ( ");
		allComponents.add(R = new Button("R"));
		allComponents.getLast().setName("R");
		allComponents.add(S = new Button("S"));
		allComponents.getLast().setName("S");

		//		Error reporting row
		allComponents.add(errorBox = new Label("No errors currently."));
		allComponents.getLast().setName("ErrorBox");

		//		Create the gridbaglayout and the constraints variable
		gridBag = new GridBagLayout();
		gbC = new GridBagConstraints();

		//		Give all the components the same weight
		gbC.weightx = 1.0;

		//		Set this panel to the gridbaglayout
		this.setLayout(gridBag);

		//		Add padding to the inside so some columns are less obviously bigger than others.
		gbC.ipadx = 50;

		//		Create a temp button
		Button b;

		//		Set the current edit to the expression field
		currentEdit = expression;

		//		Initiate a variable called gridy which will allow us to determine which row a component should be in
		int gridy = 0;

		//		Loop through all the components to create a boring but function GUI!
		for(Component c: allComponents){

			//			Initiate a variable to determine which component is in c
			name = c.getName();

			//			Add an action listener to each button

			if(c.getClass().getName().equals("java.awt.Button")){
				b = (Button)c;
				if(name.equals("C")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(currentEdit.getText().length() == 0)
								errorBox.setText("No edits to undo!");
							else{
								String currentText = currentEdit.getText().substring(0,currentEdit.getText().length()-1);
								if(currentText.endsWith("(")) leftParensCount--;
								else if (currentText.endsWith(")")) rightParensCount--;
								System.out.println(leftParensCount + " " + rightParensCount);
								if(currentText.lastIndexOf(" ")!=-1)
									currentEdit.setText(currentText.substring(0,currentText.lastIndexOf(" ")));
								else currentEdit.setText("");
								currentText = currentEdit.getText();
								if(currentText.endsWith("l ")||currentText.endsWith("r ")||currentText.endsWith("s "))
									currentEdit.setText(currentText.substring(0,currentText.length()-2));
							}
						}
					});
				} else if(name.equals("CE")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							currentEdit.setText("");
							rightParensCount = 0; leftParensCount = 0;
						}
					});
				}

				else if(name.equals("R")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							reload = true;
						}
					});
				} else if(name.equals("S")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							store= true;
						}
					});
				} else if(name.equals("M")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(store){//TODO dont we need two booleans?
								regM.setText(eval.getText());
								store = false;
							} if(reload) {
								expression.setText(expression.getText()+regM.getText());
								reload = false;
							}
						}
					});
				}  else if(name.equals("X")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(store){//TODO dont we need two booleans?
								regX.setText(eval.getText());
								store = false;
							} if(reload) {
								expression.setText(expression.getText()+regX.getText());
								reload = false;
							}
						}
					});
				} else if(name.equals("=")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							//error check, then tokenize, convert, eval, paint to A
							String input = currentEdit.getText();
							String error = hasError(input);  
							Double theResult;
							if(!error.equals("OK"))//has error
								errorBox.setText(error);
							else{ //no error
								expression.setText("");
								rightParensCount = 0;
								leftParensCount = 0;
								theResult= CalcBackend.eval( CalcBackend.convert(  CalcBackend.tokenize(input)));
								eval.setText(theResult+"");   
							}

						}
					});
				} else if(name.equals("L")){
					b.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							if(store){
								if(hasError(currentEdit.getText()).equals("OK")){
									if(eList.getItem(0).equals("Expression List"))
										eList.removeAll();
									eList.add(currentEdit.getText());
								}
								else
									errorBox.setText("Error: The expression stinks. Format it right.");
							} if(reload){
								expression.setText(expression.getText()+ eList.getSelectedItem() );           
								reload = false;
							} 
						}
					});
				}
				else if(!(name.equals("C") ||
						name.equals("CE") ||
						name.equals("R") ||
						name.equals("S") ||
						name.equals("M") ||
						name.equals("A") ||
						name.equals("X") ||
						name.equals("E") ||
						name.equals("L") ||
						name.equals("=") ||
						name.equals("ErrorBox"))){
					b.addActionListener(new ActionListener(){
						String curName = name;
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							String currentText = currentEdit.getText();
							String lastEdit = "";
							if(currentText.length() != 0 && currentText.lastIndexOf(" ") == currentText.length()-1)
								lastEdit = currentText.substring(currentText.substring(0,currentText.length()-1).lastIndexOf(" "), currentText.length()-1);
							//							If ( the current length is 0 or last edit is an operation ) and the current edit is an operation, throw an error
							if((currentText.length() == 0 ||
									(lastEdit.equals(" *") ||
											lastEdit.equals(" /") ||
											lastEdit.equals(" -") ||
											lastEdit.equals(" +") ||
											lastEdit.equals(" ^") ||
											lastEdit.equals(" (")))
											&&  
											(curName.equals(" * ") ||
											curName.equals(" / ") ||
											curName.equals(" - ") ||
											curName.equals(" + ") ||
											curName.equals(" ^ ") ||
											curName.equals(" ) ")))
								errorBox.setText("Error: Incorrect operator placement." );
							else if(lastEdit.length()==0 && ! (currentText.length()==0) &&
									(curName.equals(" ( ") || curName.equals("s ( ") || curName.equals("l ( ") || curName.equals("r ( ")))
								errorBox.setText("Error: That operator cannot be next to a variable.");
							else {
								if((lastEdit.equals(" (") || lastEdit.equals(" )")) && (curName.equals(" ) ") || curName.equals(" ( "))){
									curName = curName.substring(1, curName.length());
									System.out.println(lastEdit + "..." + curName);
								}
								errorBox.setText("No errors currently");
								currentEdit.setText(currentText + curName);

								if(curName.contains("(") || curName.equals("s ( ") || curName.equals("r ( ") || curName.equals("l ( ")) leftParensCount++;
								else if(curName.contains(")")) rightParensCount++;
							}


						}

					});
				}
			}

			//			Create the GUI

			//			Set the gridy to the value calculated in the run before
			gbC.gridy = gridy;

			//			Change the gridy for the next component. This does not change the position which this component will be put in.
			if(name.equals("E") || 
					name.equals("L") || 
					name.equals("eval") || 
					name.equals("M") ||
					name.equals("X") ||
					name.equals(" / ") ||
					name.equals(" - ") ||
					name.equals("S")){
				gridy ++;
			}

			//			Change the width for components using 2 columns
			if(name.equals("0") || name.equals("R") || name.equals("S")){
				gbC.gridwidth = 2;
			} else {
				gbC.gridwidth = 1;
			}

			//			Change the height for components using 2 rows
			if(name.equals("R") || name.equals("S") ){
				gbC.gridheight = 2;
				gbC.gridy = 6;
			} else if(name.equals("ErrorBox"))
				gbC.gridheight = 2;
			else {
				gbC.gridheight = 1;
			}

			//			Change the width for the text fields
			if(name.equals("expression")||name.equals("eval") || name.equals("eList") || name.equals("ErrorBox")){
				gbC.gridwidth = 9;
			} else if(name.equals("regM")||name.equals("regL")||name.equals("regX")){
				gbC.gridwidth = 4;
			}

			//			Make each component fill up the space it's given fully
			gbC.fill = GridBagConstraints.BOTH;

			//			Set the space between components
			gbC.insets = new Insets(3,3,3,3);

			//			Apply the constraints on the component to the component
			gridBag.setConstraints(c, gbC);

			//			Add the component to the panel
			this.add(c);
		}
	}

	public String hasError(String input){

		//is it too long
		if(input.length() >= 80)
			return("Your input is too long; please try a shorter expression");

		//is it too short  
		if(input.length() == 0)
			return("Please input an expression to evaluate"); //underflow error




		//does it have uneven parentheses
		for(int i = 0; i<input.length(); i++){
			if(input.charAt(i) == '(')
				leftParensCount++;
			if(input.charAt(i) == ')')
				rightParensCount++;
		}

		if (leftParensCount != rightParensCount)
			return("Unmatched Parentheses");




		//does it have unmatched parentheses

		Stack parens = new Stack();
		if(rightParensCount != leftParensCount)
			return("Mismatched Parentheses");
		else//parens are equal, but they might not be in the right order
			for(int i= 0; i< input.length(); i++){
				if(input.charAt(i) == '(')
					parens.push(input.charAt(i));
				if((input.charAt(i) == ')')&& parens.isEmpty())
					return ("Mismatched Parentheses");
			}

		return ("OK");
	}



}

