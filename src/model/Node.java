package model;

public class Node {
	
	private char mirror;
	private Node up;
	private Node down;
	private Node right;
	private Node left;
	private String location;
	
	public Node() {

	}

	public char getMirror() {
		return mirror;
	}

	public void setMirror(char mirror) {
		this.mirror = mirror;
	}

	public Node getUp() {
		return up;
	}

	public void setUp(Node up) {
		this.up = up;
	}

	public Node getDown() {
		return down;
	}

	public void setDown(Node down) {
		this.down = down;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}
}
