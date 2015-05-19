import java.util.List;

/**
 * Created by Nicholas on 5/11/2015.
 */
public interface Action_manager
{
    // these are not all really void
    public void schedule_action(WorldModel world, Action action, long time);
    public Action create_entity_death_action(WorldModel world);
    public void remove_pending_actions(Action action);
    public void add_pending_action(Action action);
    public List<Action> get_pending_actions();
    public void clear_pending_actions();
    public Point get_position();

    //here we just make the contract defining what types they
    //take and then actually write the functions in the classes
    //that implememt this interface correct?

}
