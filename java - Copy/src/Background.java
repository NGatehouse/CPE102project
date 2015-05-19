import processing.core.PImage;

import java.util.List;

public class Background extends On_Grid
{
    public Background(String name, Point position, List<PImage> imgs)
    {
        super(name,position,imgs); //q shouldn't have a position but needs to inherit from On_Grid for luke
    }

}
