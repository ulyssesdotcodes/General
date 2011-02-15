package random;
import java.util.Comparator;
import java.util.Random;


public class BST<T> {
	private Node<T> root;
	private Comparator<? super T> cmp;
	
	private static class Node<T>{
		T key;
		String note;
		Node<T> left, right;
		
		public Node(T keyInput, String noteInput){
			this(keyInput, noteInput, null, null);
		}
		
		public Node(T keyInput, String noteInput, Node<T> lt, Node<T> rt){
			key = keyInput;
			note = noteInput;
			left = lt;
			right = rt;
		}
	}
	
	public BST(){
		root = null;
	}
	
	public BST(Comparator<? super T> c){
		root = null;
		cmp = c;
	}
	
	private int myCompare(T lhs, T rhs){
		if(cmp != null)
			return cmp.compare(lhs, rhs);
		else
			return ((Comparable)lhs).compareTo(rhs);
	}
	
	
	public void makeEmpty(){
		root = null;
	}
	
	public boolean isEmpty(){
		return root==null;
	}
	
	
	public boolean contains(T x){
		return contains(x, root);
	}
	
	public String search(T x){
		return search(x, root);
	}
	
	public T findMin() throws Exception{
		if(isEmpty()) throw new Exception("The tree is empty!");
		return findMin(root).key;
	}
	
	public T findMax() throws Exception{
		if(isEmpty()) throw new Exception("The tree is empty!");
		return findMax(root).key;
	}
	
	public boolean insert(T x, String note){
		root = insert(x, note, root);
		return root!= null;
	}
	
	public void remove(T x){
		root = remove(x, root);
	}
	
	public void printTree(){
		if(isEmpty()) System.out.println("Empty Tree!");
		else printTree(root);
	}
	
	public void printTreePost(){
		if(isEmpty()) System.out.println("Empty Tree!");
		else printTreePost(root);
	}
	
	
	private void printTree(Node<T> t){
		if(t != null){
			printTree(t.left);
			System.out.println(t.key + " : " + t.note);
			printTree(t.right);
		}
	}
	
	private void printTreePost(Node<T> t){
		if(t != null){
			printTree(t.left);
			printTree(t.right);
			System.out.println(t.key + " : " + t.note);
		}
	}
	
	private boolean contains(T x, Node<T> a){
		if(a == null) return false;
		
		int compareResult = myCompare(x,a.key);

		if(compareResult<0) return contains(x, a.left);
		else if(compareResult>0) return contains(x,a.right);
		else return true;
	}
	
	private String search(T x, Node<T> a){
		if(a == null) return null;
		
		int compareResult = myCompare(x,a.key);

		if(compareResult<0) return search(x, a.left);
		else if(compareResult>0) return search(x,a.right);
		else return a.note;
	}
	
	private Node<T> findMin(Node<T> a){
		if(a==null) return null;
		if(a.left == null) return a;
		else return findMin(a.left);
	}
	
	private Node<T> findMax(Node<T> a){
		if(a==null) return null;
		if(a.right == null) return a;
		else return findMin(a.right);
	}
	
	private Node<T> insert(T x, String note, Node<T> r){
		 if(r==null)
			 return new Node<T>(x, note, null, null);
		 
		 int compareResult = myCompare(x, r.key);
		 
		 if(compareResult < 0) r.left = insert(x, note, r.left);
		 else if(compareResult > 0) r.right = insert(x, note, r.right);
		 
		 return r;
	}
	
	private Node<T> remove(T x, Node<T> r){
		if(r==null) return r;
		
		int compareResult = myCompare(x, r.key);
		
		if(compareResult < 0) r.left = remove(x, r.left);
		else if(compareResult > 0) r.right = remove(x, r.right);
		else if(r.left != null && r.right != null){
			r.key = findMin(r.right).key;
			r.right = remove(r.key, r.right);
		}
		else r = (r.left != null) ? r.left : r.right;
			
		return r;
	}
	
	
	
	
	public static void main(String[] args) throws Exception{
		int n =20, opt = 1, seed = 100, range = 100;
		if(args.length > 0)
			n = Integer.parseInt(args[0]);
		if(args.length > 1)
			opt = Integer.parseInt(args[1]);
		if(args.length > 2)
			seed = Integer.parseInt(args[2]);
		if(args.length > 3)
			range = Integer.parseInt(args[3]);
		
		Random generator = new Random(seed);
		
		BST<Integer> myTree = new BST<Integer>();
		
		boolean keepGoing;
		String dataValue;
		for(int i =0; i<n; i++){
			keepGoing = true;
			dataValue = "";
			while(keepGoing){
				dataValue += (char)(97 + generator.nextInt(26));
				keepGoing = generator.nextBoolean();
			}
			myTree.insert(generator.nextInt(range), dataValue);
		}
		
		if(opt==0){
			System.out.println(myTree.root.key + " : " + myTree.root.note);
			System.out.println(myTree.findMin());
			System.out.println(myTree.findMin());
		}
		else if(opt == 1){
			myTree.printTree();
		}
		else if(opt == 2){
			myTree.printTreePost();
		}
	}
}
