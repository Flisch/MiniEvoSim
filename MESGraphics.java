import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MESGraphics extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private Animal[] population = new Animal[90];
	protected String background ="#000000";
	private int speed = 0;
	private int generations;
	private JLabel genLabel;
	private boolean optionsDetails = true;
	
	
	
	public MESGraphics() {
		
		this.setTitle("Mini Evo Sim");
		this.setSize(850,850);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		this.setLayout(new BorderLayout());
		
		MESPanel evoPanel = new MESPanel();
		evoPanel.setPreferredSize(new Dimension(70, 775));
		evoPanel.setLayout(new FlowLayout());
		this.add(evoPanel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(70, 50));
		
		// ### Toggle Visuals ###
		JToggleButton toggle = new JToggleButton("Show more");
		toggle.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (optionsDetails == true) {
					optionsDetails = false;
				} else {
					optionsDetails = true;
				}
				repaint();
			}
		});
		toggle.setPreferredSize(new Dimension(40, 40));
		panel.add(toggle);
		
		// ### Generation Count ###
		genLabel = new JLabel("Generations since last environmental change: ");
		genLabel.setPreferredSize(new Dimension (300, 40));
		genLabel.setHorizontalAlignment(SwingConstants.LEFT);
		genLabel.setForeground(Color.WHITE);
		panel.add(genLabel);
		
		// ### Stop Button ###
		JButton stopButton = new JButton(">|");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (speed == 0) {
					speed = -1;
				} else {
					speed = 0;
					stopButton.setText(">|");
				}
			}
		});
		stopButton.setPreferredSize(new Dimension (70, 40));
		panel.add(stopButton);
		
		// ### Play Button (Lowest Speed) ###
		JButton playButton = new JButton(">");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speed = 1000;
				stopButton.setText("||");
			}
		});
		playButton.setPreferredSize(new Dimension (70, 40));
		panel.add(playButton);
		
		// ### Fast Button (Medium Speed) ###
		JButton fastButton = new JButton(">>");
		fastButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speed = 350;
				stopButton.setText("||");
			}
		});
		fastButton.setPreferredSize(new Dimension (70, 40));
		panel.add(fastButton);
		
		
		// ### Faster Button (Highest Speed) ###
		JButton fasterButton = new JButton(">>>");
		fasterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speed = 100;
				stopButton.setText("||");
			}
		});
		fasterButton.setPreferredSize(new Dimension (70, 40));
		panel.add(fasterButton);
		
		panel.setBackground(Color.BLACK);
		this.add(panel, BorderLayout.SOUTH);
		
		evoPanel.setVisible(true);
		panel.setVisible(true);
		this.setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		new MESGraphics();
	}
	
	
	
	public void setPopulation (Animal[] newPopulation) {
		population = newPopulation;
	}
	
	
	public void setBackgroundColor (String newBackgroundColor) {
		
		this.background = newBackgroundColor;
		//System.out.println(newBackgroundColor);
	}
	
	@Override
	public void paint (Graphics g) {
		
		super.paint(g);
		genLabel.setText("Generations since last environmental change: " + generations);
		
	}
	
	public int getSpeed () {
		return speed;
	}
	
	public void setGenerations (int value) {
		generations = value;
	}
	
	public void pause () {
		speed = 0;
	}
	
	
	
	private class MESPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public MESPanel() {
		}
		
		@Override
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			populate(g);
			this.setBackground(Color.decode(background));
		}
		
		
		
		private void populate (Graphics g) {
			
			for (int i = 0; i<population.length; i++) {
				drawAnimal (g, (i % 6)*130, (Math.round(i/6))*50, population[i]);
			}
		}
		
		
		
		private void drawAnimal (Graphics g, int x, int y, Animal animal) {
			
			if (animal != null) {
				
				g.setColor(Color.decode(Nature.parseGenes(animal.getGenome())));
				g.fillRect(40+x, 20+y, 120, 40);
				
				if (optionsDetails == true) {
					g.setColor(Color.decode("#ffffff"));
					g.fillRect(45+x, 35+y, 110, 12);
					
					g.setColor(Color.decode("#000000"));
					g.drawRect(40+x, 20+y, 120, 40);
					g.drawString(animal.getGenome(), 45+x, 45+y);
				}
				
				if (animal.getStatus() != "Alive") {
					drawDeath(g, animal, 40+x, 20+y);
				}
			
			}
		}
		
		
		
		private void drawDeath (Graphics g, Animal animal, int xPos, int yPos) {
			
			switch (animal.getStatus()) {
			case "Eaten": g.setColor(Color.RED); break;
			case "Died": g.setColor(Color.GRAY); break;
			}
			
			g.fillOval(xPos+15, yPos+5, 90, 30);
			g.setColor(Color.BLACK);
			g.drawOval(xPos+15, yPos+5, 90, 30);
			
			g.drawString(animal.getStatus(), xPos+45, yPos+25);
		}
		
		
		
	}
	
}