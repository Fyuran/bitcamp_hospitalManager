package scannerChecks;

import java.util.Scanner;

public class getInput {
	private Scanner scanner = new Scanner(System.in);
	
	public static boolean isNumber(String s) {
		return s.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	
	public String askLine(String inputMsg) {
		System.out.println(inputMsg);
		String line = scanner.nextLine();
		return line;
	}
	
	public int askInt(String inputMsg) {
		int number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextInt()) {
				number = scanner.nextInt();
				scanner.nextLine();
				break;
			} else {
				System.out.println("\u001B[31m*Inserisci un numero valido*\u001B[0m");
				scanner.nextLine();
			}
		}
		return number;
	}
	
	public double askDouble(String inputMsg) {
		double number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextDouble()) {
				number = scanner.nextDouble();
				scanner.nextLine();
				break;
			} else {
				System.out.println("\u001B[31m*Inserisci un numero valido*\u001B[0m");
				scanner.nextLine();
			}
		}
		return number;
	}
	
	public long askLong(String inputMsg) {
		long number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextLong()) {
				number = scanner.nextLong();
				scanner.nextLine();
				break;
			} else {
				System.out.println("\u001B[31m*Inserisci un numero valido*\u001B[0m");
				scanner.nextLine();
			}
		}
		return number;
	}
	
	public void close() {
		scanner.close();
	}
}
