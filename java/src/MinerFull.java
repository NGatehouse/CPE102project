import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class MinerFull extends Miner implements Action_manager , Animation_manager, Transform
{
    private List<Point> path = new ArrayList<Point>();
    public MinerFull(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
    }

    public Miner try_transform(WorldModel world)
    {
        return new MinerNotFull(this.get_name(), this.get_resource_limit(), this.get_position(), this.get_rate(),this.get_images(),this.get_animation_rate());
    }

    public boolean miner_F_dfs(Point pt, WorldModel world, List<Point> path,boolean[][] visited)
    {
        if (!(world.within_bounds(pt)))
        {
            return false;
        }

        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt, Blacksmith.class)!= null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt, Blacksmith.class).get_position())) // problem is here
        {
            return true;
        }
        if(world.find_nearest(pt,OreBlob.class) != null)
        {
            if(world.occupancy.get_cell(pt)!=null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt, OreBlob.class).get_position()))
            {
                return false;
            }
        }
        if(world.occupancy.get_cell(pt) != null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest_not_self(pt,Miner.class).get_position()))
        {
            return false;
        }
        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt,Vein.class) != null && world.find_nearest(pt,Ore.class) != null
                &&( world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Obstacle.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Vein.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Ore.class).get_position()))) // we need to check for everything not just obstacles
        {
            return false;
        }
        if (visited[pt.get_y()][pt.get_x()]) // goes in here alot............. q
        {
            //System.out.println("visited..");
            return false;
        }
        visited[pt.get_y()][pt.get_x()]= true;
        boolean found = false;
        if(pt.get_x() >= world.get_num_cols()/2+1 && pt.get_y() >= world.get_num_rows()/2+1 ) //bottom right
        {
            if(pt.get_x() >= 36 ) // far bottom x right
            {
                found = miner_F_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() <= 5) // far bottom y right
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() <=5) // top right corner
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else // regular top left
            {
                found = miner_F_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
        }

        if(pt.get_x() >= world.get_num_cols()/2+1 && pt.get_y() <= world.get_num_rows()/2-1 )// top right
        {
            if(pt.get_x() >= 36 ) // far top x right
            {
                found = miner_F_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() >= 26) // far top y right
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() >=26) // top right corner
            {
                found = miner_F_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else // regular top right
            {
                found = miner_F_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
        }

        if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() <= world.get_num_rows()/2-1 )//top left
        {
            if(pt.get_x() <= 5 ) // far top x left
            {
                found = miner_F_dfs( new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() >= 26) // far top y left
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() >=26) // top left corner
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else // regular top left
            {
                found = miner_F_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
        }

        if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() >= world.get_num_rows()/2+1 ) // bottom left
        {
            if(pt.get_x() <= 5 ) // far bottom x left
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() <= 5) // far bottom y left
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() <=5) // top left corner
            {
                found = miner_F_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_F_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else // regular top left
            {
                found = miner_F_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_F_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
        }
        if(found)
        {
            path.add(0, pt);
        }
        return found;
    }

    public Point Traverse_path(Point entity_pt,WorldModel world, boolean[][] visited)
    {
        if(path.size() > 1)
        {
            path.remove(0);
            if(world.occupancy.get_cell(path.get(0)) != null )
            {
                this.path = new ArrayList<Point>();
                miner_F_dfs(entity_pt, world, this.path, visited);
            }
            if (path.size() != 0)
            {
                return path.get(0);
            }
        }
        return null;
    }

    public boolean _to_other(WorldModel world,Blacksmith smith)
    {
        Point entity_pt = this.get_position();
        if (smith == null)
        {
            return false; //don't return tuple just boolean
        }
        Point smith_pt = smith.get_position();
        if (Utility.adjacent(entity_pt,smith_pt))
        {
            smith.set_resource_count(smith.get_resource_count()+this.get_resource_count());
            this.set_resource_count(0);
            return true;
        }
        else
        {
            boolean[][] visited = new boolean[world.get_num_rows()][world.get_num_cols()];
            if(path.size() ==0)
            {
                miner_F_dfs(entity_pt, world, this.path, visited);
            }
            Point new_pt = Traverse_path(entity_pt,world,visited);
            if(new_pt != null) {
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
            Blacksmith smith = (Blacksmith)world.find_nearest(entity_pt, Blacksmith.class);
            boolean found = this._to_other(world, smith); //q
            Miner new_miner = this;
            if(found)//whats found in python code?
            {
                new_miner = new_miner.try_transform_miner(world,new_miner::try_transform);
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, imgs), current_ticks + new_miner.get_rate());
            return null;
        };
        return action[0];
    }
}
