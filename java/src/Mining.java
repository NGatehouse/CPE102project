public class Mining extends Actor
{

    private int resource_limit;
    private int resource_count;
    public Mining(String name, int resource_limit,Point position, int rate)
    {
        super(name,position,rate);
        this.resource_limit=resource_limit;
        this.resource_count=0;
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
