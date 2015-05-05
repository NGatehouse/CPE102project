import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WorldModel {

    // fields
    //Do we want to make fields public or private?
    private int num_rows;
    private int num_cols;
    //private int background;
    //private int occupancy;
    private List entities = new ArrayList<>();
    private Grid background;
    private Grid occupancy;


    // constructor
    public WorldModel(int num_rows, int num_cols, int background) {
        //this.background = new int[num_cols][num_rows];
        this.background = new Grid(num_cols, num_rows);
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        //this.occupancy = new int[num_cols][num_rows];
        this.occupancy = new Grid(num_cols, num_rows);
        //this.action_queue = ordered_list.OrderedList();
    }

    // methods
    public void clear_pending_actions()  //assignment 4
    {
        //
    }

    public void remove_entity_schedule(Entity entity)  //assignment 4
    {
        for (Entity.action action : entity.get_pending_actions())
            this.unschedule_action(action);
        entity.clear_pending_actions();
        this.remove_entity(entity);
    }

    public Point find_open_around(Point pt, int distance)   //DONE
    {
        for (int dy; dy <= distance; dy++)
        {
            for (int dx; dx <= distance; dx++)
            {
                Point new_pt = new Point(pt.get_x() + dx, pt.get_y() + dy);
                if (this.within_bounds(new_pt) && !this.is_occupied(new_pt));
                    return new_pt;
            }
        }
        return null;
    }

    public Point blob_next_position(Point entity_pt, Point dest_pt)  //coupling; solve later TODO
    {
        int horiz = dest_pt.get_x().compareTo(entity_pt.get_x());
        Point new_pt = new Point(entity_pt.get_x() + horiz, entity_pt.get_y());
        if (horiz == 0 || (this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore)))
        {
            int vert = dest_pt.get_y().compareTo(entity_pt.get_y());
            new_pt = new Point(entity_pt.get_x(), entity_pt.get_y());
            if (vert == 0 || (this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore)))
            {
                new_pt = new Point(entity_pt.get_x(), entity_pt.get_y());
            }
        }
        return new_pt;
    }

    public Point next_position()  //coupling; solve later  //need
    {
        return null;
    }

    public Boolean within_bounds(Point pt)  //need  //Done
    {
        return (pt.get_x() >= 0 && pt.get_x() < this.num_cols && pt.get_y() >= 0 && pt.get_y() < this.num_cols);
    }

    public Boolean is_occupied(Point pt)  //need //Done
    {
        return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
    }



    public void add_entity(Entity entity)  //need //Done
    {
        int pt = entity.get_position();
        if (this.within_bounds(pt))
        {
            Entity old_entity = this.occupancy.get_cell(pt);
            if (old_entity != null)
            {
                old_entity.clear_pending_actions();
            }
            this.occupancy.set_cell(pt, entity);
            this.entities.add(entity);
        }
    }


    public List move_entity(Entity entity, Point pt)  //TODO
    {
        List tiles = new ArrayList<Point>();
        if (this.within_bounds(pt))
        {
            Point old_pt = entity.get_position();
            this.occupancy.set_cell(old_pt, null);
            tiles.add(old_pt);
            this.occupancy.set_cell(pt, entity);
            tiles.add(pt);
            entity.set_position(pt);
        }
        return tiles;
        /*
      def move_entity(self, entity, pt):
        tiles = []
        if self.within_bounds(pt):
             old_pt = entity.get_position()
             self.occupancy.set_cell( old_pt, None)
             tiles.append(old_pt)
             self.occupancy.set_cell(pt, entity)
             tiles.append(pt)
             entity.set_position(pt)
        return tiles
         */
    }


    public void remove_entity(Entity entity) //TODO
    {
        this.remove_entity_at(entity.get_position());
    }


    public returnType remove_entity_at(Point pt)  //TODO
    {
        if (this.within_bounds())
    }

    public void schedule_action(Action action, Time time)  //Assignement 4
    {}

    public void unschedule_action(Action action)  //Assignement 4
    {    }

    public returnType update_on_time(Ticks ticks)  //Assignement 4
    {}

    public returnType set_background(Point pt, Background bgnd)  //TODO
    {
        if (this.within_bounds(pt))
            this.background.set_cell(pt, bgnd);
    }

    public returnType get_tile_occupant(Point pt)  //TODO
    {
        if (this.within_bounds(pt))
        {
            return this.occupancy.get_cell(pt);
        }
    }

    public Entity get_entities()  //TODO
    {
        return this.entities;
    }

    public static double distance_sq(Point p1, Point p2)   //static method
    {
        return Math.pow((p1.get_x() - p2.get_y()),2) + Math.pow((p1.get_y() - p2.get_y()),2);
    }


    public returnType get_background_image(Point pt)  //TODO
    {
        if (this.within_bounds(pt))
        {
            return (this.background.get_cell(pt)).get_image();
        }
        return null;
    }


    public Entity find_nearest(Point pt, Type type) //How to specify type  //TODO
    {
        List<Entity> entList = new LinkedList<Entity>();
        List<Double> distsList = new LinkedList<Double>();

        for (Entity e : this.entities)
        {
            if (e instanceof type)
            {

            }
        }
        return nearest_entity(entList, distsList)

        oftype = [(e, distance_sq(pt, e.get_position()))
        for (e : this.entities)
            if e.isInstanceOf(type)];
    }


    public static returnType nearest_entity(LinkedList<Entity> entList, LinkedList<Double> distsList)  //static method
    {
        if (entList.size() > 0)
        {
            Entity ent = entList[0];
            for (Entity other : entList)
            {
                if other[1] < pair[1]:
            }
        }
        else
        {
            return null;
        }
        return nearest;
    }


}