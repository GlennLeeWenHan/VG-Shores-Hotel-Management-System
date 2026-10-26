import java.io.BufferedReader;
import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;


public class Main
{
    public static void main(String[] args)
    {
        // new HotelManagementSystem();
        ArrayList<Guest> guests = new ArrayList<>();
        ArrayList<Staff> staffs = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Booking> bookings = new ArrayList<>();

        loadData(guests, staffs, rooms, bookings);
        new LoginPage(guests, staffs, rooms, bookings);
    }

    public static void loadData(ArrayList<Guest> guests, ArrayList<Staff> staffs, ArrayList<Room> rooms, ArrayList<Booking> bookings)
    {
        try
        {
            String line;

            BufferedReader rdGuest = new BufferedReader(new FileReader("Guest.txt"));
            while((line = rdGuest.readLine()) != null)
            {
                String[] parts = line.split("@_@");
                guests.add(new Guest(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
            rdGuest.close();

            BufferedReader rdStaff = new BufferedReader(new FileReader("Staff.txt"));
            while((line = rdStaff.readLine()) != null)
            {
                String[] parts = line.split("@_@");
                staffs.add(new Staff(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
            }
            rdStaff.close();

            BufferedReader rdRoom = new BufferedReader(new FileReader("Room.txt"));
            while((line = rdRoom.readLine()) != null)
            {
                String[] parts = line.split("@_@");
                double price = Double.parseDouble(parts[2]);
                rooms.add(new Room(parts[0], parts[1], price));
            }
            rdRoom.close();

            //String bookingID, Guest guest, Room room, int numberOfDays, Staff staff
            BufferedReader rdBooking = new BufferedReader(new FileReader("Booking.txt"));
            while((line = rdBooking.readLine()) != null)
            {
                String[] parts = line.split("@_@");
                int days = Integer.parseInt(parts[3]);
                Guest tempGuest = null;
                Staff tempStaff = null;
                Room tempRoom = null;
                for (Guest g : guests)
                {
                    if (g.getGuestID().equals(parts[1]))
                    {
                        tempGuest = g;
                    }
                }

                for (Staff s : staffs)
                {
                    if (s.getStaffID().equals(parts[4]))
                    {
                        tempStaff = s;
                    }
                }

                for (Room r : rooms)
                {
                    if (r.getRoomID().equals(parts[2]))
                    {
                        tempRoom = r;
                    }
                }

                bookings.add(new Booking(parts[0], tempGuest, tempRoom, days, tempStaff)); 
            }
            rdBooking.close();
        }
        catch(IOException e)
        {
            
        }
    }
}