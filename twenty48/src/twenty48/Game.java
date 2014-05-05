package twenty48;

import java.util.Scanner;

public class Game {
	Board board;
	int width = 3;
	int height = 3;
	String up = "w";
	String down = "s";
	String left = "a";
	String right = "d";
	public void start(){
		init();
		gameLoop();
	}
	public void init(){
		board = new Board(height, width);
		putRandom(2);
	}
	public void print(){
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
//						p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//						p[x][y].setValue(0);
						return false;
//						lastval = 0;
//						lastloc[0] = -1;
//						lastloc[1] = -1;
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
	
	public void gameLoop(){
		Scanner in = new Scanner(System.in);
		while(true){
			print();
			if(checkForGameOver()){
				System.out.println("Game Over");
				break;
			}
			System.out.println("Inputs are wasd. q to quit");
			String s = in.nextLine();
//			char dir;
			boolean valid = false;
			if(s.equals(up)){
//				dir = 'u';
				valid = move('u');
			}else if(s.equals(down)){
//				dir = 'd';
				valid = move('d');
			}else if(s.equals(left)){
//				dir = 'l';
				valid = move('l');
			}else if(s.equals(right)){
//				dir = 'r';
				valid = move('r');
			}else if(s.equals("quit")||s.equals("q")){
				break;
			}else{
				continue;
			}
//			if(isValidMove()){
//				move(dir);
//			}
			if(valid){
				putRandom(1);
			}
			
			System.out.println();
			
		}
		
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
					p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
					p[x][y].setValue(0);
					lastval = 0;
					lastloc[0] = -1;
					lastloc[1] = -1;
					valid = true;
				}else if(currentval!=0){
					lastval = currentval;
					lastloc[0] = x;
					lastloc[1] = y;
//					valid = true;
				}
			}
		}
		return valid;
/*
 * up:
 * 0, 0
 * 0, 1
 * 1, 0
 * 1, 1
 * 
 * 
 * down:
 * 0, 1
 * 0, 0
 * 1, 1
 * 1, 0
 * 
 * left;
 * 0, 0
 * 1, 0
 * 0, 1
 * 1, 1
 * 
 * right:
 * 1, 0
 * 0, 0
 * 1, 1
 * 0, 1
 */
//		for(int i1 = 0; i1<width; i1++){
//			int i = Math.abs(iMod - i1);
//			int lastval = 0;
//			int[] lastloc = {-1, -1};
//			for(int j1 = 0; j1<height; j1++){
//				int j = Math.abs(jMod - j1);
//				int currentval = p[i][j].getValue();
//				if(lastval == currentval &&  lastval != 0){
//					p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//					p[i][j].setValue(0);
//					lastval = 0;
//					lastloc[0] = -1;
//					lastloc[1] = -1;
//				}else if(currentval!=0){
//					lastval = currentval;
//					lastloc[0] = i;
//					lastloc[1] = j;
//				}
//			}
//		}
			
