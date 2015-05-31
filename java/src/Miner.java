import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public abstract class Miner extends Mining implements Action_manager , Animation_manager
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
        return "miner" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_resource_limit() + " " + this.get_rate() + " " + this.get_animation_rate();
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

   public Miner try_transform_miner(WorldModel world, Transform transform)
   {
        Miner new_miner = transform.try_transform(world);
        if(this != new_miner)
        {
            world.clear_pending_actions(this);
            world.remove_entity_at(this.get_position());
            world.add_entity(new_miner);
            new_miner.schedule_animation(world);

        }
        return new_miner;
    }
    public Miner try_transform_miner_mad(WorldModel world, Transform_Anger transform)
    {
        Miner new_miner = transform.try_transform_anger(world);
        if(this != new_miner)
        {
            world.clear_pending_actions(this);
            world.remove_entity_at(this.get_position());
            world.add_entity(new_miner);
            new_miner.schedule_animation(world);
        }
        return new_miner;
    }
    public Action create_miner_action(WorldModel world, List<PImage> imgs)
    {
        return this.create_actor_motion(world,imgs);
    }
   // ..................................... animation
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


    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
    public void schedule_animation(WorldModel world)
    {
        this.schedule_action(world,this.create_animation_action(world,0),this.get_animation_rate());
    }

    public abstract Miner try_transform(WorldModel world);
    public abstract Miner try_transform_anger(WorldModel world);

    public abstract Action create_actor_motion(WorldModel world, List<PImage> imgs);

}
