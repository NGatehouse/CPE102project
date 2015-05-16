import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Nicholas on 5/16/2015.
 */
public class Scanner
{
    private static final int FILE_IDX = 0;
    private static final int MIN_ARGS = 1;

    private static boolean verifyArguments(String [] args)
    {
        return args.length >= MIN_ARGS;
    }

    private static void (java.util.Scanner in)
    {
        String name = "None";
        int age = 0;
        while (in.hasNextLine())
        {
            String [] words = in.nextLine().split("\\s"); // split on white space "the fox ran" = ["the","fox","ran"]
            int new_age = Integer.parseInt(words[1]); // goes through, finds integers and converts them to ints...
            if (age == 0 || new_age < age)//
            {
                name = words[0];
                age = new_age;
            }
        }

        if (age == 0)
        {
            System.out.println("No persons");
        }
        else
        {
            System.out.println(name + " is the youngest, age " + age);
        }
    }

    public static void main(String [] args)
    {
        try
        {
            if (verifyArguments(args)) // see's if correct args
            {
                java.util.Scanner in = new java.util.Scanner(new FileInputStream(args[FILE_IDX])); // pass it the fiile we want from terminal
                findYoungest(in); // calls previous
            }
            else
            {
                System.err.println("missing filename");
            }
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }

}
