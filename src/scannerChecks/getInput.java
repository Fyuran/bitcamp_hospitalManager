package scannerChecks;

import java.util.Scanner;

@SuppressWarnings("unused")
public class getInput {
	private static Scanner scanner = new Scanner(System.in);
	
	public static String askLine(String inputMsg) {
		System.out.println(inputMsg);
		String line = scanner.nextLine();
		return line;
	}
	
	public static int askInt(String inputMsg) {
		int number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextInt()) {
				number = scanner.nextInt();
				scanner.nextLine();
				break;
			} else {
				System.out.println("Inserire un numero valido");
			}
		}
		return number;
	}
	
	public static double askDouble(String inputMsg) {
		double number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextDouble()) {
				number = scanner.nextDouble();
				break;
			} else {
				System.out.println("Inserire un numero valido");
			}
		}
		return number;
	}
	
	public static long askLong(String inputMsg) {
		long number = -1;
		System.out.println(inputMsg);
		while(true) {
			if(scanner.hasNextLong()) {
				number = scanner.nextLong();
				break;
			} else {
				System.out.println("Inserire un numero valido");
			}
		}
		return number;
	}
}
