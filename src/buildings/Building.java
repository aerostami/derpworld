package buildings;
import game.Board;
import objects.Obj;


public abstract class Building extends Obj{
	protected int capacity;
	protected int workersInside;
	private int size;
	private String type;
	private int[] recourcesNeeded;
	public int turnsLeftToBuild;
	public Building(int row,int col,int size,String type, int turnsLeftToBuild, int[] recourcesNeeded,int ceId){
		super(row,col,ceId);
		this.ceId = ceId;
		this.recourcesNeeded=recourcesNeeded;
		this.turnsLeftToBuild=turnsLeftToBuild;
		this.size=size;
		this.type=type;
	}
	protected void placeOnBlocks(){
		id+="_"+ceId;// might cause problems in future
		for(int i=0;i<this.size;i+=1)
			for(int j=0;j<this.size;j+=1){
				Board.isAvailable[row+i][col+j] = false;
				Board.map[row+i][col+j].setObjOnBlock(id);
			}
	}
	public void destroy(){
		for(int i=0;i<this.size;i+=1)
			for(int j=0;j<this.size;j+=1){
				Board.isAvailable[row+i][col+j] = true;
				Board.map[row+i][col+j].freeBlock();
			}
	}
	public boolean isFull(){
		if(capacity<=workersInside)
			return true;
		return false;
	}
	public void addWorker(){
		workersInside+=1;
	}
	public void removeWorker(){
		workersInside-=1;
	}
	public int[] getLocation(){
		int [] ret={row,col};
		return ret;
	}
	public int getSize(){
		return size;
	}
	public String getType(){
		return type;
	}
	public int[] recourcesNeeded(){
		return recourcesNeeded;
	}
	abstract public int getAvgRecourceUnder();
	
	public abstract void update();
}
