
public class WorldModel {

    // fields
    //Do we want to make fields public or private?
    private int num_rows;
    private int num_cols;
    private int background;
    private int occupancy;
    private int entities;


    // constructor
    public WorldModel(int num_rows, int num_cols, int background) {
        this.background = occ_grid.Grid(num_cols, num_rows, background);
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.occupancy = occ_grid.Grid(num_cols, num_rows, null); // null was None fyi
        this.entities = [];

        //this.action_queue = ordered_list.OrderedList();
    }

    // methods
    public void clear_pending_actions()
    {
        //
    }

    public void remove_entity(Entity entity)
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

    public Void find_nearest(Point pt );  //What does it return?   //need
    {
        /*
      def find_nearest(self, pt, type):
        oftype = [(e, distance_sq(pt, e.get_position()))
                  for e in self.entities if isinstance(e, type)]
      return nearest_entity(oftype)
        */
    }

    public Void add_entity(Entity entity)
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
            this.entities.append(entity);
        }
    }

}

/*
move_entity

remove_entity second one

remove_entity_at

get_background

set_background

get_tile_occupant

get_entities

distance_sq  static method

nearest_entity   static method

*/

