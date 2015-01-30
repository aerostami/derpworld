package buildings;

import game.Board;

public class Farm extends Building{
	public final static int size=2;
	final static String type="5";
	public final static int[] recourcesNeeded={10,10,10};
	final static int turnsToBuild=6;
	private int avgRecourceUnder;
	
	private static int typeNum;
	public Farm(int row,int col,int ceId){
		super(row,col,size,type,turnsToBuild,recourcesNeeded,ceId);
		typeNum+=1;
		id="building_5_"+typeNum;
		placeOnBlocks();
		avgRecourceUnder=findAvgRecourceUnder();
		capacity=1;
	}
	
	private int findAvgRecourceUnder(){
		int sum=0;
		for(int i=0;i<size;i+=1)
			for(int j=0;j<size;j+=1)
				sum+=Board.map[row+i][col+j].foodNum();
			
		return sum/(size*size);
	}
	public int getAvgRecourceUnder(){
		return avgRecourceUnder;
	}
	
	
	public void update(){
		
	}

}
