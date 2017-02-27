public class randtesting
{
    public static void main(String[] args)
    {
        double d = Math.random();
        for (int i = 0; i < Integer.parseInt(args[0]); i++)
        {
            if (Math.random()==d)
                System.out.println("Repeat!");
        }
    }
}