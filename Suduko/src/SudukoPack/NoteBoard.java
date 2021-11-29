package SudukoPack;

import java.util.ArrayList;
import java.util.Random;

public class NoteBoard {
	ArrayList<ArrayList<Integer>> possibleNbrs;
	
	public NoteBoard(int[][] board, SudokuSolver solver) {
		this.addPossibleNbrs(board, solver);
	}
	
	
	private void addPossibleNbrs(int[][] board, SudokuSolver solver) {
		possibleNbrs = new ArrayList<ArrayList<Integer>>();
		
		for(int k=0;k<9*9;k++){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			possibleNbrs.add(temp);
		}
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++){
				if(board[k][i]==0){
					for(int v=1;v<10;v++) {
						if(solver.checkIfLegal(k, i, v)) {
							possibleNbrs.get(k*9+i).add(v);
						}
					}
				}
			}
		}
		
	}
	
	public int getMostPossiblePlace() {
		int temp = 100;
		int index = -1;
		
		for(int k=0;k<possibleNbrs.size();k++){
			if(temp>possibleNbrs.get(k).size() && 1<possibleNbrs.get(k).size()) {
				temp = possibleNbrs.get(k).size();
				index = k;
			}
		}
		return index;
	}
	
	public int getRandomNbrFromIndex(int row, int col){
		Random rand = new Random();
		int randPlace = rand.nextInt(possibleNbrs.get(row*9+col).size());
		int value = possibleNbrs.get(row*9+col).get(randPlace);
		possibleNbrs.get(row*9+col).remove(randPlace);
		return value;
	}
	
	public boolean checkNoteIfLeagal(int row, int col, int value) {
		ArrayList<ArrayList<Integer>> tempBoard = possibleNbrs;
		int rowStart = (row/3)*3;
		int colStart = (col/3)*3;
		
		int countNbrOfValues = 0;
		int countAtSameRow = 0;
		int countAtSameCol = 0;
		for(int rowInc=0; rowInc<9;rowInc=rowInc+3){
			for(int colInc=0; colInc<9;colInc=colInc+3){
				if(rowStart!=rowInc || colStart!=colInc){
				countNbrOfValues = 0;
				countAtSameRow = 0;
				countAtSameCol = 0;
				for(int k=rowInc;k<rowInc+3;k++) {
					for(int i=colInc;i<colInc+3;i++){
						if(tempBoard.get(k*9+i).contains(value)) {
							countNbrOfValues++;
						}if(tempBoard.get(k*9+i).contains(value) && k==row){
							countAtSameRow++;
						}if(tempBoard.get(k*9+i).contains(value) && i==col){
							countAtSameCol++;
						}
						if(countNbrOfValues>3 || (countNbrOfValues==countAtSameRow||countNbrOfValues==countAtSameCol)) {
							return true;
						}
						
					}
				}
				if(countAtSameRow>1 || countAtSameCol>1) {
					return false;
				}
				}
			}	
		}
		
		
		return true;
	}
}
