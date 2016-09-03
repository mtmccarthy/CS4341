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
        //int startingValue; //the starting value for the program
        double startingValue;
        //double time; //the amount of time the program has to solve
        double time;
        //String[] operators; //Operators for the program to process
        LinkedList<String> operators;


        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            Object[] inputArray = stream.toArray();

            type = inputArray[0].toString();//First line in the array is the type
            startingValue = Double.parseDouble(inputArray[1].toString());//Second line is the starting value
            time = Double.parseDouble(inputArray[2].toString()); //Third line is the time
            operators = new LinkedList<String>();
            for(int i = 3; i < inputArray.length; i++){
                operators.add(i-3, inputArray[i].toString());
            }

            System.out.println(type);
            System.out.println(startingValue);
            System.out.println(time);
            for(String x : operators){
                System.out.println(x);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
