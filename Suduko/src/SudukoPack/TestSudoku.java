package SudukoPack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSudoku {

	int[][] myNumbersMed= { {4, 0, 9, 1, 8, 3, 0, 0, 0}, 
							{0, 0, 0, 0, 5, 4, 0, 1, 0},
							{0, 5, 1, 2, 0, 0, 4, 0, 0},
							{9, 0, 6, 7, 1, 0, 0, 0, 0},
							{0, 2, 0, 0, 0, 0, 0, 4, 0},
							{0, 0, 0, 4, 2, 0, 6, 9, 1},
							{0, 0, 0, 5, 0, 1, 0, 0, 0},
							{7, 3, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	int[][] illeagalBoard= { {1, 2, 3, 0, 0, 0, 0, 0, 0}, 
							{4, 5, 6, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 7, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	int[][] illeagalBoard1= { {1, 2, 3, 0, 0, 0, 0, 0, 0}, 
							{4, 5, 6, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{4, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	int[][] emptyBoard= {   {0, 0, 0, 0, 0, 0, 0, 0, 0}, 
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0},
							{0, 0, 0, 0, 0, 0, 0, 0, 0}};
	
	@Test
	void testSolvebleSudoku() {
		Solver temp = new Solver();
		temp.init(myNumbersMed);
		assertEquals(temp.solve(0, 0), true, "Can't solve board, fix solver!");
	}
	
	@Test
	void testSolveUnsolveBoard() {
		Solver temp = new Solver();
		temp.init(illeagalBoard);
		assertEquals(temp.solve(0, 0), false, "Should Not be able to solve illeagal board");
		temp.init(illeagalBoard1);
		assertEquals(temp.solve(0, 0), false, "Should Not be able to solve illeagal board");
	}
	
	@Test
	void testSolveEmptyBoard() {
		Solver temp = new Solver();
		temp.init(emptyBoard);
		assertEquals(true, temp.solve(0, 0), "Should be able to solve empty board!");
	}
	
	@Test
	void testCheckLeagal() {
		Solver temp = new Solver();
		temp.init(emptyBoard);
		temp.add(0, 0, 1);
		assertEquals(false, temp.checkIfLegal(0,1,1), "Should not be leagal");
	}
	
	@Test
	void testCheckEmpty() {
		Solver temp = new Solver();
		temp.init(emptyBoard);
		assertEquals(true, temp.checkIfEmpty(0,0), "Should return false");
		temp.add(0, 0, 1);
		assertEquals(false, temp.checkIfEmpty(0,0), "Should return true");
	}
	
	@Test
	void testIfSolveble() {
		Solver temp = new Solver();
		temp.init(myNumbersMed);
		assertEquals(temp.checkIfSolveble(), true, "Should return false");
		temp.init(illeagalBoard);
		assertEquals(temp.checkIfSolveble(), false, "Should return true");
	}
	
	@Test
	void testRemove() {
		Solver temp = new Solver();
		temp.init(myNumbersMed);
		int[][] board = temp.getBoard();
		assertEquals(0, board[1][0], "Should be 0");
		temp.add(1, 0, 1);
		board = temp.getBoard();
		assertEquals(1, board[1][0], "Should be 0");
	}
	
	@Test
	void testAdd() {
		Solver temp = new Solver();
		temp.init(emptyBoard);
		temp.add(0, 0, 1);
		int[][] board = temp.getBoard();
		assertEquals(board[0][0], 1, "Should be 1");
		temp.add(0, 0, 10);
		board = temp.getBoard();
	}
	
	
	
}
