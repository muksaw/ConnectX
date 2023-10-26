/**
* Represents a single empty spot that can contain either no chip, a red chip,
* or a yellow chip. This is used to construct the entire grid design.
*
* @author Mukul Sauhta
* @author Raven Llarina
* @author Mustafa Irfan
* @author Ryan Foster
*/
public class Spot
{
    /** the integer which represents the color of the spot,
    * 0 for empty, 1 for yellow, 2 for red
    */
    private int color;

    /**
    * Constructor of the spot class
    */
    public Spot()
    {
        this.color = 0;
    }

    /**
    * This is the setter for the spot color.
    * @param color the color that we set this color to.
    */
    public void setSpotColor(int color)
    {
        this.color = color;
    }

    /**
    * This is the getter for the spot color.
    * @return simply returns the color
    */
    public int getSpotColor()
    {
        return color;
    }
}
