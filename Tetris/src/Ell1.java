
public class Ell1 extends Shape{

	public Ell1(boolean blockArray[][])
	{
		super("ELL1");
		xCoords[0]= 64;
		xCoords[1]= 80;
		xCoords[2]= 96;
		xCoords[3]= 64;
		yCoords[0]= 0;
		yCoords[1]= 0;
		yCoords[2]= 0;
		yCoords[3]= 16;
		for(int i=0;i<4;i++)
			blockArray[yCoords[i]/16][xCoords[i]/16]= true;
		
	}

	public boolean[][] rotate(boolean blockArray[][])
	{
		int tempyCoords[] = yCoords.clone();
		int tempxCoords[] = xCoords.clone();
		boolean tempBlockArray[][] = new boolean[blockArray.length][blockArray[0].length];
		
		//copying the isTaken array because copying 2d arrays by value in java is annoying
		for(int i=0; i< blockArray.length; i++)
				tempBlockArray[i]= blockArray[i].clone();
		
		for(int i=0;i<4; i++)
			tempBlockArray[tempyCoords[i]/16][tempxCoords[i]/16] = false;
		
		switch(rotation){
			case 0: 
				tempxCoords[0]+=16;
				tempyCoords[0]-=16;
				tempyCoords[3]-=32;
				tempxCoords[2]-=16;
				tempyCoords[2]+=16;
				break;
			case 1:
				tempxCoords[0]+=16;
				tempyCoords[0]+=16;
				tempxCoords[3]+=32;
				tempxCoords[2]-=16;
				tempyCoords[2]-=16;
				break;
			case 2:
				tempxCoords[0]-=16;
				tempyCoords[0]+=16;
				tempyCoords[3]+=32;
				tempxCoords[2]+=16;
				tempyCoords[2]-=16;
				break;
			case 3:
				tempxCoords[0]-=16;
				tempyCoords[0]-=16;
				tempxCoords[3]-=32;
				tempxCoords[2]+=16;
				tempyCoords[2]+=16;
				break;
			default: 
				//WE DONT NEED NO DEFAULT
				break;
		}
		
		for(int i=0; i< 4; i++){
			if((tempxCoords[i])/16 <0 || (tempxCoords[i])/16 >blockArray[1].length-1 || (tempyCoords[i])/16 <0 || (tempyCoords[i])/16 >blockArray.length-1  )
				return blockArray;

			else if(tempBlockArray[(tempyCoords[i])/16][(tempxCoords[i])/16]==true )
				return blockArray;
			
			else{
				tempBlockArray[(tempyCoords[i])/16][(tempxCoords[i])/16]=true;
			}
		}
		
		xCoords = tempxCoords.clone();
		yCoords = tempyCoords.clone();
		blockArray = tempBlockArray.clone();
		
		if(rotation == 3)
			rotation = 0;
		else
			rotation++;
		return blockArray;
	}
}
