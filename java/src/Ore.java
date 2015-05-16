import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
public class Ore extends Actor implements Action_manager
{
    private static final int BLOB_RATE_SCALE = 4;
    public Ore(String name,Point position,int rate,List<PImage> imgs)
    {
        super(name,position,rate,imgs);  //q make rate default to 5000
    }

    public Ore(String name,Point position,List<PImage> imgs)
    {
        this(name,position,5000,imgs);
    }

    public String entity_string()
    {
        return "ore" + " " + this.get_name() + " " + this.get_position().get_x() + " " + this.get_position().get_y() + " " + this.get_rate();
    }
    public Action create_ore_transform_action(WorldModel world,type i_store)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            OreBlob blob = Utility.create_blob(world, this.get_name() + " -- blob", this.get_position(),wrong this.get_rate()/BLOB_RATE_SCALE,(int)current_ticks,i_store);
            world.remove_entity_schedule(this);
            world.add_entity(blob);
            return null;
        };
        return action[0];
    }

}
