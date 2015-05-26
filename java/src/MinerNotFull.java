import processing.core.PImage;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.List;

public class
        MinerNotFull extends Miner implements Action_manager , Animation_manager, Transform
{
    private int resource_count;
    private List<Point> path = new ArrayList<Point>();

    public MinerNotFull(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
        this.resource_count=0;
    }
    public List<Point> get_path()
    {
        return this.path;
    }
    public Miner try_transform(WorldModel world)
    {
        if(this.get_resource_count() < this.get_resource_limit())
        {
            return this;
        }
        else
        {
            return new MinerFull(this.get_name(),this.get_resource_limit(),this.get_position(),this.get_rate(),this.get_images(),this.get_animation_rate());
        }

    }

    public boolean miner_NF_dfs(Point pt, WorldModel world, List<Point> path)
    {
        System.out.println(world.within_bounds(pt) + ": that it is in bounds");
        Grid Visited = new Grid(world.get_num_cols(),world.get_num_rows());

        if (!(world.within_bounds(pt)))
        {
            return false;
        }
        System.out.println(pt.get_x() + "x" + pt.get_y() + "y" + ": we will find you!");
        if (world.occupancy.get_cell(pt) == world.find_nearest(pt, Ore.class) ) //q
        {
            path.add(0,pt); // why is this here
            return true;
        }
        if (world.occupancy.get_cell(pt) == world.find_nearest(pt,Obstacle.class))
        {
            return false;
        }
        //if (world.occupancy.get_cell(pt) == Visited.get_cell(pt))
        //{
         //   return false;
        //

        //Visited.set_cell(pt, world.occupancy.get_cell(pt));
        boolean found = miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path) ||
                miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path) ||
                miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path) ||
                miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path);
        if(found)
        {
            path.add(0,pt);
        }
        return found;
    }
    public Point Traverse_path()
    {
        if(path.size() != 0)
        {
            Point next_pt = path.get(0);
            path.remove(0);
            return next_pt;
        }
        return null;
    }
    public boolean _to_other(WorldModel world, Ore ore) // whats the return type..tuple?
    {

        Point entity_pt = this.get_position();
        if (ore == null)
        {
            return false;
        }
        Point ore_pt = ore.get_position();
        if (Utility.adjacent(entity_pt, ore_pt))
        {
            this.set_resource_count(1+this.get_resource_count());
            world.remove_entity(ore);
            return true;
        }
        else
        {
            if (path.size() == 0) // doesn't help if he gets blocked... yet (:
            {
                miner_NF_dfs(entity_pt,world,get_path());
            }
            Point new_pt = Traverse_path();
            if(new_pt != null)
            {
                world.move_entity(this, new_pt);
            }
            return false;
        }
    }

    public Action create_actor_motion(WorldModel world, List<PImage> imgs)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {

            this.remove_pending_actions(action[0]);
            Point entity_pt = this.get_position();
            Ore ore = (Ore)world.find_nearest(entity_pt, Ore.class);
            //System.out.println("ore:" + ore);
            boolean found = this._to_other(world, ore); //q
            //System.out.println("found:" + found);
            Miner new_miner = this;
            if(found)//whats found in python code?
            {
                new_miner = new_miner.try_transform_miner(world, new_miner::try_transform);
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, imgs), current_ticks + new_miner.get_rate());
            return null;
        };
        return action[0];
    }
}
