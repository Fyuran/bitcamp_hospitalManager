package consoleMenuUI;

public enum Colors {
	
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	CYAN("\u001B[36m"),
	BLACK("\u001B[30m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	WHITE("\u001B[37m");
	
	private Colors(final String color) {
		this.CODE = color;
	}
	
	@SuppressWarnings("unused")
	private String CODE;
	private static final String RESET = "\u001B[0m";
	
	
	public static String toColor(String text, Colors color) {
		return color.CODE + text + RESET;
	}
}
