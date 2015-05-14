import processing.core.PImage;

import java.util.List;
import java.util.ArrayList;

public class Quake
        extends  On_Grid
        implements Animation_manager, Action_manager
{
    List<Action> pending_actions = new ArrayList<Action>();
    private int animation_rate;
    public Quake(String name,Point position,List<PImage> imgs,int animation_rate)
    {
        super(name, position,imgs);
        this.animation_rate = animation_rate;
    }


    //...........................actions
    public void schedule_action(WorldModel world, Action action, long time)
    {
        this.add_pending_action(action);
        world.schedule_action(action,time);
    }
    public Action create_entity_death_action(WorldModel world)
    {
        Action[] action = {null};
        action[0] = (long current_ticks) ->
        {
            this.remove_pending_actions(action[0]);
            Point pt = this.get_position();
            world.remove_entity_schedule(this);
            List<Point> new_list = new ArrayList<Point>();
            new_list.add(pt);
            return new_list; //q
        };
        return action[0];
    }


    public void remove_pending_actions(Action action)
    {
        this.pending_actions.remove(action); // only use pending actions if you have it anyway
    }
    public void add_pending_action(Action action)
    {
        this.pending_actions.add(action);
    }
    public List<Action> get_pending_actions()
    {
        return this.pending_actions;
    }
    public void clear_pending_actions()
    {
        List<Action> new_list = new ArrayList<Action>();
        this.pending_actions = new_list;
    }

    //.................................... animations

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
}
