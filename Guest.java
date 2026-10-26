public class Guest extends Person
{
    private String guestID;

    public Guest(String name, String ICNumber, String phoneNumber, String email, String guestID)
    {
        super(name, ICNumber, phoneNumber, email);
        this.guestID = guestID;
    }

    public String getGuestID()
    {
        return guestID;
    }
}