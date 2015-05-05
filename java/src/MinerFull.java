public class MinerFull extends Miner
{
    public MinerFull(String name,int resource_limit,Point position,int rate)
    {
        super(name,resource_limit,position,rate);
    }

    public Miner try_transform(WorldModel world)
    {
        return new MinerNotFull(this.get_name(), this.get_resource_limit(), this.get_position(), this.get_rate());
    }
    public boolean _to_other(WorldModel world,Blacksmith smith)
    {
        Point entity_pt = this.get_position();
        if (smith != null)
        {
            return false; //don't return tuple just boolean
        }
        Point smith_pt = smith.get_position();
        if (Utility.adjacent(entity_pt,smith_pt))
        {
            this.set_resource_count(1+this.get_resource_count());
            world.remove_entity(smith);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,smith_pt);
            return false;
        }
    }
}
