package buildings;

import game.Board;

public class GoldMine extends Building{
	final static String type="3";
	public final static int size=2;
	public final static int[] recourcesNeeded={50,50,50};
	final static int turnsToBuild=6;
	private int avgRecourceUnder;
	private static int typeNum;

	public GoldMine(int row,int col,int ceId){
		super(row,col,size,type,turnsToBuild,recourcesNeeded,ceId);
		typeNum+=1;
		id="building_3_"+typeNum;
		placeOnBlocks();
		avgRecourceUnder=findAvgRecourceUnder();
		capacity=1;
	}
	private int findAvgRecourceUnder(){
		int sum=0;
		for(int i=0;i<size;i+=1)
			for(int j=0;j<size;j+=1)
				sum+=Board.map[row+i][col+j].goldNum();
			
		return sum/(size*size);
	}
	public int getAvgRecourceUnder(){
		return avgRecourceUnder;
	}
	public void update(){
		
	}

}
