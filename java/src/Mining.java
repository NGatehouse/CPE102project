public class Mining extends Actor
{
    private int resource_limit;
    private Point position;
    public Mining(String name, int resource_limit,Point position, int rate)
    {
        super(name,position,rate);
        this.resource_limit=resource_limit;
        //what do you do for constants?
    }

    public int get_resource_limit()
    {
        return this.resource_limit;
    }
    /*public int get_resource_count()
    {
        return this.resource_count;
    }*/
}
