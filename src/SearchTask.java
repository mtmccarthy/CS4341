import java.util.TimerTask;

import exception.OperatorNotSupportedException;

public class SearchTask extends TimerTask {
	
	public SearchTask(AISearch s)
	{
		this.search = s;
		search.setTimerTask(this);
	}

	private AISearch search;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		try {
			AISearch.displayPath(search.startingValue,search.path);
		} catch (OperatorNotSupportedException | NullPointerException e) {
			
			System.out.println("Search did not complete in time");
			// TODO Auto-generated catch block
		}
		
		this.cancel();
		
        /*
         * Failure Case: Need to output
         * Error
         * Search require
         * Nodes expanded:
         * Maximum search depth:
         */
		
		System.exit(0);
		

	}

}
