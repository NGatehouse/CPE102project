public class Miner extends Mining
    //implements Animation_manager missing data imgs,animation_rate
{

    public Miner(String name,int resource_limit,Point position,int rate)
    {
        super(name,resource_limit,position,rate);
    }

    public String entity_string()
    {
        return "miner" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_resource_limit() + " " + this.get_rate();
    }

    public boolean equals(Miner that)
    {
        if(this == that)
        {
            return false;
        }
        Miner that = (Miner) other;
        return this
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
