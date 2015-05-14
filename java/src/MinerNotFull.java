import processing.core.PImage;

import java.util.List;

public class MinerNotFull extends Miner implements Action_manager , Animation_manager
{
    private int resource_count;
    public MinerNotFull(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
        this.resource_count=0;
    }

    public Miner try_transform(WorldModel world)
    {
        if(this.get_resource_count() < this.get_resource_limit())
        {
            return this;
        }
        else
        {
            return new MinerFull(this.get_name(),this.get_resource_limit(),this.get_position(),this.get_rate(),this.get_images(),this.get_animation_rate());
        }

    }
    public boolean _to_other(WorldModel world, Ore ore) // whats the return type..tuple?
    {
        Point entity_pt = this.get_position();
        if (ore != null)
        {
            return false;
        }
        Point ore_pt = ore.get_position();
        if (Utility.adjacent(entity_pt, ore_pt))
        {
            this.set_resource_count(1+this.get_resource_count());
            world.remove_entity(ore);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,ore_pt);
            return false;
        }
    }

    public Action create_actor_motion(WorldModel world, type i_store)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            Point entity_pt = this.get_position();
            Ore ore = world.find_nearest(entity_pt, ore);
            (tiles,found) = this._to_other(world,ore); //q
            Miner new_miner = this;
            if(found)//whats found in python code?
            {
                new_miner = new_miner.try_transform_miner(world,new_miner.try_transform());
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, i_store), current_ticks + new_miner.get_rate());
            return tiles;
        };
        return action[0];
    }
}
