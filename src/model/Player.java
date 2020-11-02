package model;

public class Player {
	
	private String nickName;
	private long score;
	private Player left;
	private Player right;
	private Player parent;
	
	public Player(String nickName, long score) {
		this.nickName = nickName;
		this.score = score;
		left = null;
		right = null;
		parent = null;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public Player getLeft() {
		return left;
	}

	public void setLeft(Player left) {
		this.left = left;
	}

	public Player getRight() {
		return right;
	}

	public void setRight(Player right) {
		this.right = right;
	}

	public Player getParent() {
		return parent;
	}

	public void setParent(Player parent) {
		this.parent = parent;
	}

	
	
}
