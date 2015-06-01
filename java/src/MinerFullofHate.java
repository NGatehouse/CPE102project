import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas on 5/29/2015.
 */
public class MinerFullofHate extends Miner implements Animation_manager, Action_manager, Transform
{
    public MinerFullofHate(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
    }


    public Miner try_transform(WorldModel world)
    {
        return null;
    }

    public Miner try_transform_anger(WorldModel world)
    {
        return new MinerFullofHate(this.get_name(), this.get_resource_limit(), this.get_position(), this.get_rate(),this.get_images(),this.get_animation_rate());
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
            world.remove_entity(smith);
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,smith_pt);
            world.move_entity(this, new_pt);
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
                world.remove_entity(new_miner);
            }
            return null;
        };
        return action[0];
    }
    private List<Point> path = new ArrayList<Point>();
    private List<Point> visited_path = new ArrayList<Point>();

    public List<Point> getPath()
    {
        return this.path;
    }
    public List<Point> get_visitedPath()
    {
        return this.visited_path;
    }
}
