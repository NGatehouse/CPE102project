/**
 * Created by Nicholas on 5/11/2015.
 */
public interface Action_manager
{
    public schedule_action(WorldModel world, int action, int time);
    public create_entity_death_action(WorldModel world);
    public remove_pending_actions(int action);
    public add_pending_action(int action);
    public get_pending_actions();
    public clear_pending_actions();

    //here we just make the contract defining what types they
    //take and then actually write the functions in the classes
    //that implememt this interface correct?

}
