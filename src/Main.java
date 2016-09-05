import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;

import exception.OperatorNotSupportedException;
import exception.SearchTypeNotSupportedException;


/**
 * Created by Matt McCarthy on 9/2/16. ReInvented by Ben Bianchi
 */
public class Main {

    public static void main(String[] args){
        //We assume that the program is given a single argument, the name of the file containing the rest
        //of the information about the assignment.
        String fileName = args[0];

        String type; //iterative or deepening
        double startingValue; //Starting value for the search
        double targetValue; //Target value for the search
        double time; //the amount of time the program has to solve
        LinkedList<String> operators; //Operators for the program to process


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Object[] inputArray = stream.toArray();

            type = inputArray[0].toString();//First line in the array is the type
            startingValue = Double.parseDouble(inputArray[1].toString());//Second line is the starting value
            targetValue = Double.parseDouble(inputArray[2].toString());
            time = Double.parseDouble(inputArray[3].toString()); //Third line is the time
            operators = new LinkedList<String>();
            
            for(int i = 4; i < inputArray.length; i++){
                operators.add(i - 4, inputArray[i].toString());
            }
            
            //Populate the search, dont run the actual search yet.
            AISearch search = new AISearch(type, startingValue, targetValue, operators);
            
            //Set a timer that will stop the program if it goes over the time limit. THIS IS ASYNC!
            Timer t = new Timer();
            SearchTask st = new SearchTask(search);
            
            //Execute the Search and start the clock.
            t.schedule(st,0, (long) (time * 1000)); 
            search.path = search.execute();
            
            
            

            
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (SearchTypeNotSupportedException e) {
            e.printStackTrace();
        }
        catch(OperatorNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
