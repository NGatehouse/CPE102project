import java.util.ArrayList;
import java.util.List;
/**
 * Created by Nicholas on 5/8/2015.
 */
public class OrderedList
{
    private List<Double> list;
    public OrderedList(int list)
    {
        this.list = new ArrayList<>();

    }
    public void insert(Action item, int ord)//what is ord and item?
    {
        int size = this.list.size();
        int idx = 0;
        for(; idx < size && this.list.get(idx).ord < ord ; )
        {
            idx++;
        }
        this.list[idx] = [ListItem(item,ord)]

        

    }
    public void remove(Action item)
    {
        int size = this.list.size();
        int idx = 0;
        for(; idx < size && this.list.get(idx).item != item;idx++)
        {}
        if (idx < size)
        {
            this.list[idx].remove();
        }
    }

    public void head()
    {
        return this.list[0]
        if(this.list)
        {

        }
        else
        {
           return  null;
        }
    }

    public void pop()
    {
        if (this.list)
        {
            return this.list[0].remmove();
        }
    }
}
