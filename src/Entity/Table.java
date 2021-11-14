package Entity;

public class Table{
    // public enum seatNumber {
    // 	TWO,FOUR,SIX,EIGHT,TEN
    // }
    
    /**
     * The table ID
     */
    private int tableId;

    /**
     * The indicator if table is occupied or not 
     */
    private boolean isOccupied;

    /**
     * The number of seats
     */
    int numOfSeats;

    
    /**
     * Constructor of Table which initialises a table object with the details of the parameters entered
     */
    public Table(int tableId, int numOfSeats)
    {
        this.tableId = tableId;
        this.isOccupied = false;
        this.numOfSeats = numOfSeats;

    }

    /**
     * This is the accessor method of getOccupied field.
     * @return isOccupied
     */
    public boolean getOccupied()
    {
        return this.isOccupied;
    }

    /**
     * This is the accessor method of getTableId field.
     * @return Table ID
     */
    public int getTableId()
    {
        return tableId;
    }

    /**
     * This is the accessor method of get number of seats field.
     * @return number of seats
     */
    public int getNumOfSeats()
    {
        return numOfSeats;
    }

    /**
     * This is the mutator method of set table as occupied.
     */
    public void setOccupied()
    {
        this.isOccupied = true;
    }

    /**
     * This is the mutator method of set table as unoccupied.
     */
    public void setUnoccupied()
    {
        this.isOccupied = false;
    }

}
