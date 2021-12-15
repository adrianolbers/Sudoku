package SudukoPack;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.w3c.dom.Text;

public class SudokuController {
	
	JPanel panelSudoku;
	private DefaultSodukos actBoard;
	/**
	* Sudukoproblem av olika svårighetsgrader.
	*/
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
	
	/**
	* Skapar ett swing-objekt för att visa Suduko-bärdet grafiskt.
	*/
	public SudokuController() { 
		SwingUtilities.invokeLater(() -> createWindow("Sudoku", 300, 400));
	}
	
	/**
	* Skapar Javax.swing-objekt för att skapa GUI:et till användaren med knapparna "solve" och "clear".
	* Beräknar hur lång tid det tog för sudukot att lösas och ger även varningar vid otillåten inskrivning av värden.
	* Berättar om sudukot går att lösa eller ej.
	* @param title namnet på fönstret
	* @param width bredden på fönstret
	* @param height höjden på fönstret
	*/
	public void createWindow(String title, int width, int height) {
		DefaultSodukos[] defaultSudokus = new DefaultSodukos[6];
		defaultSudokus[0] = new DefaultSodukos(emptyBoard, "Empty");
		defaultSudokus[1] = new DefaultSodukos(myNumbersMed, "Medium");
		defaultSudokus[2] = new DefaultSodukos(myNumbersHard, "Hard");
		defaultSudokus[3] = new DefaultSodukos(myNumbersVeryHard, "VeryHard");
		defaultSudokus[4] = new DefaultSodukos(myNumbersEvilHard, "EvilHard");
		defaultSudokus[5] = new DefaultSodukos(myNumbersWorldsHardest, "WorldsHardest");
		actBoard = defaultSudokus[0];
		
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
		
		this.printPanelBoard(actBoard.getBoard());
		JPanel panelFunction = new JPanel();
		
		JButton solveBtn = new JButton("Solve");
		ActionListener action = event -> {
			Solver solver = new Solver();
			try {
				solver.init(this.readPanelBoard());
				long timeStart = System. currentTimeMillis();
				boolean solved = solver.solve(0, 0);
				long timeEnd = System. currentTimeMillis();
				int[][] tempBoard = solver.getBoard();
				if(solver.checkIfSolveble() && solved){
					this.printPanelBoard(tempBoard);
					JOptionPane messageText = new JOptionPane();
					messageText.showMessageDialog(frame ,"It took " + ((timeEnd-timeStart)/1000) + "s and "+((timeEnd-timeStart)%1000) +"ms");
				}else{
					JOptionPane messageText = new JOptionPane();
					messageText.showMessageDialog(frame ,"Can't solve Sudoku!");
				}
			}catch(Exception e) {
				System.out.println(e);
				JOptionPane messageText = new JOptionPane();
				messageText.showMessageDialog(frame ,"Iligale input, only numbers from 1-9 are ok!");
			}
        };
		solveBtn.addActionListener(action);
		
		JComboBox<DefaultSodukos> comboBox = new JComboBox<>(defaultSudokus);
        comboBox.addActionListener(e -> {
        	actBoard = (DefaultSodukos)comboBox.getSelectedItem();
            this.printPanelBoard(actBoard.getBoard());
        });
	     
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(event->	this.printPanelBoard(emptyBoard));

																
		panelFunction.add(solveBtn);
		panelFunction.add(clearBtn);
		panelFunction.add(comboBox);
			
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
	
	/**
	* Läser av brädet som finns grafiskt och tar in värden som användaren har skrivit in.
	* Ändrar färgen på den ruta där otillåtna värden har skrivits in av användaren.
	* @throws NullPointerException om användaren har skrivit in otillåtna värden
	* @return board en integer-matris med användarens inskrivna värden
	*/
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
	
	/**
	* Skriver ut brädet till GUI:et.
	* @param board integer-matris med brädet som ska skrivas ut i GUI:et
	*/
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
