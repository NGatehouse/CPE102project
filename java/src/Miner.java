import java.util.List;

public class Miner extends Mining
    //implements Animation_manager missing data imgs,animation_rate
{
    private int animation_rate; //q
    public Miner(String name,int resource_limit,Point position,int rate, List<PImage> imgs, int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs);
        this.animation_rate = animation_rate;
    }

    public String entity_string()
    {
        return "miner" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_resource_limit() + " " + this.get_rate();
    }

    public boolean equals(Miner that)
    {
        if(this == that)
        {
            return true;
        }
        if(!(that instanceof Miner))
        {
            return false;
        }
        return this.get_name().equals(that.get_name()) && this.get_resource_limit() == that.get_resource_limit() && this.get_position().get_x() ==that.get_position().get_x() && this.get_position().get_y() ==that.get_position().get_y() && this.get_rate()==that.get_rate();
    }

   /* public try_transform_miner(WorldModel world, function transform)
    {
        Miner new_miner = tranform(world);
        if(this != new_miner)
        {
            world.clear_pending_actions();
            world.remove_entity_at(this.get_position());
            world.add_entity(new_miner);
            new_miner.schedule_animation(world);

        }
        return new_miner;

    }*/

}
