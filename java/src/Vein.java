public class Vein extends Actor
{
    private int resource_distance = 1;
    public Vein(String name, int rate, Point position, int resource_distance)
    {
        super(name,position,rate);
    }
    public int get_resource_distance()
    {
        return this.resource_distance;
    }
}
