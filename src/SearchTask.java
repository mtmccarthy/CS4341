import java.util.TimerTask;

import exception.OperatorNotSupportedException;

public class SearchTask extends TimerTask {

	private AISearch search;
	
	public SearchTask(AISearch s)
	{
		this.search = s;
		search.setTimerTask(this);
	}


	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			AISearch.displayPath(search.startingValue,search.path);

		} catch (OperatorNotSupportedException | NullPointerException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
		
		this.cancel();
		
        /*
         * Failure Case: Need to output
         * Error
         * Search require
         * Nodes expanded:
         * Maximum search depth:
         */
		int errorAmt = (int) java.lang.Math.abs(search.finalVal - search.targetValue);
		//int stepsOutput = search.path.size();


		System.out.println("Search did not complete in time");
        System.out.println("Error: " + errorAmt);
        //System.out.println("Number of steps required:" + stepsOutput);
        System.out.println("Nodes Expanded: " + search.nodesExpanded);
        System.out.println("Maximum search depth: " + search.maxDepth);
		
		
		System.exit(0);
		

	}

}
