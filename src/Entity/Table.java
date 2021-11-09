package Entity;

public class Table{
    // public enum seatNumber {
    // 	TWO,FOUR,SIX,EIGHT,TEN
    // }

    private int tableId;
    private boolean isOccupied;
    int numOfSeats;


    public Table(int tableId, int numOfSeats)
    {
        this.tableId = tableId;
        this.isOccupied = false;
        this.numOfSeats = numOfSeats;

    }

    public boolean getOccupied()
    {
        return this.isOccupied;
    }

    public int getTableId()
    {
        return tableId;
    }

    public int getNumOfSeats()
    {
        return numOfSeats;
    }

    public void setOccupied()
    {
        this.isOccupied = true;
    }

    public void setUnoccupied()
    {
        this.isOccupied = false;
    }

}
