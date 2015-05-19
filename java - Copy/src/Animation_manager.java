/**
 * Created by Nicholas on 5/11/2015.
 */
public interface Animation_manager
{
    public int get_animation_rate();
    public Action create_animation_action(WorldModel world, int repeat_count);
    public void schedule_animation(WorldModel world, int repeat_count);

}
