import java.util.ArrayList;
import java.util.Arrays; // just for printing
public class MinHeap
{
    /*
    Arraylist is just an array that doubles its length when it overfills--insertion is 
    still amortized O(1)
    */
    ArrayList<HeapElt> heap;
    int heapsize;
    int[] ptrs;
    /* ptrs [x] contains the location of the vertex with id x in the heap
    if x is in the heap, -1 otherwise. necessary for decreasekey process in insert
    */
    public MinHeap(int n) //n is the maximum number of vertices
    {
        heapsize = 0;
        heap = new ArrayList<HeapElt>();
        ptrs = new int[n];
        for (int i = 0; i < n; i++)
        {
            ptrs[i] = -1;
        }
    }

    // insert v into our this heap with priority p
    public void insert(Vertex v1, Vertex v2, double p)
    {
        System.out.println("{" + v1 + ", " + v2 + ", " + String.format("%.4g", p) + "}");
        if(ptrs[v2.getID()] == -1) // there is no edge to v2 in the heap
        {
            heap.add(new HeapElt(v1, v2, p));
            
            int loc = heapsize;//keep track of where v currently is

            ptrs[v2.getID()] = loc;
            int parent = parentIdx(loc);
            while (parent >= 0)
            {
                if (heap.get(parent).getPriority()>p)
                {
                    swap(loc, parent);
                    loc = parent;
                    parent = parentIdx(loc);
                }
                else
                {
                    break;
                }
            }
            heapsize++;
        }
        else // there's already a way to reach v2 in the heap
        {
            HeapElt HE = heap.get(ptrs[v2.getID()]);
            double currentPriority = HE.getPriority();
            if (p<currentPriority) // we need to update our entry for the best edge to v2 in the heap
            {
                HE.setPriority(p);
                HE.setV1(v1);
                int loc = ptrs[v2.getID()];
                int parent = parentIdx(loc);
                while (parent >= 0)
                {
                    if (heap.get(parent).getPriority()>p)
                    {
                        swap(loc, parent);
                        loc = parent;
                        parent = parentIdx(loc);
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
    }

    public HeapElt deleteMin()
    {
        HeapElt HE = heap.get(0);
        HeapElt last = heap.get(heapsize - 1);
        swap(heapsize-1, 0);// swap first and last elts
        heap.remove(heapsize - 1); // remove HE
        ptrs[HE.getV2().getID()] = -1;//v2 is now no longer indexed in our heap

        heapsize--;
        //now we fix the location of last
        int loc = 0; // current location of last
        int r = rightChildIdx(loc);
        int l = leftChildIdx(loc);
        double p = last.getPriority();
        while (l < heapsize)
        {
            int target = loc;
            // r exists, r is lower priority than l, and r is lower priority than p
            if (r<heapsize && heap.get(r).getPriority()<heap.get(l).getPriority() && heap.get(r).getPriority()<p)
                target = r;
            else if (heap.get(l).getPriority()<p) //l is lower priority than p
                target = l;
            if (target == loc)
                break;
            else
            {
                swap(target, loc);
                loc = target;
                r = rightChildIdx(loc);
                l = leftChildIdx(loc);

            }
            
        }
        return HE;
    }

    private void swap(int idx1, int idx2)
    {
        HeapElt tempHE = heap.get(idx1);
        heap.set(idx1, heap.get(idx2));
        heap.set(idx2, tempHE);
        ptrs[heap.get(idx1).getV2().getID()] = idx1;
        ptrs[heap.get(idx2).getV2().getID()] = idx2;

    }

    private int leftChildIdx(int idx)
    {
        return 2*idx + 1;
    }

    private int rightChildIdx(int idx)
    {
        return 2*idx + 2;
    }

    private int parentIdx(int idx)
    {
        if (idx == 0)
            return -1; // signal that we've reached the root of the heap
        return (idx-1)/2;
    }

    public String toString()
    {
        return Arrays.toString(this.heap.toArray());
    }

    public void testInvariants()
    {
        testInvariantsHelper(0);
    }

    private void testInvariantsHelper(int i)
    {
        int r = rightChildIdx(i);
        int l = leftChildIdx(i);
        if (r<heapsize)
        {
            assert(heap.get(i).getPriority()<heap.get(r).getPriority());
            testInvariantsHelper(r);
        }
        if (l<heapsize)
        {
            assert(heap.get(i).getPriority()<heap.get(l).getPriority());
            testInvariantsHelper(l);
        }
    }

    public boolean isEmpty()
    {
        return heapsize <= 0;
    }
}