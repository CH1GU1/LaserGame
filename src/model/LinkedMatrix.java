package model;

public class LinkedMatrix {

	//Initialization and constants declaration
	
	private Node first;
	private int m;
	private int n;

	/**
	 *  This method is the constructor of Player
	 * <b><pre><br><br>
	 * 
	 * @param nickName String of player nickname
	 * @param score long of player score value
	 * 
	 * <b>post:</b><br>
	 */
	public LinkedMatrix(int m, int n) {
		this.m = m;
		this.n = n;
		createMatrix();
	}

	/**
	 * This method gets first one node
	 * <b><pre><br><br>
	 * 
	 * @return first
	 * 
	 * <b>post:</b><br>
	 */
	public Node getFirst() {
		return first;
	}
	/**
	 * This method sets the first one node
	 * <b><pre><br><br>
	 * 
	 * @param first
	 * 
	 * <b>post:</b><br>
	 */
	public void setFirst(Node first) {
		this.first = first;
	}
	/**
	 * This method creates the matrix by the first one node
	 * <b><pre><br><br>
	 * 
	 * <b>post:</b><br>
	 */
	private void createMatrix() {
		first = new Node(0,0);
		createRow(0,0,first);
	}
	/**
	 * This method creates the matrix row recursive
	 * <b><pre><br><br>
	 * 
	 * @param i integer of row coordinate
	 * @param j integer of column coordinate 
	 * @param currentFirstRow Node of actual first row node 
	 * 
	 * <b>post:</b><br>
	 */
	private void createRow(int i, int j, Node currentFirstRow) {
		createCol(i,j+1,currentFirstRow,currentFirstRow.getUp());
		if(i+1<m) {
			Node downFirstRow = new Node(i+1,j);
			downFirstRow.setUp(currentFirstRow);
			currentFirstRow.setDown(downFirstRow);
			createRow(i+1,j,downFirstRow);
		}
	}
	/**
	 * This method creates the matrix column recursive
	 * <b><pre><br><br>
	 * 
	 * @param i integer of row coordinate
	 * @param j integer of column coordinate 
	 * @param prev Node of previous node
	 * @param rowPrev Node of previous row node
	 * 
	 * <b>post:</b><br>
	 */
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
	/**
	 * This method overrides the toString class method to display matrix 
	 * <b><pre><br><br>
	 * 
	 * @return msg as String with the matrix information
	 * 
	 * <b>post:</b><br>
	 */
	public String toString() {
		String msg;
		msg = toStringRow(first);
		return msg;
	}
	/**
	 * This method is the toString of rows
	 * <b><pre><br><br>
	 * 
	 * @return msg as String with the matrix rows information
	 * 
	 * <b>post:</b><br>
	 */
	private String toStringRow(Node firstRow) {
		String msg = "";
		if(firstRow!=null) {
			msg = toStringCol(firstRow) + "\n";
			msg += toStringRow(firstRow.getDown());
		}
		return msg;
	}
	/**
	 * This method is the toString of columns
	 * <b><pre><br><br>
	 * 
	 * @return msg as String with the matrix columns information
	 * 
	 * <b>post:</b><br>
	 */
	private String toStringCol(Node current) {
		String msg = "";
		if(current!=null) {
			msg = current.toString();
			msg += toStringCol(current.getNext());
		}
		return msg;
	}


}
