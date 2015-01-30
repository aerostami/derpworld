package buildings;

import game.Board;

public class StockPile extends Building{
	final static String type="7";
	public final static int size=2;
	public final static int[] recourcesNeeded={10,100,100};
	final static int turnsToBuild=15;

	private static int typeNum;
	public StockPile(int row,int col,int ceId){
		super(row,col,size,type, turnsToBuild,recourcesNeeded,ceId);
		typeNum+=1;
		id="building_7_"+typeNum;
		Board.derpCElization.addResourceCapacity(100);
		placeOnBlocks();
	}
	public void update(){
		
	}
	public int getAvgRecourceUnder(){
		return 0;
	}

}
