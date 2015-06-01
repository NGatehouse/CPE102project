import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Fire extends On_Grid implements Animation_manager
{
    private int animation_rate;
    public Fire(String name,Point position,List<PImage> imgs, int animation_rate)
    {
        super(name,position,imgs);
        this.animation_rate = animation_rate;
    }
    public int get_animation_rate()
    {
        return this.animation_rate;
    }
    public Action create_animation_action(WorldModel world, int repeat_count)
    {
        Action[] action = {null};
        action[0 ]=(long current_ticks) ->//lamda
        {
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
    public void schedule_action(WorldModel world, Action action, long time)
    {
        world.schedule_action(action,time);
    }
    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }
    public void schedule_animation(WorldModel world)
    {
        this.schedule_action(world, this.create_animation_action(world, 500), this.get_animation_rate());
    }
}
