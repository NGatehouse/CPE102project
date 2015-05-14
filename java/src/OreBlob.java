import java.util.List;

public class OreBlob extends Actor
{
    private int animation_rate; //q
    public OreBlob(String name,Point position, int rate, List<PImage> imgs,int animation_rate)
    {
        super(name,position,rate,imgs);
        this.animation_rate = animation_rate;
    }


}
