import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class On_Grid extends Entity
{
    private Point position;


    public On_Grid(String name, Point position,List<PImage> imgs)
    {
        super(name,imgs);
        this.position = position;
    }

    public Point get_position()
    {
        return this.position;
    }
    public void set_position(Point p)
    {
        this.position = p;
    }

}
