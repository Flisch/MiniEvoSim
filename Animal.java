import java.util.Random;

public class Animal {
	
	private String genome = "";
	private String status = "Alive";
	
	
	
	public Animal(String startGenome) {
		this.genome = startGenome;
	}
	
	
	
	public void mutate() {
		int pos = new Random().nextInt(12);
		
		int mutationChance = 5;
		
		if ((new Random()).nextInt(100) < mutationChance) {
			//System.out.print(this + " mutated from " + this.getGenome());
			this.genome = this.genome.substring(0, pos)
				+ Nature.randomLetter()
				+ this.genome.substring(pos+1);
			//System.out.print(" into " + this.getGenome());
			//System.out.println(" ");
			this.mutate();
		}
	}
	
	
	
	public void dieToPredator() {
		this.status = "Eaten";
	}
	
	public void dieToEnvironment() {
		this.status = "Died";
	}
	
	public void setGenome(String newGenome) {
		this.genome = newGenome;
	}
	
	public String getGenome() {
		return this.genome;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
	
	public String getStatus() {
		return this.status;
	}
}
