/**
 * Created by Nicholas on 5/10/2015.
 */
import java.io.*;

import java.util.List;
import java.util.Scanner;

public class Scan
{
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 808080; //q this is wrong
    private static final int FILE_IDX = 0;
    private static final int MIN_ARGS = 1;

    private static boolean verifyArguments(String [] args)
    {
        return args.length >= MIN_ARGS;
    }

    private static void create_ents(Scanner in)
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
                Scanner in = new Scanner(new FileInputStream(args[FILE_IDX])); // pass it the fiile we want from terminal
                create_ents(in); // calls previous
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




















    /*private static boolean verifyArgs(String [] args)
    {
        return args.length >= MIN_ARGS;
    } */

    public static String getDefaultImageName()
    {
        return DEFAULT_IMAGE_NAME;
    }
    public void create_default_image(int tile_width,int tile_height)
    {
        type surf = // pygame stuff;
                surf.fill(DEFAULT_IMAGE_COLOR);
    }

    public void load_images(file filename, int tile_width, int tile_height)
    {
        images = {}; // hashmap what is a hashmap?
        with as?
        // File I/O
    }

    public void process_image_line(img images, String line)
    {
        List<String> attrs = line.split();
        if (attrs.size() >= 2)
        {
            key = attrs[0];
            img = //pygame

            if (img)
            {
                imgs = get_images_internal(images,key);
                imgs.add(img);
                images[key] = imgs;

                if (attrs.size() == 6)
                {
                    int r = (int)(attrs[2]);
                    int g = (int)(attrs[2]);
                    int b = (int)(attrs[2]);
                    int a = (int)(attrs[2]);
                    img.set_colorkey(pygame.Color(r,g,b,a))
                }
            }
        }
    }
    public List get_images_internal(img images, type key)
    {
        if( key in images)
        {
            return images[key];
        }
        else
        {
            return [];
        }
    }

    public get_images(img images, int key)
    {
        if (key in images)
        {
            return images[keys];
        }
        else
        {
            return images[DEFAULT_IMAGE_NAME];
        }
    }
}
