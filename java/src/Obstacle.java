import java.util.List;

public class Obstacle extends On_Grid
{
    public Obstacle(String name,Point position,List<PImage> imgs)
    {
        super(name,position,imgs);
    }

    public String entity_string()
    {
        return "obstacle" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y();
    }
}
