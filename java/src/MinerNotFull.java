public class MinerNotFull extends Miner
{
    private Point position;
    public MinerNotFull(String name,int resource_limit,Point position,int rate)
    {
        super(name,resource_limit,position,rate);
    }
}
