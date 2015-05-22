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
    public void insert(Action item, long ord)
    {
        int size = this.list.size();
        int idx = 0;
        for(; idx < size && this.list.get(idx).get_ord() < ord ; ) // -q if ord is time, why in python is it .ord
        {
            idx++;
        }
        this.list.add(idx,new ListItem(item,ord)); // -q what is goin on here exactly?v  //changed from idx+1 to idx

        

    }
    public void remove(Action item)
    {
        int size = this.list.size();
        //System.out.println("Size of list: " + size);
        int idx = 0;
        //for(; idx < size && this.list.get(idx).get_item() != item;idx++) // was .item before
        while (idx < size && this.list.get(idx).get_item() != item) {
            idx++;
        }

        if (idx < size) {
            //System.out.println("idx: " + idx);
            this.list.remove(idx);
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
