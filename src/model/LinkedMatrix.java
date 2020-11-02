package model;

public class LinkedMatrix {

	private Node first;
	private int m;
	private int n;

	public LinkedMatrix(int m, int n) {
		this.m = m;
		this.n = n;
		createMatrix();
	}

	
	public Node getFirst() {
		return first;
	}

	public void setFirst(Node first) {
		this.first = first;
	}

	private void createMatrix() {
		first = new Node(0,0);
		createRow(0,0,first);
	}

	private void createRow(int i, int j, Node currentFirstRow) {
		createCol(i,j+1,currentFirstRow,currentFirstRow.getUp());
		if(i+1<m) {
			Node downFirstRow = new Node(i+1,j);
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1,j,downFirstRow);
		}
	}

	private void createCol(int i, int j, Node prev, Node rowPrev) {
		if(j<n) {
			Node current = new Node(i, j);
			current.setPrev(prev);
			prev.setNext(current);

			if(rowPrev!=null) {
				rowPrev = rowPrev.getNext();
				current.setUp(rowPrev);
				rowPrev.setDown(current);
			}

			createCol(i,j+1,current,rowPrev);
		}
	}

	public String toString() {
		String msg;
		msg = toStringRow(first);
		return msg;
	}

	private String toStringRow(Node firstRow) {
		String msg = "";
		if(firstRow!=null) {
			msg = toStringCol(firstRow) + "\n";
			msg += toStringRow(firstRow.getDown());
		}
		return msg;
	}

	private String toStringCol(Node current) {
		String msg = "";
		if(current!=null) {
			msg = current.toString();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}

	public String toString2() {
		String msg = "";

		return msg;
	}

}
