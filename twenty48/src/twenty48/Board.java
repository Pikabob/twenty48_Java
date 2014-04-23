package twenty48;

public class Board {
	Piece[][] board;
	public Board(int width, int height){
		board = new Piece[width][height];
//		for(Piece[] i : board){
//			for(Piece j : i){
//				j = new Piece(0);
//			}
//		}
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j] = new Piece(0);
			}
		}
//		board[0][0] = new Piece(2);
//		board[0][1] = new Piece(4);
//		board[0][2] = new Piece(8);
//		board[0][3] = new Piece(16);
//		board[1][0] = new Piece(32);
//		board[1][1] = new Piece(64);
//		board[1][2] = new Piece(128);
//		board[1][3] = new Piece(256);
//		board[2][0] = new Piece(512);
//		board[2][1] = new Piece(1024);
//		board[2][2] = new Piece(2048);
	}
	public void print(){
		for(Piece[] i : board){
			for(int row = 0; row<=8; row++){
				for(Piece j : i){
					String[] s = j.getRepresentation();
					System.out.print(s[row] + "  ");
					
				}
				System.out.println();
			}
			System.out.println();
		}
	}
	public Piece[][] getBoard(){
		return board;
	}
}
