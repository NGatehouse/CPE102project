import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class OreBlob extends Actor implements Animation_manager
{
    private int animation_rate; //q
    public OreBlob(String name,Point position, int rate, List<PImage> imgs,int animation_rate)
    {
        super(name,position,rate,imgs);
        this.animation_rate = animation_rate;
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
            List<Point> new_list = new ArrayList<Point>();
            new_list.add(this.get_position());
            return new_list; // making a new list with this position?

        };
        return action[0];
    }

    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
    public void schedule_animation(WorldModel world)
    {
        this.schedule_action(world,this.create_animation_action(world,0),this.get_animation_rate());
    }
    public boolean _to_other(WorldModel world,Vein vein)
    {
        Point entity_pt = this.get_position();
        if (vein != null)
        {
            return false; //don't return tuple just boolean
        }
        Point vein_pt = vein.get_position();
        if (Utility.adjacent(entity_pt,vein_pt))
        {
            world.remove_entity_schedule(vein);
            return true;
        }
        else
        {
            Point new_pt = world.blob_next_position(entity_pt,vein_pt);
            On_Grid old_entity = world.get_tile_occupant(new_pt);
            if (old_entity instanceof Ore)
            {
                world.remove_entity_schedule((Action_manager)old_entity);
            }

            return world.move_entity(this,new_pt), false; //q was being returned in python code
        }
    }
    public Action create_actor_motion(WorldModel world, type i_store)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            Point entity_pt = this.get_position();
            Vein vein = world.find_nearest(entity_pt, Vein.class);
            boolean found = this._to_other(world, vein);
            long next_time = current_ticks + (long)this.get_rate();
            if(found)
            {
                Quake quake = Utility.create_quake(world,tiles[0],current_ticks,i_store);
                world.add_entity(quake);
                long next_time = current_ticks + (long)this.get_rate()*2;
            }
            this.schedule_action(world,this.create_actor_motion(world,i_store),next_time);
            return null;
        };
        return action[0];
    }

}
