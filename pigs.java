import java.util.Arrays;//for printing
import java.util.ArrayList;
class pigs
{
    public static void main(String[] args)
    {
        final int VNUM = Integer.parseInt(args[1]); // number of vertices
        final int TRIALS = Integer.parseInt(args[2]); // number of trials
        final int DIM = Integer.parseInt(args[3]); // number of dimensions
        double weightavg = 0; //average over TRIALS trials
        for (int j = 1; j <= TRIALS; j++)
        {
            System.out.println("Trial " + j);
            double weight = 0; //records the total weight of all edges in the mst
            ArrayList<HeapElt> MST = new ArrayList<HeapElt>();
            Vertex[] vertices = new Vertex[VNUM];
            for (int i = 0; i < VNUM; i++)
            {
                vertices[i] = new Vertex(i, DIM);
            }
            double[] dist = new double[VNUM];
            Vertex[] prev = new Vertex[VNUM];
            MinHeap h = new MinHeap(VNUM);
            assert(h.isEmpty());
            //vertex 0 will be our initial vertex. load up the heap with all adjacent edges
            for (int i = 0; i < VNUM; i++)
            {
                if (DIM > 0)
                    h.insert(vertices[0], vertices[i], vertices[0].weightTo(vertices[i]));
                else 
                {
                    if (i == 0)
                        h.insert(vertices[0], vertices[0], 0);
                    else
                        h.insert(vertices[0], vertices[i], Math.random());
                }
            }
            vertices[0].addToS(); // flag our initial vertex as in our tree
            //System.out.println(h);
            //System.out.println(Arrays.toString(h.ptrs));
            h.testInvariants();
            while(!h.isEmpty())
            {
                HeapElt HE = h.deleteMin();
                HE.getV2().addToS();//we are adding v2 to our MST via the edge from v1
                MST.add(HE);
                weight += HE.getPriority();
                for (int i = 0; i < VNUM; i++)
                {
                    if (!vertices[i].inS())
                    {
                        if (DIM > 0)
                            h.insert(vertices[HE.getV2().getID()], vertices[i], vertices[HE.getV2().getID()].weightTo(vertices[i]));
                        else
                        {
                            h.insert(vertices[HE.getV2().getID()], vertices[i], Math.random());
                        }
                    }

                }
                if (h.isEmpty())
                    System.out.println("highest priority edge is " + String.format("%.2g", HE.getPriority()));
            }
            weightavg += weight;
            //System.out.println("vertices are: " + Arrays.toString(vertices));
            System.out.println("MST is: " + Arrays.toString(MST.toArray()));
            System.out.println("MST has weight " + String.format("%.2g", weight));
        }
        System.out.println("Average weight over all trials is " + String.format("%.6g", weightavg/TRIALS));
    }
}