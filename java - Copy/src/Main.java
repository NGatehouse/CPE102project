import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import java.util.List;
import java.util.ArrayList;
import processing.core.*;
import sun.security.acl.PermissionImpl;

import javax.management.BadAttributeValueExpException;

public class Main extends PApplet
{
    private static final boolean RUN_AFTER_LOAD = true;
    private static final String IMAGE_LIST_FILE = "imagelist";
    private static final String WORLD_FILE = "gaia.sav";

    private static final int SCREEN_WIDTH = 640;
    private static final int SCREEN_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;

    private List<PImage> imgs;
    private int current_image;
    private long next_time;

    private final int BGND_COLOR = color(550, 230, 245);
    private static final int ANIMATION_TIME = 100;
    private Grid[][] world = new Grid[15][20];
    private Grid[][] backworld = new Grid[30][40];
    private WorldModel worldModel = new WorldModel(30,40);
    private final static int HEIGHT = 20;
    private final static int WIDTH = 15;
    private PImage grass = loadImage("images/grass.bmp");
 //   private PImage rock = loadImage("C:\\Users\\Luke\\cpe102\\experiment\\CPE102project\\images\\rock.bmp");
    public Point origin = new Point(0,0);


/*
    public enum Grid
    {
        BACKGROUND, OBSTACLE
    }

    public void tiles()
    {
        for (int h=0; h < world.length; h++)
        {
            for (int w=0; w < world[h].length; w++)
            {
                if (((h%8==1) && (w%3==0 || w%3==2 || h==1)) && !(h==1 && w==17))
                {
                    world[h][w] = Grid.OBSTACLE;
                }
                else
                {
                    world[h][w] = Grid.BACKGROUND;
                }
            }
        }
    }
*/

    public void draw_background(WorldModel world)
    {
        int yPix = SCREEN_HEIGHT/32;
        int xPix = SCREEN_WIDTH/32;
        Point newPoint = new Point(origin.get_x(),origin.get_y());
        for (int i = 0; i < yPix; i++)
        {
            for (int j=0;j<xPix;j++)
            {
                image(world.background.get_cell(newPoint).get_image(),  newPoint.get_x()*32-origin.get_x()*32,  newPoint.get_y()*32-origin.get_y()*32);
                newPoint.addTo_x(1);
            }

            newPoint.addTo_y(1);

            newPoint.setX(origin.get_x());
        }
    }


    public void draw_occupancy(WorldModel world)
    {
        int yPix = SCREEN_HEIGHT/32;
        int xPix = SCREEN_WIDTH/32;
        Point newPoint = new Point(origin.get_x(),origin.get_y());
        for (int i=0;i<yPix;i++)
        {
            for (int j=0;j<xPix;j++)
            {
                for (On_Grid e:world.get_entities())
                {
                    if(e.get_position().get_x() == newPoint.get_x()  && e.get_position().get_y() ==newPoint.get_y() )
                    {
                        image(worldModel.occupancy.get_cell(e.get_position()).get_image(), e.get_position().get_x() * 32 - origin.get_x() * 32, e.get_position().get_y() * 32 - origin.get_y() * 32);
                    }
                    if (e.get_name() == "ore")
                    {
                        System.out.println("*******" + e.get_image());
                    }
                }
                newPoint.addTo_x(1);
            }
            newPoint.addTo_y(1);

            newPoint.setX(origin.get_x()); //ch
        }
    }

    public void setup()
    {

       // File world_file = null;
        //File image_list_file = null;

        //world_file = new File(WORLD_FILE);
        //image_list_file = new File(IMAGE_LIST_FILE);
        Scanner i_list_scanner = null;
        Scanner w_scanner = null;

        try
        {
            File world_file = new File(getClass().getClassLoader().getResource(WORLD_FILE).getFile());
            File image_list_file = new File(getClass().getClassLoader().getResource(IMAGE_LIST_FILE).getFile());
            i_list_scanner = new Scanner(image_list_file);
            w_scanner = new Scanner(world_file);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage() + " " + getClass().getClassLoader().getResource(WORLD_FILE));
        }


        Scan.get_imgs(i_list_scanner);
        Scan.create_ents(w_scanner, worldModel);
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
        background(BGND_COLOR);
        System.out.println(world.length);
        imgs = new ArrayList<PImage>();


        current_image = 0;
        next_time = System.currentTimeMillis() + ANIMATION_TIME;
      //  tiles();
        System.out.println(world.length);
        System.out.println(world[0].length);

    }

    public void keyPressed()
    {
        if (key == CODED)
        {
            if (keyCode == LEFT)
            {
                if (origin.get_x() > 0)
                {
                    origin.addTo_x(-1);
                }
            }
            if (keyCode == RIGHT)
            {
                if (origin.get_x() < 20) {
                    origin.addTo_x(1);
                }
            }
            if (keyCode == UP)
            {
                if (origin.get_y() > 0) {

                    origin.addTo_y(-1);
                }
            }
            if (keyCode == DOWN)
            {
                if (origin.get_y() < 15) {
                    origin.addTo_y(1);
                }
            }
        }
    }


    private void next_image()
    {
        current_image = (current_image + 1) % imgs.size();
    }

  /*  private void add_ents()
    {
        for (int h=0; h < world.length; h++)
        {
            for (int w=0; w < world[h].length; w++)
            {
                if (world[h][w] == Grid.OBSTACLE)
                {
                    image(rock, w*32, h*32);
                }
                else
                {
                    image(grass, w*32, h*32);
                }
            }
        }
    }
*/
    public void draw()
    {
       // add_ents();
        // A simplified action scheduling handler
       // long time = System.currentTimeMillis();
    //    image_store i_store = new image_store().load_images(IMAGE_LIST_FILE_NAME,
     //           TILE_WIDTH, TILE_HEIGHT);
        int num_cols = 40;
        int num_rows = 30;
       // image(, 0, 0);
        draw_background(worldModel);
        draw_occupancy(worldModel);

        //image(grass, 32,32);
        //num_cols = SCREEN_WIDTH // TILE_WIDTH * WORLD_WIDTH_SCALE
        //num_rows = SCREEN_HEIGHT // TILE_HEIGHT * WORLD_HEIGHT_SCALE

       // Background default_background = create_default_background(
       //         image_store.get_images(i_store, image_store.DEFAULT_IMAGE_NAME));

        //WorldModel world = new WorldModel(num_rows, num_cols, default_background)
      //  view = worldview.WorldView(SCREEN_WIDTH / TILE_WIDTH, SCREEN_HEIGHT / TILE_HEIGHT, screen, world, TILE_WIDTH, TILE_HEIGHT)
     //           load_world(world, i_store, WORLD_FILE)

     //           view.update_view()

       //         controller.activity_loop(view, world)

        long ticks = System.currentTimeMillis();
        if (ticks >= next_time)
        {
            //next_image();  //updater on ticks in project... instead of handle timer events
            worldModel.update_on_time(ticks);
            next_time = ticks + ANIMATION_TIME;
        }
    }
/*
    public Background create_default_background(PImage img)
    {
        List<PImage> imagelist = new ArrayList<>();
        imagelist.add(img);
        return new Background(image_store.getDefaultImageName(), origin, imagelist);
    }
*/
  //  public void createWorld()

    public static void main(String[] args)
    {
        PApplet.main("Main");
    }
}


