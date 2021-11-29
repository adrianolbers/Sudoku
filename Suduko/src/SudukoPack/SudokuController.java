package SudukoPack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.w3c.dom.Text;

public class SudokuController implements SudokuSolver {
	int[][] board = new int[9][9];
	int[][] savedBoard = new int[9][9];
	
	JPanel panelSudoku;
	int[][] myNumbersMed= { {4, 0, 9, 1, 8, 3, 0, 0, 0}, 
							{0, 0, 0, 0, 5, 4, 0, 1, 0},
							{0, 5, 1, 2, 0, 0, 4, 0, 0},
							{9, 0, 6, 7, 1, 0, 0, 0, 0},
							{0, 2, 0, 0, 0, 0, 0, 4, 0},
							{0, 0, 0, 4, 2, 0, 6, 9, 1},
							{0, 0, 0, 5, 0, 1, 0, 0, 0},
							{7, 3, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 5, 0, 0, 6, 0, 0, 4}};
	int[][] myNumbersHard = {{7, 5, 0, 0, 8, 0, 0, 0, 0}, 
							{0, 0, 8, 3, 0, 0, 7, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{1, 8, 5, 0, 0, 0, 0, 2, 0},
							{0, 6, 0, 0, 1, 0, 0, 0, 8},
							{0, 0, 0, 9, 2, 0, 0, 0, 0},
							{0, 0, 0, 6, 0, 0, 9, 0, 0},
							{0, 0, 7, 5, 0, 9, 0, 1, 3},
							{0, 4, 0, 0, 0, 0, 0, 0, 0}};
	int[][] myNumbersVeryHard = { 	{4, 1, 0, 0, 8, 0, 0, 2, 0}, 
							{2, 0, 0, 0, 6, 0, 0, 0, 9},
							{0, 0, 0, 1, 0, 5, 0, 0, 0},
							{0, 0, 9, 5, 0, 0, 3, 0, 0},
							{5, 3, 0, 0, 0, 0, 0, 6, 7},
							{0, 0, 2, 0, 0, 4, 9, 0, 0},
							{0, 0, 0, 8, 0, 6, 0, 0, 0},
							{9, 0, 0, 0, 4, 0, 0, 0, 8},
							{0, 6, 0, 0, 1, 0, 0, 7, 4}};
	int[][] myNumbersEvilHard = {	{0, 9, 0, 0, 0, 0, 0, 1, 0}, 
								{5, 0, 1, 0, 0, 3, 0, 0, 6},
								{0, 7, 0, 0, 5, 0, 0, 0, 0},
								{2, 0, 4, 0, 9, 0, 0, 6, 0},
								{0, 0, 0, 8, 0, 0, 0, 0, 3},
								{0, 1, 0, 0, 0, 0, 0, 0, 0},
								{6, 0, 5, 0, 2, 0, 0, 4, 0},
								{7, 0, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 0, 0, 0, 9, 2, 0, 0}};
	
	public SudokuController() {
		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 300, 400));
	}
	
	public void createWindow(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setBounds(width, height, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		
		panelSudoku = new JPanel(new GridLayout(9,9));
		for(int i=0;i<3;i++) {
			for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.orange));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.white));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.orange));
			}
		}for(int i=0;i<3;i++) {
			for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.white));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.orange));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.white));
			}
		}for(int i=0;i<3;i++) {
			for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.orange));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.white));
			}for(int k=0;k<3;k++) {
				panelSudoku.add(getTextField(Color.orange));
			}
		}
		
		this.init(myNumbersMed);
		JPanel panelFunction = new JPanel();
		JButton solveBtn = new JButton("Solve");
		ActionListener action = event -> {
			this.init(this.getBoard());
			int iterations = 0;
			int restarts = 0;
			while(this.isNotSolved()){
				boolean bruteForce = true;
				board = this.getBoard();
				for(int k=0;k<9;k++){
					for(int i=0;i<9;i++) {
						if(this.checkIfEmpty(k, i)){
							if(this.solve(k, i)) {
								bruteForce = false;
							}
						}
					}
				}
				if(this.isFull() && this.isNotSolved()) {
					this.clear();
					this.init(savedBoard);
					restarts++;
				}else if(bruteForce){
					NoteBoard noteBoard = new NoteBoard(board, this);
					if(noteBoard.getMostPossiblePlace()>-1){
						int newRow = noteBoard.getMostPossiblePlace()/9;
						int newCol = noteBoard.getMostPossiblePlace()%9;
						int newValue = noteBoard.getRandomNbrFromIndex(newRow, newCol);
						this.add(newRow, newCol, newValue);
					}
				}
				iterations++;
			}
			System.out.println("Iterations: "+iterations);
			System.out.println("Restarts: "+restarts);
        };
		solveBtn.addActionListener(action);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(event->this.clear());
		panelFunction.add(solveBtn);
		panelFunction.add(clearBtn);
			
		pane.add(panelSudoku,BorderLayout.CENTER);
		pane.add(panelFunction,BorderLayout.SOUTH);
		
		
		

		frame.setVisible(true);
	}
	
	private JTextField getTextField(Color color) {
		JTextField nbrSquare = new JTextField();
		nbrSquare.setSize(10, 5);
		nbrSquare.setBackground(color);
		nbrSquare.setHorizontalAlignment(JTextField.CENTER);
		nbrSquare.setFont(new Font("",Font.PLAIN,20));
		return nbrSquare;
	}
	
	
	@Override
	public boolean checkIfLegal(int row, int col, int value) {
		
		int options = 9;
		for(int k=(row/3)*3;k<(row/3)*3+3;k++) {
			for(int i=(col/3)*3;i<(col/3)*3+3;i++) {
				if((k==row && i==col));
				else if(board[k][i]==value) {
					return false;
				}else if(board[k][i]>0 || !this.checkRowNCol(k, i, value) ) {
					options--;
				}
			}
		}

		if(options==1) {
			this.add(row, col, value);
			return true;
		}else if(checkRowNCol(row,col,value)){
			return true;
		}
		return false;
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

	@Override
	public boolean checkIfEmpty(int row, int col) {
		return board[row][col]==0;
	}

	@Override
	public void init(int[][] start) {
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				board[k][i] = start[k][i];
				savedBoard[k][i] = start[k][i];
				this.add(k, i, start[k][i]);
			}
		}
	}

	@Override
	public int[][] getBoard() {
		int[][] board = new int[9][9];
		int temp = 0;
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				if(panelSudoku.getComponent(temp) instanceof JTextField) {
					JTextField text = (JTextField)panelSudoku.getComponent(temp);
					if(text.getText().isBlank()){
						board[k][i] = 0;
					}else {
						board[k][i] = Integer.parseInt(text.getText());
					}
					temp++;
				}else {
					System.out.println(" Failed to read board values! ");
				}
			}
		}
		
		return board;
	}

	@Override
	public boolean solve(int row, int col) {
		NoteBoard noteBoard = new NoteBoard(board, this);
		int temp = 0;
		int value = 0;
		for(int k=1;k<10;k++) {
			if(this.checkIfLegal(row, col, k) && noteBoard.checkNoteIfLeagal(row,col,value)) {
				temp++;
				value = k;
			}
		}
		if(temp==1) {
			this.add(row, col, value);
			return true;
		}
		return false;
	}

	@Override
	public void add(int row, int col, int value) {
		if(panelSudoku.getComponent(row*9+col) instanceof JTextField) {
			JTextField text = (JTextField)panelSudoku.getComponent(row*9+col);
				if(value>0) {
					text.setText(Integer.toString(value));
				}else {
					text.setText("");
				}
				
		}else {
			System.out.println(" Failed to add board values! ");
		}
		board[row][col] = value;
	}

	@Override
	public void clear() {
		for(int k=0;k<9*9;k++) {
			if(panelSudoku.getComponent(k) instanceof JTextField) {
				JTextField text = (JTextField)panelSudoku.getComponent(k);
				text.setText("");
			}else {
				System.out.println(" Failed to clear board values! ");
			}
		}
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++){
				board[k][i] = 0;
			}
		}
	}

	@Override
	public void remove(int row, int col) {
		if(panelSudoku.getComponent(row*9+col) instanceof JTextField) {
			JTextField text = (JTextField)panelSudoku.getComponent(row*9+col);
			text.setText("");
		}else {
			System.out.println(" Failed to remove board values! ");
		}
		board[row][col] = 0;
	}
	
	private boolean isNotSolved() {
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				if(this.checkIfEmpty(k, i)){
					return true;
				}else if(!this.checkIfLegal(k, i, board[k][i])){
					return true;
				}else if(!this.checkRowNCol(k, i, board[k][i])){
					return true;
				}
			}
		}
		return false;
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
	
}
