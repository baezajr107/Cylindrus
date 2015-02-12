/*TODO:

 * get music to loop correctly
 * 
 * OPTIONAL:
 * add leveling function(speed up the game/modify shape generation rates(Z SHAPES EVERYWHERE))
 * add menu
 * reticulate splines
 * answer the last question
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.Timer;

import java.util.*;

public class Tetris implements KeyListener, ActionListener {
	
		private final int height = 352;
		private final int width = 160;
		private final int levelThreshold = 1;

		private DrawPanel drawPanel = new DrawPanel();
		private Shape currBlock, nextBlock;
		private boolean blockArray[][] = new boolean[height/16][width/16];
		private boolean isPaused = false;
		private int score, level, lines;
		private Clip clip= null;
		Timer timer = new Timer(1000, this);

	private class DrawPanel extends JPanel {
	  	  
		@Override
		public void paint(Graphics g) {
			//paint game background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.gray);
			g.fillRect(160, 0, 120, height);
			
			//paint stat info
			g.setFont(new Font(Font.SERIF, Font.PLAIN, 10));
			g.setColor(Color.white);
			g.drawString("SCORE", 200, 150);
			g.drawString("LINES", 202, 210);
			g.drawString("LEVEL", 202, 270);
			g.drawString(Integer.toString(score), 200, 165);
			g.drawString(Integer.toString(lines), 215, 225);
			g.drawString(Integer.toString(level), 215, 285);
			g.drawString("MOVE WITH ARROWS", 170, 310);
			g.drawString(" OR WASD", 190, 320);
			g.drawString("M TO MUTE", 190, 330);
			g.drawString("P TO PAUSE", 190, 340);
			
			//paint next block preview
			g.setColor(Color.BLACK);
			g.fillRect(170, 20, 100, 100);
			nextBlock.preview(g);
			g.setColor(Color.white);
			g.drawString("NEXT BLOCK", 185, 15);
			
			//paint blocks on board
			for(int i=0; i< blockArray.length; i++){
			    for(int j=0; j< blockArray[0].length;j++){
			    	if(blockArray[i][j]==true){
			    		//Random rand = new Random();
			    		//g.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
			    		g.setColor(Color.white);
				        g.fillRect(j*16, i*16, 16, 16);
			    	}
			    }
			}
			
			//paint paused message
			if(isPaused){
				g.setColor(Color.white);
				g.setFont(new Font(Font.SERIF, Font.BOLD, 15));
				g.drawString("GAME PAUSED", 30, 150);
			}
		}
		
		    @Override
		public void update(Graphics g) {   	
		    	paint(g);
		}    
	}
  
	public class No implements ActionListener{
		public final void actionPerformed(ActionEvent e){
			System.exit(0); 
		}
	
	}
	public class newLevel implements ActionListener{
		public final void actionPerformed(ActionEvent e){
			if(timer.getDelay()>100)
			timer.setDelay(timer.getDelay()-100); 
		}
	
	}

 
  
	public void launch() {    
	music();
	score = 0;
	level = 1;
	lines = 0;
	  
	genBlock(true);
	
	drawPanel.setPreferredSize(new Dimension(280, height));
	
	JFrame frame = new JFrame();
	frame.addKeyListener(this);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setContentPane(drawPanel);
	frame.setResizable(false);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setVisible(true);
	
	JButton button = new JButton();
	newLevel speedincrease = new newLevel();
	button.addActionListener(speedincrease); 
	button.setVisible(false);
	drawPanel.add(button);
	
	timer.start();
	
	}

  @Override
  public void keyPressed(KeyEvent e) {
	  if(!isPaused){
	  	if (e.getKeyCode() == KeyEvent.VK_A||e.getKeyCode() == KeyEvent.VK_LEFT) {
	  		blockArray = currBlock.setX(-16, blockArray);
	
	    }
		else if (e.getKeyCode() == KeyEvent.VK_D||e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    	      
			blockArray = currBlock.setX(16, blockArray);
	    }
		else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			
			boolean newBlockArray[][] = currBlock.setY(blockArray);
			  
			if(newBlockArray.equals(blockArray)){
				for(int j=0; j< blockArray.length; j++){
					int isFull = checkRow(blockArray[j]);
					if(isFull == blockArray[0].length){
						mergeDown(j, blockArray);
						lines++;
						score+= 10;
						if(lines%levelThreshold == 0){
							level++;
							((AbstractButton) drawPanel.getComponent(0)).doClick();
						}
					}
					else if(isFull >0 && j== 0){
						   	timer.stop();
							clip.stop();
						   
						    JFrame frame = new JFrame();
						    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    frame.setSize(new Dimension(150,130));
						    frame.setLocation(900,400);
						    frame.setVisible(true);
						    
						    JPanel panel = new JPanel();
						    frame.add(panel);
						    
						    JLabel label = new JLabel("You Lose!");
						    panel.add(label);
						    
							JButton button = new JButton("Exit");
							button.setPreferredSize(new Dimension(100,50));
						    No speedincrease = new No();
						    button.addActionListener(speedincrease); 
						    panel.add(button);
						    
						    return;
					}
				}
				score+=3;
				genBlock(false);
			}
			else
				blockArray = newBlockArray.clone();
	
		}
		else if (e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_UP){
			blockArray = currBlock.rotate(blockArray);
		}
		
	  	
	  }
	if (e.getKeyCode() == KeyEvent.VK_P){
		if(timer.isRunning()){
			timer.stop();
			clip.stop();
			isPaused = true;
		}
		else{
			timer.start();
			clip.start();
			isPaused = false;
		}

	}
	if(e.getKeyCode() == KeyEvent.VK_M){
		if(clip.isRunning())
			clip.stop();
		else
			clip.start();
	}
	if(e.getKeyCode() == KeyEvent.VK_SPACE){
		boolean newBlockArray[][] = currBlock.setY(blockArray);
		
		while(!newBlockArray.equals(blockArray)){
			if(newBlockArray.equals(blockArray))
			{
				for(int j=0; j< blockArray.length; j++)
				{
					int isFull = checkRow(blockArray[j]);
					if(isFull == blockArray[0].length)
					{
						mergeDown(j, blockArray);
						lines++;
						score+= 10;
						if(lines%levelThreshold == 0){
							level++;
							((AbstractButton) drawPanel.getComponent(0)).doClick();
						}
					}
					else if(isFull >0 && j== 0){
						   	timer.stop();
							clip.stop();
						   
						    JFrame frame = new JFrame();
						    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						    frame.setSize(new Dimension(150,130));
						    frame.setLocation(900,400);
						    frame.setVisible(true);
						    
						    JPanel panel = new JPanel();
						    frame.add(panel);
						    
						    JLabel label = new JLabel("You Lose!");
						    panel.add(label);
						    
							JButton button = new JButton("Exit");
							button.setPreferredSize(new Dimension(100,50));
						    No speedincrease = new No();
						    button.addActionListener(speedincrease); 
						    panel.add(button);
						    
						    return;
					}
				}
			}
			else
				blockArray = newBlockArray.clone();
			
			newBlockArray = currBlock.setY(blockArray);
		}
		score+=3;
		genBlock(false);
	}
	
    
    drawPanel.invalidate();
    drawPanel.repaint();
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void actionPerformed(ActionEvent e) {
		
	  	boolean newBlockArray[][] = currBlock.setY(blockArray);
		  
		if(newBlockArray.equals(blockArray))
		{
			for(int j=0; j< blockArray.length; j++)
			{
				int isFull = checkRow(blockArray[j]);
				if(isFull == blockArray[0].length)
				{
					mergeDown(j, blockArray);
					lines++;
					score+= 10;
					if(lines%levelThreshold == 0){
						level++;
						((AbstractButton) drawPanel.getComponent(0)).doClick();
					}
				}
				else if(isFull >0 && j== 0){
				   timer.stop();
				   clip.stop();
				   
				    JFrame frame = new JFrame();
				    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				    frame.setSize(new Dimension(150,130));
				    frame.setLocation(900,400);
				    frame.setVisible(true);
				    
				    JPanel panel = new JPanel();
				    frame.add(panel);
				    
				    JLabel label = new JLabel("You Lose!");
				    panel.add(label);
				    
					JButton button = new JButton("Exit");
					button.setPreferredSize(new Dimension(100,50));
				    No speedincrease = new No();
				    button.addActionListener(speedincrease); 
				    panel.add(button);
				    
				    return;
				}
			}
			score+=3;
			genBlock(false);
		}
		else
			blockArray = newBlockArray.clone();

   drawPanel.invalidate();
    drawPanel.repaint();
    
  }
  
  //checks if a row is full
  public int checkRow(boolean blockArray[])
  {
	  int sum = 0;
	  for(int i=0; i< blockArray.length; i++)
	  {
		  if(blockArray[i]== true)
		  {
			  sum++;
		  }
	  }
		  return sum;
  }

  //merge down rows above onto cleared row
  public void mergeDown(int row, boolean blockArray[][])
  {
	  for(int i= row; i>0; i--)
	  {
		  for(int j=0; j< blockArray[0].length; j++)
		  {
		  blockArray[i][j]= blockArray[i-1][j];
		  }
	  }

	  for(int i=0; i< blockArray[0].length; i++)
	  {
		  blockArray[0][i]= false;
	  }
  }
  
  public void genBlock(boolean isFirst)
  {
	  Random rand = new Random();
	  int random;
	  
	  //generate ghost block for current block if it's the first generated block
	  if(isFirst){
	  random = rand.nextInt(100);
		  if(random >= 0 && random < 10)
			  nextBlock = new Shape("LINE");
		  
		  else if( random >= 10 && random < 30)
			  nextBlock = new Shape("SQUARE");
		  
		  else if (random >= 30 && random < 50)
			  nextBlock = new Shape("TEE");
		  
		  else if(random >= 50 && random < 65)
			  nextBlock = new Shape("ELL1");
		  
		  else if(random >= 65 && random < 80)
			  nextBlock = new Shape("ELL2");
		  
		  else if(random >= 80 && random < 90)
			  nextBlock = new Shape("ZEE1");
		  
		  else
			  nextBlock = new Shape("ZEE2");
	  }
	  
	  //set current block to what the ghost block was
	  if(nextBlock.type == Shape.blockType.LINE)
		  currBlock = new Line(blockArray);
	  
	  else if(nextBlock.type == Shape.blockType.SQUARE)
		  currBlock = new Square(blockArray);
	  
	  else if (nextBlock.type == Shape.blockType.TEE)
		  currBlock = new Tee(blockArray);
	  
	  else if(nextBlock.type == Shape.blockType.ELL1)
		  currBlock = new Ell1(blockArray);
	  
	  else if(nextBlock.type == Shape.blockType.ELL2)
		  currBlock = new Ell2(blockArray);
	  
	  else if(nextBlock.type == Shape.blockType.ZEE1)
		  currBlock = new Zee1(blockArray);
	  
	  else
		  currBlock = new Zee2(blockArray);
	  
	  //generate the ghost block
	  random = rand.nextInt(100);
	  if(random >= 0 && random < 10)
		  nextBlock = new Shape("LINE");
	  
	  else if( random >= 10 && random < 30)
		  nextBlock = new Shape("SQUARE");
	  
	  else if (random >= 30 && random < 50)
		  nextBlock = new Shape("TEE");
	  
	  else if(random >= 50 && random < 65)
		  nextBlock = new Shape("ELL1");
	  
	  else if(random >= 65 && random < 80)
		  nextBlock = new Shape("ELL2");
	  
	  else if(random >= 80 && random < 90)
		  nextBlock = new Shape("ZEE1");
	  
	  else
		  nextBlock = new Shape("ZEE2");

  }
  public void music(){ 

		try {

			clip = AudioSystem.getClip();
	        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("x.wav"));
	        clip.open(inputStream);
	        clip.loop(999);
	        
		} catch (UnsupportedAudioFileException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e);
		}
		 catch (LineUnavailableException e) {
			System.out.println(e);
		}

	  }

  public static void main(String... args) {

	  
	  
	  EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {        
        Tetris block = new Tetris();  
        
        block.launch();
      }
    });
  }
  
}