/**
 * Created by Nicholas on 5/10/2015.
 */
public class image_store
{
    private String DEFAULT_IMAGE_NAME = "background_default";
    private List<int> DEFAULT_IMAGE_COLOR = new ArrayList[128,128,128,0];

    public void create_default_image(int tile_width,int tile_height)
    {
        type surf = // pygame stuff;
        surf.fill(DEFAULT_IMAGE_COLOR);
    }

    public void load_images(file filename, int tile_width, int tile_height)
    {
        images = {}; // hashmap what is a hashmap?
        with as?
        // File I/O
    }

    public void process_image_line(img images, String line)
    {
        List<String> attrs = line.split();
        if (attrs.size() >= 2)
        {
            key = attrs[0];
            img = //pygame

            if (img)
            {
                imgs = get_images_internal(images,key);
                imgs.add(img);
                images[key] = imgs;

                if (attrs.size() == 6)
                {
                    int r = (int)(attrs[2]);
                    int g = (int)(attrs[2]);
                    int b = (int)(attrs[2]);
                    int a = (int)(attrs[2]);
                    img.set_colorkey(pygame.Color(r,g,b,a))
                }
            }
        }
    }
    public List get_images_internal(img images, type key)
    {
        if( key in images)
        {
            return images[key];
        }
        else
        {
            return [];
        }
    }

    public get_images(img images, int key)
    {
        if (key in images)
        {
            return images[keys];
        }
        else
        {
            return images[DEFAULT_IMAGE_NAME];
        }
    }
}
