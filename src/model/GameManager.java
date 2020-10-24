package model;

public class GameManager {

	private Node first;
	private int nFinal;
	private int mFinal;
	private Node firstPerLine;

	public GameManager() {
		first = null;
	}

	public Node getfirst() {
		return first;
	}


	public void addNode(int m, int n) {
		nFinal = n;
		mFinal = m;
		if(first == null) {
			Node toAdd = new Node();
			first = toAdd;
		}
		addNode(m,n-1,first);
	}


	private void addNode(int m, int n, Node current) {
		if(n > 0) {
			if(current.getRight() == null) {
				Node toAdd = new Node();
				toAdd.setLeft(current);
				current.setRight(toAdd);
			}
			addNode(m,n-1,current.getRight());
		} 	
		else {
			Node currentBack = recursiveBack(1,current);
			lineJump(m-1,n,currentBack);
		}
		if(current.getLeft() == null && current != first) {
			addVerticalRelations(m, n, current, first);
		}

	}
	private void addVerticalRelations(int m, int n, Node current, Node f) {
		if(mFinal == 2) {
			if(n > 0) {
				f.getRight().setDown(current.getRight());
				current.getRight().setUp(f.getRight());
				addVerticalRelations(m,n-1,current.getRight(),first.getRight());
			}
		}
	}

	private Node recursiveBack(int i, Node current) {
		Node temporal = current;
		if(i < nFinal) {
			return recursiveBack(i+1, current.getLeft());
		} else {
			return temporal;
		}
	}

	private void lineJump(int m, int n, Node currentBack) {
		if(m > 0) {
			if(first.getDown() == null) {
				Node toAdd = new Node();
				toAdd.setUp(first);
				first.setDown(toAdd);
				Node currentNewLine = toAdd;
				addNode(m,nFinal-1,currentNewLine);
			}
			if(first != currentBack) {
				Node toAdd = new Node();
				toAdd.setUp(currentBack);
				currentBack.setDown(toAdd);
				Node currentNewLine = toAdd;
				addNode(m,nFinal-1,currentNewLine);
			}
		}
	}



}
