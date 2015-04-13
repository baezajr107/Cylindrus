package example.project.cylindris;

/**
 * Created by Austin on 4/10/2015.
 */
public class Shape {

    public enum type{
        L,
        J,
        Z,
        S,
        O,//block
        T,
        I//line
    }

    public static type currentType;
    public static int[] xCoords = new int[4];
    public static int[] yCoords = new int[4];
    private static int rotation;

    public Shape(){
    }


    /*
    each of the following methods takes in the current matrix of cubes
    and attempts to perform its specific action on the matrix
    if the action is successful it returns true
    if the function encountered a collision it returns false
     */
    public boolean initialize(boolean[][] cubeMatrix,int currentFront, type makeType){
        /*
        Because of the way java handles % with - numbers i had to do some stupid shit to make sure 
        the places wrapped around after going below 0
         */
        boolean[][] tempMatrix = new boolean[15][16];
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                tempMatrix[k][l] = cubeMatrix[k][l];
            }
        }
        int[] tempxcoords = new int[4];
        int[] tempycoords = new int[4];
        for(int i=0;i<4;i++){
            tempxcoords[i] = xCoords[i];
            tempycoords[i] = yCoords[i];
        }

            switch (makeType) {
                case L:
                    int yCalc = ((tempMatrix.length - 1)%15);
                    int xCalc = (currentFront-1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[0]= xCalc<0?(16+xCalc):xCalc;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    xCalc = (currentFront)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[1]= xCalc<0?(16+xCalc):xCalc;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case J:
                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[0]= xCalc<0?(16+xCalc):xCalc;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    xCalc = (currentFront)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[1]= xCalc<0?(16+xCalc):xCalc;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    xCalc = (currentFront-1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case Z:
                    yCalc = ((tempMatrix.length - 1)%15);

                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[0]= currentFront;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[1]= currentFront;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    xCalc = (currentFront-1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case S:
                    yCalc = ((tempMatrix.length - 1)%15);

                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[0]= currentFront;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[1]= currentFront;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront-1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case O:
                    yCalc = ((tempMatrix.length - 1)%15);

                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[0]= currentFront;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[1]= currentFront;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case T:
                    yCalc = ((tempMatrix.length - 1)%15);

                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[0]= currentFront;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[1]= currentFront;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront+1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc]= true;
                        tempxcoords[2] =xCalc<0?(16+xCalc):xCalc;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 1)%15);
                    xCalc = (currentFront-1)%16;
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][xCalc<0?(16+xCalc):xCalc] = true;
                        tempxcoords[3] = xCalc<0?(16+xCalc):xCalc;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
                case I:
                    yCalc = ((tempMatrix.length - 1)%15);

                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]== true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[0]= currentFront;
                        tempycoords[0]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 2)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[1]= currentFront;
                        tempycoords[1]= yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 3)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront]= true;
                        tempxcoords[2] = currentFront;
                        tempycoords[2] = yCalc<0?(15+yCalc):yCalc;
                    }

                    yCalc = ((tempMatrix.length - 4)%15);
                    if (tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] == true)
                        return false;
                    else {
                        tempMatrix[yCalc<0?(15+yCalc):yCalc][currentFront] = true;
                        tempxcoords[3] = currentFront;
                        tempycoords[3] =  yCalc<0?(15+yCalc):yCalc;
                    }
                break;
            }

        for(int i=0;i<4;i++){
            xCoords[i] = tempxcoords[i];
            yCoords[i] = tempycoords[i];
        }
        
        rotation = 0;
        currentType = makeType;
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                cubeMatrix[k][l] = tempMatrix[k][l];
            }
        }
        return true;
    }

    public boolean shiftLeft(boolean[][]cubeMatrix){
        boolean[][] tempMatrix = new boolean[15][16];
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                tempMatrix[k][l] = cubeMatrix[k][l];
            }
        }

        //put coords into a rollback buffer
        int[] tempxcoords = new int[4];
        for(int i=0;i<4;i++){
            tempxcoords[i] = xCoords[i];
        }

        //declaim current coordinates and update position
        for(int i=0;i<4;i++){
            tempMatrix[yCoords[i]][tempxcoords[i]]=false;
            tempxcoords[i]=(tempxcoords[i]+1)%16;
        }

        for(int i=0;i<4;i++){
            if(tempMatrix[yCoords[i]][tempxcoords[i]])
                return false;
            else
                tempMatrix[yCoords[i]][tempxcoords[i]]=true;
        }

        //update the true matrix with the new positions
        for(int i=0;i<4;i++){
            xCoords[i] = tempxcoords[i];
        }

        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                cubeMatrix[k][l] = tempMatrix[k][l];
            }
        }
        return true;
    }

    public boolean shiftRight(boolean[][]cubeMatrix){
        //first 2 loops put the current values into rollback buffers so they discard changes in the case of a collision
        boolean[][] tempMatrix = new boolean[15][16];
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                tempMatrix[k][l] = cubeMatrix[k][l];
            }
        }

        int[] tempxcoords = new int[4];
        for(int i=0;i<4;i++){
            tempxcoords[i] = xCoords[i];
        }

        //declaim current coordinates and update position
        for(int i=0;i<4;i++){
            tempMatrix[yCoords[i]][tempxcoords[i]]=false;
            tempxcoords[i]=(tempxcoords[i]-1)%16<0?16+(tempxcoords[i]-1)%16:(tempxcoords[i]-1)%16;
        }

        //check for collisions in new coordinates
        for(int i=0;i<4;i++){
            if(tempMatrix[yCoords[i]][tempxcoords[i]])
                return false;
            else
                tempMatrix[yCoords[i]][tempxcoords[i]]=true;
        }

        //update the true matrix with the new positions
        for(int i=0;i<4;i++){
            xCoords[i] = tempxcoords[i];
        }

        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                cubeMatrix[k][l] = tempMatrix[k][l];
            }
        }
        return true;
    }

    public boolean shiftDown(boolean[][]cubeMatrix){

        //first 2 loops put the current values into rollback buffers so they discard changes in the case of a collision
        boolean[][] tempMatrix = new boolean[15][16];
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                tempMatrix[k][l] = cubeMatrix[k][l];
            }
        }

        int[] tempycoords = new int[4];
        for(int i=0;i<4;i++){
            tempycoords[i]=yCoords[i];
        }

        //declaim current positions and update
        for(int i=0;i<4;i++){
            tempMatrix[tempycoords[i]][xCoords[i]]=false;
            tempycoords[i]--;
        }

        //check for collisions or if the block has reached the bottom of the play field
        for(int i=0;i<4;i++){
            if(tempycoords[i]<0 || tempMatrix[tempycoords[i]][xCoords[i]]){
                return false;
            }
            else{
                tempMatrix[tempycoords[i]][xCoords[i]] = true;
            }
        }

        for(int i=0;i<4;i++){
            yCoords[i]=tempycoords[i];
        }

        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                cubeMatrix[k][l] = tempMatrix[k][l];
            }
        }

        return true;
    }

    public boolean rotate(boolean[][]cubeMatrix){
        boolean[][] tempMatrix = new boolean[15][16];
        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                tempMatrix[k][l] = cubeMatrix[k][l];
            }
        }

        int temprotation = rotation;

        int[] tempycoords = new int[4];
        int[] tempxcoords = new int[4];
        for(int i=0;i<4;i++){
            tempycoords[i]=yCoords[i];
            tempxcoords[i]=xCoords[i];
        }

        for(int i=0;i<4;i++){
            tempMatrix[tempycoords[i]][tempxcoords[i]] = false;
        }

        switch(currentType){
            case L:
                switch(rotation){
                    case 0:
                        tempxcoords[0]++;
                        tempycoords[0]++;
                        tempxcoords[2]--;
                        tempycoords[2]--;
                        tempxcoords[3]-=2;
                    break;
                    case 1:
                        tempxcoords[0]++;
                        tempycoords[0]--;
                        tempxcoords[2]--;
                        tempycoords[2]++;
                        tempycoords[3]+=2;
                    break;
                    case 2:
                        tempxcoords[0]--;
                        tempycoords[0]--;
                        tempxcoords[2]++;
                        tempycoords[2]++;
                        tempxcoords[3]+=2;
                    break;
                    case 3:
                        tempxcoords[0]--;
                        tempycoords[0]++;
                        tempxcoords[2]++;
                        tempycoords[2]--;
                        tempycoords[3]-=2;
                    break;
                }
                temprotation=(temprotation+1)%4;
            break;
            case J:
                switch(rotation){
                    case 0:
                        tempxcoords[0]--;
                        tempycoords[0]--;
                        tempxcoords[2]++;
                        tempycoords[2]++;
                        tempycoords[3]+=2;
                        break;
                    case 1:
                        tempxcoords[0]--;
                        tempycoords[0]++;
                        tempxcoords[2]++;
                        tempycoords[2]--;
                        tempxcoords[3]+=2;
                        break;
                    case 2:
                        tempxcoords[0]++;
                        tempycoords[0]++;
                        tempxcoords[2]--;
                        tempycoords[2]--;
                        tempycoords[3]-=2;
                        break;
                    case 3:
                        tempxcoords[0]++;
                        tempycoords[0]--;
                        tempxcoords[2]--;
                        tempycoords[2]++;
                        tempxcoords[3]-=2;
                        break;
                }
                temprotation=(temprotation+1)%4;
            break;
            case Z:
                switch(rotation){
                    case 0:
                        tempxcoords[0]--;
                        tempycoords[1]++;
                        tempxcoords[2]-=2;
                        tempycoords[2]++;
                        tempxcoords[3]++;
                    break;
                    case 1:
                        tempxcoords[0]++;
                        tempycoords[1]--;
                        tempxcoords[2]+=2;
                        tempycoords[2]--;
                        tempxcoords[3]--;
                    break;
                }
                temprotation=(temprotation+1)%2;
            break;
            case S:
                switch(rotation){
                    case 0:
                        tempycoords[1]+=2;
                        tempxcoords[3]-=2;
                        break;
                    case 1:
                        tempycoords[1]-=2;
                        tempxcoords[3]+=2;
                        break;
                }
                temprotation=(temprotation+1)%2;
            break;
            case O:
                //literally no point
            break;
            case T:
                switch(rotation){
                    case 0:
                        tempxcoords[2]--;
                        tempycoords[2]++;
                        tempxcoords[1]++;
                        tempycoords[1]++;
                        tempxcoords[3]++;
                        tempycoords[3]--;
                        break;
                    case 1:
                        tempxcoords[2]--;
                        tempycoords[2]--;
                        tempxcoords[1]--;
                        tempycoords[1]++;
                        tempxcoords[3]++;
                        tempycoords[3]++;
                        break;
                    case 2:
                        tempxcoords[2]++;
                        tempycoords[2]--;
                        tempxcoords[1]--;
                        tempycoords[1]--;
                        tempxcoords[3]--;
                        tempycoords[3]++;
                        break;
                    case 3:
                        tempxcoords[2]++;
                        tempycoords[2]++;
                        tempxcoords[1]++;
                        tempycoords[1]--;
                        tempxcoords[3]--;
                        tempycoords[3]--;
                        break;
                }
                temprotation=(temprotation+1)%4;
            break;
            case I:
                switch(rotation){
                    case 0:
                        tempxcoords[0]++;
                        tempycoords[0]--;
                        tempxcoords[2]--;
                        tempycoords[2]++;
                        tempxcoords[3]-=2;
                        tempycoords[3]+=2;
                        break;
                    case 1:
                        tempxcoords[0]--;
                        tempycoords[0]++;
                        tempxcoords[2]++;
                        tempycoords[2]--;
                        tempxcoords[3]+=2;
                        tempycoords[3]-=2;
                        break;
                }
                temprotation=(temprotation+1)%2;
            break;
        }

        for(int i=0;i<4;i++){
            tempxcoords[i] = tempxcoords[i]%16;
            if(tempxcoords[i]<0)
                tempxcoords[i] = 16+tempxcoords[i]%16;

            if(tempycoords[i]>tempMatrix.length-1 || tempycoords[i]<0)
                return false;
            else if(tempMatrix[tempycoords[i]][tempxcoords[i]])
                return false;
            else
                tempMatrix[tempycoords[i]][tempxcoords[i]] = true;

        }

        rotation=temprotation;

        for(int i=0;i<4;i++){
            yCoords[i]=tempycoords[i];
            xCoords[i]=tempxcoords[i];
        }

        for(int k=0;k<15;k++){
            for(int l=0;l<16;l++){
                cubeMatrix[k][l] = tempMatrix[k][l];
            }
        }
        return true;
    }




}
