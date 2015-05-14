import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.ArrayList;
import processing.core.*;
//TODO GET CODE FROM MAIN.PY
public class Main extends PApplet
{
    private List<PImage> imgs;
    private int current_image;
    private long next_time;

    private final int BGND_COLOR = color(550, 230, 245);
    private static final int ANIMATION_TIME = 100;
    private Grid[][] world = new Grid[15][20];
    private final static int HEIGHT = 20;
    private final static int WIDTH = 15;
    private PImage grass = loadImage("C:\\Users\\Luke\\cpe102\\experiment\\CPE102project\\images\\grass.bmp");
    private PImage rock = loadImage("C:\\Users\\Luke\\cpe102\\experiment\\CPE102project\\images\\rock.bmp"); // Need to make images relative so Nick and Luke can use them
    public Point origin = new Point(0,0);

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


    public void setup()
    {
        size(640, 480);
        background(BGND_COLOR);
        System.out.println(world.length);
        imgs = new ArrayList<PImage>();

        current_image = 0;
        next_time = System.currentTimeMillis() + ANIMATION_TIME;
        tiles();
        System.out.println(world.length);
        System.out.println(world[0].length);

    }

    public void keyPressed()
    {
        if (key == CODED)
        {
            if (keyCode == LEFT)
            {
                origin.addTo_x(-1);
            }
            if (keyCode == RIGHT)
            {
                origin.addTo_x(1);
            }
            if (keyCode == UP)
            {
                origin.addTo_y(1);
            }
            if (keyCode == DOWN)
            {
                origin.addTo_y(-1);
            }
        }
    }


    private void next_image()
    {
        current_image = (current_image + 1) % imgs.size();
    }

    private void add_ents()
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

    public void draw()
    {
        add_ents();
        // A simplified action scheduling handler
        long time = System.currentTimeMillis();
        if (time >= next_time)
        {
            next_image();  //updater on ticks in project... instead of handle timer events
            next_time = time + ANIMATION_TIME;
        }
    }

    public static void main(String[] args)
    {
        PApplet.main("Main");
    }
}

