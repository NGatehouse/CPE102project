public class Actor extends On_Grid
   //implements Action_manager
{
    private int rate;
    private Point position;
    public Actor(String name,Point position,int rate)
    {
        super(name,position);
        this.rate = rate;
    }

    protected int get_rate()
    {
        return this.rate;
    }
   //delete me.. NO
}