/**
 * Created by Nicholas on 5/11/2015.
 */
public interface Action_manager
{
    public void schedule_action(WorldModel world, Action action, long time);
    public void create_entity_death_action(WorldModel world);
    public void remove_pending_actions(Action action);
    public void add_pending_action(Action action);
    public void get_pending_actions();
    public void clear_pending_actions();
    public Point get_position();

    //here we just make the contract defining what types they
    //take and then actually write the functions in the classes
    //that implememt this interface correct?

}
