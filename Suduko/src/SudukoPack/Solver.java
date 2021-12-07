package SudukoPack;

import javax.swing.JTextField;

public class Solver implements SudokuSolver{
	
	int[][] board = new int[9][9];
	int[][] savedBoard = new int[9][9];
	NoteBoard noteBoard = new NoteBoard(board, this);
	
	/**
	* Använder noteBoard där möjliga värden beräknas och returnerar true om brädet har en lösning, false om lösning saknas.
	* @return true eller false
	*/
	public boolean checkIfSolveble() {
		return noteBoard.checkIfBoadIsSolvable(board, this);
	}
	
	/**
	* Verifierar om värdet value är tillåtet att placera på row och col enligt sudukos regler.
	* @param row raden som värdet ska placeras på
	* @param col kolumnen som värdet ska placeras på
	* @param value värdet som ska verifieras
	* @return true eller false
	*/
	@Override
	public boolean checkIfLegal(int row, int col, int value) {
		if(!checkRowNCol(row,col,value) || !checkSquare(row,col,value)){
			return false;
		}
		return true;
	}
	
	private boolean checkSquare(int row, int col, int value) {
		for(int k=(row/3)*3;k<(row/3)*3+3;k++) {
			for(int i=(col/3)*3;i<(col/3)*3+3;i++) {
				if((k==row && i==col));
				else if(board[k][i]==value) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkRowNCol(int row, int col, int value){
		for(int k=0; k<9;k++) {
			if(value==board[row][k] && col!=k){
				return false;
			}
			if(value==board[k][col] && row!=k){
				return false;
			}
		}
		return true;
	}
	
	/**
	* Returnerar true om platsen för raden och kolumnen är tom. 
	* @param row raden för platsen som ska granskas
	* @param col kolumnen för platsen som granskas
	* @return true eller false
	*/
	@Override
	public boolean checkIfEmpty(int row, int col) {
		return board[row][col]==0;
	}
	
	/**
	* Adderar användarens input till savedBoard.
	* @param integer-matris med användarens input
	*/
	@Override
	public void init(int[][] start) {
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				savedBoard[k][i] = start[k][i];
				this.add(k, i, start[k][i]);
			}
		}
	}
	
	/**
	* Beräknar själva lösningen av brädet genom att anropa metoden solve() och checkIfBoardIsSolvable().
	* @return integer-matris med lösningen
	*/
	@Override
	public int[][] getBoard() {
		while(!this.isFull()){
			boolean bruteForce = true;
			for(int k=0;k<9;k++){
				for(int i=0;i<9;i++) {
					if(this.checkIfEmpty(k, i)){
						if(this.solve(k, i)) {
							bruteForce = false;
						}
					}
				}
			}
			if(bruteForce && !noteBoard.checkIfBoadIsSolvable(board, this)) {
				this.clear();
				this.init(savedBoard);
			}else if(bruteForce){
				if(noteBoard.getMostPossiblePlace()>-1){
					do{
						int newRow = noteBoard.getMostPossiblePlace()/9;
						int newCol = noteBoard.getMostPossiblePlace()%9;
						int newValue = noteBoard.getRandomNbrFromIndex(newRow, newCol);
						this.add(newRow, newCol, newValue);
					}while(noteBoard.solvedNbrExist()); 
				}
			}
		}
		return board;
	}
	
	private boolean isFull(){
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				if(board[k][i]==0){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	* Löser varje plats för raden och kolumnen genom att använda värdena och reglerna för suduko.
	* @param row raden där ett värde ska placeras
	* @param col kolumnen där ett värde ska placeras
	* @return true eller false
	*/
	@Override
	public boolean solve(int row, int col) {
		for(int v=1;v<10;v++){
			if(this.checkIfLegal(row, col, v) && noteBoard.checkNoteIfLeagal(row, col, v)) {	
				int options = 9;
				for(int k=0;k<9;k++) {
					if((board[k][col]>0 || !this.checkIfLegal(k, col, v)) || !noteBoard.checkNoteIfLeagal(k, col, v)) {
						options--;
					}
				}
				if(options==1) {
					this.add(row, col, v);
					return true; 
				}
				
				options = 9;
				for(int k=0;k<9;k++) {
					if((board[row][k]>0 || !this.checkIfLegal(row, k, v)) || !noteBoard.checkNoteIfLeagal(row, k, v)) {
						options--;
					}
				}
				if(options==1) {
					this.add(row, col, v);
					return true; 
				}
				
				options = 9;
				for(int k=(row/3)*3;k<(row/3)*3+3;k++) {
					for(int i=(col/3)*3;i<(col/3)*3+3;i++){
						if((board[k][i]>0 || !this.checkIfLegal(k, i, v)) || !noteBoard.checkNoteIfLeagal(k, i, v)) {
							options--;
						}
					}
				}
				if(options==1) {
					this.add(row, col, v);
					return true; 
				}
			}
		}
		return false;
	}
	
	/**
	* Lägger till rätt värde på rätt plats som beräknas i metoden solve().
	* @param row raden som värdet ska adderas till
	* @param col kolumnen som värdet ska adderas till
	* @param value korrekta värdet som ska adderas på rätt plats.
	*/
	@Override
	public void add(int row, int col, int value) {
		board[row][col] = value;
		noteBoard = new NoteBoard(board, this);
	}

	@Override
	/**
	* Tömmer hela brädet från alla värden.
	*/
	public void clear() {
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++){
				board[k][i] = 0;
			}
		}
	}
	
	/**
	* Tar bort värdet från en specifik plats på brädet.
	* @param row raden som värdet ska tas bort ifrån
	* @param col kolumnen som värdet ska tas bort ifrån
	*/
	@Override
	public void remove(int row, int col) {
		board[row][col] = 0;
	}
}
