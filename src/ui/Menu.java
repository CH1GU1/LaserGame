package ui;


//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
import java.util.Scanner;
import model.GameManager;


public class Menu {
	//	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

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
		menu += "2. Show score\n";
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
			play();
			break;
		case 2:

			break;
		case 3:
			exitProgram();
			break;
		default:
			System.out.println("Select a correct option");
			break;
		}

	}

	private void play() {
		System.out.println("Please enter the Nickname, Number of columns, cumber of rows and number of mirrors in a line");
		String line = sc.nextLine();
		String [] parts = line.split(" ");
		String nickName = (parts[0]);
		int n = Integer.parseInt(parts[1]);
		int m = Integer.parseInt(parts[2]);
		int k = Integer.parseInt(parts[3]);
		gm.addMatrix(m, n);
		if(k <= m*n) {
			gm.generateRandomMirrors(m, n, k);
			fireCoordinates(false, m, n, 1, nickName, k);
		} else {
			System.out.println("Mirrors must be minors than the matrix size");
		}
	}

	public void fireCoordinates(boolean stop,int m, int n, int count, String nickName, int k) {
		stop = false;
		if (count > 0) {
			System.out.println("--------- LASER MATRIX ---------");
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println(gm.getMatrix());
			System.out.println("Type menu to exit, L to locate or coordinate to fire");
		} else {
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println("Type menu to exit, L to locate or coordinate to fire");
		}
//		System.out.println("Type menu to exit, L to locate or coordinate to fire");
		String fire = sc.nextLine();
		String [] fParts = fire.split("");
		if(fire.equalsIgnoreCase("Menu") || stop == true) {
			//exit
			stop = true;

		} else if(fParts[0].equalsIgnoreCase("L")){
			boolean mirrorFound = false;
			int kRest = 0;
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
			}
			System.out.println(gm.getMatrix());
			fireCoordinates(stop, m, n, count-1, nickName, k-kRest);

		} else {
			int rowFire = Integer.parseInt(fParts[0]);
			char colFireChar = fParts[1].charAt(0);
			int colFire = colFireChar;
			int colToFire = (char)(colFire-'A');
			String directorFire = (fParts[2]);
			if(rowFire-1 < m && colToFire < n) {
				if(gm.fire(rowFire-1, colToFire, directorFire) == false) {
					System.out.println("Fire can not be executed");
				} else {
					System.out.println("Fired!!");
					System.out.println(gm.getMatrix());
					gm.auxSearch(rowFire-1, colToFire).setState("");
					gm.getExit().setState("");
				}
			} else {
				System.out.println("Coordinates out of matrix!!");
			}
			System.out.println("--------- LASER MATRIX ---------");
			System.out.println(nickName+": "+k+"mirrors remaining");
			System.out.println(gm.getMatrix());
			fireCoordinates(stop, m, n, count-1, nickName, k);
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
	 * This method start the program, and deserialize program data.
	 * <b>pre:</b><br>
	 * 
	 * <b>post:</b><br>
	 */
	public void startMenu() {
		String menu = getMenu();
		int option = 0;
		recursiveMenu(option, menu);
	}

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
