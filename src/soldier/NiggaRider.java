package soldier;

import game.Board;

public class NiggaRider extends Soldier{
	
	public static final int staticPointOnInfantry = 30;
	public static final int staticPointOnHorseman = 50;
	public static final int staticPointOnDefence = 10;
	public static final int staticPointOnAttack = 60;
	public static final int staticHealth = 300;
	
	public static int niggasNum;
	
	public NiggaRider(int row, int col, boolean[][] isDiscovered, int ceId) {
		super(row, col, isDiscovered, ceId, staticHealth, staticPointOnInfantry, staticPointOnHorseman,
				staticPointOnAttack, staticPointOnDefence);
		niggasNum+=1;
		id="sol_nigga_"+niggasNum+"_"+ceId;
		Board.map[row][col].setObjOnBlock(id);
		// TODO Auto-generated constructor stub
	}

}
