/**
 * Created by Nicholas on 5/4/2015.
 */
public class Grid
{
    private static final int EMPTY =0;
    private static final int GATHERER =1;
    private static final int GENERATOR=2;
    private static final int RESOURCE = 3;
    private double width;
    private double height;
    private int[][] cells;

    public Grid(int width, int height, int[][] occupancy_value)
    {
        this.width = width;
        this.height = height;

        for(int i = 0; i<this.height;i++)
        {
            this.cells.add(int[]);
            for(int j=0;j<this.width;j++)
            {
                this.cells[i].add(occupancy_value);
            }
        }

    }
    public void set_cell(Point p,int[][] value)
    {
        this.cells[p.get_y()][p.get_x()]=value;
    }
    public cells get_cell(Point p)
    {
        return this.cells[p.get_y()][p.get_x()];
    }

}
