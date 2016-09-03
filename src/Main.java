import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;

/**
 * Created by Matt McCarthy on 9/2/16.
 */
public class Main {

    public static void main(String[] args){
        //We assume that the program is given a single arguement, the name of the file containing the rest
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

            AISearch search = new AISearch(type, startingValue, targetValue, time, operators);
            LinkedList<String> operatorPath = search.execute();
            AISearch.displayPath(startingValue, operatorPath);
            
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
