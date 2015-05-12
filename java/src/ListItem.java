/**
 * Created by Nicholas on 5/10/2015.
 */
public class ListItem
{
    private Action item;
    private int ord;
    public ListItem(Action item, int ord)
    {
        this.item = item;
        this.ord = ord;
    }
    public Action get_item()
    {
        return this.item;
    }
    public int get_ord()
    {
        return this.ord;
    }

   /* public boolean equals(ListItem that) // don't know if we need this item
    {
        if(this == that)
        {
            return true;
        }
        if(!(that instanceof ListItem))
        {
            return false;
        }
        return this.item == that.item && this.ord == that.ord;
    }*/
}
