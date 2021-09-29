package helpers;

public class Parse
{
    /**
     * Parse a String to int
     * @param string String to parse
     * @return the String as an int. If cannot be parsed will throw an exception
     */
    public static int toInt(String string) throws Exception
    {
        try
        {
            return Integer.parseInt(string);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("The value '%s' cannot be parsed to int", string));
        }
    }

    /**
     * Parse a String to long
     * @param string String to parse
     * @return the String as a long. If cannot be parsed will throw an exception
     */
    public static long toLong(String string) throws Exception
    {
        try
        {
            return Long.parseLong(string);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("The value '%s' cannot be parsed to long", string));
        }
    }

    /**
     * Parse a String to double
     * @param string String to parse
     * @return the String as a double. If cannot be parsed will throw an exception
     */
    public static double toDouble(String string) throws Exception
    {
        try
        {
            return Double.parseDouble(string);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("The value '%s' cannot be parsed to double", string));
        }
    }

    /**
     * Parse a String to float
     * @param string String to parse
     * @return the String as a float. If cannot be parsed will throw an exception
     */
    public static float toFloat(String string) throws Exception
    {
        try
        {
            return Float.parseFloat(string);
        }
        catch (Exception e)
        {
            throw new Exception(String.format("The value '%s' cannot be parsed to float", string));
        }
    }

}
