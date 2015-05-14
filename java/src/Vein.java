import processing.core.PImage;

import java.util.List;

public class Vein extends Actor
{
    private static final int RESOURCE_DISTANCE = 1;
    private int resource_distance; //q
    public Vein(String name, int rate, Point position,List<PImage> imgs,int resource_distance)
    {
        super(name,position,rate,imgs);
        this.resource_distance = resource_distance;
    }
    public Vein(String name, int rate, Point position)
    {
        this(name,position,rate,imgs,RESOURCE_DISTANCE);


    }
    public int get_resource_distance()
    {
        return this.resource_distance; //q was RESOURCE_DISTANCE
    }
    public String entity_string()
    {
       return "vein" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate() + " " + this.get_resource_distance();
    }
}
