import java.util.List;

/**
 * Created by Nicholas on 5/4/2015.
 */
public class Utility
{
    private int x;
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

    public static create_blob(WorldModel world, String name, Point pt, int rate, int ticks, type i_store)
    {}
    public static schedule_blob(WorldModel world, OreBlob blob, long ticks, type i_store)
    {}
    public static schedule_miner(WorldModel world, Miner miner, long ticks, type i_store)
    {}
    public static create_ore(WorldModel world, String name, Point point, long ticks, type i_store)
    {}
    public static schedule_ore(WorldModel world, Ore ore, long ticks, type i_store)
    {}
    public static create_quake(WorldModel world, Point pt, long ticks, type i_store)
    {}
    public static schedule_quake(WorldModel world, Quake quake, long ticks)
    {}
    public static create_vein(WorldModel world,String name , Point pt, long ticks, type i_store)
    {}
    public static schedule_vein(WorldModel world, Vein vein, long ticks, type i_store)
    {}


}
