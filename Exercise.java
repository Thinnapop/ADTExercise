import java.io.*;
import java.util.*;

class studentClass {
    private String firstName;
    private String lastName;
    private String SID;

    public studentClass(String sid, String firstName, String lastName) {
        this.SID = sid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSID() {
        return SID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return SID + ": " + firstName + " " + lastName;
    }
}

public class Exercise {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the filename: ");
        String fileName = input.nextLine();
        
        Vector<studentClass> students = fileReading(fileName);
        userInteraction(students);
    }

    public static Vector<studentClass> fileReading(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        Scanner readFile = new Scanner(f);

        for (int i = 0; i < 7; i++) {
            if (readFile.hasNextLine()) {
                readFile.nextLine();
            }
        }

        Vector<studentClass> studentInfo = new Vector<>();

        while (readFile.hasNextLine()) {
            String dataLine = readFile.nextLine();
            StringTokenizer studentToken = new StringTokenizer(dataLine.trim(), ",");
            studentToken.nextToken();  
            studentInfo.addElement(new studentClass(
                studentToken.nextToken().trim(),
                studentToken.nextToken().trim(),
                studentToken.nextToken().trim()
            ));
        }
        return studentInfo;
    }

    public static void userInteraction(Vector<studentClass> students) {
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter your command (-n, -f, -l, -s): ");
        String userInput = input.nextLine();

        StringTokenizer userInputs = new StringTokenizer(userInput.trim(), " ");
        String choice = userInputs.nextToken();
        String searchFN = "";
        
        if (choice.equals("-s") && userInputs.hasMoreTokens()) {
            searchFN = userInputs.nextToken();
        }

        switch (choice.toLowerCase()) {
            case "-n":
                Sort(students, "SID");
                break;
            case "-f":
                Sort(students, "firstName");
                break;
            case "-l":
                Sort(students, "lastName");
                break;
            case "-s":
                int searchResult = linearSearch(students, searchFN);
                if (searchResult != -1) {
                    System.out.println("The name " + searchFN + " was found at index " + searchResult);
                } else {
                    System.out.println("The name " + searchFN + " is either spelled incorrectly or not in the database.");
                }
                break;
                default: System.out.println("Invalid input! Please try again.");
        }
    }

    public static void Sort(Vector<studentClass> vector, String sortBy) {
        int boundary = vector.size() - 1;
        boolean sorted = false;

        while (boundary > 0 && !sorted) {
            sorted = true;
            for (int i = 0; i < boundary; i++) {
                boolean swap = false;

                switch (sortBy) {
                    case "SID":
                        swap = vector.elementAt(i).getSID().compareTo(vector.elementAt(i + 1).getSID()) > 0;
                        break;
                    case "firstName":
                        swap = vector.elementAt(i).getFirstName().compareTo(vector.elementAt(i + 1).getFirstName()) > 0;
                        break;
                    case "lastName":
                        swap = vector.elementAt(i).getLastName().compareTo(vector.elementAt(i + 1).getLastName()) > 0;
                        break;
                }

                if (swap) {
                    studentClass temp = vector.elementAt(i);
                    vector.set(i, vector.elementAt(i + 1));
                    vector.set(i + 1, temp);
                    sorted = false;
                }
            }
            boundary--;
        }

        for (studentClass student : vector) {
            System.out.println(student);
        }
    }

    public static int linearSearch(Vector<studentClass> students, String name) {
        for (int i = 0; i < students.size(); i++) {
            if (students.elementAt(i).getFirstName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }
}