//		if(dir == 'u'){
//			for(int j = width-1; j>=0; j--){
//				int lastval = 0;
//				int[] lastloc = {-1, -1};
//				for(int i = 0; i<height; i++){
//					int currentval = p[i][j].getValue();
//					if(lastval == currentval &&  lastval != 0){
//						p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//						p[i][j].setValue(0);
//						lastval = 0;
//						lastloc[0] = -1;
//						lastloc[1] = -1;
//					}else if(currentval!=0){
//						lastval = currentval;
//						lastloc[0] = i;
//						lastloc[1] = j;
//					}
//				}
//			}
//		}else if(dir == 'd'){
//			for(int j = 0; j<width; j++){
//				int lastval = 0;
//				int[] lastloc = {-1, -1};
//				for(int i = 0; i<height; i++){
//					int currentval = p[i][j].getValue();
//					if(lastval == currentval &&  lastval != 0){
//						p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//						p[i][j].setValue(0);
//						lastval = 0;
//						lastloc[0] = -1;
//						lastloc[1] = -1;
//					}else if(currentval!=0){
//						lastval = currentval;
//						lastloc[0] = i;
//						lastloc[1] = j;
//					}
//				}
//			}
//		}else if(dir == 'l'){
//			for(int i = 0; i<height; i++){
//				int lastval = 0;
//				int[] lastloc = {-1, -1};
//				for(int j = 0; j<width; j++){
//					int currentval = p[i][j].getValue();
//					if(lastval == currentval &&  lastval != 0){
//						p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//						p[i][j].setValue(0);
//						lastval = 0;
//						lastloc[0] = -1;
//						lastloc[1] = -1;
//					}else if(currentval!=0){
//						lastval = currentval;
//						lastloc[0] = i;
//						lastloc[1] = j;
//					}
//				}
//			}
//		}else if(dir == 'r'){
//			for(int i = height-1; i>=0; i--){
//				int lastval = 0;
//				int[] lastloc = {-1, -1};
//				for(int j = 0; j<width; j++){
//					int currentval = p[i][j].getValue();
//					if(lastval == currentval &&  lastval != 0){
//						p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
//						p[i][j].setValue(0);
//						lastval = 0;
//						lastloc[0] = -1;
//						lastloc[1] = -1;
//					}else if(currentval!=0){
//						lastval = currentval;
//						lastloc[0] = i;
//						lastloc[1] = j;
//					}
//				}
//			}
//		}
		
	}
	public boolean shift(char dir){
		Piece[][] p = board.getBoard();
		boolean valid = false;
		if(dir == 'u'){
			boolean shifted = false;
			while(!shifted){
				shifted = true;
				for(int j = 0; j<width; j++){
					int lastval = 0;
					int[] lastloc = {-1, -1};
					for(int i = 0; i<height; i++){
						int currentval = p[i][j].getValue();
						if(currentval == 0){
							lastloc[0] = i;
							lastloc[1] = j;
						}else if(currentval!=0){
							if(lastloc[0]!=-1){
								shifted = false;
								p[lastloc[0]][lastloc[1]].setValue(p[i][j].getValue());
								p[i][j].setValue(0);
								lastloc[0] = -1;
								lastloc[1] = -1;
								valid = true;
							}
						}
					}
				}
			}
		}else if(dir == 'd'){
			boolean shifted = false;
			while(!shifted){
				shifted = true;
				for(int j = 0; j<width; j++){
					int lastval = 0;
					int[] lastloc = {-1, -1};
					for(int i = height-1; i>=0; i--){
						int currentval = p[i][j].getValue();
						if(currentval == 0){
							lastloc[0] = i;
							lastloc[1] = j;
						}else if(currentval!=0){
							if(lastloc[0]!=-1){
								shifted = false;
								p[lastloc[0]][lastloc[1]].setValue(p[i][j].getValue());
								p[i][j].setValue(0);
								lastloc[0] = -1;
								lastloc[1] = -1;
								valid = true;
							}
						}
					}
				}
			}
		}else if(dir == 'l'){
			boolean shifted = false;
			while(!shifted){
				shifted = true;
				for(int i = 0; i<height; i++){
					int lastval = 0;
					int[] lastloc = {-1, -1};
					for(int j = 0; j<width; j++){
						int currentval = p[i][j].getValue();
						if(currentval == 0){
							lastloc[0] = i;
							lastloc[1] = j;
						}else if(currentval!=0){
							if(lastloc[0]!=-1){
								shifted = false;
								p[lastloc[0]][lastloc[1]].setValue(p[i][j].getValue());
								p[i][j].setValue(0);
								lastloc[0] = -1;
								lastloc[1] = -1;
								valid = true;
							}
						}
					}
				}
			}
		}else if(dir == 'r'){
			boolean shifted = false;
			while(!shifted){
				shifted = true;
				for(int i = 0; i<height; i++){
					int lastval = 0;
					int[] lastloc = {-1, -1};
					for(int j = width-1; j>=0; j--){
						int currentval = p[i][j].getValue();
						if(currentval == 0){
							lastloc[0] = i;
							lastloc[1] = j;
						}else if(currentval!=0){
							if(lastloc[0]!=-1){
								shifted = false;
								p[lastloc[0]][lastloc[1]].setValue(p[i][j].getValue());
								p[i][j].setValue(0);
								lastloc[0] = -1;
								lastloc[1] = -1;
								valid = true;
							}
						}
					}
				}
			}
		}
		return valid;
		
	}
	public boolean move(char dir){
//		combine(dir);
//		shift(dir);
		boolean cvalid = combine(dir);
		boolean svalid = shift(dir);
		return cvalid||svalid;
	}
}



