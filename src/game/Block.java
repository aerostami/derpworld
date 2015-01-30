package game;


public class Block {
	private BlockType type;
	private String objOnBlock;
	private int goldNum, stoneNum, lumberNum, foodNum;
	public Block(BlockType type, int gold, int stone,int lumber, int food){
		this.type=type;
		this.goldNum=gold;
		this.stoneNum=stone;
		this.lumberNum=lumber;
		this.foodNum=food;
		objOnBlock="empty";
	}
	//**
	public String objOnBlock() {
		return objOnBlock;
	}

	public void freeBlock(){
		objOnBlock="empty";
	}
	public void setObjOnBlock(String objOnBlock) {
//		if (this.objOnBlock.equals("empty"))
			this.objOnBlock = objOnBlock;
	}
	//********
	public BlockType type(){
		return type;
	}
	//********
	public boolean hasGold(){
		if (goldNum>0)
			return true;
		return false;
	}
	
	public boolean hasStone(){
		if (stoneNum>0)
			return true;
		return false;
	}
	
	public boolean hasLumber(){
		if (lumberNum>0)
			return true;
		return false;
	}
	
	public boolean hasFood(){
		if (foodNum>0)
			return true;
		return false;
	}
	//***********************
	public int takeGold(int capacity){
		if (goldNum>capacity){
			goldNum-=capacity;
			return capacity;
		}
		int borrowed = goldNum;
		goldNum=0;
		return borrowed;
	}
	
	public int takeStone(int capacity){
		if (stoneNum>capacity){
			stoneNum-=capacity;
			return capacity;
		}
		int borrowed = stoneNum;
		stoneNum=0;
		return borrowed;
	}
	
	public int takeLumber(int capacity){
		if (lumberNum>capacity){
			lumberNum-=capacity;
			return capacity;
		}
		
		int borrowed = lumberNum;
		lumberNum=0;
		return borrowed;
	}
	public int takeFood(int capacity){
		if (foodNum>capacity){
			foodNum-=capacity;
			return capacity;
		}
		int borrowed = foodNum;
		foodNum=0;
		return borrowed;
	}
	public int goldNum(){
		return goldNum;
	}
	public int stoneNum(){
		return stoneNum;
	}
	public int lumberNum(){
		return lumberNum;
	}
	public int foodNum(){
		return foodNum;
	}
}
