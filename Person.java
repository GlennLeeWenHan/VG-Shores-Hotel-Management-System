abstract class Person
{
    private String name;
    private String ICNumber;
    private String phoneNumber;
    private String email;

    public Person(String name, String ICNumber, String phoneNumber, String email)
    {
        this.name = name;
        this.ICNumber = ICNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getICNumber()
    {
        return ICNumber;
    }

    public void setICNumber(String ICNumber)
    {
        this.ICNumber = ICNumber;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
