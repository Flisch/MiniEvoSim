import java.util.Random;

public class Nature {
	
	
	
	public static String generateNewGenes() {
		String newGenome = "";
		for (int i = 0; i<12; i++) {
			newGenome += randomLetter();
		}
		return newGenome;
	}
	
	
	
	public static char randomLetter() {
		String letters = "ACGT";
		return letters.charAt((new Random()).nextInt(4));
	}
	
	
	
	public static String parseGenes(String genome) {
		String color = "#";
		int pos = 0;
		String segment = "";
		
		while (pos < genome.length()) {
			segment += genome.charAt(pos);
			segment += genome.charAt(pos+1);
			
			color += translateGenes(segment);
			
			segment = "";
			pos = pos+2;
		}
		
		return color;	
	}
	
	
	
	public static char translateGenes(String genes) {
		char result = '#';
		
		switch (genes) {
		case "AA": result = '0'; break;
		case "AC": result = '1'; break;
		case "AG": result = '2'; break;
		case "AT": result = '3'; break;
		case "CA": result = '4'; break;
		case "CC": result = '5'; break;
		case "CG": result = '6'; break;
		case "CT": result = '7'; break;
		case "GA": result = '8'; break;
		case "GC": result = '9'; break;
		case "GG": result = 'a'; break;
		case "GT": result = 'b'; break;
		case "TA": result = 'c'; break;
		case "TC": result = 'd'; break;
		case "TG": result = 'e'; break;
		case "TT": result = 'f'; break;
		}
		
		return result;
		
	}
	
	
	
	public static double calcFitness(String environColor, Animal animal) {
		double result = 0;
		String animalColor = parseGenes(animal.getGenome());
		
		int environmentRed = ((hexToInt(environColor.charAt(1))*16) + (hexToInt(environColor.charAt(2))));
		int environmentGreen = ((hexToInt(environColor.charAt(3))*16) + (hexToInt(environColor.charAt(4))));
		int environmentBlue = ((hexToInt(environColor.charAt(5))*16) + (hexToInt(environColor.charAt(6))));
		
		int animalRed = ((hexToInt(animalColor.charAt(1))*16) + (hexToInt(animalColor.charAt(2))));
		int animalGreen = ((hexToInt(animalColor.charAt(3))*16) + (hexToInt(animalColor.charAt(4))));
		int animalBlue = ((hexToInt(animalColor.charAt(5))*16) + (hexToInt(animalColor.charAt(6))));
		
		result = Math.sqrt(Math.pow(Math.abs(environmentRed-animalRed), 2)
				+ Math.pow(Math.abs(environmentGreen-animalGreen), 2)
				+ Math.pow(Math.abs(environmentBlue-animalBlue), 2));
		
		if (animal.getStatus() != "Alive") {
			result = 500;
		}
		
		return result;
	}
	
	
	
	public static int hexToInt(char input) {
		int result = 0;
		
		switch (input) {
		case '0': result = 0; break;
		case '1': result = 1; break;
		case '2': result = 2; break;
		case '3': result = 3; break;
		case '4': result = 4; break;
		case '5': result = 5; break;
		case '6': result = 6; break;
		case '7': result = 7; break;
		case '8': result = 8; break;
		case '9': result = 9; break;
		case 'a': result = 10; break;
		case 'b': result = 11; break;
		case 'c': result = 12; break;
		case 'd': result = 13; break;
		case 'e': result = 14; break;
		case 'f': result = 15; break;
		}
		
		return result;
	}
	
	
	
	public static Animal combineGenes(Animal mother, Animal father) {
		String childGenome = "";
		
		for (int i = 0; i<mother.getGenome().length(); i++) {
			if ((new Random()).nextInt(2) == 0) {
				childGenome += mother.getGenome().charAt(i);
			} else {
				childGenome += father.getGenome().charAt(i);
			}
		}
		
		Animal child = new Animal(childGenome);
		
		return child;
	}
	
	
	
static void sortByFitness(String environment, Animal[] pop) {
		
		int size = pop.length;
		
		for(int i = size; i>1; i--) {
			
			for (int j=0; j<i-1; j++) {
				
				if (calcFitness(environment, pop[j])
						> calcFitness(environment, pop[j+1])) {
					
					Animal tmp = pop[j];
					pop[j] = pop[j+1];
					pop[j+1] =  tmp;
				}
			}
		}
	}
}
