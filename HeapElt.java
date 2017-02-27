public class HeapElt
{
    private Vertex v1;
    private Vertex v2;
    private double p;
    // represents an edge going from v1 (in MST) to v2 (not in MST)
    public HeapElt(Vertex v1, Vertex v2, double p)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.p = p;
    }
    public double getPriority()
    {
        return p;
    }

    public void setPriority(double p)
    {
        this.p = p;
    }

    public Vertex getV1()
    {
        return v1;
    }

    public void setV1(Vertex v1)
    {
        this.v1=v1;
    }

    public Vertex getV2()
    {
        return v2;
    }

    public String toString()
    {
        return "{" + v1 + ", " + v2 + ", " + String.format("%.2g", p) + "}";
    }
}