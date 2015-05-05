/**
 * Created by Nicholas on 5/4/2015.
 */
public class Grid
{
    private static final int EMPTY =0;
    private static final int GATHERER =1;
    private static final int GENERATOR=2;
    private static final int RESOURCE = 3;
    private On_Grid[][] cells;
    public Grid(int width, int height)
    {
        this.cells = new On_Grid[height][width];
    }
    public void set_cell(Point p,On_Grid value)
    {
        this.cells[p.get_y()][p.get_x()]=value;
    }
    public On_Grid get_cell(Point p)
    {
        return this.cells[p.get_y()][p.get_x()];
    }

}
