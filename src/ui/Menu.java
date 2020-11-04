package ui;


import java.io.IOException;
import java.util.Scanner;
import model.GameManager;


public class Menu {

	static Scanner sc;
	final static int EXIT_MENU = 3;
	private GameManager gm = new GameManager();

	/**
	 * This method deploy the main program menu.
	 * <b>pre:</b>Select a valid option.<br>
	 * 
	 * <b>post:</b><br>
	 */
	private String getMenu() {
		String menu;
		menu = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
		menu += "«•«•«•«•«• THIS IS •»•»•»•»•»\n";
		menu += "«•«•«•«•«•«• THE •»•»•»•»•»•»\n"; 
		menu += "«•«•«•«• LASER GAME •»•»•»•»•»\n";
		menu += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
		menu += "1. Play!\n";
		menu += "2. Show players scores\n";
		menu += "3. Exit\n";
		menu += "Please enter an option\n";
		return menu;
	}

	/**
	 * This method receives the menu option .
	 * <b>pre:</b>Select a valid option.<br>
	 * 
	 * <b>post:</b><br>
	 */
	private void executeOperation(int option) {
		switch (option) {
		case 1:
			System.out.println("~~~~~~~~~~ PLAYING ~~~~~~~~~~");
			try {
				play();
				System.out.println("Data saved!");
			} catch (IOException e) {
				System.out.println("Data can not be saved!!");
			}
			break;
		case 2:
			System.out.println("~~~~~~~~~~ SCORES ~~~~~~~~~~");
			showScoresInOrder();
			break;
		case 3:
			exitProgram();
			break;
		default:
			System.out.println("Select a correct option");
			break;
		}
	}

	/**
	 * This method receives the Nickname, Number of columns, number of rows and number of mirrors in a line, then serialize the players information.
	 * <b>pre:</b>Enter the values in a line.<br>
	 * 
	 * @throws IOException
	 * <b>post:</b><br>
	 * 
	 */
	private void play() throws IOException {
		System.out.println("Please enter the Nickname, Number of columns, cumber of rows and number of mirrors in a line");
		String line = sc.nextLine();
		String [] parts = line.split(" ");
		String nickName = (parts[0]);
		long initialScore = 50;
		int n = Integer.parseInt(parts[1]);
		int m = Integer.parseInt(parts[2]);
		int k = Integer.parseInt(parts[3]);
		gm.addMatrix(m, n);
		if(k <= m*n) {
			gm.generateRandomMirrors(m, n, k);
			fireLocCheatCoordinates(false, m, n, true, nickName, k, initialScore);
			gm.saveData();
		} else {
			System.out.println("Mirrors must be minors than the matrix size");
		}
	}

