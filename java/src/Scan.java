import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.io.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Scan extends PApplet
{
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    private static final int DEFAULT_IMAGE_COLOR = 808080; //q this is wrong
    private static final int COLOR_MASK = 0xffffff;
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


    public static List<PImage> miner_imgs = new ArrayList<PImage>();
    private static List<PImage> blacksmith_imgs = new ArrayList<PImage>();
    private static List<PImage> rocks_imgs = new ArrayList<PImage>();
    private static List<PImage> grass_imgs = new ArrayList<PImage>();
    private static List<PImage> ore_imgs = new ArrayList<PImage>();
    private static List<PImage> vein_imgs = new ArrayList<PImage>();
    private static List<PImage> obstacle_imgs = new ArrayList<PImage>();
    private static List<PImage> blob_imgs = new ArrayList<PImage>();
    private static List<PImage> quake_imgs = new ArrayList<PImage>();
    private static List<PImage> bgnd_imgs = new ArrayList<PImage>();
    private static PApplet paplet = new PApplet();

    public static List<PImage> get_miner_images()
    {
        return miner_imgs;
    }
    public static List<PImage> get_blacksmith_images()
    {
        return blacksmith_imgs;
    }
    public static List<PImage> get_rocks_images()
    {
        return rocks_imgs;
    }
    public static List<PImage> get_grass_images()
    {
        return grass_imgs;
    }
    public static List<PImage> get_ore_images()
    {
        return ore_imgs;
    }
    public static List<PImage> get_vein_images()
    {
        return vein_imgs;
    }
    public static List<PImage> get_obastacle_images()
    {
        return obstacle_imgs;
    }
    public static List<PImage> get_blob_images()
    {
        return blob_imgs;
    }
    public static List<PImage> get_quake_images()
    {
        return quake_imgs;
    }
    public static List<PImage> get_bgnd_images()
    {
        return bgnd_imgs;
    }



    // Called with color for which alpha should be set and alpha value1.
    //PImage img = setAlpha(loadImage("wyvern1.bmp"), color(255, 255, 255), 0));
    private static PImage setAlpha(PImage img, int maskColor, int alpha)
    {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha)
            {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
        return img;
    }

    private static void add_to_occupancy(WorldModel world,On_Grid ent)
    {
        if (ent != null)
        {
            world.add_entity(ent);
        }
    }

    private static boolean verifyArguments(String [] args)
    {
        return args.length >= MIN_ARGS;
    }



    public static void get_imgs(Scanner in) {
        while (in.hasNextLine())
        {

            String[] amige = in.nextLine().split("\\s"); // amige is image lolz
            String the_key = amige[0];
            //System.out.println(amige[0]);
            int r = DEFAULT_IMAGE_COLOR;
            int g = DEFAULT_IMAGE_COLOR;
            int b = DEFAULT_IMAGE_COLOR;
            int a = DEFAULT_IMAGE_COLOR;

            PImage file_location = paplet.loadImage(amige[1]);

            if (amige.length > 2)
            {
                r = Integer.parseInt(amige[2]);
                g = Integer.parseInt(amige[3]);
                b = Integer.parseInt(amige[4]);
                a = Integer.parseInt(amige[5]);
                int rgb = paplet.color(r,g,b); //Keen, I tried passing as second param but needs int
                file_location = setAlpha(paplet.loadImage(amige[1]), rgb, a);
                //Keen: says color can't be referenced from a static context
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
                case DEFAULT_IMAGE_NAME:
                {
                  bgnd_imgs.add(file_location);
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
                case OBSTACLE_KEY:
                {
                    obstacle_imgs.add(file_location);
                    break;
                }
            }
        }
    }


    public static void create_ents(Scanner in,WorldModel world) // pass in a worldmodel?
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
            int resource_lim = -1;
            int the_rate = -1;
            int reach = -1;
            //i=3;
            //System.out.println("----" + entity.length);
            if (entity.length >= 5)
            {
                resource_lim = Integer.parseInt(entity[4]);
                //System.out.println("--------" );
                //i =4;
                if (entity.length >= 6)
                {
                     the_rate = Integer.parseInt(entity[5]);
                    //System.out.println("--------" +the_rate);
                    //i = 5;
                    if (entity.length >= 7)
                    {
                         reach = Integer.parseInt(entity[6]);
                        //System.out.println("--" + reach);
                        //i=6;
                    }
                }
            }

            switch(class_type) //q gotta be more we have to do here..
            {
                case MINER_KEY:
                {
                    Miner miner = new MinerNotFull(ent_name,resource_lim,ent_p,the_rate,miner_imgs,reach);
                    add_to_occupancy(world,miner);
                    Utility.schedule_miner(world,miner,Utility.ticks,miner_imgs);
                    break;
                }
                case BGND_KEY:
                {
                    switch(ent_name)
                    {
                        case "grass":
                        {
                            Background background = new Background(ent_name,ent_p, grass_imgs);
                            world.set_background(ent_p,background); // why cant we make world work
                            break;
                        }
                        case "rocks": {
                            Background background = new Background(ent_name,ent_p, rocks_imgs);
                            world.set_background(ent_p,background); // why cant we make world work
                            break;
                        }
                    }

                    break;
                }
                case OBSTACLE_KEY:
                {
                    Obstacle obstacle = new Obstacle(ent_name,ent_p,obstacle_imgs);
                    add_to_occupancy(world,obstacle);
                    break;
                }
                case ORE_KEY:
                {
                    Ore ore = new Ore(ent_name, ent_p, the_rate, ore_imgs);
                    Utility.schedule_ore(world,ore,the_rate,blob_imgs);
                    add_to_occupancy(world, ore);

                    break;
                }
                case SMITH_KEY:
                {
                    Blacksmith blacksmith = new Blacksmith(ent_name,ent_p,blacksmith_imgs,resource_lim,the_rate);
                    add_to_occupancy(world,blacksmith);
                    break;
                }
                case VEIN_KEY:
                {
                    Vein vein = Utility.create_vein(world, ent_name, ent_p, Utility.ticks, vein_imgs);
                    add_to_occupancy(world,vein);
                    Utility.schedule_vein(world, vein, Utility.ticks,ore_imgs);
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
