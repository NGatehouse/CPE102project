import processing.core.PImage;

import java.util.List;

public class Vein extends Actor implements Action_manager
{
    private static final int RESOURCE_DISTANCE = 1;
    private int resource_distance; //q
    public Vein(String name, int rate, Point position,List<PImage> imgs,int resource_distance)
    {
        super(name,position,rate,imgs);
        this.resource_distance = resource_distance;
    }
    public Vein(String name, int rate, Point position,List<PImage> imgs)
    {
        this(name,rate,position,imgs,RESOURCE_DISTANCE);
    }
    public int get_resource_distance()
    {
        return this.resource_distance; //q was RESOURCE_DISTANCE
    }
    public String entity_string()
    {
       return "vein" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate() + " " + this.get_resource_distance();
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
                Ore ore = Utility.create_ore(world,"ore - " + this.get_name() + " - " + current_ticks,open_pt,current_ticks,imgs );
                world.add_entity(ore);
            }
            this.schedule_action(world,this.create_actor_motion(world,imgs),current_ticks + this.get_rate());
            return null;
        };
        return action[0];
    }
}
