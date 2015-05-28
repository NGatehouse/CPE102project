import processing.core.PImage;

import java.beans.Visibility;
import java.util.ArrayList;
import java.util.List;

public class
        MinerNotFull extends Miner implements Action_manager , Animation_manager, Transform
{
    private int resource_count;
    private List<Point> path = new ArrayList<Point>();
    private List<Point> visited_path = new ArrayList<Point>();


    public MinerNotFull(String name,int resource_limit,Point position,int rate,List<PImage> imgs,int animation_rate)
    {
        super(name,resource_limit,position,rate,imgs,animation_rate);
        this.resource_count=0;
    }

    public List<Point> getPath()
    {
        return this.path;
    }
    public List<Point> get_visitedPath()
    {
        return this.visited_path;
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

    public boolean miner_NF_dfs(Point pt, WorldModel world, List<Point> path,boolean[][] visited)
    {
        if (!(world.within_bounds(pt)))
        {
            return false;
        }

        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt, Ore.class)!= null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt, Ore.class).get_position())) // problem is here
        {
            path.add(0, pt);
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
        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt,Vein.class) != null
                &&( world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Obstacle.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Vein.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Blacksmith.class).get_position()))) // we need to check for everything not just obstacles
        {
            return false;
        }
        if (visited[pt.get_y()][pt.get_x()]) // goes in here alot............. q
        {
            this.visited_path.add(0,pt);
            return false;
        }
        visited[pt.get_y()][pt.get_x()]= true;
        boolean found = false;

        if(pt.get_x() >= world.get_num_cols()/2+1 && pt.get_y() >= world.get_num_rows()/2+1 ) //bottom right
        {
            if(pt.get_x() >= 36 ) // far bottom x right
            {
                found = miner_NF_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() <= 5) // far bottom y right
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() <=5) // top right corner
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else // regular top left
            {
                found = miner_NF_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
        }

        else if(pt.get_x() >= world.get_num_cols()/2+1 && pt.get_y() <= world.get_num_rows()/2-1 )// top right
        {
            if(pt.get_x() >= 36 ) // far top x right
            {
                found = miner_NF_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() >= 26) // far top y right
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() >=26) // top right corner
            {
                found = miner_NF_dfs( new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited);
            }
            else // regular top right
            {
                found = miner_NF_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
        }

        else if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() <= world.get_num_rows()/2-1 )//top left
        {
            if(pt.get_x() <= 5 ) // far top x left
            {
                found = miner_NF_dfs( new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() >= 26) // far top y left
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() >=26) // top left corner
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited);
            }
            else // regular top left
            {
                found = miner_NF_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
        }

        else if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() >= world.get_num_rows()/2+1 ) // bottom left
        {
            if(pt.get_x() <= 5 ) // far bottom x left
            {
                found = miner_NF_dfs( new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited);
            }
            else if(pt.get_y() <= 5) // far bottom y left
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() <=5) // top left corner
            {
                found = miner_NF_dfs( new Point(pt.get_x(),pt.get_y()-1),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()+1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x()-1,pt.get_y()),world,path,visited) ||
                        miner_NF_dfs(new Point(pt.get_x(),pt.get_y()+1),world,path,visited);
            }
            else // regular top left
            {
                found = miner_NF_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        miner_NF_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }

        }
        else // if they get stuck in the middle
        {
            found = miner_NF_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                    miner_NF_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                    miner_NF_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                    miner_NF_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
        }

        if(found)
        {
            this.visited_path.add(0,pt);
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
                miner_NF_dfs(entity_pt, world, this.path, visited);
            }
            if (path.size() != 0)
            {
                return path.get(0);
            }
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
            boolean[][] visited = new boolean[world.get_num_rows()][world.get_num_cols()];
            if(path.size() ==0)
            {
                miner_NF_dfs(entity_pt,world,this.path,visited);
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
            Ore ore = (Ore)world.find_nearest(entity_pt, Ore.class);
            boolean found = this._to_other(world, ore); //q
            Miner new_miner = this;
            if(found)
            {
                new_miner = new_miner.try_transform_miner(world, new_miner::try_transform);
            }
            new_miner.schedule_action(world, new_miner.create_miner_action(world, imgs), current_ticks + new_miner.get_rate());
            return null;
        };
        return action[0];
    }
}
