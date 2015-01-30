package buildings;

public class Market extends Building{
	final static String type="8";
	public final static int size=3;
	public final static int[] recourcesNeeded={60,120,120};
	final static int turnsToBuild=20;

	private static int typeNum;
	public Market(int row,int col,int ceId){
		super(row,col,size,type, turnsToBuild,recourcesNeeded,ceId);
		typeNum+=1;
		id="building_8_"+typeNum;
		placeOnBlocks();
	}
	public void update(){
		
	}
	public int getAvgRecourceUnder(){
		return 0;
	}

}
