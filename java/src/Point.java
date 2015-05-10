/**
 * Created by Nicholas on 4/27/2015.
 */
public class Point
{
    private int x;
    private int y;
    public Point(int x,int y)
    {
        this.x = x;
        this.y = y;
    }
    public int get_x()
    {
        return this.x;
    }
    public int get_y()
    {
        return this.y;
    }

    public boolean equals(Point compPoint)
    {
        if ((this.get_x() == compPoint.get_x()) && (this.get_y() == compPoint.get_y()))
        {
            return true;
        }
        else { return false; }
    }
}
