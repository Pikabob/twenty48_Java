package twenty48;

import java.util.Scanner;

public class Game {
	Board board;
	int width = 4;
	int height = 4;
	int goal = 2048;
	String up = "w";
	String down = "s";
	String left = "a";
	String right = "d";
	int score;
	int moves;
	
	public void start(){
		init();
		gameLoop();
	}
	
	public void init(){
		board = new Board(height, width);
		putRandom(2);
		score = 0;
		moves = 0;
	}
	
	public void gameLoop(){
		Scanner in = new Scanner(System.in);
		while(true){
			printBoard();
			System.out.println("Moves: " + moves);
			printScore();
			if(checkForWin()){
				System.out.println("You Win");
				break;
			}
			if(checkForGameOver()){
				System.out.println("Game Over");
				break;
			}
			System.out.println("Inputs are wasd. q to quit. reset to reset");
			String s = in.nextLine();
			char dir = '\0';
			boolean valid = false;
			if(s.equals(up)){
				dir = 'u';
			}else if(s.equals(down)){
				dir = 'd';
			}else if(s.equals(left)){
				dir = 'l';
			}else if(s.equals(right)){
				dir = 'r';
			}else if(s.equals("quit")||s.equals("q")){
				break;
			}else if(s.equals("reset")){
				init();
			}else{
				continue;
			}
			valid = move(dir);
			if(valid){
				putRandom(1);
			}
			
			System.out.println();
			
		}
		
	}
	
	public void printScore(){
		System.out.println("Score: " + score);
	}
	
	public void printBoard(){
		board.print();
	}
	
	public void putRandom(int count){
		int placed = 0;
		if(checkFull()){
			return;
		}
		while(placed < count){
			int rx = (int) (Math.random()*height);
			int ry = (int) (Math.random()*width);
			Piece[][] p = board.getBoard();
			if(p[rx][ry].getValue()==0){
				p[rx][ry].setValue((Math.random()<.9)?2:4);
				placed++;
			}
		}
	}
	
	public boolean checkFull(){
		Piece[][] p = board.getBoard();
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				if(p[i][j].getValue()==0){
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkForWin(){
		Piece[][] b = board.getBoard();
		for(int i = 0; i<b.length; i++){
			for(int j = 0; j<b[i].length; j++){
				if(b[i][j].value==goal){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkForGameOver(){
		Piece[][] p = board.getBoard();
		if(!checkFull()){
			return false;
		}
		int iEnd = 0;
		int iStart = 0;
		int jEnd = 0;
		int jStart = 0;
		char orient = '\0';
		for(int d = 0; d < 2; d++){
			if(d==0){
				iStart = 0;
				iEnd = width;
				jStart = height-1;
				jEnd = -1;
				orient = 'h';
			}else if(d==1){
				iStart = 0;
				iEnd = width;
				jStart = height-1;
				jEnd = -1;
				orient = 'v';
			}
			for(int i = iStart; i!=iEnd; i += (int)(Math.signum(iEnd-iStart))){
				int lastval = 0;
				int[] lastloc = {-1, -1};
				for(int j = jStart; j!=jEnd; j += (int)(Math.signum(jEnd-jStart))){
					int currentval = 0;
					int x = -1;
					int y = -1;
					if(orient == 'v'){
						x=i;
						y=j;
					}else if(orient == 'h'){
						x=j;
						y=i;
					}
					currentval = p[x][y].getValue();
					if(lastval == currentval &&  lastval != 0){
						return false;
					}else if(currentval!=0){
						lastval = currentval;
						lastloc[0] = x;
						lastloc[1] = y;
					}
				}
			}
		}
		return true;
	}
	
	
	
	public boolean combine(char dir){
		boolean valid = false;
		Piece[][] p = board.getBoard();
		int iEnd = 0;
		int iStart = 0;
		int jEnd = 0;
		int jStart = 0;
		char orient = '\0';
		if(dir == 'r'){
			iStart = 0;
			iEnd = width;
			jStart = height-1;
			jEnd = -1;
			orient = 'h';
		}else if(dir == 'l'){
			iStart = 0;
			iEnd = width;
			jStart = 0;
			jEnd = height;
			orient = 'h';
		}else if(dir == 'd'){
			iStart = 0;
			iEnd = width;
			jStart = height-1;
			jEnd = -1;
			orient = 'v';
		}else if(dir == 'u'){
			iStart = 0;
			iEnd = width;
			jStart = 0;
			jEnd = height;
			orient = 'v';
		}
		
		for(int i = iStart; i!=iEnd; i += (int)(Math.signum(iEnd-iStart))){
			int lastval = 0;
			int[] lastloc = {-1, -1};
			for(int j = jStart; j!=jEnd; j += (int)(Math.signum(jEnd-jStart))){
				int currentval = 0;
				int x = -1;
				int y = -1;
				if(orient == 'h'){
					x=i;
					y=j;
				}else if(orient == 'v'){
					x=j;
					y=i;
				}
				currentval = p[x][y].getValue();
				if(lastval == currentval &&  lastval != 0){
					int newval = lastval+currentval;
					p[lastloc[0]][lastloc[1]].setValue(newval);
					score+=newval;
					p[x][y].setValue(0);
					lastval = 0;
					lastloc[0] = -1;
					lastloc[1] = -1;
					valid = true;
				}else if(currentval!=0){
					lastval = currentval;
					lastloc[0] = x;
					lastloc[1] = y;
				}
			}
		}
		return valid;

		
	}
	
	public boolean shift(char dir){
		boolean valid = false;
		Piece[][] p = board.getBoard();
		int iEnd = 0;
		int iStart = 0;
		int jEnd = 0;
		int jStart = 0;
		char orient = '\0';
		if(dir == 'r'){
			iStart = 0;
			iEnd = width;
			jStart = height-1;
			jEnd = -1;
			orient = 'h';
		}else if(dir == 'l'){
			iStart = 0;
			iEnd = width;
			jStart = 0;
			jEnd = height;
			orient = 'h';
		}else if(dir == 'd'){
			iStart = 0;
			iEnd = width;
			jStart = height-1;
			jEnd = -1;
			orient = 'v';
		}else if(dir == 'u'){
			iStart = 0;
			iEnd = width;
			jStart = 0;
			jEnd = height;
			orient = 'v';
		}
		boolean shifted = false;
		while(!shifted){
			shifted = true;
			for(int i = iStart; i!=iEnd; i += (int)(Math.signum(iEnd-iStart))){
				int lastval = 0;
				int[] lastloc = {-1, -1};
				
				for(int j = jStart; j!=jEnd; j += (int)(Math.signum(jEnd-jStart))){
					int currentval = 0;
					int x = -1;
					int y = -1;
					if(orient == 'h'){
						x=i;
						y=j;
					}else if(orient == 'v'){
						x=j;
						y=i;
					}
					currentval = p[x][y].getValue();
					if(currentval == 0){
						lastloc[0] = x;
						lastloc[1] = y;
					}else if(currentval!=0){
						if(lastloc[0]!=-1){
							shifted = false;
							p[lastloc[0]][lastloc[1]].setValue(p[x][y].getValue());
							p[x][y].setValue(0);
							lastloc[0] = -1;
							lastloc[1] = -1;
							valid = true;
						}
					}
				}
			}
		}
		
		return valid;

	}
	
	public boolean move(char dir){
		moves++;
		boolean cvalid = combine(dir);
		boolean svalid = shift(dir);
		return cvalid||svalid;
	}
}