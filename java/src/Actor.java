import java.util.ArrayList;
import java.util.List;

public class Actor extends On_Grid
   //implements Action_manager
{
    List<Action> pending_actions = new ArrayList<Action>();
    private int rate;
    public Actor(String name,Point position,int rate)
    {
        super(name,position);
        this.rate = rate;
    }



    public int get_rate()
    {
        return this.rate;
    }

}
