import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.PImage;


public class WorldModel
{

    // fields
    private int num_rows;
    private int num_cols;
    private List<ListItem> list = new ArrayList<ListItem>();
    private OrderedList action_queue;
    //private int background;
    //private int occupancy;
    private List<On_Grid> entities = new ArrayList<On_Grid>();
    public Grid background;
    public Grid occupancy;
    //private OrderedList<> = OrderedList(Action action);


    // constructor
    public WorldModel(int num_rows, int num_cols)// int background)
    {
        //this.background = new int[num_cols][num_rows];
        this.background = new Grid(num_cols, num_rows);
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        //this.occupancy = new int[num_cols][num_rows];
        this.occupancy = new Grid(num_cols, num_rows);
        this.action_queue = new OrderedList(list);

    }
    public int get_num_rows()
    {
        return this.num_rows;
    }
    public int get_num_cols()
    {
        return this.num_cols;
    }

    // methods

    public Point find_open_around(Point pt, int distance)
    {
        for (int dy=-distance; dy <= distance; dy++)
        {
            for (int dx=-distance; dx <= distance; dx++)
            {
                Point new_pt = new Point(pt.get_x() + dx, pt.get_y() + dy);
                if (this.within_bounds(new_pt) && !(this.is_occupied(new_pt)))
                {
                    return new_pt;
                }
            }
        }
        return null;
    }

    public Point blob_next_position(Point entity_pt, Point dest_pt)  //coupling; solve later
    {
        //System.out.println(entity_pt.get_x());
        //System.out.println(entity_pt.get_y());
        //System.out.println("____________");
        //System.out.println(dest_pt.get_x());
        //System.out.println(dest_pt.get_y());

        int horiz = Utility.sign(dest_pt.get_x() - entity_pt.get_x());
        //System.out.println("horiz" + horiz);
        Point new_pt = new Point(entity_pt.get_x() + horiz, entity_pt.get_y());
        if (horiz == 0 || (this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore)))
        {
          //  System.out.println("Into first if");
            int vert = Utility.sign(dest_pt.get_y() - entity_pt.get_y());
            new_pt = new Point(entity_pt.get_x(), entity_pt.get_y() + vert);
            if (vert == 0 || (this.is_occupied(new_pt) && !(this.get_tile_occupant(new_pt) instanceof Ore)))
            {
            //    System.out.println("Into second if");
                new_pt = new Point(entity_pt.get_x(), entity_pt.get_y());
            }
        }
        //System.out.println("____________");
        ///System.out.println("newpt x = " + new_pt.get_x());
        //System.out.println("newpt y = " + new_pt.get_y());

        return new_pt;
    }

    public Point next_position(Point entity_pt, Point dest_pt)  //coupling; solve later //TODO
    {
        int horiz = Utility.sign(dest_pt.get_x() - entity_pt.get_x());
        Point new_pt = new Point(entity_pt.get_x() + horiz, entity_pt.get_y());

        if (horiz == 0 || (this.is_occupied(new_pt)))
        {
            int vert = Utility.sign(dest_pt.get_y() - entity_pt.get_y());
            new_pt = new Point(entity_pt.get_x(), entity_pt.get_y() + vert);
            if (vert == 0 || (this.is_occupied(new_pt)))
            {
                new_pt = new Point(entity_pt.get_x(), entity_pt.get_y());
            }
        }
        return new_pt;
    }

    public Boolean within_bounds(Point pt)
    {
        return (pt.get_x() >= 0 && pt.get_x() < this.num_cols && pt.get_y() >= 0 && pt.get_y() < this.num_rows);
    }

    public Boolean is_occupied(Point pt)
    {
        //System.out.println("______s________");
        //System.out.println("within bounds " + this.within_bounds(pt));
        //System.out.println("occ " + this.occupancy.get_cell(pt));
        //System.out.println((this.within_bounds(pt) && this.occupancy.get_cell(pt) != null));
        //System.out.println("______e________");
        return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
    }

    public List move_entity(On_Grid entity, Point pt)
    {
        List<Point> tiles = new ArrayList<Point>();
        if (this.within_bounds(pt)) {
            Point old_pt = entity.get_position();
            this.occupancy.set_cell(old_pt, null);
            tiles.add(old_pt);
            this.occupancy.set_cell(pt, entity);
            tiles.add(pt);
            entity.set_position(pt);
        }
        return tiles;
    }

    public void remove_entity(Action_manager entity)
    {
        this.remove_entity_at(entity.get_position());
    }

    public void remove_entity_at(Point pt)
    {
        if (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null )
        {
            On_Grid ent = this.occupancy.get_cell(pt);
            ent.set_position(new Point(-1, -1));
            this.entities.remove(ent);
            this.occupancy.set_cell(pt, null);
        }
    }

    public void set_background(Point pt, On_Grid bgnd)
    {
        if (this.within_bounds(pt))
            this.background.set_cell(pt, bgnd); //bgnd isn't technically a background may cause future problems
    }

    public On_Grid get_tile_occupant(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return this.occupancy.get_cell(pt);
        }
        else
        { return null; }
    }

    public List<On_Grid> get_entities()
    {
        return this.entities;
    }

    public void add_entity(On_Grid entity)
    {
        Point pt = entity.get_position();
        //System.out.println(pt.get_x() + " " + pt.get_y());
        if (this.within_bounds(pt))
        {
            On_Grid old_entity =  this.occupancy.get_cell(pt); //q
            if (old_entity instanceof Action_manager)
            {
                ((Action_manager) old_entity).clear_pending_actions();
            }
            this.occupancy.set_cell(pt, entity);
            this.entities.add(entity);

        }
    }


    public void clear_pending_actions(Action_manager entity)  //assignment 4
    {
        for (Action action : entity.get_pending_actions()) {
            this.unschedule_action(action);
        }
        entity.clear_pending_actions();
    }

    public void remove_entity_schedule(Action_manager entity)  //assignment 4
    {
        for (Action action : entity.get_pending_actions())
            this.unschedule_action(action);
        entity.clear_pending_actions();
        this.remove_entity(entity);
    }

    public void schedule_action(Action action, long time)
    {
        this.action_queue.insert(action, time);
        //System.out.println("timtimtimtimtimtimt" + time);
        //action is a functional interface, i.e. lambda, ordered by time in millsexs
    } //hash map instead of dictionary

    public void unschedule_action(Action action)
    {
        this.action_queue.remove(action);
    }

    public void update_on_time(long ticks)
    {
        //tile = new ArrayList<>(); // ordered list is processed.. ticks is a long, time is passed to it, look thoruhgh orderd list and then call actions
        ListItem next = this.action_queue.head();
        while (next != null && next.get_ord() < ticks)
        {
            this.action_queue.pop();
            next.get_item().action(ticks);
            next = this.action_queue.head();
        }
        //return tiles;
    }

    public On_Grid find_nearest(Point pt, Class<?> ent) //q
    {
        List<On_Grid> entList = new LinkedList<On_Grid>();
        List<Double> distsList = new LinkedList<Double>();

        int i = 0;
        for (On_Grid e : this.entities)
        {
            //System.out.println(ent.toString() + "is it an " + e.getClass());
            if (ent.isInstance(e))
            {
                entList.add(e);
                distsList.add(Utility.distance_sq(pt, e.get_position()));
                i++;
            }
        }
        return Utility.nearest_entity(entList, distsList);
    }

    public PImage get_background_image(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return (this.background.get_cell(pt)).get_image();
        }
        return null;
    }

}