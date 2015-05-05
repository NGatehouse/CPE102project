public class Mining extends Actor
{
    final int RESOURCE_COUNT = 0;
    private int resource_limit;
    private int resource_count;
    public Mining(String name, int resource_limit,Point position, int rate,int resource_count)
    {
        super(name,position,rate);
        this.resource_limit=resource_limit;
        this.resource_count=RESOURCE_COUNT;
    }

    public int get_resource_limit()
    {
        return this.resource_limit;
    }

    public int get_resource_count()
    {
        return this.resource_count;
    }
    public void set_resource_count(int n)
    {
        this.resource_count=n;
    }
}
