import processing.core.PImage;

import java.util.List;
import java.util.ArrayList;
public class Entity
{
    private String name;
    private int current_img;
    private List<PImage> imgs; // imgs is a list of images... for animation
    public Entity(String name, List<PImage> imgs)
    {
        this.name = name;
        this.imgs = new ArrayList<PImage>();
        this.current_img = 0;
    }

    public List<PImage> get_images() // returns list
    {
        return this.imgs;
    }
    public PImage get_image()
    {
        return this.get_images().get(this.current_img);
    }
    public void next_image()
    {
        this.current_img = (this.current_img + 1) % this.imgs.size();
    }
    public String get_name()
    {
        return this.name;
    }
}

