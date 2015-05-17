/**
 * Created by Nicholas on 5/10/2015.
 */
import processing.core.PApplet;
import processing.core.PImage;

import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scan extends PApplet
{
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 808080 wrong; //q this is wrong
    private static final int FILE_IDX = 0;
    private static final int MIN_ARGS = 1;

    private static final String MINER_KEY = "miner";
    private static final String BGND_KEY = "background";
    private static final String OBSTACLE_KEY = "obstacle";
    private static final String ORE_KEY = "ore";
    private static final String SMITH_KEY = "blacksmith";
    private static final String VEIN_KEY = "vein";
    private static final String BLOB_KEY = "blob";
    private static final String QUAKE_KEY = "quake";
    private static final String GRASS_KEY = "grass";
    private static final String ROCKS_KEY = "rocks";
    private WorldModel world;

    private List<PImage> miner_imgs = new ArrayList<PImage>();
    private List<PImage> blacksmith_imgs = new ArrayList<PImage>();
    private List<PImage> rocks_imgs = new ArrayList<PImage>();
    private List<PImage> grass_imgs = new ArrayList<PImage>();
    private List<PImage> ore_imgs = new ArrayList<PImage>();
    private List<PImage> vein_imgs = new ArrayList<PImage>();
    private List<PImage> obstacle_imgs = new ArrayList<PImage>();
    private List<PImage> blob_imgs = new ArrayList<PImage>();
    private List<PImage> quake_imgs = new ArrayList<PImage>();

    private static boolean verifyArguments(String [] args)
    {
        return args.length >= MIN_ARGS;
    }

    public void get_imgs(Scanner in) {
        while (in.hasNextLine())
        {

            String[] amige = in.nextLine().split("\\s"); // amige is image lolz
            String the_key = amige[0];
            PImage file_location = loadImage(amige[1]);

            if (amige[2] != null)
            {
                int r= Integer.parseInt(amige[2]);
                int g= Integer.parseInt(amige[3]);
                int b= Integer.parseInt(amige[4]);
                int a= Integer.parseInt(amige[5]);
            }




            switch (the_key)
            {
                case MINER_KEY:
                {
                    miner_imgs.add(file_location);
                    break;
                }
                case GRASS_KEY:
                {
                    grass_imgs.add(file_location);
                    break;
                }
                case ROCKS_KEY:
                {
                    rocks_imgs.add(file_location);
                    break;
                }
                case ORE_KEY:
                {
                    ore_imgs.add(file_location);
                    break;
                }
                case VEIN_KEY:
                {
                    vein_imgs.add(file_location);
                    break;
                }
                case SMITH_KEY:
                {
                    blacksmith_imgs.add(file_location);
                    break;
                }
                case QUAKE_KEY:
                {
                    quake_imgs.add(file_location);
                    break;
                }
                case BLOB_KEY:
                {
                    blob_imgs.add(file_location);
                    break;
                }




            }
        }
    }
    public static void create_ents(Scanner in)
    {

        while (in.hasNextLine())
        {
            //int i =0;
            String [] entity = in.nextLine().split("\\s"); // split on white space "the fox ran" = ["the","fox","ran"]
            String class_type = entity[0];
            String ent_name = entity[1];
            int x_cord = Integer.parseInt(entity[2]);
            int y_cord = Integer.parseInt(entity[3]);
            Point ent_p = new Point(x_cord,y_cord);
            //i=3;
            if (entity[4] != null)
            {
                int resource_lim = Integer.parseInt(entity[4]);
                //i =4;
                if (entity[5] != null)
                {
                    String the_rate = entity[5];
                    //i = 5;
                    if (entity[6] != null)
                    {
                        String reach = entity[6];
                        //i=6;
                    }
                }
            }


            switch(class_type)
            {
                case MINER_KEY:
                {
                    Miner miner = new MinerNotFull(ent_name,ent_p,resource_lim,the_rate,reach,miner_imgs);
                    Utility.schedule_miner(world,miner,(long)(the_rate),miner_imgs);
                    break;
                }
                case BGND_KEY:
                {
                    Background background = new Background(ent_name,ent_p, bgnd_imgs);
                    world.set_background(ent_p,background); // why cant we make world work
                    break;
                }
                case OBSTACLE_KEY:
                {
                    Obstacle obastacle = new Obstacle(ent_name,ent_p,obstacle_imgs);
                    break;
                }
                case ORE_KEY:
                {
                    Ore ore = new Ore(ent_name,ent_p,ore_imgs);
                    Utility.schedule_ore(world,ore,ticks,ore_imgs);
                    break;
                }
                case SMITH_KEY:
                {
                    Blacksmith blacksmith = new Blacksmith(ent_name,ent_p,blacksmith_imgs,resource_lim,the_rate);
                    break;
                }
                case VEIN_KEY:
                {
                    Vein vein = new Vein(ent_name,the_rate,ent_p,vein_imgs,resource_lim);
                    break;
                }

            }

        }


    }

   /* public static void main(String [] args)
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
    }*/





    /* private static boolean verifyArgs(String [] args)
    {
        return args.length >= MIN_ARGS;
    } */

    /*
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

    public get_images(img images, int key) {
        if (key in images)
        {
            return images[keys];
        }
        else
        {
            return images[DEFAULT_IMAGE_NAME];
        }
    }
*/
}
