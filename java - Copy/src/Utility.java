import processing.core.PImage;

import java.util.List;
import java.util.Random;

/**
 * Created by Nicholas on 5/4/2015.
 */
public class Utility
{

    //private static final int BLOB_RATE_SCALE = 4;
    private static final int BLOB_ANIMATION_RATE_SCALE = 50;
    private static final int BLOB_ANIMATION_MIN = 1;
    private static final int BLOB_ANIMATION_MAX = 3;

    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;

    private static final int QUAKE_STEPS = 10;
    private static final int QUAKE_DURATION = 1100;
    private static final int QUAKE_ANIMATION_RATE = 100;

    private static final int VEIN_SPAWN_DELAY = 500; //may noy used
    private static final int VEIN_RATE_MIN = 8000;
    private static final int VEIN_RATE_MAX = 17000;

    public static long ticks = System.currentTimeMillis();

    public static int randInt(int min, int max)
    {
        Random rand = new Random();
        return rand.nextInt((max - min)+1)+min;
    }

    public static int sign(int x)
    {
        if(x<0)
        {
            return -1;
        }
        else if(x>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }

    public static boolean adjacent(Point p1, Point p2)
    {
        return ((p1.get_x() == p2.get_x() && Math.abs(p1.get_y()-p2.get_y()) == 1) || (p1.get_y()==p2.get_y() && Math.abs(p1.get_x()-p2.get_x())==1) );
    }

    public static double distance_sq(Point p1, Point p2)   //static method
    {
        return Math.pow((p1.get_x() - p2.get_y()),2) + Math.pow((p1.get_y() - p2.get_y()),2);
    }

    public static On_Grid nearest_entity(List<On_Grid> entList, List<Double> distsList)  //static method
    {
        if (entList.size() > 0)
        {
            double dists = distsList.get(0);
            int i=0;
            for (double other : distsList)
            {
                if (other < dists)
                {
                    dists = other;
                }
                i++;
            }
            return entList.get(i-1);
        }
        else
        {
            return null;
        }
    }

    public static OreBlob create_blob(WorldModel world, String name, Point pt, int rate, int ticks, List<PImage> imgs)
    {
        OreBlob blob = new OreBlob(name,pt,rate,imgs,randInt(BLOB_ANIMATION_MIN, BLOB_ANIMATION_MAX)*BLOB_ANIMATION_RATE_SCALE);
        schedule_blob(world,blob,ticks,imgs);
        return blob;
    }
    public static void schedule_blob(WorldModel world, OreBlob blob, long ticks, List<PImage> imgs)
    {
        blob.schedule_action(world,blob.create_actor_motion(world,imgs),ticks+blob.get_rate());
        blob.schedule_animation(world);
    }
    public static void schedule_miner(WorldModel world, Miner miner, long ticks, List<PImage> imgs)
    {
        miner.schedule_action( world, miner.create_miner_action(world, imgs),
                ticks + miner.get_rate());
        miner.schedule_animation(world);
    }
    public static Ore create_ore(WorldModel world, String name, Point point, long ticks, List<PImage> imgs)
    {
        Ore ore = new Ore(name, point,
                randInt(ORE_CORRUPT_MIN, ORE_CORRUPT_MAX),imgs);
        schedule_ore(world,ore, ticks, imgs);
        return ore;
    }
    public static void schedule_ore(WorldModel world, Ore ore, long ticks, List<PImage> imgs)
    {
        ore.schedule_action(world,ore.create_ore_transform_action(world, imgs),
                ticks + ore.get_rate());
    }
    public static Quake create_quake(WorldModel world, Point pt, long ticks, List<PImage> imgs)
    {
        Quake quake = new Quake("quake", pt,
                imgs, QUAKE_ANIMATION_RATE);
        schedule_quake(world, quake, ticks);
        return quake;
    }
    public static void schedule_quake(WorldModel world, Quake quake, long ticks)
    {
        quake.schedule_animation(world, QUAKE_STEPS);
        quake.schedule_action(world, quake.create_entity_death_action(world),
                ticks + QUAKE_DURATION);
    }
    public static Vein create_vein(WorldModel world,String name , Point pt, long ticks, List<PImage> imgs)
    {
        Vein vein = new Vein("vein" + name,
                randInt(VEIN_RATE_MIN, VEIN_RATE_MAX),
                pt, imgs);
        return vein;
    }
    public static void schedule_vein(WorldModel world, Vein vein, long ticks, List<PImage> amiges)
    {
        vein.schedule_action(world, vein.create_actor_motion(world, amiges),
                ticks + vein.get_rate());
    }


}
