public class WorldModel {
        
    // fields
    //Do we want to make fields public or private?
    private int num_rows;
    private int num_cols;
    private int background;
        
    // constructor
    public WorldModel(int num_rows, int num_cols, int background) {
      this.background = occ_grid.Grid(num_cols, num_rows, background)
      this.num_rows = num_rows
      this.num_cols = num_cols
      this.occupancy = occ_grid.Grid(num_cols, num_rows, None)
      this.entities = []
      this.action_queue = ordered_list.OrderedList() 
    }
        
    // methods
        
        
}