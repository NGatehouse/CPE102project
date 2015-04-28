public class MinerFull extends Miner
{
    public MinerFull(String name,int resource_limit,Point position,int rate)
    {
        super(name,resource_limit,position,rate);
    }

    public Miner try_transform(WorldModel world)
    {
        return new MinerNotFull(this.get_name(), this.get_resource_limit(), this.get_position(), this.get_rate());
    }
}
