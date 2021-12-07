package SudukoPack;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.w3c.dom.Text;

public class SudokuController {
	
	JPanel panelSudoku;
	
	int[][] emptyBoard= {   {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0}};

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
	int[][] myNumbersWorldsHardest= { {8, 0, 0, 0, 0, 0, 0, 0, 0}, 
										{0, 0, 3, 6, 0, 0, 0, 0, 0},
										{0, 7, 0, 0, 9, 0, 2, 0, 0},
										{0, 5, 0, 0, 0, 7, 0, 0, 0},
										{0, 0, 0, 0, 4, 5, 7, 0, 0},
										{0, 0, 0, 1, 0, 0, 0, 3, 0},
										{0, 0, 1, 0, 0, 0, 0, 6, 8},
										{0, 0, 8, 5, 0, 0, 0, 1, 0},
										{0, 9, 0, 0, 0, 0, 4, 0, 0}};
	
	public SudokuController() { 
		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 300, 400));
	}
	
	public void createWindow(String title, int width, int height) {
		JFrame frame = new JFrame(title);
		frame.setBounds(width, height, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		
		panelSudoku = new JPanel(new GridLayout(9,9));
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				if(k%6<3 && i%6<3 || (i/3==1 && k/3==1)) {
					panelSudoku.add(getTextField(Color.orange));
				}else {
					panelSudoku.add(getTextField(Color.white));
				}
			}	
		}
		
		this.printPanelBoard(myNumbersMed);
		JPanel panelFunction = new JPanel();
		JButton solveBtn = new JButton("Solve");
		ActionListener action = event -> {
			Solver solver = new Solver();
			try {
				long timeStart = System. currentTimeMillis();
				solver.init(this.readPanelBoard());
				if(solver.checkIfSolveble()){
					this.printPanelBoard(solver.getBoard());
					long timeEnd = System. currentTimeMillis();
					System.out.println("Time it took: "+ (timeEnd-timeStart)/1000 + "s");
					JOptionPane messageText = new JOptionPane();
					messageText.showMessageDialog(frame ,"It took " + (timeEnd-timeStart)/1000 + "s and "+(timeEnd-timeStart)%1000+"ms");
				}else{
					JOptionPane messageText = new JOptionPane();
					messageText.showMessageDialog(frame ,"Can't solve Sudoku!");
				}
			}catch(Exception e) {
				JOptionPane messageText = new JOptionPane();
				messageText.showMessageDialog(frame ,"Iligale input, only numbers from 1-9 are ok!");
			}
        };
		solveBtn.addActionListener(action);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(event->	this.printPanelBoard(emptyBoard));

																
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
	
	public int[][] readPanelBoard() {
		int[][] board = new int[9][9];
		int temp = 0;
		for(int k=0;k<9;k++) {
			for(int i=0;i<9;i++) {
				if(panelSudoku.getComponent(temp) instanceof JTextField) {
					JTextField text = (JTextField)panelSudoku.getComponent(temp);
					if(k%6<3 && i%6<3 || (i/3==1 && k/3==1)) {
						text.setBackground(Color.orange);
					}else {
						text.setBackground(Color.white);
					}
					if(text.getText().isBlank()){
						board[k][i] = 0;
					}else {
						try {
							board[k][i] = Integer.parseInt(text.getText());
						}catch(Exception e) {
							text.setBackground(Color.red);
							throw new NullPointerException("Ileagal input");
						}
						if(board[k][i]<1 || board[k][i]>9) {
							text.setBackground(Color.red);
							throw new NullPointerException("Ileagal input");
						}
					}
					temp++;
				}else {
					System.out.println(" Failed to read board values! ");
				}
			}
		}
		
		return board;
	}
	
	public void printPanelBoard(int[][] board){
		for(int k=0; k<9;k++) {
			for(int i=0; i<9;i++) {
				if(panelSudoku.getComponent(k*9+i) instanceof JTextField) {
					JTextField text = (JTextField)panelSudoku.getComponent(k*9+i);
						if(board[k][i]>0) {
							text.setText(Integer.toString(board[k][i]));
						}else {
							text.setText("");
						}
				}else {
					System.out.println(" Failed to add board values! ");
				}
			}
		}
		
	}
	

	
	
}
