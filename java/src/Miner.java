public class Miner extends Mining
    //implements Animation_manager missing data imgs,animation_rate
{
    private Point position;
    public Miner(String name,int resource_limit,Point position,int rate)
    {
        super(name,resource_limit,position,rate);
    }

    public String entity_string()
    {
        return 'miner' + this.get_name() + Integer.toString(this.position.get_x()) + Integer.toString(this.position.get_y()) + Integer.toString(this.resource_limit) + Integer.toString(this.rate);
    }

}
