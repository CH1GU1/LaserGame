package model;

public class GameManager {

	private Node first;
	private int n;
	private int m;

	public GameManager() {
		first = null;
	}

	public Node getfirst() {
		return first;
	}


	public void addNode(int m, int n) {
		if(first == null) {
			Node toAdd = new Node();
			first = toAdd;
		}
		addNode(m,n-1,first);
	}


	private void addNode(int m, int n, Node current) {
		if(n > 0) {
			Node toAdd = new Node();
			if(current.getRight() == null) {
				toAdd.setLeft(current);
				current.setRight(toAdd);
			}
			addNode(m,n-1,current.getRight());
		}
		lineJump(m,n,current);
	}

	public void lineJump(int m, int n) {
		Node current = null;
		if(first.getDown() == null) {
			Node toAdd = new Node();
			toAdd.setUp(first);
			first.setDown(toAdd);
			current = first.getDown();
		}
		addNode(m-1,n-1,current);
	}

	private void lineJump(int m, int n, Node current) {
		if(m > 0) {
			Node toAdd = new Node();
			if(current.getDown() == null)
				toAdd.setUp(first);
			first.setDown(toAdd);
			Node current = first.getDown();
			addNode(m-1,n-1,current);
		}
	}



}
