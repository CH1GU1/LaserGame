package model;

import java.io.Serializable;

public class Player implements Serializable{

	private static final long serialVersionUID = 1L;
	private String nickName;
	private long score;
	private Player left;
	private Player right;

	/**
	 *  This method is the constructor of Player
	 * <b><pre><br><br>
	 * 
	 * @param nickName String of player nickname
	 * @param score long of player score value
	 * 
	 * <b>post:</b><br>
	 */
	public Player(String nickName, long score) {
		this.nickName = nickName;
		this.score = score;
		left = null;
		right = null;
	}
	/**
	 * This method gets the player nickName 
	 * <b><pre><br><br>
	 * 
	 * @return nickName
	 * 
	 * <b>post:</b><br>
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * This method sets the player nickName
	 * <b><pre><br><br>
	 * 
	 * @param nickName
	 * 
	 * <b>post:</b><br>
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * This method gets the player score 
	 * <b><pre><br><br>
	 * 
	 * @return score
	 * 
	 * <b>post:</b><br>
	 */
	public long getScore() {
		return score;
	}
	/**
	 * This method gets the left player
	 * <b><pre><br><br>
	 * 
	 * @return left
	 * 
	 * <b>post:</b><br>
	 */
	public Player getLeft() {
		return left;
	}
	/**
	 * This method sets a player to the left
	 * <b><pre><br><br>
	 * 
	 * @param left
	 * 
	 * <b>post:</b><br>
	 */
	public void setLeft(Player left) {
		this.left = left;
	}
	/**
	 * This method gets the right player
	 * <b><pre><br><br>
	 * 
	 * @return right
	 * 
	 * <b>post:</b><br>
	 */
	public Player getRight() {
		return right;
	}
	/**
	 * This method sets a player to the right
	 * <b><pre><br><br>
	 * 
	 * @param right
	 * 
	 * <b>post:</b><br>
	 */
	public void setRight(Player right) {
		this.right = right;
	}
}
