package consoleMenuUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
* A class that handles both UI and functionality for a numbered choice menu on console
*
* @author Daniel Camuffo
* @see java.util.ArrayList
* @see java.lang.String
* @see java.util.Scanner
* @since 1.0
*/
public class MenuUI {
	//Console text colors
	private List<Command> commands = new ArrayList<>();
	private String name;
	private static Scanner scanner = new Scanner(System.in);

	public MenuUI(String name) {
		this.name = name;
	}

	public void addCmd(Command cmd) {
		commands.add(cmd);
	}
	/**
	* Adds a new command to menu's interface
	* @author Daniel Camuffo
	* @param name Name of Menu option
	* @param fnc callback function to pass
	* @since 1.1
	*/
	public void addCmd(String name, Callback fnc) {
		commands.add(new Command(name, fnc));
	}

	public void removeCmd(String name) {
		for(int i = 0; i < commands.size(); i++) {
			if(commands.get(i).name().equalsIgnoreCase(name)) {
				commands.remove(i);
			} else {
				System.out.println("Could not find " + name);
			}
		}
	}

	/**
	* Handles the loop for the MenuUI instance
	* @author Daniel Camuffo
	* @throws NoCommandsAvailableException
	* @since 1.1
	*/
	public void showCmds() {
		if(commands.size() == 0) {
			throw new NoCommandsAvailableException();
		}
		while(true) {
	        System.out.println(Colors.toColor("-=-=-=-=" + name + "=-=-=-=-", Colors.CYAN));
	        for(int i = 0; i < commands.size(); i++) {
	        	System.out.println((i+1) + ". " + commands.get(i).name());
	        }
	        System.out.println((commands.size() + 1) + ". Esci");
	        System.out.println("Fai la tua scelta");

	        if(scanner.hasNextInt()) {
	        	int choice = scanner.nextInt() - 1;
	        	scanner.nextLine(); //advance buffer
	        	if(choice == commands.size()) {
	        		System.out.println(Colors.toColor("Uscito da " + name, Colors.CYAN));
	        		break;
	        	}
	        	if(choice >= 0  && choice < commands.size()) {
	        		Command cmd = commands.get(choice);
	        		System.out.println(Colors.toColor("Selezionato: " + cmd.name(), Colors.CYAN));
	        		cmd.execute();
	        	} else {
	        		System.out.println(Colors.toColor("*Scelta non valida*", Colors.RED));
	        	}
	        } else {
	        	System.out.println(Colors.toColor("*Inserisci un numero valido*", Colors.RED));
	        	scanner.nextLine(); //advance buffer
	        }

		}
	}
}
