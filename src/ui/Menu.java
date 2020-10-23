package ui;


import java.util.Scanner;

import model.GameManager;


public class Menu {

	private Scanner sc;
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
		int option;
		do {
			System.out.println(menu);
			option = readOption();
			executeOperation(option);

		} while (option!=EXIT_MENU);
	}
	/**
	 *This method initialize the scanner.
	 *<b>pre:</b> <br>
	 *<b>post:</b>The scanner is ready<br>
	 */
	public Menu() {
		sc = new Scanner(System.in);
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
		int op;
		op = Integer.parseInt(sc.nextLine());
		return op;
	}
}
