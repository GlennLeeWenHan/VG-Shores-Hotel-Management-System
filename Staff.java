public class Staff extends Person
{
    private String staffID;
    private String password;

    public Staff(String name, String ICNumber, String phoneNumber, String email, String staffID, String password)
    {
        super(name, ICNumber, phoneNumber, email);
        this.staffID = staffID;
        this.password = password;
    }

    public String getStaffID()
    {
        return staffID;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
