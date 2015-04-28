public class Blacksmith extends Mining
{
    public Blacksmith(String name, Point position,int resource_limit, int rate)
    {
        super(name,resource_limit,position,rate);
    }

    public String entity_string()
    {
        return "blacksmith" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_resource_limit() + " " + this.get_rate();
    }

}
