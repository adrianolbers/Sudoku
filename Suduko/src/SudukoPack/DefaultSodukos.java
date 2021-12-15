package SudukoPack;

public class DefaultSodukos {
	private int[][] board;
	private String title;
	
	public DefaultSodukos(int[][] board, String title) {
		this.board = board;
		this.title = title;
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	public String toString() {
		return title;
	}
}
