import processing.core.PImage;

import java.util.List;

public class Mining extends Actor implements Action_manager
{

    private int resource_limit;
    private int resource_count;
    public Mining(String name, int resource_limit,Point position, int rate,List<PImage> imgs)
    {
        super(name,position,rate,imgs);
        this.resource_limit=resource_limit;
        this.resource_count=0;
    }

    public int get_resource_limit()
    {
        return this.resource_limit;
    }

    public int get_resource_count()
    {
        return this.resource_count;
    }
    public void set_resource_count(int n)
    {
        this.resource_count=n;
    }
}
