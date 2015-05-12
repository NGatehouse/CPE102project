import java.util.List;

public class Quake extends  On_Grid
        implements Animation_manager
        implements Action_manager
{
    private Point position;
    public Quake(String name,Point position)
    {
        super(name,position);
    }


    //...........................actions
    public void schedule_action(WorldModel world, int action, int time)
    {
        this.add_pending_action(action);
        world.schedule_action(action,time);
    }
    public void create_entity_death_action(WorldModel world)
    {
        public action(int current_ticks) // local function? static?
        {
            this.remove_pending_action(action);
            Point pt = this.get_position();
            world.remove_entity_schedule(this);
            return [pt] // this is wrong
        }
        return action;
    }
    public void remove_pending_action(int action)
    {
        if(hasattr("pending_actions"))
        {
            this.pending_actions.remove(action);
        }
    }
    public void get_pending_actions()
    {
        if(hasattr("pending_actions"))
        {
            return this.pending_actions.add();
        }
        else
        {
            return new List<Integer>;// empty list, what type though?
        }

    }
    public void clear_pending_actions()
    {
        if(hasattr("pending_actions"))
        {
            this.pending_actions = [] // empty list, but what type though?
        }
    }

    //.................................... animations

    public void get_animation_rate()
    {
        return this.animation_rate;
    }
    public void create_animation_action(WorldModel world, int repeat_count)
    {
        public void action(int current_ticks)
        {
            this.remove_pending_action(action);
            this.next_image();
            if(repeat_count != 1)
            {
                this.schedule_action(world,this.create_animation_action(world,max(repeat_count -1,0)),current_ticks + this.get_animation_rate());

            }
            return [this.get_position()]; // making a new list with this position?

        }
    }

    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
}
