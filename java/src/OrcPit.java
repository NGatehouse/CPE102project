import processing.core.PImage;

import java.util.List;

/**
 * Created by Nicholas on 5/29/2015.
 */
public class OrcPit extends Actor implements Action_manager
{
    private static final int ORC_RATE_SCALE = 4;
    private static final int RESOURCE_DISTANCE = 1;
    private int resource_distance; //q Why?
    public OrcPit(String name, int rate, Point position,List<PImage> imgs,int resource_distance)
    {
        super(name,position,rate,imgs);
        this.resource_distance = resource_distance;
    }

    public OrcPit(String name, int rate, Point position,List<PImage> imgs)
    {
        this(name,rate,position,imgs,RESOURCE_DISTANCE);
    }

    public int get_resource_distance()
    {
        return this.resource_distance; //q was RESOURCE_DISTANCE
    }

    public Action create_actor_motion(WorldModel world, List<PImage> imgs)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            Point open_pt = world.find_open_around(this.get_position(),this.get_resource_distance());
            if(open_pt != null)//whats found in python code?
            {

                Orc orc = Utility.create_orc(world, this.get_name() + " -- orc", this.get_position(), this.get_rate() / ORC_RATE_SCALE,(int)current_ticks,imgs );

                world.add_entity(orc);
            }
            this.schedule_action(world,this.create_actor_motion(world,imgs),current_ticks + this.get_rate());
            return null;
        };
        return action[0];
    }
}
