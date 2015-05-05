/**
 * Created by Nicholas on 5/4/2015.
 */
public class Utility
{
    private int x;
    public int sign(x)
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
    public boolean adjacent(Point p1,Point p2)
    {
        return ((p1.get_x() == p2.get_x() && Math.abs(p1.get_y()-p2.get_y()) == 1) || () )
    }
}
