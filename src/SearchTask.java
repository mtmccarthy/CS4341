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
		} catch (OperatorNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        /*
         * Failure Case: Need to output
         * Error
         * Search require
         * Nodes expanded:
         * Maximum search depth:
         */
		System.out.println("Search did not complete in time");
		System.exit(0);
		

	}

}
