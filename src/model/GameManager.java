package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class GameManager implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String SAVE_PATH_FILE_SCORES = "data/scores.ap2";
	private LinkedMatrix matrix;
	private Node exit;
	private Node aim;
	private Player root;
	private boolean c1 = false;
	private boolean c2 = false;
	private boolean c3 = false;
	private boolean c4 = false;

	public GameManager() {
		exit = null;
		matrix = null;
		root = null;
	}

	public Player getRoot() {
		return root;
	}

	public Node getAim() {
		return aim;
	}

	public void setAim(Node aim) {
		this.aim = aim;
	}

	public LinkedMatrix getMatrix() {
		return matrix;
	}


	public void addPlayer(String nickName, long score) {
		Player toAdd = new Player(nickName, score);
		if(root == null) {
			root = toAdd;
		} else {
			addPlayer(root, toAdd);
		}
	}

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

	public String printInOrder() {
		String info = "";
		if(root == null) {
			//nothing
		} else {
			info = printInOrder(root,"", 1);
		}
		return info;
	}

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

	public void cheatMode(int m, int n) {

	}
	
	public void runCheat(Node n) {
		if(n == null) {
			//nada
		} else {
			runLeftToRight(n);
			runCheat(n.getDown());
		}
	}
	
	public void runLeftToRight(Node n) {
		if(n == null) {
			//nada
		} else {
			n.setState(n.getMirror());
			runLeftToRight(n.getNext());
		}
	}
	
	public Node goByMatrix(Node toSearch, Node n) {
		if(n == null) {
			//nothing
		} 
		Node found = goBymatrixLeftToRight(toSearch, n);
		if(found == null) {
			return goByMatrix(toSearch, n.getDown());
		}
		return found;
	}

	private Node goBymatrixLeftToRight(Node toSearch, Node n) {
		Node found = null;
		if(n == null) {
			//nothing
		} else if(toSearch.getNameCol() == n.getNameCol() && toSearch.getRow() == n.getRow()){
			found = n;
			return found;
		} else {
			return goBymatrixLeftToRight(toSearch, n.getNext());
		}
		return found;

	}

	public Node getExit() {
		return exit;
	}

	public void setExit(Node exit) {
		this.exit = exit;
	}

	public boolean fire(int rowFire, int colFire, String director) {
		boolean found = !false;
		String corner = "";
		String toGo = "";
		Node toSearch = new Node(rowFire, colFire);
		aim = goByMatrix(toSearch, matrix.getFirst());
		if(aim != null) {

			if(!director.equalsIgnoreCase("")) {
				corner = DetermineInitialDirectionatCorner(director, aim);
				exit =	Shoot(aim, corner);
			} else {
				toGo = DetermineInitialDirection(aim);
				exit =	Shoot(aim, toGo);
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

	public boolean locate(int rowFire, int colFire, String direction) {
		boolean point = !false;
		String mirrorDirector;
		Node toSearch = new Node(rowFire, colFire);
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

	public void generateRandomMirrors(int m, int n, int k) {
		if(k != 0) {
			int randomN = (int)(Math.random()*n+1)-1;
			int randomM = (int)(Math.random()*m+1)-1;

			Node aleatory = auxSearch(randomM, randomN);

			if(aleatory.getMirror().equals("")) {
				int mirrorRandom = (int)(Math.random()*2+1);
				if(mirrorRandom==1) {
					aleatory.setMirror("/");
				}else if(mirrorRandom==2) {
					aleatory.setMirror("\\");
				}
				generateRandomMirrors(m, n, k-1);
			}else {
				generateRandomMirrors(m, n, k);
			}
		}
	}

	public String DetermineInitialDirection(Node ToLocateStart) {
		String InitialD = "";
		if(ToLocateStart.getUp() == null) {
			InitialD = "down";
		}else if(ToLocateStart.getDown() == null) {
			InitialD = "up";
		}else if(ToLocateStart.getPrev() == null) {
			InitialD = "right";
		}else if(ToLocateStart.getNext() == null) {
			InitialD = "left";
		}
		return InitialD;
	}

	public String DetermineInitialDirectionatCorner(String orientation, Node ToLocateStart) {
		String Direction = "";
		if(ToLocateStart.getUp() == null ) {
			if(orientation.equalsIgnoreCase("H") && ToLocateStart.getNext() == null) {
				Direction = "left";
			}else {
				Direction = "right";
			}if(orientation.equalsIgnoreCase("V")) {
				Direction = "down";
			}
		}
		else if(ToLocateStart.getDown() == null) {
			if(orientation.equalsIgnoreCase("H") && ToLocateStart.getNext() == null) {
				Direction = "left";
			}else {
				Direction = "right";
			} if(orientation.equalsIgnoreCase("V")) {
				Direction = "up";
			}
		}
		return Direction;
	}

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

	public Node Shoot(Node ShootEnd, String initialTrayect) {
		if(initialTrayect.equalsIgnoreCase("down")) {
			if(ShootEnd.getDown() != null) {
				if(ShootEnd.getDown().getMirror().equals("\\")) {
					return Shoot(ShootEnd.getDown(), "right");
				}else if(ShootEnd.getDown().getMirror().equals("/")) {
					return 	Shoot(ShootEnd.getDown(), "left");
				}else {
					return	Shoot(ShootEnd.getDown(), initialTrayect);
				}
			}
			return ShootEnd;
		}
		if(initialTrayect.equalsIgnoreCase("up")) {
			if(ShootEnd.getUp() != null) {
				if(ShootEnd.getUp().getMirror().equals("\\")) {
					return	Shoot(ShootEnd.getUp(), "left");
				}else if(ShootEnd.getUp().getMirror().equals("/")) {
					return	Shoot(ShootEnd.getUp(), "right");
				}else {
					return	Shoot(ShootEnd.getUp(), initialTrayect);
				}
			}
			return ShootEnd;
		}
		if(initialTrayect.equalsIgnoreCase("Right")) {
			if(ShootEnd.getNext() != null) {
				if(ShootEnd.getNext().getMirror().equals("\\")) {
					return	Shoot(ShootEnd.getNext(), "down");
				}else if(ShootEnd.getNext().getMirror().equals("/")) {
					return	Shoot(ShootEnd.getNext(), "up");
				}else {
					return	Shoot(ShootEnd.getNext(), initialTrayect);
				}
			}
			return ShootEnd;
		}
		if(initialTrayect.equalsIgnoreCase("Left")) {
			if(ShootEnd.getPrev() != null) {
				if(ShootEnd.getPrev().getMirror().equals("\\")) {
					return	Shoot(ShootEnd.getPrev(), "up");
				}else if(ShootEnd.getPrev().getMirror().equals("/")) {
					return	Shoot(ShootEnd.getPrev(), "down");
				}else {
					return	Shoot(ShootEnd.getPrev(), initialTrayect);
				}
			}
		}
		return ShootEnd;
	}

	public Node auxSearch(int row, int col) {
		Node toReturn  = new Node(row, col);
		return goByMatrix(toReturn, matrix.getFirst());
	}

	public void addMatrix(int m, int n) {
		matrix = new LinkedMatrix(m, n);
	}
}