	/**
	 * This method can do a shot, localize, go to menu or activate the cheat mode
	 * <b>pre:</b>Matrix already created.<br>
	 * <b>pre:</b>The following coordinates must be correctly aboute the matrix size created<br>
	 * 
	 * @param stop Boolean is a boolean to stop the recursive method 
	 * @param m integer is the rows of matrix
	 * @param n integer is the columns of matrix
	 * @param count boolean is just boolean to make the first one "if bloke" 
	 * @param nickName String is the player nickName
	 * @param k integer of the total
	 * @param score long player score
	 * 
	 * <b>post:</b><br>
	 * 
	 */
	private void fireLocCheatCoordinates(boolean stop,int m, int n, boolean key, String nickName, int k, long score) {
		stop = false;
		if (key) {
			System.out.println("--------- LASER MATRIX ---------");
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println(gm.getMatrix());
			System.out.println("Type menu to exit, L+coordinates to localize a mirror, type a coordinate to fire or type the word cheat to see mirrors");
		} else if (k == 0) {
			stop = true;
		} else if(stop != true){
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println("Type menu to exit, L+coordinates to localize a mirror, type a coordinate to fire or type the word cheat to see mirrors");
		}
		if(stop == true || k == 0) {
			//exit
			if(k == 0) {
				System.out.println("You won!!!");
				gm.addPlayer(nickName, score);

			} else {
				System.out.println("Leaving...");
				gm.addPlayer(nickName, score);
			}
			stop = true;
			return;
		}
		String fire = sc.nextLine();
		String [] fParts = fire.split("");
		if(fire.equalsIgnoreCase("cheat")) {
			gm.runCheat(gm.getMatrix().getFirst());
			System.out.println(gm.getMatrix());
			fireLocCheatCoordinates(stop, m, n, true, nickName, k, score);
		}
		else if(fire.equalsIgnoreCase("menu")) {
			stop = true;
			System.out.println("Leaving to menu...");
			gm.addPlayer(nickName, score);
		}
		else if(fParts[0].equalsIgnoreCase("L")){
			boolean mirrorFound = false;
			int kRest = 0;
			long scoreMult = 0;
			int rowFire = Integer.parseInt(fParts[1]);
			char colFireChar = fParts[2].charAt(0);
			int colFire = colFireChar;
			int colToFire = (char)(colFire-'A');
			String directorMirror = (fParts[3]);
			if(gm.locate(rowFire-1, colToFire, directorMirror) == false) {
				System.out.println(gm.getMatrix());
				gm.auxSearch(rowFire-1, colToFire).setState("");
			} else {
				mirrorFound = true;
			}
			if(mirrorFound == true) {
				kRest = 1;
				scoreMult = 100;
			}
			System.out.println(gm.getMatrix());
			fireLocCheatCoordinates(stop, m, n, false, nickName, k-kRest, score+scoreMult);

		} else {
			//*********** Fire ****************************

			int rowFire = Integer.parseInt(fParts[0]);
			char colFireChar = fParts[1].charAt(0);
			int colFire = colFireChar;
			int colToFire = (char)(colFire-'A');
			String directorFire = "";
			if(fParts.length == 3) {
				directorFire = (fParts[2]);
			}
			if(rowFire-1 < m && colToFire < n) {
				if(gm.fire(rowFire-1, colToFire, directorFire) == false) {
					System.out.println("Fire can not be executed");
				} else {
					System.out.println("Fired!!");
					System.out.println(gm.getMatrix());
					gm.validateState();
				}
			} else {
				System.out.println("Coordinates out of matrix!!");
			}
			System.out.println("--------- LASER MATRIX ---------");
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println(gm.getMatrix());
			fireLocCheatCoordinates(stop, m, n, false, nickName, k, score);
		}
	}

	/**
	 * This method show the players scores table 
	 * <b>pre:</b><br>
	 * 
	 * <b>post:</b><br>
	 * 
	 */
	private void showScoresInOrder() {

		if(gm.printInOrder().isEmpty()) {
			System.out.println("***** There no scores yet ******");
		} else {
			System.out.println(gm.printInOrder());	
		}
	}

	/**
	 * This method deserialize the score table
	 * <b>pre:</b><br>
	 * 
	 * 
	 * <b>post:</b><br>
	 * 
	 */
//	private void loadProgram() {
//		System.out.println("Loading data ...");
//		try{
//			gm.loadData();
//			System.out.println("The program data were loaded succesfully");
//		}catch(IOException | ClassNotFoundException e){
//			System.out.println("The data can't be load");
//		}
//	}



	//***************** UI execute ***************************************** 

	/**
	 *This method close the scanner and then, the program.
	 *<b>pre:</b> <br>
	 *<b>post:</b>The program is closed<br>
	 */
	private void exitProgram() {
		sc.close();
		System.out.println("Good bye!");
	}
	/**
	 * This method start the program, and deserialize program data.
	 * <b>pre:</b><br>
	 * 
	 * <b>post:</b><br>
	 */
	public void startMenu() {
//		loadProgram();
		String menu = getMenu();
		int option = 0;
		recursiveMenu(option, menu);
	}
	/**
	 *This method open the menu and keeps recursive until 3 is pressed
	 *<b>pre:</b> <br>
	 *<b>post:</b>d<br>
	 */
	public void recursiveMenu(int option, String menu) {
		if(option == EXIT_MENU) {
			//nothing
		} else {
			System.out.println(menu);
			option = readOption();
			executeOperation(option);
			recursiveMenu(option, menu);
		}
	}
	/**
	 *This method initialize the scanner.
	 *<b>pre:</b> <br>
	 *@return GameManager
	 *<b>post:</b>The scanner is ready<br>
	 */
	public GameManager startGame() {	
		sc = new Scanner(System.in);	
		return gm;
	}
	/**
	 *This method initialize the menu.
	 *<b>pre:</b> <br>
	 *<b>post:</b>The Menu is ready<br>
	 */
	public Menu () {
		this.gm = startGame();
	}
	/**
	 *This method read the option selected in main menu.
	 *<b>pre:</b>Program is already running<br>
	 *
	 *@return op
	 *
	 *<b>post:</b>The program is closed<br>
	 */
	private int readOption() {
		int choice = sc.nextInt();
		sc.nextLine();
		System.out.println();
		return choice;
	}
}
