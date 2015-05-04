public class Vein extends Actor
{
    private int RESOURCE_DISTANCE = 1; // make this a constant
    public Vein(String name, int rate, Point position, int resource_distance)
    {
        super(name,position,rate);
        this.RESOURCE_DISTANCE = resource_distance;
    }
    public int get_resource_distance()
    {
        return this.RESOURCE_DISTANCE;
    }
    public String entity_string()
    {
       return "vein" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate() + " " + this.get_resource_distance();
    }
}
