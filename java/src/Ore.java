import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
public class Ore extends Actor
{

    public Ore(String name,Point position,int rate,List<PImage> imgs)
    {
        super(name,position,rate,imgs);  //q make rate default to 5000
    }

    public Ore(String name,Point position,List<PImage> imgs)
    {
        this(name,position,5000,imgs);
    }

    public String entity_string()
    {
        return "ore" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate();
    }
}
