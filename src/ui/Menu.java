package ui;


import java.io.IOException;
import java.util.Scanner;
import model.GameManager;


public class Menu {

	//Initialization and constants declaration

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
	public final static void clearConsole()
	{
	    try
	    {
	        final String os = System.getProperty("os.name");

	        if (os.contains("Windows"))
	        {
	            Runtime.getRuntime().exec("cls");
	        }
	        else
	        {
	            Runtime.getRuntime().exec("clear");
	        }
	    }
	    catch (final Exception e)
	    {
	        //  Handle any exceptions.
	    }
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
			clearConsole();
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
		long initialScore = 1000;
		int n = Integer.parseInt(parts[1]);
		int m = Integer.parseInt(parts[2]);
		int k = Integer.parseInt(parts[3]);
		gm.addMatrix(m, n);
		if(k <= m*n) {
			gm.randomMirrors(m, n, k);
			fireLocCheatCoordinates(false, m, n, true, nickName, k, initialScore);
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
		int size = fire.length();
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
		else if(fire.charAt(0) == 'L'){
			int kRest = 0;
			long scoreMult = 0;
			int rowFire = 0;
			int colFire = 0;
			char colToFire = 'x';
			String directorMirror = "";
			rowFire = Integer.parseInt(fire.substring(1, size-2));
			colToFire = fire.substring(size-2, size-1).charAt(0);
			colFire = (char)(colToFire -'A');
			directorMirror = fire.substring(size-1, size);
			if(gm.locate(rowFire-1, colFire, directorMirror) == false) {
				System.out.println(gm.getMatrix());
				gm.auxSearch(rowFire-1, colFire).setState("");
				scoreMult = -200;
				System.out.println("Incorrect! -200 points!");
			} else {
				kRest = 1;
				scoreMult = 100;
				System.out.println("Correct! +100 points");
			}
			System.out.println(gm.getMatrix());
			fireLocCheatCoordinates(stop, m, n, false, nickName, k-kRest, score+scoreMult);

		} else {
			fire(fire, size, m, n, nickName, k, stop, score);
		}
	}
	/**
	 * This method makes the fire
	 * <b>pre:</b>Matrix already created.<br>
	 * <b>pre:</b>The following coordinates must be correctly about the matrix size created<br>
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
	private void fire(String fire, int size, int m, int n, String nickName, int k, boolean stop, long score ) {
		//*********** Fire ****************************
		int rowFire = 0;
		int colFire = 0;
		char colToFire = 'x';
		String directorFire = "";
		if (Character.isLetter(fire.charAt(size-2))) {
			rowFire = Integer.parseInt(fire.substring(0, size-2));
			colToFire = fire.substring(size-2, size-1).charAt(0);
			colFire = (char)(colToFire -'A');
			directorFire = fire.substring(size-1, size);
		} else {
			rowFire = Integer.parseInt(fire.substring(0, size-1));
			colToFire = fire.substring(size-1, size).charAt(0);
			colFire = (char)(colToFire -'A');
		}
		if(rowFire-1 < m && colFire < n) {
			if(gm.fire(rowFire-1, colFire, directorFire) == false) {
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
	 * This method start the program.
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
