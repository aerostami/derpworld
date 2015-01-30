package units;

import game.BlockType;
import game.Board;
import objects.Obj;

public abstract class Unit extends Obj{
	protected boolean[][] isDiscovered;
	protected String occupation;
	public abstract void update();
	
	protected Unit(int row,int col, boolean[][] isDiscovered, int ceId){
		super(row,col,ceId);
		this.isDiscovered=isDiscovered;
	}
	
	public void teleport(int dRow, int dCol) {
		row = dRow;
		col = dCol;
		Board.map[dRow][dCol].setObjOnBlock(id);
		Board.isAvailable[dRow][dCol] = false;
		isDiscovered[dRow][dCol] = true;
		exploreBlocks();
	}
	public void kill() {
		Board.map[row][col].freeBlock();
//		if (0 < toDoList.size()) {
//
//			String is = toDoList.get(0).split(" ")[0];
//			if (!is.equals("workingAt") && !is.equals("constructing"))
//				freeNode();
//		} else
			freeNode();
		occupation = "dead";
		// TODO
	}
	protected void freeNode() {
		Board.map[row][col].freeBlock();
		if (Board.map[row][col].type() == BlockType.Plain)
			Board.isAvailable[row][col] = true;
	}
	
	/*
	 * explores the 8 block in unit location
	 */
	protected void exploreBlocks(){
		if (0<row){
			isDiscovered[row-1][col]=true;
			if(0<col)
				isDiscovered[row-1][col-1]=true;
			if(col<isDiscovered[0].length-1)
				isDiscovered[row-1][col+1]=true;
		}
		if(row<isDiscovered.length-1){
			isDiscovered[row+1][col]=true;
			if(0<col)
				isDiscovered[row+1][col-1]=true;
			if(col<isDiscovered[0].length-1)
				isDiscovered[row+1][col+1]=true;
		}
		if(0<col)
			isDiscovered[row][col-1]=true;
		if(col<isDiscovered[0].length-1)
			isDiscovered[row][col+1]=true;
	} 
	
}
