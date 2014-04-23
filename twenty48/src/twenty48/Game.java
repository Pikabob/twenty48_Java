package twenty48;

import java.util.Scanner;

public class Game {
	Board board;
	int width = 4;
	int height = 4;
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
	public void gameLoop(){
		Scanner in = new Scanner(System.in);
		while(true){
			print();
			if(checkFull()){
				System.out.println("Game Over");
				break;
			}
			System.out.println("Inputs are up, down, left, and right. Sorry.");
			String s = in.nextLine();
			if(s.equals("up")||s.equals("u")||s.equals("w")){
				move('u');
			}if(s.equals("down")||s.equals("d")){
				move('d');
			}if(s.equals("left")||s.equals("l")){
				move('l');
			}if(s.equals("right")||s.equals("r")){
				move('r');
			}if(s.equals("quit")||s.equals("q")){
				break;
			}
			putRandom(1);
			System.out.println();
			
		}
		
	}
	public void combine(char dir){
		Piece[][] p = board.getBoard();
		int jMod = 0;
		int iMod = 0;
		if(dir == 'u'){
			iMod = 3;
			jMod = 0;
		}
		for(int i1 = 0; i1<width; i1++){
			int i = Math.abs(iMod - i1);
			int lastval = 0;
			int[] lastloc = {-1, -1};
			for(int j1 = 0; j1<height; j1++){
				int j = Math.abs(jMod - j1);
				int currentval = p[i][j].getValue();
				if(lastval == currentval &&  lastval != 0){
					p[lastloc[0]][lastloc[1]].setValue(lastval+currentval);
					p[i][j].setValue(0);
					lastval = 0;
					lastloc[0] = -1;
					lastloc[1] = -1;
				}else if(currentval!=0){
					lastval = currentval;
					lastloc[0] = i;
					lastloc[1] = j;
				}
			}
		}
			
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
	public void shift(char dir){
		Piece[][] p = board.getBoard();
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
							}
						}
					}
				}
			}
		}
		
	}
	public void move(char dir){
		combine(dir);
		shift(dir);
	}
}



