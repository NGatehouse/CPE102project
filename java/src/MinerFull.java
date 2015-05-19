import processing.core.PImage;

import java.util.List;

public class MinerFull extends Miner implements Action_manager , Animation_manager, Transform
{
    public MinerFull(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
    }

    public Miner try_transform(WorldModel world)
    {
        return new MinerNotFull(this.get_name(), this.get_resource_limit(), this.get_position(), this.get_rate(),this.get_images(),this.get_animation_rate());
    }
    public boolean _to_other(WorldModel world,Blacksmith smith)
    {
        Point entity_pt = this.get_position();
        if (smith == null)
        {
            return false; //don't return tuple just boolean
        }
        Point smith_pt = smith.get_position();
        if (Utility.adjacent(entity_pt,smith_pt))
        {
            smith.set_resource_count(smith.get_resource_count()+this.get_resource_count());
            this.set_resource_count(0);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,smith_pt);
            world.move_entity(this,new_pt);
            return false;
        }
    }
    public Action create_actor_motion(WorldModel world, List<PImage> imgs)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            Point entity_pt = this.get_position();
            Blacksmith smith = (Blacksmith)world.find_nearest(entity_pt, Blacksmith.class);
            boolean found = this._to_other(world, smith); //q
            Miner new_miner = this;
            if(found)//whats found in python code?
            {
                new_miner = new_miner.try_transform_miner(world,new_miner::try_transform);
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, imgs), current_ticks + new_miner.get_rate());
            return null;
        };
        return action[0];
    }
}
