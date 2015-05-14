import java.util.ArrayList;
import java.util.List;

public class Actor extends On_Grid implements Action_manager
   //implements Action_manager
{
    List<Action> pending_actions = new ArrayList<Action>();
    private int rate;
    public Actor(String name,Point position,int rate,List<PImage> imgs)
    {
        super(name,position,imgs);
        this.rate = rate;
    }



    public int get_rate()
    {
        return this.rate;
    }
    //...........................................actions
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


}
