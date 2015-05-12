/**
 * Created by Nicholas on 5/11/2015.
 */
public interface Animation_manager
{
    public void get_animation_rate();
    public void create_animation_action(WorldModel world, int repeat_count);
    public void schedule_animation(WorldModel world, int repeat_count);

}
