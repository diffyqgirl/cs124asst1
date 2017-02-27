import java.util.Arrays;
public class Vertex
{
    private double[] coords; //array of coordinates in R^n
    private boolean in_S; //whether I'm in set S
    //private int dim;
    private int id;
    public Vertex(int id, int dim)
    {
        //this.dim = dim;
        if (dim >= 1)
        {
            coords = new double[dim];
            for (int i = 0; i<coords.length; i++)
                coords[i] = Math.random();
        }
        else
            coords = null;
        in_S = false;
        this.id = id;
    }
    public boolean inS()
    {
        return in_S;
    }
    public void addToS()
    {
        in_S = true;
    }
    public int getID()
    {
        return id;
    }

    public double[] getCoords()
    {
        return coords;
    }

    public String toString()
    {
        if (coords == null) // dimension 0 case
            return "{" + id + "}";
        String crds = "(";
        for (int i = 0; i < coords.length; i++)
        {
            crds += String.format("%.2g", coords[i]);
            if (i != coords.length-1)
                crds += ", ";
        }
        crds += ")";
        return "{"+ id + ", " + crds + "}";
    }

    //TODO fix the dimension 0 case
    public double weightTo(Vertex v)
    {
        if (coords==null)
            return -1;// this shouldn't happen, we just add a random priority in the main method
        double sum = 0;
        // woo Euclidean distance in R^n from this to v
        for (int i = 0; i < coords.length; i++)
            sum += Math.pow(coords[i] - v.getCoords()[i], 2);
        return Math.sqrt(sum);
    }
}