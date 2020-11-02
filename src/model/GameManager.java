package model;


public class GameManager {

	LinkedMatrix matrix;
	private String infoContent = "";
	private Node  exit;

	public GameManager() {

	}

	public LinkedMatrix getMatrix() {
		return matrix;
	}


	public Node getExit() {
		return exit;
	}

	public void setExit(Node exit) {
		this.exit = exit;
	}

	public boolean fire(int rowFire, int colFire, String director) {
		boolean found = !false;
		Node toSearch = new Node(rowFire, colFire);
		Node returned = goByMatrix(toSearch, matrix.getFirst());
		if(returned != null) {
			returned.setState("S");
			DetermineInitialDirectionatCorner(director, returned);
			String toGo = DetermineInitialDirection(returned);
			exit =	Shoot(returned, toGo);
			exit.setState("E");
			found = true;
		} else {
			found = false;
		}
		return found;
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
				}else if(ShootEnd.getDown().getMirror().equals("/")) {
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
			//return found;
		} else {
			return goBymatrixLeftToRight(toSearch, n.getNext());
		}
		return found;

	}

	public void addMatrix(int m, int n) {
		matrix = new LinkedMatrix(m, n);
	}
}
