package buildings;

import game.Board;
import game.CElization;

import java.util.ArrayList;


public class University extends Building {
	final static String type = "2";
	public final static int size = 3;
	public final static int[] recourcesNeeded = { 0, 20, 20 };
	final static int turnsToBuild = 2;
	private String finished = "";
	ArrayList <String> ScholarIds = new ArrayList<String>();
	
	private int researchLimit=1;
	private int isResearching=0;
	private int turnsLeftToResearch;
	private int researchName;
	
	private int trainLimit = 1;
	private int isTraining = 0;
	private int turnsToTrain = 6;
	private int turnsLeftToTrain;
	public int scholarsInside = 0;
	private int scholarLimit = 1;
	private static int scholarsNum;
	private static int typeNum;

	public University(int row, int col,int ceId) {
		super(row, col, size, type, turnsToBuild, recourcesNeeded,ceId);
		typeNum += 1;
		id = "building_2_" + typeNum;
		placeOnBlocks();
	}

	public boolean isBusy() {
		if (isTraining < trainLimit)
			return false;
		return true;
	}
	
	public boolean isBusyForResearch() {
		if (isResearching < researchLimit)
			return false;
		return true;
	}

	public boolean isFull() {
		if (scholarsInside < scholarLimit)
			return false;
		return true;
	}

	public void train() {
		isTraining += 1;
		turnsLeftToTrain = turnsToTrain;
	}
	
	public boolean hasScholar(String scholarId){
		for(String schId : ScholarIds)
			if (schId.equals(scholarId))
				return true;
		return false;
	}
	
	public void research(int research){
		isResearching+=1;
		researchName=research;
		turnsLeftToResearch=CElization.researchInfo[research-1][4];
		
	}
	
	public ArrayList <String> getScholarIds(){
		return ScholarIds;
	}
	
	public String scholarsIdGenerator(){
		String ret="";
		for (String string : ScholarIds)
			ret+=" "+string;
		if (ret.length() == 0)
			return ret;
		return ret.substring(1);
	}

	
	
	
	
	
	
	
	
	public ArrayList<String> updateWithReturn() {
		ArrayList<String> ret = new ArrayList<String>();
	
		
		if (0 < turnsLeftToTrain) {
			turnsLeftToTrain -= 1;
			if (turnsLeftToTrain == 0){
				scholarsInside += 1;
				scholarsNum+=1;
				ScholarIds.add("scholar_"+scholarsNum+"-"+id);
				ret.add("scholar trained scholar_"+scholarsNum+"-"+id);
			}
		}
		if (0<isResearching){
			turnsLeftToResearch -=1;
			if (turnsLeftToResearch==0){
				isResearching-=1;
				finished+=" "+researchName;
				Board.derpCElization.addResearch(researchName);
				ret.add("research finished "+researchName);
			}
		}
		
		Board.derpCElization.addKnowledge(scholarsInside);
		return ret;
	}
	
	
	
	
	
	
	
	
	public void update() {
		
	}
	
	public String cheatTrainScholar(){
		scholarsInside += 1;
		scholarsNum+=1;
		ScholarIds.add("scholar_"+scholarsNum+"-"+id);
		return "scholar_"+scholarsNum+"-"+id;
	}

	public int getAvgRecourceUnder() {
		return 0;
	}
	
	
	/*
	 * for judge info method
	 */
	public String scholarLimit(){
		if (scholarLimit==-1)
			return "inf";
		return ""+scholarLimit;
	}
	
	public String researchInProccess(){
		if (isResearching==0)
			return "no researches in process";
		return ""+researchName;
	}

	public String finished(){
		if (finished.length()==0)
			return finished;
		return finished.substring(0);
	}
}
