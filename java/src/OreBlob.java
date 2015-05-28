import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class OreBlob extends Actor implements Animation_manager
{
    private int animation_rate; //q
    private List<Point> path = new ArrayList<Point>();
    private List<Point> visited_path = new ArrayList<Point>();
    public OreBlob(String name,Point position, int rate, List<PImage> imgs,int animation_rate)
    {
        super(name,position,rate,imgs);
        this.animation_rate = animation_rate;

    }
    public List<Point> getPath()
    {
        return this.path;
    }
    public List<Point> get_visitedPath()
    {
        return this.visited_path;
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
            this.remove_pending_actions(action[0]);
            this.next_image(); //
            if(repeat_count != 1)
            {
                this.schedule_action(world,this.create_animation_action(world,Math.max(repeat_count -1,0)),current_ticks + this.get_animation_rate());
            }
            List<Point> new_list = new ArrayList<Point>();
            new_list.add(this.get_position());
            return new_list; // making a new list with this position?

        };
        return action[0];
    }

    public void schedule_animation(WorldModel world, int repeat_count)
    {
        this.schedule_action(world,this.create_animation_action(world,repeat_count),this.get_animation_rate());
    }

    public void schedule_animation(WorldModel world)
    {
        this.schedule_action(world,this.create_animation_action(world,0),this.get_animation_rate());
    }

    public boolean oreblob_dfs(Point pt, WorldModel world, List<Point> path,boolean[][] visited)
    {
        if (!(world.within_bounds(pt)))
        {
            return false;
        }

        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt, Vein.class)!= null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt, Vein.class).get_position())) // problem is here
        {
            return true;
        }
        if(world.find_nearest(pt,Ore.class) != null)
        {
            if(world.occupancy.get_cell(pt)!=null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt, Ore.class).get_position()))
            {
                return false;
            }
        }
        if(world.occupancy.get_cell(pt) != null && world.find_nearest_not_self(pt,OreBlob.class) != null && world.occupancy.get_cell(pt).get_position().equals(world.find_nearest_not_self(pt,OreBlob.class).get_position()))
        {
            return false;
        }
        if (world.occupancy.get_cell(pt) != null && world.find_nearest(pt,Miner.class) != null
                &&( world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Obstacle.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Miner.class).get_position())
                || world.occupancy.get_cell(pt).get_position().equals(world.find_nearest(pt,Blacksmith.class).get_position()))) // we need to check for everything not just obstacles
        {
            return false;
        }
        if (visited[pt.get_y()][pt.get_x()])
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
                found = oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
            else if(pt.get_y() <= 5) // far bottom y right
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() <=5) // top right corner
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
            else // regular top left
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
        }

        else if(pt.get_x() >= world.get_num_cols()/2+1 && pt.get_y() <= world.get_num_rows()/2-1 )// top right
        {
            if(pt.get_x() >= 36 ) // far top x right
            {
                found = oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
            else if(pt.get_y() >= 26) // far top y right
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
            else if (pt.get_x() >= 36 && pt.get_y() >=26) // top right corner
            {
                found = oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
            else // regular top right
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
        }

        else if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() <= world.get_num_rows()/2-1 )//top left
        {
            if(pt.get_x() <= 5 ) // far top x left
            {
                found = oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited);
            }
            else if(pt.get_y() >= 26) // far top y left
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() >=26) // top left corner
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
            else // regular top left
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited);
            }
        }

        else if(pt.get_x() <= world.get_num_cols()/2-1 && pt.get_y() >= world.get_num_rows()/2+1 ) // bottom left
        {
            if(pt.get_x() <= 5 ) // far bottom x left
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited);
            }
            else if(pt.get_y() <= 5) // far bottom y left
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
            else if (pt.get_x() <= 5 && pt.get_y() <=5) // top left corner
            {
                found = oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited);
            }
            else // regular top left
            {
                found = oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                        oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
            }
        }
        else // if they get stuck in the middle
        {
            found = oreblob_dfs(new Point(pt.get_x() - 1, pt.get_y()), world, path, visited) ||
                    oreblob_dfs(new Point(pt.get_x(), pt.get_y() + 1), world, path, visited) ||
                    oreblob_dfs(new Point(pt.get_x() + 1, pt.get_y()), world, path, visited) ||
                    oreblob_dfs(new Point(pt.get_x(), pt.get_y() - 1), world, path, visited);
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
                oreblob_dfs(entity_pt, world, path, visited);
            }
            if (path.size() != 0)
            {
                return path.get(0);
            }
        }
        return null;
    }

    public boolean _to_other(WorldModel world,Vein vein)
    {
        Point entity_pt = this.get_position();
        if (vein == null)
        {
            return false; //don't return tuple just boolean
        }
        Point vein_pt = vein.get_position();
        if (Utility.adjacent(entity_pt,vein_pt))
        {
            world.remove_entity_schedule(vein);
            return true;
        }
        else
        {
            boolean[][] visited = new boolean[world.get_num_rows()][world.get_num_cols()];
            if(path.size() == 0) {
                oreblob_dfs(entity_pt,world,this.path,visited);
            }
            Point new_pt = Traverse_path(entity_pt, world, visited);

            if(new_pt != null)
            {
                On_Grid old_entity = world.get_tile_occupant(new_pt);
                if (old_entity instanceof Ore)
                {
                    world.remove_entity_schedule((Action_manager)old_entity);
                }
                world.move_entity(this, new_pt);
            }
            return false; //q was being returned in python code
        }
    }

    public Action create_actor_motion(WorldModel world, List<PImage> imgs)
    {
        Action[] action = {null};
        action[0] = (current_ticks)->
        {
            this.remove_pending_actions(action[0]);
            Point entity_pt = this.get_position();
           // System.out.println("__________________"+entity_pt.get_x());
            Vein vein = (Vein)world.find_nearest(entity_pt, Vein.class); // not recognizing veis existance
            Point vein_pt = null;

            if (vein != null) {
                vein_pt = vein.get_position();
            }
                boolean found = this._to_other(world, vein);
                long next_time = current_ticks + (long) this.get_rate();
                if (found) {
                    Quake quake = Utility.create_quake(world, vein_pt, current_ticks, Scan.get_quake_images());
                    world.add_entity(quake);
                    next_time = current_ticks + (long) this.get_rate() * 2;
                }
                this.schedule_action(world, this.create_actor_motion(world, imgs), next_time);

                return null;

        };
        return action[0];
    }


}
