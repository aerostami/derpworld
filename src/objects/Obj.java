package objects;

public abstract class Obj {
	protected int row, col;
	protected String id;
	protected int ceId;
	public abstract void update();
	public Obj(int row, int col, int ceId){
		this.ceId = ceId;
		this.row=row;
		this.col=col;
	}
	public boolean hasId(String id){
		if (this.id.equals(id))
			return true;
		return false;
	}
	public String getId(){
		return id;
	}
	public int getRow(){
		return row;
	}
	public int ceId(){
		return ceId;
	}
	public int getCol(){
		return col;
	}
}
