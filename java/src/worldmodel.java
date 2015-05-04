import java.util.ArrayList;
import java.util.List;

public class WorldModel {

    // fields
    //Do we want to make fields public or private?
    private int num_rows;
    private int num_cols;
    //private int background;
    //private int occupancy;
    private List entities = new ArrayList<>();
    private int[][] background;
    private int[][] occupancy;


    // constructor
    public WorldModel(int num_rows, int num_cols, int background) {
        this.background = new int[num_cols][num_rows];
        //this.background = occ_grid.Grid(num_cols, num_rows, background);
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.occupancy = new int[num_cols][num_rows];
        //this.occupancy = occ_grid.Grid(num_cols, num_rows, null);
        //this.action_queue = ordered_list.OrderedList();
    }

    // methods
    public void clear_pending_actions()
    {
        //
    }

    public void remove_entity_schedule(Entity entity)  //Assignment 4
    {
        for (Entity.action action : entity.get_pending_actions())
            this.unschedule_action(action);
        entity.clear_pending_actions();
        this.remove_entity(entity);
    }

    public Point find_open_around(Point pt, int distance)  //need
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

    public Point blob_next_position()
    {
        return null;
    }

    public Point next_position()
    {
        return null;
    }

    public Boolean within_bounds(Point pt)  //need
    {
        return (pt.get_x() >= 0 && pt.get_x() < this.num_cols && pt.get_y() >= 0 && pt.get_y() < this.num_cols);
    }

    public Boolean is_occupied(Point pt)  //need
    {
        return (this.within_bounds(pt) && this.occupancy.get_cell(pt) != null);
    }


    public void find_nearest(Point pt )  //What does it return?   //need
    {
        pt = null;
        /*
      def find_nearest(self, pt, type):
        oftype = [(e, distance_sq(pt, e.get_position()))
                  for e in self.entities if isinstance(e, type)]
      return nearest_entity(oftype)
        */
    }

    public void add_entity(Entity entity)
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


    public List move_entity(Entity entity, Point pt)
    {
        List tiles = new ArrayList<>();

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


    public void remove_entity(Entity entity)
    {}


    public returnType remove_entity_at()
    {}

    public void schedule_action(Action action, Time time)
    {}

    public void unschedule_action(Action action)
    {    }

    public returnType update_on_time(Ticks ticks)
    {}


    public returnType get_background_image(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return (this.background.get_cell(pt)).get_image();
        }
    }

    public returnType get_background(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return this.background.get_cell(pt);
        }
    }


    public returnType set_background(Point pt, Background bgnd)
    {
        if (this.within_bounds(pt))
            this.background.set_cell(pt, bgnd);
    }

    public returnType get_tile_occupant(Point pt)
    {
        if (this.within_bounds(pt))
        {
            return this.occupancy.get_cell(pt);
        }
    }





/*

remove_entity

remove_entity_at

get_background_image

get_background

set_background

get_tile_occupant

get_entities

distance_sq  static method

nearest_entity   static method
[[],[]]

*/

}