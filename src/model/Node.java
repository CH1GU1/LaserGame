package model;

public class Node {

	private int row;
	private int col;
	private String mirror;
	private Node next;
	private Node prev;
	private Node up;	
	private Node down;
	private String state;


	/**
	 *  This method is the constructor of Node
	 * <b><pre>:<br><br>
	 * 
	 * @param r integer of row position
	 * @param c integer of column position
	 * 
	 * <b>post:</b><br>
	 */
	public Node(int r, int c) {
		row = r;
		col = c;
		mirror = "";
		state = "";
	}

	/**
	 * This method gets the mirror
	 * <b><pre>:<br><br>
	 * 
	 * @return mirror
	 * 
	 * <b>post:</b><br>
	 */
	public String getMirror() {
		return mirror;
	}
	/**
	 * This method sets the mirror of the node
	 * <b><pre>:<br><br>
	 * 
	 * @param mirror
	 * 
	 * <b>post:</b><br>
	 */
	public void setMirror(String mirror) {
		this.mirror = mirror;
	}
	/**
	 * This method gets the visual state
	 * <b><pre>:<br><br>
	 * 
	 * @return state
	 * 
	 * <b>post:</b><br>
	 */
	public String getState() {
		return state;
	}
	/**
	 * This method sets the status of node
	 * <b><pre>:<br><br>
	 * 
	 * @param state
	 * 
	 * <b>post:</b><br>
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * This method gets the row position
	 * <b><pre>:<br><br>
	 * 
	 * @return row
	 * 
	 * <b>post:</b><br>
	 */
	public int getRow() {
		return row;
	}
	/**
	 * This method gets the column position
	 * <b><pre>:<br><br>
	 * 
	 * @return col
	 * 
	 * <b>post:</b><br>
	 */
	public int getCol() {
		return col;
	}
	/**
	 * This method gets the name of column in char
	 * <b><pre>:<br><br>
	 * 
	 * @return column name
	 * 
	 * <b>post:</b><br>
	 */
	public char getNameCol() {
		return (char)('A'+col);
	}
	/**
	 * This method gets the node next to
	 * <b><pre>:<br><br>
	 * 
	 * @return next
	 * 
	 * <b>post:</b><br>
	 */
	public Node getNext() {
		return next;
	}
	/**
	 * This method gets the previous node
	 * <b><pre>:<br><br>
	 * 
	 * @return prev
	 * 
	 * <b>post:</b><br>
	 */
	public Node getPrev() {
		return prev;
	}
	/**
	 * This method gets the upside node
	 * <b><pre><br><br>
	 * 
	 * @return up
	 * 
	 * <b>post:</b><br>
	 */
	public Node getUp() {
		return up;
	}
	/**
	 * This method gets the down one node
	 * <b><pre>:<br><br>
	 * 
	 * @return down
	 * 
	 * <b>post:</b><br>
	 */
	public Node getDown() {
		return down;
	}
	/**
	 * This method sets the previous node
	 * <b><pre>:<br><br>
	 * 
	 * @param p as Node
	 * 
	 * <b>post:</b><br>
	 */
	public void setPrev(Node p) {
		prev = p;
	}
	/**
	 * This method sets the next node
	 * <b><pre><br><br>
	 * 
	 * @param n as Node
	 * 
	 * <b>post:</b><br>
	 */
	public void setNext(Node n) {
		next = n;
	}
	/**
	 * This method sets the upside node
	 * <b><pre><br><br>
	 * 
	 * @param u as Node
	 * 
	 * <b>post:</b><br>
	 */
	public void setUp(Node u) {
		up = u;
	}
	/**
	 * This method sets the down one node
	 * <b><pre><br><br>
	 * 
	 * @param d as Node
	 * 
	 * <b>post:</b><br>
	 */
	public void setDown(Node d) {
		down = d;
	}
	/**
	 * This method overrides the toString class method to display it on matrix
	 * <b><pre><br><br>
	 * 
	 * @return 
	 * 
	 * <b>post:</b><br>
	 */
	public String toString() {
		return "[ "+getState()+" ]";
	}
}
