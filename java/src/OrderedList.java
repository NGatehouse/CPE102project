import java.util.ArrayList;
import java.util.List;
/**
 * Created by Nicholas on 5/8/2015.
 */
public class OrderedList
{
    private List<ListItem> list;
    public OrderedList(List<ListItem> list)
    {
        this.list = list;

    }
    public void insert(Action item, int ord)
    {
        int size = this.list.size();
        int idx = 0;
        for(; idx < size && this.list.get(idx).get_ord() < ord ; ) // -q if ord is time, why in python is it .ord
        {
            idx++;
        }
        this.list.add(idx+1,new ListItem(item,ord)); // -q what is goin on here exactly?

        

    }
    public void remove(Action item)
    {
        int size = this.list.size();
        int idx = 0;
        for(; idx < size && this.list.get(idx).get_item() != item;idx++) // was .item before
        if (idx < size)
        {
            this.list.remove(0);
        }
    }

    public ListItem head()
    {
        if(this.list.size() > 0)
        {
            return this.list.get(0);
        }
        else
        {
           return  null;
        }
    }

    public void pop() // what does pop do in
    {
        if (this.list.size() > 0) // confusion in translation list isn't a boolean, just checking to see if the list isn't empty?
        {
            this.list.remove(0);
        }
    }
}
