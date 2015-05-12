import java.util.List;
import java.util.ArrayList;

public class Quake extends  On_Grid
        implements Animation_manager
        implements Action_manager
{
    private Point position;
    public Quake(String name,Point position)
    {
        super(name, position);
    }


    //...........................actions
    public void schedule_action(WorldModel world, Action action, long time)
    {
        this.add_pending_action(action);
        world.schedule_action(action,time);
    }
    public Action create_entity_death_action(WorldModel world)
    {
        int current_ticks;
        public Action action(long current_ticks) // lambda
        {
            this.remove_pending_action(action);
            Point pt = this.get_position();
            world.remove_entity_schedule(this);
            return [pt] // this is wrong
        }
        return action;
    }
    public void remove_pending_action(Action action)
    {
        if(this instanceof "pending_actions")
        {
            this.pending_actions.remove(action);
        }
    }
    public void add_pending_action(Action action)
    {
        if(this instanceof "pending_actions")
        {
            this.pending_actions.add(action);
        }
    }
    public List<Action> get_pending_actions()
    {
        if(this instanceof "pending_actions")
        {
            return this.pending_actions;
        }
        else
        {
            List<Action> new_list = new ArrayList<Action>();
            return new_list;// empty list, what type though?
        }

    }
    public void clear_pending_actions()
    {
        if(this instanceof "pending_actions")
        {
            List<Action> new_list = new ArrayList<Action>();
            this.pending_actions = new_list;// empty list, but what type though?
        }
    }

    //.................................... animations

    public long get_animation_rate()
    {
        return this.animation_rate;
    }
    public Action create_animation_action(WorldModel world, int repeat_count)
    {
        public Action action(long current_ticks)//lamda
        {
            this.remove_pending_action(action);
            this.next_image(); // python code?
            if(repeat_count != 1)
            {
                this.schedule_action(world,this.create_animation_action(world,Math.max(repeat_count -1,0)),current_ticks + this.get_animation_rate());

            }
            List<Point> new_list = new ArrayList<Point>();
            new_list.add(this.get_position());
            return new_list; // making a new list with this position?

        }
        return action;
    }

    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
}
