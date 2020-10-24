package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class GameManagerTest {

	private GameManager gm = new GameManager();
	//	public void setupScenary1() throws IOException {
	//		gm.addNode(2,2);
	//	}
	public void setupScenary1() {
		gm = new GameManager();
		gm.addNode(1, 1);
	}

	public void setupScenary2() {
		gm = new GameManager();
		gm.addNode(1, 2);
	}
	public void setupScenary3() {
		gm = new GameManager();
		gm.addNode(1, 3);
	}
	public void setupScenary4() {
		gm = new GameManager();
		gm.addNode(1, 4);
	}
	public void setupScenary5() {
		gm = new GameManager();
		gm.addNode(2, 3);
	}
	public void setupScenary6() {
		gm = new GameManager();
		gm.addNode(3, 3);
	}
	public void setupScenary7() {
		gm = new GameManager();
		gm.addNode(5, 10);
	}
	public void setupScenary8() {
		gm = new GameManager();
		gm.addNode(4, 3);
	}

	//	@Test
	//    void testAddNode1Line() {
	//        setupScenary1();
	//        assertEquals("Fail test", true ,gm.getfirst()!=null && gm.getfirst().getRight()==null );
	//    }
	//
	//    @Test
	//    void testAddNode2Line() {
	//        setupScenary2();
	//        assertEquals(true, gm.getfirst().getRight()!=null, "Como que no papi");
	//    }
	//    @Test
	//    void testAddNode3Line() {
	//        setupScenary3();
	//        assertEquals(true, gm.getfirst().getRight().getRight()!=null, "Como que no papi");
	//    }
	//    
	//    @Test
	//    void testAddNode4Line() {
	//        setupScenary3();
	//        assertEquals("Fail test", true ,gm.getfirst().getRight() != gm.getfirst() && gm.getfirst().getRight() != gm.getfirst().getRight().getRight() && gm.getfirst() != gm.getfirst().getRight().getRight());
	//    }
	//    @Test
	//    void testAddNode5Line() {
	//        setupScenary4();
	//        assertEquals("Fail test", true ,gm.getfirst() == gm.getfirst().getRight().getLeft() && gm.getfirst().getRight() == gm.getfirst().getRight().getRight().getLeft() && gm.getfirst().getRight().getRight() == gm.getfirst().getRight().getRight().getRight().getLeft() && gm.getfirst().getRight().getRight().getRight().getLeft() != null);
	//    }
	@Test
	void testAddNode5Line() {
		setupScenary5();
		assertEquals("Fail test", true,  gm.getfirst().getDown() != null && gm.getfirst().getRight() == gm.getfirst().getDown().getRight().getUp() && gm.getfirst().getRight().getRight() == gm.getfirst().getDown().getRight().getRight().getUp());
	}
	//    @Test
	//    void testAddNode1Jump() {
	//        setupScenary6();
	//        assertEquals("Fail test", true ,gm.getfirst().getDown().getDown() != null && gm.getfirst().getDown().getDown().getRight().getRight() != null && gm.getfirst().getDown().getDown().getDown() == null);
	//    }
	//  @Test
	//  void testAddNode1Jump() {
	//      setupScenary7();
	//      assertEquals("Fail test", true ,gm.getfirst().getDown().getDown().getDown().getDown() != null && gm.getfirst().getDown().getDown().getDown().getDown().getRight().getRight().getRight().getRight() != null && gm.getfirst().getDown().getDown().getDown().getDown().getDown() == null);
	//  }
//	@Test
//	void testAddNodeVerticalRelation() {
//		setupScenary8();
//		assertEquals("Fail test", true, gm.getfirst());
//	}
}
