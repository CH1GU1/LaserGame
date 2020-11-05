package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameManager {

	//Initialization and constants declaration

	public final static String SAVE_PATH_FILE_SCORES = "data/scores.ap2";
	private LinkedMatrix matrix;
	private Node exit;
	private Node aim;
	private Player root;
	private boolean c1 = false;
	private boolean c2 = false;
	private boolean c3 = false;
	private boolean c4 = false;

	/**
	 *  This method is the constructor of GameManager
	 * <b><pre>:<br><br>
	 * 
	 * 
	 * <b>post:</b><br>
	 */
	public GameManager() {
		exit = null;
		matrix = null;
		root = null;
	}
	/**
	 * This method gets the first player on the binary tree 
	 * <b><pre><br><br>
	 * 
	 * @return root
	 * 
	 * <b>post:</b><br>
	 */
	public Player getRoot() {
		return root;
	}
	/**
	 * This method gets the node that the user want to fire 
	 * <b><pre><br>Coordinates must be on the matrix coordinates<br>
	 * 
	 * @return aim
	 * 
	 * <b>post:</b><br>
	 */
	public Node getAim() {
		return aim;
	}

	public void setAim(Node aim) {
		this.aim = aim;
	}
	/**
	 * This method gets the matrix
	 * <b><pre><br>Matrix must be created<br>
	 * 
	 * @return matrix
	 * 
	 * <b>post:</b><br>
	 */
	public LinkedMatrix getMatrix() {
		return matrix;
	}

	/**
	 * This is the public method to add a player to the binary tree
	 * <b><pre><br>Players must be have different scores cause is saved to a binary tree<br>
	 * 
	 * @param nickName String as the player nick name
	 * @param score long as the player score
	 * 
	 * <b>post:</b><br>
	 */
	public void addPlayer(String nickName, long score) {
		Player toAdd = new Player(nickName, score);
		if(root == null) {
			root = toAdd;
		} else {
			addPlayer(root, toAdd);
		}
	}
	/**
	 * This is the private method to add a player to the binary tree recursive way
	 * <b><pre><br>Players must be have different scores cause is saved to a binary tree<br>
	 * 
	 * @param current Player as the player reference in the recursive way
	 * @param newPlayer Player as the new player to add
	 * 
	 * <b>post:</b>Player was saved on the binary tree<br>
	 */
	private void addPlayer(Player currentPlayer, Player newPlayer) {
		if (newPlayer.getScore() < currentPlayer.getScore() && currentPlayer.getLeft() == null) {
			currentPlayer.setLeft(newPlayer);
		} else if (newPlayer.getScore() > currentPlayer.getScore() && currentPlayer.getRight() == null) {
			currentPlayer.setRight(newPlayer);
		} else {
			if(newPlayer.getScore() < currentPlayer.getScore() && currentPlayer.getLeft() != null) {
				addPlayer(currentPlayer.getLeft(), newPlayer);
				return;
			} 
			else {
				addPlayer(currentPlayer.getRight(), newPlayer);
				return;
			}
		}
	}
	/**
	 * This is the public method to print InOrder the binary tree
	 * <b><pre><br>Players must be have different scores cause this way to print is exclusive to a strict binary tree<br>
	 * 
	 * @return info as String with the players information
	 * 
	 * <b>post:</b>Player on the binary tree were deploy<br>
	 */
	public String printInOrder() {
		String info = "";
		if(root == null) {
			//nothing
		} else {
			info = printInOrder(root,"", 1);
		}
		return info;
	}
	/**
	 * This is the private method to print InOrder the binary tree in recursive way
	 * <b><pre><br>Players must be have different scores cause this way to print is exclusive to a strict binary tree<br>
	 * 
	 * @param ply as the current player to make the recursive way
	 * @param infoScores as the String with the players information
	 * @param num as integer of the players enumeration  
	 * 
	 * @return info as String with the players information
	 * 
	 * <b>post:</b>Player on the binary tree were deploy<br>
	 */
	private String printInOrder(Player ply, String infoScores, int num) {
		if (ply == null) 
			return infoScores; 

		/* first recursion on left child */
		printInOrder(ply.getLeft(), infoScores, num);

		/* then print the data of node */
		infoScores += ""+num+ ". Nickname: "+ply.getNickName()+"\nScore: "+ply.getScore()+"\n"; 

		/* now recursion on right child */
		return printInOrder(ply.getRight(), infoScores, num+1); 

	} 
	/**
	 * This is the public recursive method to enable the cheat mode to reveal the mirrors on the game
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @param n as the first matrix Node
	 * 
	 * <b>post:</b><br>
	 */
	public void runCheat(Node n) {
		if(n == null) {
			//nothing
		} else {
			runLeftToRight(n);
			runCheat(n.getDown());
		}
	}
	/**
	 * This is the private method to run the matrix left to the right and reveal mirrors on the game 
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @param n as the current node revealing mirror
	 * 
	 * <b>post:</b><br>
	 */
	private void runLeftToRight(Node n) {
		if(n == null) {
			//nothing
		} else {
			n.setState(n.getMirror());
			runLeftToRight(n.getNext());
		}
	}
	/**
	 * This is the public method to search a node on the matrix
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Node to search must be on the matrix size<br>
	 * 
	 * @param toSearch as the node to search on matrix
	 * @param n as the current Node to make the recursive way
	 * 
	 * @return Node as the node found is on the matrix, otherwise, returns null
	 * 
	 * <b>post:</b><br>
	 */
	public Node goByMatrix(Node toSearch, Node n) {
		if(n == null) {
			//nothing
		} 
		Node found = goBymatrixLeftToRight(toSearch, n);
		if(found == null) {
			found = goByMatrix(toSearch, n.getDown());
		}
		return found;
	}
	/**
	 * This is the private method to go by the matrix left to the right and search a node
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Node to search must be on the matrix size<br>
	 * 
	 * @param toSearch as the node to search on matrix
	 * @param n as the current Node to make the recursive way
	 * 
	 * @return Node as the node found is on the matrix, otherwise, returns null
	 * 
	 * <b>post:</b><br>
	 */
	private Node goBymatrixLeftToRight(Node toSearch, Node n) {
		Node found = null;
		if(n == null) {
			return found;
		} else if(toSearch.getNameCol() == n.getNameCol() && toSearch.getRow() == n.getRow()){
			found = n;
			//			return found;
		} else {
			return goBymatrixLeftToRight(toSearch, n.getNext());
		}
		return found;

	}
	/**
	 * This method returns the node where the laser will exit
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @return Node as the exit laser node
	 * 
	 * <b>post:</b><br>
	 */
	public Node getExit() {
		return exit;
	}
	/**
	 * This method makes the fire that the user type in the coordinates
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Node to fire must be on the matrix size<br>
	 * 
	 * @param rowFire as integer of the row node to fire
	 * @param colFire as integer of the column node to fire
	 * @param director String as the direction (vertical or horizontal) to fire, only when are corners
	 * 
	 * @return found as boolean if the fire was executed or not
	 * 
	 * <b>post:</b><br>
	 */
	public boolean fire(int rowFire, int colFire, String director) {
		boolean found = !false;
		String corner = "";
		String toGo = "";
		Node toSearch = new Node(rowFire, colFire);
		aim = goByMatrix(toSearch, matrix.getFirst());
		if(aim != null) {

			if(!director.equalsIgnoreCase("")) {
				corner = startCourseSpecialCase(director, aim);
				exit =	shot(aim, corner);
			} else {
				toGo = startCourse(aim);
				exit =	shot(aim, toGo);
			}
			saveMirror();
			aim.setState("S");
			exit.setState("E");
			found = true;
		} else {
			found = false;
		}
		return found;
	}
	/**
	 * This method saves the mirrors on the matrix view if the player already knew the position 
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * <b>post:</b><br>
	 */
	private void saveMirror() {

		if(getAim().getState().equalsIgnoreCase("/")) {
			c1 = true;
		} 
		if(getAim().getState().equalsIgnoreCase("\\")) {
			c2 = true;
		}
		if(getExit().getState().equalsIgnoreCase("/")) {
			c3 = true;
		}
		if(getExit().getState().equalsIgnoreCase("\\")) {
			c4 = true;
		}

	}
	/**
	 * This method check from the boolean changed by saveMirror method if the player already knew a mirror, if a boolean is true, so returns the mirror to the matrix view
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * <b>post:</b><br>
	 */
	public void validateState() {
		if(c1) {
			getAim().setState("/");
		}
		else if(c2) {
			getAim().setState("\\");
		} else {
			getAim().setState("");
		}
		if(c3) {
			getExit().setState("/");
		}
		else if(c4) {
			getExit().setState("\\");
		} else {
			getExit().setState("");
		}
		c1 = false;
		c2 = false;
		c3 = false;
		c4 = false;
	}
	/**
	 * This method makes the location to find a mirror by the user reference
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Mirror to find must be on the matrix size, not out of it coordinates<br>
	 * 
	 * @param rowLoc as integer of the row node to localize
	 * @param colLoc as integer of the column node to localize
	 * @param director String as the direction (left or right) f the mirror
	 * 
	 * @return a boolean if in the coordinates type by the user and direction was a mirror or not
	 * 
	 * <b>post:</b><br>
	 */
	public boolean locate(int rowLoc, int colLoc, String direction) {
		boolean point = !false;
		String mirrorDirector;
		Node toSearch = new Node(rowLoc, colLoc);
		Node returned = goByMatrix(toSearch, matrix.getFirst());
		if(direction.equalsIgnoreCase("L")) {
			mirrorDirector = "\\";
		}else {
			mirrorDirector = "/";
		}
		if(returned != null) {
			if(returned.getMirror().equalsIgnoreCase(mirrorDirector)) {
				returned.setState(returned.getMirror());
				point = true;
			} else {
				returned.setState("X");
				point = false;
			}
		} else {
			point = false;
		}
		return point;
	}
	/**
	 * This method makes the mirrors in a random way
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Mirrors quantity can not be more than the matrix size<br>
	 * 
	 * @param m as integer of the row 
	 * @param n as integer of the column 
	 * @param k as integer of the mirrors quantity
	 * 
	 * <b>post:</b><br>
	 */
	public void randomMirrors(int m, int n, int k) {
		if(k != 0) {
			int randomColumns = 0;
			int randomRows = 0;
			randomColumns = ((int)(Math.random()*n+1)-1);
			randomRows = ((int)(Math.random()*m+1)-1);

			Node rand = auxSearch(randomRows, randomColumns);
			if(rand.getMirror().equals("")) {
				int rand2 = (int)(Math.random()*2+1);
				if(rand2 == 1) {
					rand.setMirror("/");
				}else {
					rand.setMirror("\\");
				}
				randomMirrors(m, n, k-1);
			}else {
				randomMirrors(m, n, k);
			}
		}
	}

	/**
	 * This method define the first direction to make the laser fire, this method is not for the special case (corner fire)
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @param nodeToGo as node of player want to make the fire
	 * 
	 * @return course String of the initial course
	 * 
	 * <b>post:</b><br>
	 */
	public String startCourse(Node nodeToGo) {
		String course = "";
		if(nodeToGo.getUp() == null) {
			course = "down";
			if(!nodeToGo.getMirror().contentEquals("")) {
				if(nodeToGo.getMirror().contentEquals("/")) {
					course = "left";
				}else {
					course = "right";
				}
			} 
		}else if(nodeToGo.getDown() == null) {
			course = "up";
			if(!nodeToGo.getMirror().equals("")) {
				if(nodeToGo.getMirror().contentEquals("/")) {
					course = "right";
				}else {
					course = "left";
				}
			}
		}else if(nodeToGo.getPrev() == null) {
			course = "right";
			if(!nodeToGo.getMirror().contentEquals("")) {
				if(nodeToGo.getMirror().contentEquals("/")) {
					course = "up";
				}else {
					course = "down";
				}
			}
		}else if(nodeToGo.getNext() == null) {
			course = "left";
			if(!nodeToGo.getMirror().contentEquals("")) {
				if(nodeToGo.getMirror().contentEquals("/")) {
					course = "down";
				}else {
					course = "up";
				}
			}
		}
		return course;
	}
	/**
	 * This method define the first direction to make the laser fire when player wants to make it in a corner 
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @param nodeToGo as node of player want to make the fire
	 * 
	 * @return course String of the initial course (horizontal or vertical)
	 * @return go String of the initial course
	 * 
	 * <b>post:</b><br>
	 */
	public String startCourseSpecialCase(String course, Node nodeToGo) {
		String go = "";
		if(nodeToGo.getUp() == null ) {
			if(course.equalsIgnoreCase("H") && nodeToGo.getNext() == null ) {
				go = "left";
				if(!nodeToGo.getMirror().contentEquals("")) {
					if(nodeToGo.getMirror().contentEquals("/")) {
						go = "down";
					}else {
						go = "up";
					}
				}
			}else if(course.equalsIgnoreCase("H") && nodeToGo.getPrev() == null ) {
				go = "right";
				if(!nodeToGo.getMirror().contentEquals("")){
					if(nodeToGo.getMirror().contentEquals("/")){
						go = "up";
					}else {
						go = "down";
					}
				}
			}if(course.equalsIgnoreCase("V")){
				if(nodeToGo.getMirror().contentEquals("")) {
					go = "down";
				}else if(nodeToGo.getMirror().contentEquals("/")) {
					go = "left";
				}else {
					go = "right";
				}
			}
		}

		if(nodeToGo.getDown() == null) {
			if(course.equalsIgnoreCase("H") && nodeToGo.getNext() == null ) {
				go = "left";
				if(!nodeToGo.getMirror().contentEquals("")) {
					if(nodeToGo.getMirror().contentEquals("/")) {
						go = "down";
					}else {
						go = "up";
					}
				}
			}else if(course.equalsIgnoreCase("H") && nodeToGo.getPrev() == null ) {
				go = "right";
				if(!nodeToGo.getMirror().contentEquals("")){
					if(nodeToGo.getMirror().contentEquals("/")){
						go = "up";
					}else {
						go = "down";
					}
				}
			}if(course.equalsIgnoreCase("V")){
				if(nodeToGo.getMirror().contentEquals("")) {
					go = "up";
				}else if(nodeToGo.getMirror().contentEquals("/")) {
					go = "right";
				}else {
					go = "left";
				}
			}
		}
		return go;
	}
	/**
	 * This method serialize the players information
	 * <b><pre><br>Nothing<br>
	 * 
	 * <b>post:</b><br>
	 */
	public void saveData() throws IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE_SCORES));
		//		oos.writeObject(getInfoScores());
		oos.close();
	} 
	//	public boolean loadData() throws IOException, ClassNotFoundException{
	//		File r = new File(SAVE_PATH_FILE_SCORES);
	//		boolean loaded = false;
	//		if(r.exists()){
	//			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(r));
	//			
	//			infoScores = (String)ois.readObject();
	//			loaded = true;
	//			ois.close();	
	//		}
	//		return loaded;
	//	}
	/**
	 * This method makes the shot and go by the matrix depending of the course that the laser takes in a recursive way
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * 
	 * @param current as node of laser is passes
	 * @param course as Sting of course that laser goes
	 * 
	 * @return current when laser out of matrix
	 * 
	 * <b>post:</b><br>
	 */
	private Node shot(Node current, String course) {
		if(course.equalsIgnoreCase("down")) {
			if(current.getDown() != null) {
				if(current.getDown().getMirror().equals("\\")) {
					return shot(current.getDown(), "right");
				}else if(current.getDown().getMirror().equals("/")) {
					return shot(current.getDown(), "left");
				}else {
					return shot(current.getDown(), course);
				}
			}
			return current;
		}
		if(course.equalsIgnoreCase("up")) {
			if(current.getUp() != null) {
				if(current.getUp().getMirror().equals("\\")) {
					return	shot(current.getUp(), "left");
				}else if(current.getUp().getMirror().equals("/")) {
					return shot(current.getUp(), "right");
				}else {
					return shot(current.getUp(), course);
				}
			}
			return current;
		}
		if(course.equalsIgnoreCase("Right")) {
			if(current.getNext() != null) {
				if(current.getNext().getMirror().equals("\\")) {
					return shot(current.getNext(), "down");
				}else if(current.getNext().getMirror().equals("/")) {
					return shot(current.getNext(), "up");
				}else {
					return shot(current.getNext(), course);
				}
			}
			return current;
		}
		if(course.equalsIgnoreCase("Left")) {
			if(current.getPrev() != null) {
				if(current.getPrev().getMirror().equals("\\")) {
					return shot(current.getPrev(), "up");
				}else if(current.getPrev().getMirror().equals("/")) {
					return shot(current.getPrev(), "down");
				}else {
					return shot(current.getPrev(), course);
				}
			}
		}
		return current;
	}
	/**
	 * This method makes a node search by typing the column and row, then call the goByMatrix method 
	 * <b><pre><br>Matrix must be created, thats mean, playing<br>
	 * <b><pre><br>Node to search must be on the matrix size<br>
	 * 
	 * @param row as integer of the row 
	 * @param col as integer of the column 
	 * 
	 * @return returns the node by goByMatrix method if found, otherwise, returns null
	 * 
	 * <b>post:</b><br>
	 */
	public Node auxSearch(int row, int col) {
		Node toReturn = null;
		toReturn = new Node(row, col);
		return goByMatrix(toReturn, matrix.getFirst());
	}
	/**
	 * This method makes a linked matrix 
	 * <b><pre><br>Nothing<br>
	 * 
	 * @param m as integer of the row size
	 * @param n as integer of the column size
	 * 
	 * <b>post:</b><br>
	 */
	public void addMatrix(int m, int n) {
		matrix = new LinkedMatrix(m, n);
	}
}
