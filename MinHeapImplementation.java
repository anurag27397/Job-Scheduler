
public class MinHeapImplementation
{
    private int heap[];
    private int size;
    private int maxsize;
 
    private static final int FRONT = 1;
 
    public MinHeapImplementation(int maxsize)
    {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new int[this.maxsize + 1];
        heap[0] = Integer.MIN_VALUE;
    }
 
    private int parent(int pos)
    {
        return pos / 2;
    }
 
    private int leftChild(int pos)
    {
        return (2 * pos);
    }
 
    private int rightChild(int pos)
    {
        return (2 * pos) + 1;
    }
 
    private boolean isLeaf(int pos)
    {
        if (pos >=  (size / 2)  &&  pos <= size)
        { 
            return true;
        }
        return false;
    }
 
    private void swap(int a, int b)
    {
        int tmp;
        tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }
    
    public int remove()
    {
        int pop_item = heap[FRONT];
        heap[FRONT] = heap[size--]; 
        minHeapify(FRONT);
        return pop_item;
    }
    
    public void insert(int element)
    {
        heap[++size] = element;
        int current = size;
 
        while (heap[current] < heap[parent(current)])
        {
            swap(current,parent(current));
            current = parent(current);
        }	
    }
    
 
    private void minHeapify(int pos)
    {
        if (!isLeaf(pos))
        { 
            if ( heap[pos] > heap[leftChild(pos)]  || heap[pos] > heap[rightChild(pos)])
            {
                if (heap[leftChild(pos)] < heap[rightChild(pos)])
                {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }else
                {
                    swap(pos, rightChild(pos));
                    minHeapify(rightChild(pos));
                }
            }
        }
    }
 
    
    public void MinHeapImplementation()
    {
        for (int pos = (size / 2); pos >= 1 ; pos--)
        {
            minHeapify(pos);
        }
    }

}