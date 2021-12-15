package SudukoPack;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class NoteBoard {
	private ArrayList<ArrayList<Integer>> possibleNbrs;
	
	/**
	* Skapar en NoteBoard som används för att hitta alla möjliga lösningar av aktuellt bräde.
	* @param integer-matris
	* @param Solver-objekt
	*/
	public NoteBoard(int[][] board, Solver solver) {
		this.addPossibleNbrs(board, solver);
	}
	
	
	private void addPossibleNbrs(int[][] board, Solver solver) {
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
	/**
	* Tar fram index för mest sannolika lösningsförslaget.
	* @return index med lägst antal möjliga siffror
	*/
	public int getMostPossiblePlace() {
		int temp = 100;
		int index = -1;
		
		for(int k=0;k<possibleNbrs.size();k++){
			if(temp>possibleNbrs.get(k).size() && 1<=possibleNbrs.get(k).size()) {
				temp = possibleNbrs.get(k).size();
				index = k;
				if(temp==1)return index; 
			}
		}
		return index;
	}
	
	public int amountOfPossibleNumbers(int row, int col){
		return possibleNbrs.get(row*9+col).size();
	}
	
	public int getValueFromIndex(int row, int col, int index) {
		return possibleNbrs.get((row*9)+col).get(index);
	}
	
	/**
	* Går igenom det aktuella brädet och verifierar om alla möjliga integer-värden är placerade på brädet.
	* @param integer-matris
	* @param Solver-objekt
	* @return true eller false
	*/
	public boolean checkIfBoadIsSolvable(int [][] board, Solver solver) {
		for(int row=0;row<9;row=row+3) {
			for(int col=0;col<9;col=col+3) {
				
				for(int value=1;value<10;value++) {
					boolean notFound = true;
					for(int k=(row/3)*3;k<(row/3)*3+3;k++) {
						for(int i=(col/3)*3;i<(col/3)*3+3;i++) {
							if(value==board[k][i]) {
								if(notFound==false) {
									return false;
								}
								notFound = false;
							}else if(possibleNbrs.get(k*9+i).contains(value)) {
								notFound = false;
							}
						}
					}
					if(notFound) {
						return false;
					}
				}	
						
			}
		}
		
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++){
				if(!(board[k][i]==0)) {
					if(!solver.checkIfLegal(k, i, board[k][i])) {
						return false;
					}
					
				}
			}
		}
			
				
		return true;
	}
	
	/**
	* Verifierar att alla värden i matrisen är legala inom Sudukoreglerna genom att jämföra alla värden på varje column, row och ruta.
	* @param row
	* @param col
	* @param value
	* @return true eller false
	*/
	public boolean checkNoteIfLeagal(int row, int col, int value) {
		for(int indexSqr=0; indexSqr<9; indexSqr=indexSqr+3){
			if((row/3)*3!=indexSqr && (col/3)*3!=indexSqr) {
				int totalSameNbrs = 0;
				int[] indexCol = new int[3];		
				for(int k=indexSqr;k<indexSqr+3;k++) {
					for(int i=(col/3)*3;i<((col/3)*3)+3;i++) {
						if(possibleNbrs.get(k*9+i).contains(value)){
							indexCol[i%3]++;
							totalSameNbrs++;
						}
					}		
				}
				if(indexCol[col%3] == totalSameNbrs && totalSameNbrs>1 && totalSameNbrs<=3) {
					return false;
				}
				
				totalSameNbrs = 0;
				int[] indexRow = new int[3];	
				for(int k=(row/3)*3;k<((row/3)*3)+3;k++) {
					for(int i=indexSqr;i<indexSqr+3;i++) {
						if(possibleNbrs.get(k*9+i).contains(value)){
							indexRow[k%3]++;
							totalSameNbrs++;
						}
					}
				}
				if(indexRow[row%3] == totalSameNbrs && totalSameNbrs>1 && totalSameNbrs<=3) {
					return false;
				}
			}
		}
		
		return true;
	}
}
