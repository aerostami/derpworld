package units;

import game.Board;

public class Boat extends Unit{
	public static int boatsNum;
	public Boat (int row, int col, boolean[][] isDiscovered,int ceId){
		super(row,col,isDiscovered,ceId);
		boatsNum+=1;
		id="boat_"+boatsNum;
		id+="_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		//TODO
	}
	
	public void update(){
		//TODOs
		Board.derpCElization.addFoodInBank(15);
	}

}
