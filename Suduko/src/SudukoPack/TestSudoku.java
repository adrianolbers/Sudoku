package SudukoPack;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSudoku {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	void testSolvebleSudoku() {
		SudokuController temp = new SudokuController();
		int[][] myNumbersMed= { {4, 0, 9, 1, 8, 3, 0, 0, 0}, 
								{0, 0, 0, 0, 5, 4, 0, 1, 0},
								{0, 5, 1, 2, 0, 0, 4, 0, 0},
								{9, 0, 6, 7, 1, 0, 0, 0, 0},
								{0, 2, 0, 0, 0, 0, 0, 4, 0},
								{0, 0, 0, 4, 2, 0, 6, 9, 1},
								{0, 0, 0, 5, 0, 1, 0, 0, 0},
								{7, 3, 0, 0, 0, 0, 0, 0, 0},
								{0, 0, 5, 0, 0, 6, 0, 0, 4}};
	//	temp.init(myNumbersMed);
		
	}

}
