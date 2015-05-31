import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicholas on 5/29/2015.
 */
public class Orc extends Actor implements Animation_manager
{

    private List<Point> path = new ArrayList<Point>();
    private List<Point> visited_path = new ArrayList<Point>();

    private int animation_rate;
    public Orc(String name,Point position,int rate, List<PImage> imgs, int animation_rate)
    {
        super(name,position,rate,imgs);
        this.animation_rate = animation_rate;
    }

    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
    public void schedule_animation(WorldModel world)
    {
        this.schedule_action(world,this.create_animation_action(world,0),this.get_animation_rate());
    }
    public int get_animation_rate()
    {
        return this.animation_rate;
    }
    public Action create_animation_action(WorldModel world, int repeat_count)
    {
        Action[] action = {null};
        action[0 ]=(long current_ticks) ->//lamda
        {
            this.remove_pending_actions(action[0]);
            this.next_image(); //
            if(repeat_count != 1)
            {
                this.schedule_action(world,this.create_animation_action(world,Math.max(repeat_count -1,0)),current_ticks + this.get_animation_rate());
            }
            List<Point> new_list = new ArrayList<>();
            new_list.add(this.get_position());
            return new_list; // making a new list with this position?

        };
        return action[0];
    }
    public Action create_orc_action(WorldModel world, List<PImage> imgs)
    {
        return this.create_actor_motion(world,imgs);
    }
    public boolean _to_other(WorldModel world, Miner miner) // whats the return type..tuple?
    {
        Point entity_pt = this.get_position();
        if (miner == null)
        {
            return false;
        }
        Point miner_pt = miner.get_position();
        if (Utility.adjacent(entity_pt, miner_pt))
        {
            return true;
        }
        else
        {
            Point new_pt = world.next_position(entity_pt,miner_pt);
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
            Miner miner = (Miner)world.find_nearest(entity_pt, Miner.class);
            boolean found = this._to_other(world, miner); //q
            Miner new_miner = miner;
            if(found)
            {
                new_miner = new_miner.try_transform_miner_mad(world, new_miner::try_transform_anger);
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, imgs), current_ticks + new_miner.get_rate());
            return null;
        };
        return action[0];
    }

   /* public Miner try_transform_anger(WorldModel world, Miner miner)
    {
        return new MinerFullofHate(miner.get_name(), miner.get_resource_limit(), miner.get_position(), miner.get_rate(),miner.get_images(),miner.get_animation_rate());
    }
   */
}
