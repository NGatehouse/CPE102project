public class Ore extends Actor
{
    private int rate = 5000;
    public Ore(String name,Point position,int rate)
    {
        super(name,position,rate);
    }

    public String entity_string()
    {
        return "ore" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate();
    }
}
