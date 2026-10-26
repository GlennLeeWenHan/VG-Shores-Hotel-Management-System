public class Booking
{
    private String bookingID;
    private Guest guest;
    private Room room;
    private int numberOfDays;
    private Staff staff;
    
    public Booking(String bookingID, Guest guest, Room room, int numberOfDays, Staff staff)
    {
        this.bookingID = bookingID;
        this.guest = guest;
        this.room =room;
        this.numberOfDays = numberOfDays;
        this.staff = staff;
        room.setAvailability(false);
    }

    public String getBookingID()
    {
        return bookingID;
    }

    public int getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays)
    {
        this.numberOfDays = numberOfDays;
    }

    public Guest getGuest()
    {
        return guest;
    }

    public Room getRoom()
    {
        return room;
    }

    public Staff getStaff()
    {
        return staff;
    }

    public double calculateTotal()
    {
        return numberOfDays * room.getPricePerNight();
    }

    public void displayBooking()
    {
        System.out.println("\n=== Booking Information ===");
        System.out.println("Booking ID    : " + bookingID);
        System.out.println("Guest Name    : " + guest.getName());
        System.out.println("Room ID       : " + room.getRoomID());
        System.out.println("Room Type     : " + room.getRoomType());
        System.out.println("Number of Days: " + numberOfDays);
        System.out.println("Managed By    : " + staff.getName());
        System.out.println("Total Price   : RM " + String.format("%.2f", calculateTotal()));
    }
}
