
import java.util.Arrays;
import java.util.Queue;

import java.util.concurrent.ArrayBlockingQueue;

@SuppressWarnings("unused")
public class RedBlackImplementation {
	public static enum COLOR{RED, BLACK};
	private Node root = null;
	
	public static class Node{
		public Node(int value, COLOR color) {
			this.value = value;
			this.color = color;
		}
		
		public Node(int value){
			this(value, COLOR.RED);
		}
		
		public COLOR color;
		public int value;
		public Node left;
		public Node right;
		public Node parent;
	}
	
	public void insert(int v) {
		if (root == null){
			root = new Node(v, COLOR.BLACK);
			return;
		}
		
		Node parent = root;
		Node current = root;
		while (current != null){
			parent = current;
			if(v < current.value)
				current = current.left;
			else
				current = current.right;
		}
		
		Node node = new Node(v);
		node.parent = parent;
		if(v < parent.value)
			parent.left = node;
		else
			parent.right = node;

		fixTree(node);
	}
	
	public void print() {
		Node MARKER_NODE = new Node(0, COLOR.BLACK);
		Node EMPTY_NODE = new Node(0, COLOR.BLACK);
		
		Queue<Node> queue = new ArrayBlockingQueue<RedBlackImplementation.Node>(100);
		queue.offer(root);
		queue.offer(MARKER_NODE);
		while(!queue.isEmpty()){
			Node curr = queue.poll();
			if(curr == MARKER_NODE && !queue.isEmpty()){
				queue.offer(MARKER_NODE);
				System.out.println("");
			}else{
				if(curr == EMPTY_NODE)
					System.out.print(" x ");
				else{
					if(curr != MARKER_NODE){
						String print = "  "+curr.value+"  ";
						if(curr.color == COLOR.RED)
							print = " ["+curr.value+"] ";
						System.out.print(print);
					}
					if(curr.left == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.left);
					
					if(curr.right == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.right);
				}
			}
		}
	}



	public Integer next(int i) {
		
		if(root==null) {
			return null;
		}
		
		Integer next = null;
		Node MARKER_NODE = new Node(0, COLOR.BLACK);
		Node EMPTY_NODE = new Node(0, COLOR.BLACK);
		
		Queue<Node> queue = new ArrayBlockingQueue<RedBlackImplementation.Node>(100);
		queue.offer(root);
		queue.offer(MARKER_NODE);
		while(!queue.isEmpty()){
			Node curr = queue.poll();
			if(curr == MARKER_NODE && !queue.isEmpty()){
				queue.offer(MARKER_NODE);
			}else{
				if(curr != EMPTY_NODE) {
					if(curr.value==i) {
						if(curr.left!=null) {
							next = curr.right.value;
							break;
						}
						else {
							break;
						}
					}
					
					if(curr.left == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.left);
					
					if(curr.right == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.right);
				}
			}
		}
		return next;
	}
	
	public Integer prev(int i) {
		
		Integer prev = null;
		
		Node MARKER_NODE = new Node(0, COLOR.BLACK);
		Node EMPTY_NODE = new Node(0, COLOR.BLACK);
		
		if(root==null) {
			return null;
		}
		Queue<Node> queue = new ArrayBlockingQueue<RedBlackImplementation.Node>(100);
		queue.offer(root);
		queue.offer(MARKER_NODE);
		while(!queue.isEmpty()){
			Node curr = queue.poll();
			if(curr == MARKER_NODE && !queue.isEmpty()){
				queue.offer(MARKER_NODE);
			}else{
				if(curr != EMPTY_NODE) {
					if(curr.value==i) {
						if(curr.parent!=null) {
							prev = curr.parent.value;
							break;
						}
					}
					if(curr != MARKER_NODE){
					}
					if(curr.left == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.left);
					
					if(curr.right == null)
						queue.offer(EMPTY_NODE);
					else
						queue.offer(curr.right);
				}
			}
		}
		return prev;
	}
	
	private void fixTree(Node node) {
		if(node.parent == null || node.parent.color == COLOR.BLACK)
			return;
		
		Node uncle = getUnclenode(node);
		
		if(isRed(uncle)){
			
			node.parent.color = COLOR.BLACK;
			node.parent.parent.color = COLOR.RED;
			uncle.color = COLOR.BLACK;
			fixTree(node.parent.parent);
		}else if(isRightChild(node)){
			
			if(node.parent.right == node)
				rotateLeft(node);
			
			node.parent.color = COLOR.BLACK;
			node.parent.parent.color = COLOR.RED;
			rotateRight(node.parent);
		}else{
			
			if(node.parent.left == node)
				rotateRight(node);
			
			node.parent.color = COLOR.BLACK;
			node.parent.parent.color = COLOR.RED;
			rotateLeft(node.parent);
		}
		
	root.color = COLOR.BLACK;
		
	}

	private void rotateRight(Node node) {
		Node x = node;
		Node y = x.parent;
		Node g = y.parent;
		Node alpha = x.left;
		Node beta = x.right;
		Node gamma = y.right;
		
		//Fixing x
		x.parent = g;
		if(g != null){
			if(isLeftChild(y))
				g.left = x;
			else
				g.right = x;
		}else{
			this.root = x;
		}
		
		x.right = y;
		
		//Fixing y
		y.parent = x;
		y.left = beta;
		
		//Fixing beta
		if(beta != null)
			beta.parent = y;
	}

	private void rotateLeft(Node node) {
		Node y 		= node;
		Node x 		= node.parent;
		Node g 		= x.parent;
		Node alpha 	= x.left;  //it does not move
		Node beta 	= y.left;
		Node gamma 	= y.right; //it does not move
		
		//Fixing y
		y.parent = g;
		
		if (g != null){
			if(isLeftChild(x))
				g.left = y;
			else
				g.right = y;
		}else{
			this.root = y;
		}
		
		//Fixing x
		y.left = x;
		x.parent = y;
		
		//Fixing beta
		x.right = beta;
		if(beta != null)
			beta.parent = x;
	}

	private static boolean isLeftChild(Node node) {
		return node.parent.left == node;
	}

	private static boolean isRightChild(Node node) {
		return node.parent == node.parent.parent.left;
	}

	private static boolean isRed(Node uncle) {
		return uncle != null && uncle.color == COLOR.RED;
	}

	private static Node getUnclenode(Node node) {
		if (node.parent == null || node.parent.parent == null)
			return null;
		
		Node parent = node.parent;
		Node grandParent = parent.parent;
		
		if (parent == grandParent.left)
			return grandParent.right;
		else
			return grandParent.left;
	}
}