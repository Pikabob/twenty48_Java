package twenty48;

public class Board {
	Piece[][] board;
	public Board(int width, int height){
		board = new Piece[width][height];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				board[i][j] = new Piece(0);
			}
		}
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
