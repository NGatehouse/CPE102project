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
}
