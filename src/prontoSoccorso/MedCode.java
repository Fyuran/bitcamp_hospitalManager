package prontoSoccorso;

import java.util.Locale;

public enum MedCode {
	WHITE,
	GREEN,
	YELLOW,
	RED;
	
	//https://www.oracle.com/java/technologies/javase/jdk21-suported-locales.html
	private static final String currentLocale = Locale.getDefault().toLanguageTag();
	
	public static MedCode toMedCode(String s) {
		if(currentLocale == "it-IT") {
			if(s.equalsIgnoreCase("bianco")) return MedCode.WHITE;
			if(s.equalsIgnoreCase("verde")) return MedCode.GREEN;
			if(s.equalsIgnoreCase("giallo")) return MedCode.YELLOW;
			if(s.equalsIgnoreCase("rosso")) return MedCode.RED;
		}
		
		if(s.equalsIgnoreCase("white")) return MedCode.WHITE;
		if(s.equalsIgnoreCase("green")) return MedCode.GREEN;
		if(s.equalsIgnoreCase("yellow")) return MedCode.YELLOW;
		if(s.equalsIgnoreCase("red")) return MedCode.RED;
		return null;
	}
	public static MedCode toMedCode(int index) {
		switch(index) {
		case 1:
			return MedCode.WHITE;
		case 2:
			return MedCode.GREEN;
		case 3:
			return MedCode.YELLOW;
		case 4:
			return MedCode.RED;
		}
		return null;
	}
	
	@Override
	public String toString() {
		switch(this) {
			case WHITE:
				if(currentLocale == "it-IT") return "Bianco";
				return "White";
			case GREEN:
				if(currentLocale == "it-IT") return "Verde";
				return "Green";
			case YELLOW:
				if(currentLocale == "it-IT") return "Giallo";
				return "Yellow";
			case RED:
				if(currentLocale == "it-IT") return "Rosso";
				return "Red";
			default:
				return "Color";
		}
	}
}
