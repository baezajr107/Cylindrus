import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;

public class Shape extends JPanel{

	protected int xCoords[] = new int[4];
	protected int yCoords[] = new int[4];
	public blockType type;
	protected int rotation;
	
	enum blockType{
		LINE,
		SQUARE,
		ZEE1,
		ZEE2,
		ELL1,
		ELL2,
		TEE
	}
	
	public Shape(String type)
	{
		if(type == "SQUARE")
			this.type = blockType.SQUARE;
		else if(type == "LINE")
			this.type = blockType.LINE;
		else if(type == "ZEE1")
			this.type = blockType.ZEE1;
		else if(type == "ZEE2")
			this.type = blockType.ZEE2;
		else if(type == "ELL1")
			this.type = blockType.ELL1;
		else if(type == "ELL2")
			this.type = blockType.ELL2;
		else if(type == "TEE")
			this.type = blockType.TEE;
		rotation = 0;
	}
	
	public boolean[][] setX(int change, boolean blockArray[][])
	{

		int tempxCoords[] = xCoords.clone();
		boolean tempBlockArray[][] = new boolean[blockArray.length][blockArray[0].length];
		
		//copying the isTaken array because copying 2d arrays by value in java is annoying
		for(int i=0; i< blockArray.length; i++)
			for(int j=0; j< blockArray[0].length;j++)
				tempBlockArray[i][j]= blockArray[i][j];
		

		//finds the leftmost or rightmost block based on movement direction
		for(int x = 0;x<4; x++)
		{
			int extremity;
			int index = 0;
			
			if(change <0){
				extremity = 500;
				for(int j=0; j<4; j++){
					if(xCoords[j]== tempxCoords[j]){
						if(tempxCoords[j]< extremity){
							extremity = tempxCoords[j];
							index = j;
						}
					}
				}
			}
			else{
				extremity = -500;
				for(int j=0; j<4; j++){
					if(xCoords[j]== tempxCoords[j]){
						if(tempxCoords[j]> extremity){
							extremity = tempxCoords[j];
							index = j;
						}
					}
				}
			}

			//if the move to position is out of bounds make no change
			if((xCoords[index]+change)/16 <0 || (xCoords[index]+change)/16 >blockArray[1].length-1 )
				return blockArray;

			//if the move to position space if already occupied make no change
			else if(tempBlockArray[(yCoords[index])/16][(xCoords[index]+change)/16]==true )
				return blockArray;

			else
			{
				tempBlockArray[yCoords[index]/16][xCoords[index]/16]= false;
				tempxCoords[index] = xCoords[index] +change;
				tempBlockArray[yCoords[index]/16][tempxCoords[index]/16]= true;
			}
		}

		
		xCoords = tempxCoords.clone();
		blockArray = tempBlockArray.clone();
		return blockArray;
	}
	
	//moves the block down 1 space if it can
	public boolean[][] setY(boolean blockArray[][])
	{
		int tempyCoords[] = yCoords.clone();
		boolean tempBlockArray[][] = new boolean[blockArray.length][blockArray[0].length];
		
		//copying the isTaken array because copying 2d arrays by value in java is annoying
		for(int i=0; i< blockArray.length; i++){
				tempBlockArray[i]= blockArray[i].clone();
		}
		
		for(int x=0;x<4;x++)
		{
			int extremity = -500;
			int index = 0;
			
			for(int j=0; j<4; j++){
				if(yCoords[j]== tempyCoords[j]){
					if(tempyCoords[j]> extremity){
						extremity = tempyCoords[j];
						index = j;
					}
				}
			}
			
			if((yCoords[index]+16)/16 >blockArray.length-1 || tempBlockArray[(yCoords[index]+16)/16][xCoords[index]/16]==true)
			{

				return blockArray;
			}
			else
			{
				tempBlockArray[yCoords[index]/16][xCoords[index]/16]= false;
				tempyCoords[index]= yCoords[index] + 16;
				tempBlockArray[tempyCoords[index]/16][xCoords[index]/16]= true;
			}
		}
		yCoords = tempyCoords.clone();
		return tempBlockArray;


	}
	
	//empty function used by the separate shapes
	public boolean[][] rotate(boolean blockArray[][]){
		return blockArray;
	}

	//prints out a ghost block to show which shape is coming next
	public void preview(Graphics g){
		
			g.setColor(Color.white);
		  switch(type){
		  case LINE:
				g.fillRect(190, 56 , 16, 16);
				g.fillRect(206, 56 , 16, 16);
				g.fillRect(222, 56, 16, 16);
				g.fillRect(238, 56, 16, 16);
				break;
		  
		  case SQUARE:
				g.fillRect(198, 56 , 16, 16);
				g.fillRect(214, 56 , 16, 16);
				g.fillRect(198, 72, 16, 16);
				g.fillRect(214, 72, 16, 16);
				break;
		  
		  
		  case TEE:
				g.fillRect(198, 56 , 16, 16);
				g.fillRect(214, 56 , 16, 16);
				g.fillRect(230, 56, 16, 16);
				g.fillRect(214, 72, 16, 16);
				break;
		  
		  
		  case ELL1:
				g.fillRect(198, 56 , 16, 16);
				g.fillRect(214, 56 , 16, 16);
				g.fillRect(230, 56, 16, 16);
				g.fillRect(198, 72, 16, 16);
				break;
		  
		  
		  case ELL2:
				g.fillRect(198, 56 , 16, 16);
				g.fillRect(214, 56 , 16, 16);
				g.fillRect(230, 56, 16, 16);
				g.fillRect(230, 72, 16, 16);
				break;
		  
		  
		  case ZEE1:
				g.fillRect(222, 56 , 16, 16);
				g.fillRect(206, 56 , 16, 16);
				g.fillRect(206, 72, 16, 16);
				g.fillRect(190, 72, 16, 16);
				break;
		  
		  default:
				g.fillRect(222, 72 , 16, 16);
				g.fillRect(206, 72 , 16, 16);
				g.fillRect(206, 56, 16, 16);
				g.fillRect(190, 56, 16, 16);
				break;
		  
		  }
	}

}
