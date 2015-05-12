import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import java.util.ArrayList;
import processing.core.*;

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
    private Point locOfBird = new Point(0,0);
    private PImage grass = loadImage("../imgs/grass.jpg");
    private PImage apple = loadImage("../imgs/apple.png");
    private PImage fire = loadImage("../imgs/fire.gif");
    private PImage done = loadImage("../imgs/done.gif");
    //  private int stepSize = 10; //del
//    public int x = frame.getX(); //del
    //   public int y = frame.getY(); //del  //or is it Frame?


    public enum Grid
    {
        BACKGROUND, OBSTACLE, GOAL
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
                else if (h==3 && w==4)
                {
                    world[h][w] = Grid.GOAL;
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
        imgs.add(loadImage("wyvern1.png"));
        imgs.add(loadImage("wyvern2.png"));
        imgs.add(loadImage("wyvern3.png"));

        current_image = 0;
        next_time = System.currentTimeMillis() + ANIMATION_TIME;
        tiles();
        System.out.println(world.length);
        System.out.println(world[0].length);

    }

    public void keyPressed()
    {

        if ( (locOfBird.get_x()/32 != 4) || (locOfBird.get_y()/32 != 3) ) {

            switch (key) {
                case 'a':
                    if ((locOfBird.get_x() > 0)
                            && world[(locOfBird.get_y() / 32)][(locOfBird.get_x() / 32) - 1] != Grid.OBSTACLE)
                    // && (world[locOfBird.get_x()/32][locOfBird.get_y()/32] != Grid.OBSTACLE))
                    {
                        locOfBird.set_x(locOfBird.get_x() - 32);
                    }
                    break;
                case 'd':
                    if ((locOfBird.get_x() < 608)
                            && world[(locOfBird.get_y() / 32)][(locOfBird.get_x() / 32) + 1] != Grid.OBSTACLE) {
                        locOfBird.set_x(locOfBird.get_x() + 32);
                    }
                    break;
                case 'w':
                    if ((locOfBird.get_y() > 0)
                            && world[(locOfBird.get_y() / 32) - 1][(locOfBird.get_x() / 32)] != Grid.OBSTACLE) {
                        locOfBird.set_y(locOfBird.get_y() - 32);
                    }
                    break;
                case 's':
                    if ((locOfBird.get_y() < 448)
                            && world[(locOfBird.get_y() / 32) + 1][(locOfBird.get_x() / 32)] != Grid.OBSTACLE) {
                        locOfBird.set_y(locOfBird.get_y() + 32);
                    }
                    break;
                default:
                    System.out.print(locOfBird.get_x() / 32);
                    System.out.print(" ");
                    System.out.println(locOfBird.get_y() / 32);
            }
        }
        else
        { image(done, 0,0); }

/*
        if (key=='a') //del
            x -= stepSize;
        else if (key=='d')
            x += stepSize;
        else if (key=='w')
            y -= stepSize;
        else if (key=='s')
            y += stepSize;
        frame.setLocation(x, y);
        */
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
                    image(fire, w*32, h*32);
                }
                else if (world[h][w] == Grid.GOAL)
                {
                    image(apple, w*32, h*32);
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
            next_image();
            next_time = time + ANIMATION_TIME;
        }
        image(imgs.get(current_image), locOfBird.get_x(), locOfBird.get_y());
    }

    public static void main(String[] args)
    {
        PApplet.main("ProcessingIntro");
    }
}

