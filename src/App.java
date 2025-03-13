import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

// menu mode
enum M {
    MAIN,
    GEN,
    SORT
}
static M mode = M.MAIN;

// The main function will handle a user's keyboard input specified in the next session
public static void main(String[] args) {
    boolean running = true;
    Scanner scn = new Scanner(System.in);

    while (running) {
        switch (mode) {
            case MAIN:
                System.out.println("Type one of the following to run a command:");
                System.out.println("gen   |   generate an array and store to file");
                System.out.println("sort  |   load an array from file and sort. store to new file");
                System.out.println("exit  |   stop program");
                {
                    String inp = scn.nextLine();
                    if (inp.equals("gen")) mode = M.GEN;
                    else if (inp.equals("sort")) mode = M.SORT;
                    else if (inp.equals("exit")) running = false;
                    else {
                        System.out.println("Sorry, try again.");
                    }
                }

            break;
            // (1) Generate an array of random integers and store it in a file
            case GEN:
                {
                    System.out.println("How long of an array to generate?");
                    int arrlen = Integer.parseInt(scn.nextLine());
                    System.out.println("What file would you like to store to?");
                    String storeFile = scn.nextLine();
                    int[] arr = createRandomArray(arrlen);
                    writeArrayToFile(arr, storeFile);
                }
                // return to main
                mode = M.MAIN;
            break;
            // (2) Read an existing file containing a list of integers (one integer per line), sort it and store the sorted array in another file.
            case SORT:
                {
                    System.out.println("What file would you like to read from?");
                    String readFilename = scn.nextLine();
                    System.out.println("What file would you like to store to?");
                    String writeFilename = scn.nextLine();

                    int[] arr = readFileToArray(readFilename);
                    bubbleSort(arr);
                    writeArrayToFile(arr, writeFilename);
                }
                // return to main
                mode = M.MAIN;
            break;
            // Something weird happened. go back to main
            default:
                mode = M.MAIN;
            break;
        }
    }
    scn.close();
}


// Given arrayLength as argument, create an array of random integers between 0 and 100; return the created array.
public static int[] createRandomArray(int arrayLength) {
    Random r = new Random();
    // arrayLength++; // add one so we actually reach x numbers
    int[] array = new int[arrayLength];
    for (int i = 0; i < arrayLength; i++) {
        array[i] = r.nextInt(100);
    }
    return array;
}
 

// Given an integer array and filename, write the array to the file, with each line containing one integer in the array.
public static void writeArrayToFile(int[] array, String filename) {
    if (array.length == 0) {
        System.out.println("There was an error writing to file as array does not exist");
        return;
    }
    BufferedWriter writer;
    try {
        writer = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < array.length; i++) {
            writer.write(array[i] + "\n");
        }
        writer.close();

        System.out.println("Successfully wrote to " + filename);
    } catch (IOException e) {
        System.out.println("There was an error writing to the file");
        return;
    }
}

 

// This is the reverse of writeArrayToFile; Given the filename, read the integers (one line per integer) to an array, and return the array
public static int[] readFileToArray(String filename) {
    BufferedReader reader;
    String fileText = "";
    try {
        // open reader
        reader = new BufferedReader(new FileReader(filename));
        String line;
        // add all lines to a single string
        while (true) {
            line = reader.readLine();
            if (line == null || line == "") {
                break;
            }
            fileText += line + "\n";
            
        }
        reader.close();
        // Split file into array of lines
        String[] lines = fileText.split("\n");
        int[] array = new int[lines.length];
        
        if (lines.length == 0)
        
        // Turn array of lines into array of integers!
        for (int i = 0; i < lines.length; i++) {
            array[i] = Integer.parseInt(lines[i]);
        }
        
        System.out.println("Successfully read from " + filename);
        return array;
    } catch (IOException e) {
        // e.printStackTrace();
        System.out.println("There was an error reading the file");
        return new int[0];
    }
}
    
// Given an integer array, sort it in-place, i.e., the order of the elements will be changed so that the final array is in sorted order.
public static void bubbleSort(int[] arr) {
    boolean swapped = false;
    // run this. then keep looping as long as we keep swapping numbers
    do {
        swapped = false;
        for (int i = 0; i < arr.length -1; i++) {
            if (arr[i] > arr[i+1]) {
                // swap i and i+1
                int x = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = x;
    
                swapped = true;
            }
        }
    } while (swapped);
    System.out.println("Successfully sorted array");
}
