import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class MESManager {
	private MESGraphics graphics;
	private Animal[] population;
	private String environment;
	private Timer timer;
	private int counter = 0;
	private int generations = 0;
	
	
	
	public MESManager(){
		environment = newColorBackground();
		population = firstGeneration();
		
		graphics = new MESGraphics();
		graphics.setPopulation(population);
		graphics.setBackgroundColor(environment);
		
		timer = new Timer(1, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (graphics.getSpeed() == -1) {
					
					timer.setDelay(1);
					
					singleStep();
					counter++;
					
					graphics.pause();
					endStep();
					
				} else if (graphics.getSpeed() == 0){
					timer.setDelay(1);
				} else {
					while (counter != 0) {
						singleStep();
						counter++;
					}
					
					timer.setDelay(graphics.getSpeed());
					naturalSelection();
					population = newGeneration(population);
					counter = 0;
					endStep();
					countGenerations();
				}
			}
			
		});
		timer.start();
	}
	
	
	
	private void singleStep() {
		if (counter > 1) {
			counter = 0;
		}
		
		if (counter == 0) {
			naturalSelection();
		} else {
			population = newGeneration(population);
			counter = -1;
			countGenerations();
		}
	}
	
	
	
	private void countGenerations() {
		generations++;
		graphics.setGenerations(generations);
		/*System.out.println(
				"Generations since last environmental change: " + generations
				);*/
	}
	
	
	
	private void endStep() {		
		if ((new Random()).nextInt(50) == 0) {
			environment = newColorBackground();
			generations = 0;
			graphics.setBackgroundColor(environment);
		}
		
		graphics.setPopulation(population);
		
		System.out.println(environment);
		
		graphics.repaint();
	}
	
	
	
	private Animal[] firstGeneration() {
		
		Animal[] pop = new Animal[90];
		
		for (int i = 0; i<pop.length; i++) {
			pop[i] = new Animal(Nature.generateNewGenes());
		}
		
		return pop;
	}
	
	
	
	private void naturalSelection() {
		
		for (int i = 0; i<population.length; i=i+3) {
			
			Animal[] tempPop = {population[i],population[i+1],population[i+2]};
			Nature.sortByFitness(environment, tempPop);
			tempPop[2].dieToPredator();
		}
		
		for (int i = 0; i<population.length; i=i+6) {
			
			Animal[] tempPop = {
					population[i],
					population[i+1],
					population[i+2],
					population[i+3],
					population[i+4],
					population[i+5]
							};
			
			int randomPos = (new Random()).nextInt(6);
			
			if (tempPop[randomPos].getStatus() == "Alive") {
				tempPop[randomPos].dieToEnvironment();
				//System.out.println(i/6 + ": " + " not occupied");
			} else {
				if (randomPos == 0) {					
					tempPop[randomPos+1].dieToEnvironment();
					//System.out.println(i/6 + ": " + " occupied +1");
				} else {
					if (tempPop[randomPos-1].getStatus() == "Alive") {
						tempPop[randomPos-1].dieToEnvironment();
						//System.out.println(i/6 + ": " + randomPos + " occupied -1");
					} else {
						tempPop[randomPos-2].dieToEnvironment();
						//System.out.println(i/6 + ": " +  randomPos + " occupied -2");
					}
					
				}
			}
		}
	}
	
	
	
	public Animal[] newGeneration(Animal[] oldGen) {
		Animal[] newGen = new Animal[90];
		Animal[] reproduction = oldGen;
		Nature.sortByFitness(environment, reproduction);
		
		for (int i = 0; i<newGen.length; i++) {
			int randomMother = (new Random()).nextInt(45);
			int randomFather = (new Random()).nextInt(45);
			
			if (randomMother == randomFather) {
				if (randomMother == 0) {
					randomFather++;
				} else {
					randomFather--;
				}
			}
			
			newGen[i] = Nature.combineGenes(oldGen[randomMother], oldGen[randomFather]);
			newGen[i].mutate();
		}
		
		return newGen;
	}
	
	
	
	public String newColorBackground() {
		String hexadecimals = "0123456789abcdef";
		String result = "#";
		
		for (int i = 0; i<6; i++) {
			result += hexadecimals.charAt((new Random()).nextInt(16));
		}
		
		return result;
	}
	
	
	
	public static void main(String args[]) {
		
		new MESManager();
	}
}