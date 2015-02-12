import java.awt.Color;

	public class Square extends Shape {

		public Square(boolean blockArray[][])
		{
			super("SQUARE");
			xCoords[0]= 96;
			xCoords[1]= 80;
			xCoords[2]= 96;
			xCoords[3]= 80;
			yCoords[0]= 16;
			yCoords[1]= 16;
			yCoords[2]= 0;
			yCoords[3]= 0;
			for(int i=0;i<4;i++)
				blockArray[yCoords[i]/16][xCoords[i]/16]= true;
			
		}

		public boolean[][] rotate(boolean blockArray[][])
		{
			return blockArray;
		}
		
}