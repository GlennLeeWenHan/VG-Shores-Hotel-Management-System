public class Room
{
    private String roomID;
    private String roomType;
    private double pricePerNight;
    private boolean availability;

    public Room(String roomID, String roomType, double pricePerNight)
    {
        this.roomID = roomID;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.availability = true;
    }

    public String getRoomID()
    {
        return roomID;
    }

    public String getRoomType()
    {
        return roomType;
    }

    public double getPricePerNight()
    {
        return pricePerNight;
    }

    public boolean getAvailability()
    {
        return availability;
    }

    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }

    public void displayRoomDetails()
    {
        System.out.println("\n=== Room Details ===");
        System.out.println("Room ID        : " + roomID);
        System.out.println("Room Type      : " + roomType);
        System.out.println("Availability   : " + availability);
        System.out.println("Price Per Night: " + pricePerNight);
        System.out.println("=====================");
    }
}