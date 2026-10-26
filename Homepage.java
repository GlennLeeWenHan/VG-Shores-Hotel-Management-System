import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Homepage extends JFrame
{
    private ArrayList<Guest> guests;
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;

    public Homepage(ArrayList<Guest> guests, ArrayList<Staff> staffs, ArrayList<Room> rooms, ArrayList<Booking> bookings, Staff usingStaff)
    {
        /*---------------------------*/
        this.guests = guests;
        this.rooms = rooms;
        this.bookings = bookings;
        /*---------------------------*/

        /*---------------------------------------------------*/
        setTitle("VG Hotel Mangement System");
        setSize(1000, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Image logoImage = Toolkit.getDefaultToolkit().getImage("logo.png");
        setIconImage(logoImage);
        /*---------------------------------------------------*/


        // Guest Management Panel
        // ---------------------------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------------

        // Main Panel
        JPanel guestManagementPanel = new JPanel();
        guestManagementPanel.setLayout(new BorderLayout());
        
        // Panel 1 (Contain Panel 2 and Panel 3)
        JPanel guestControlPanel = new JPanel();
        guestControlPanel.setLayout(new BorderLayout());
        guestManagementPanel.add(guestControlPanel);

        // Panel 2
        JPanel guestControlPanelTitlePanel = new JPanel();
        guestControlPanelTitlePanel.setLayout(new BorderLayout());
        guestControlPanelTitlePanel.setPreferredSize(new Dimension(0, 30));
        guestControlPanel.add(guestControlPanelTitlePanel, BorderLayout.NORTH);

        JLabel guestManagementPanelTitle = new JLabel("Guest Management");
        guestManagementPanelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        guestControlPanelTitlePanel.add(guestManagementPanelTitle);

        // Panel 3 (Contain guest add and search panel && inner guest control panel)
        JPanel guestActualControlPanel = new JPanel();
        guestActualControlPanel.setLayout(new BorderLayout());

        // Panel 4 (Contain Panel 5)
        guestControlPanel.add(guestActualControlPanel, BorderLayout.CENTER);

        JButton addGuestButton = new JButton("Add Guest");

        JTextField guestSearchTextField = new JTextField();

        // Panel 5 (Contain add guest button and search guest text field)
        JPanel addSearchGuestPanel = new JPanel();
        addSearchGuestPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        addSearchGuestPanel.add(addGuestButton);

        addGuestButton.setPreferredSize(new Dimension(95, 20));
        guestSearchTextField.setPreferredSize(new Dimension(200, 20));

        addSearchGuestPanel.add(guestSearchTextField);
        guestActualControlPanel.add(addSearchGuestPanel, BorderLayout.NORTH);

        // Panel 6 
        JPanel innerGuestActualControlPanel = new JPanel();
        innerGuestActualControlPanel.setLayout(new BorderLayout());
        innerGuestActualControlPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        // Panel 7 (Contains the 3 guest categories (Index number, name, guest ID))
        JPanel guestCategoryPanel = new JPanel();
        guestCategoryPanel.setLayout(new BorderLayout());

        JLabel guestCategoryLabel = new JLabel(String.format("%-6s", "No.") + String.format("%-40s", "Name") + "[Guest ID]");
        guestCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        guestCategoryPanel.add(guestCategoryLabel);

        JList<String> guestJList = new JList<>();
        guestJList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        updateGuestList(guestJList, "");


        //Document Listener for guestSearchTextField
        guestSearchTextField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent e)
            {
                updateGuestList(guestJList, guestSearchTextField.getText());
            }

            public void removeUpdate(DocumentEvent e)
            {
                updateGuestList(guestJList, guestSearchTextField.getText());
            }

            public void changedUpdate(DocumentEvent e)
            {
                updateGuestList(guestJList, guestSearchTextField.getText());
            }
        });

        guestJList.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int index = guestJList.locationToIndex(e.getPoint());
                
                if (index != -1)
                {
                    Rectangle cellBounds = guestJList.getCellBounds(index, index);

                    if (cellBounds != null && cellBounds.contains(e.getPoint()))
                    {
                        Guest selectedGuest = guests.get(index);
                        new GuestDetailsDialog(Homepage.this, selectedGuest, guests, bookings);
                        updateGuestList(guestJList, guestSearchTextField.getText());
                    }
                }
            }
        });

        addGuestButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new AddGuestDialog(Homepage.this, guests);
                updateGuestList(guestJList, guestSearchTextField.getText());
            }
        });

        
        JScrollPane guestScrollPane = new JScrollPane(guestJList);

        innerGuestActualControlPanel.add(guestCategoryPanel, BorderLayout.NORTH);
        innerGuestActualControlPanel.add(guestScrollPane, BorderLayout.CENTER);

        guestActualControlPanel.add(innerGuestActualControlPanel, BorderLayout.CENTER);

        // ---------------------------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------------


        //Room Management Panel
        // ===========================================================================================================================
        // ===========================================================================================================================
        JPanel roomManagementPanel = new JPanel();
        roomManagementPanel.setLayout(new BorderLayout());

        JLabel roomTitleLabel = new JLabel("Room Management");
        roomTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        roomManagementPanel.add(roomTitleLabel, BorderLayout.NORTH);

        JPanel roomControlPanel = new JPanel();
        roomControlPanel.setLayout(new BorderLayout());
        roomControlPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        
        JPanel roomAddAndSearchPanel = new JPanel();
        roomAddAndSearchPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton addRoomButton = new JButton("Add Room");
        addRoomButton.setPreferredSize(new Dimension(95, 20));
        JTextField searchRoomField = new JTextField();
        searchRoomField.setPreferredSize(new Dimension(200, 20));

        roomAddAndSearchPanel.add(addRoomButton);
        roomAddAndSearchPanel.add(searchRoomField);

        roomControlPanel.add(roomAddAndSearchPanel, BorderLayout.NORTH);

        JList<String> roomJList = new JList<>();
        roomJList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        updateRoomList(roomJList, "");

        JPanel roomListPanel = new JPanel();
        roomListPanel.setLayout(new BorderLayout());

        JScrollPane roomScrollPane = new JScrollPane(roomJList);
        roomListPanel.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        JLabel roomListCategories = new JLabel(String.format("%-6s", "No.") + String.format("%-15s", "[Room ID]") + String.format("%-25s", "Room Type") + String.format("%-25s", "Price/night (RM)") + "Room Availability");

        roomListCategories.setFont(new Font("Monospaced", Font.PLAIN, 14));

        roomListPanel.add(roomScrollPane, BorderLayout.CENTER);
        roomListPanel.add(roomListCategories, BorderLayout.NORTH);

        roomControlPanel.add(roomListPanel, BorderLayout.CENTER);

        searchRoomField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent e)
            {
                updateRoomList(roomJList, searchRoomField.getText());
            }

            public void removeUpdate(DocumentEvent e)
            {
                updateRoomList(roomJList, searchRoomField.getText());
            }

            public void changedUpdate(DocumentEvent e)
            {
                updateRoomList(roomJList, searchRoomField.getText());
            }
        });

        roomJList.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int index = roomJList.locationToIndex(e.getPoint());

                if (index != -1)
                {
                    Rectangle cellBounds = roomJList.getCellBounds(index, index);

                    if (cellBounds != null && cellBounds.contains(e.getPoint()))
                    {  
                        Room selectedRoom = rooms.get(index);
                        new RoomDetailsDialog(Homepage.this, rooms, selectedRoom, bookings);
                        updateRoomList(roomJList, searchRoomField.getText());
                    }
                }
            }
        });

        addRoomButton.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent e)
           {
                new AddRoomDialog(Homepage.this, rooms);
                updateRoomList(roomJList, searchRoomField.getText());
           } 
        });

        roomManagementPanel.add(roomControlPanel, BorderLayout.CENTER);

        // ===========================================================================================================================
        // ===========================================================================================================================


        // Booking Management Panel
        // ---------------------------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------------

        JPanel bookingManagementPanel = new JPanel();
        bookingManagementPanel.setLayout(new BorderLayout());

        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BorderLayout());
        bookingPanel.setBackground(Color.blue);

        //Title
        JLabel bookingTitleLabel = new JLabel("Booking Management");
        bookingTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bookingManagementPanel.add(bookingTitleLabel, BorderLayout.NORTH);

        // Add and Search Panel
        JPanel addAndSearchBookingPanel = new JPanel();
        addAndSearchBookingPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        addAndSearchBookingPanel.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));
        JButton addBookingButton = new JButton("Add Booking");
        JTextField searchBookingField = new JTextField();
        addBookingButton.setPreferredSize(new Dimension(120, 20));
        searchBookingField.setPreferredSize(new Dimension(200, 20));
        addAndSearchBookingPanel.add(addBookingButton);
        addAndSearchBookingPanel.add(searchBookingField);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel bookingCategoryLabel = new JLabel(String.format("%-6s", "No.") + String.format("%-17s", "[Booking ID]") + String.format("%-17s", "[Guest ID]") + String.format("%-15s", "[Room ID]") + String.format("%-17s", "Room Type") + String.format("%-9s", "Days") + String.format("%-17s", "Total Price") + "Managing Staff");
        bookingCategoryLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JList<String> bookingList = new JList<>();
        JScrollPane bookingScrollPane = new JScrollPane(bookingList);
        bookingList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        updateBookingList(bookingList, searchBookingField.getText());
        
        searchBookingField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent e)
            {
                updateBookingList(bookingList, searchBookingField.getText());
            }

            public void removeUpdate(DocumentEvent e)
            {
                updateBookingList(bookingList, searchBookingField.getText());
            }

            public void changedUpdate(DocumentEvent e)
            {
                updateBookingList(bookingList, searchBookingField.getText());
            }
        });

        bookingList.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                int index = bookingList.locationToIndex(e.getPoint());

                if (index != -1)
                {
                    Rectangle cellBounds = bookingList.getCellBounds(index, index);

                    if (cellBounds != null && cellBounds.contains(e.getPoint()))
                    {
                        Booking selectedBooking = bookings.get(index);
                        new BookingDetailsDialog(Homepage.this, bookings, selectedBooking);
                        updateBookingList(bookingList, searchBookingField.getText());
                    }
                }
            }
        });

        addBookingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new AddBookingDialog(Homepage.this, guests, rooms, staffs, usingStaff, bookings);
                updateBookingList(bookingList, searchBookingField.getText());
            }
        });
        
        
        listPanel.add(bookingCategoryLabel, BorderLayout.NORTH);
        listPanel.add(bookingScrollPane, BorderLayout.CENTER);

        bookingPanel.add(listPanel, BorderLayout.CENTER);
        bookingPanel.add(addAndSearchBookingPanel, BorderLayout.NORTH);

        bookingManagementPanel.add(bookingPanel, BorderLayout.CENTER);
        // ---------------------------------------------------------------------------------------------------------------------------
        // ---------------------------------------------------------------------------------------------------------------------------

        

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        cardPanel.add(guestManagementPanel, "Guest Management");
        cardPanel.add(roomManagementPanel, "Room Management");
        cardPanel.add(bookingManagementPanel, "Booking Management");

        JButton logoutButton = new JButton("Logout");
        JLabel currentStaffLabel = new JLabel("Logged in as (" + usingStaff.getName() + ")    ");
        currentStaffLabel.setFont(new Font("Serif", Font.ITALIC, 12));
        currentStaffLabel.setForeground(Color.gray);


        String[] pageOptions = {"Guest Management", "Room Management", "Booking Management"};
        JComboBox<String> pageSelector = new JComboBox<>(pageOptions);

        pageSelector.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String selectedPage = (String) pageSelector.getSelectedItem();
                cardLayout.show(cardPanel, selectedPage);

                updateGuestList(guestJList, guestSearchTextField.getText());
                updateBookingList(bookingList, searchBookingField.getText());
                updateRoomList(roomJList, searchRoomField.getText());
            }
        });

        logoutButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveData(guests, staffs, rooms, bookings);
                new LoginPage(guests, staffs, rooms, bookings);
                dispose();
            }
        });

        setLocationRelativeTo(null);
        //panel for the top JCOmbobox panel
        //***********************************
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel innerBorderPanelLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel innerBorderPanelRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel pageSelectorLabel = new JLabel("Select Operation:");
        //***********************************

        innerBorderPanelLeft.add(pageSelectorLabel);
        innerBorderPanelLeft.add(pageSelector);

        innerBorderPanelRight.add(currentStaffLabel);
        innerBorderPanelRight.add(logoutButton);

        topPanel.add(innerBorderPanelLeft, BorderLayout.WEST);
        topPanel.add(innerBorderPanelRight, BorderLayout.EAST);


        // innerBorderPanelRight
        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
        setResizable(true);
    }

    public void updateGuestList(JList<String> guestJList, String searchText)
    {
        ArrayList<String> filteredDisplayList = new ArrayList<>();

        for (int i = 0 ; i < guests.size() ; i++)
        {
            Guest currentGuest = guests.get(i);

            if (currentGuest.getName().toLowerCase().contains(searchText.toLowerCase()))
            {
                int guestNumber = i + 1;
                String formattedGuestNumber = String.format("%-6s", guestNumber + ".");
                String formattedName = String.format("%-40s", currentGuest.getName());

                filteredDisplayList.add(formattedGuestNumber + formattedName + "[" + currentGuest.getGuestID() + "]");
            }
        }

        //Converting back the arrayList back to regular array because JList takes in array, not arraylist
        String[] filteredArray = new String[filteredDisplayList.size()];

        for (int i = 0 ; i < filteredDisplayList.size() ; i++)
        {
            filteredArray[i] = filteredDisplayList.get(i);
        }

        guestJList.setListData(filteredArray);
    }

    public void updateRoomList(JList<String> roomJList, String searchText)
    {
        ArrayList<String> filteredDisplayList = new ArrayList<>();

        for (int i = 0 ; i < rooms.size() ; i++)
        {
            Room currentRoom = rooms.get(i);

            if (currentRoom.getRoomType().toLowerCase().contains(searchText.toLowerCase()) || currentRoom.getRoomID().toLowerCase().contains(searchText.toLowerCase()))
            {
                int roomNumber = i + 1;
                String formattedRoomNumber = String.format("%-6s", roomNumber + ".");
                String formattedRoomID = String.format("%-15s", "[" + currentRoom.getRoomID() + "]");
                String formattedRoomType = String.format("%-25s", "[" + currentRoom.getRoomType() + "]");
                String formattedPrice = String.format("%-25s", "RM " + String.format("%.2f", currentRoom.getPricePerNight()));
                String availability;

                if (currentRoom.getAvailability())
                {
                    availability = "VACANT";
                }
                else
                {
                    availability = "OCCUPIED";
                }
                filteredDisplayList.add(formattedRoomNumber + formattedRoomID + formattedRoomType + formattedPrice + availability);
            }
        }

        String[] filteredArray = new String[filteredDisplayList.size()];

        for (int i = 0 ; i < filteredDisplayList.size() ; i++)
        {
            filteredArray[i] = filteredDisplayList.get(i);
        }

        roomJList.setListData(filteredArray);
    }

    public void updateBookingList(JList<String> bookingList, String searchText)
    {
        ArrayList<String> filteredDisplayList = new ArrayList<>();

        for (int i = 0 ; i < bookings.size() ; i ++)
        {
            // 6 17 17 10 17 9 17
            Booking currentBooking = bookings.get(i);
            String filteredSearchText = searchText.toLowerCase();
            String filteredCurrentBookingID = currentBooking.getBookingID().toLowerCase();
            String filteredCurrentBookingRoomID = currentBooking.getRoom().getRoomID().toLowerCase();
            String filteredCurrentBookingGuestID = currentBooking.getGuest().getGuestID().toLowerCase();
            String filteredCurrentBookingRoomType = currentBooking.getRoom().getRoomType().toLowerCase();

            if (filteredCurrentBookingID.contains(filteredSearchText) || filteredCurrentBookingRoomID.contains(filteredSearchText) || filteredCurrentBookingGuestID.contains(filteredSearchText) || filteredCurrentBookingRoomType.contains(filteredSearchText))
            {
                int bookingNumber = i + 1;
                String formattedBookingNumber = String.format("%-6s", bookingNumber + ".");
                String formattedBookingID = String.format("%-17s", "[" + currentBooking.getBookingID() + "]");
                String formattedGuestID = String.format("%-17s", "[" + currentBooking.getGuest().getGuestID() + "]");
                String formattedRoomID = String.format("%-15s", "[" + currentBooking.getRoom().getRoomID() + "]");
                String formattedRoomType = String.format("%-17s", currentBooking.getRoom().getRoomType());
                String formattedDays = String.format("%-9s", currentBooking.getNumberOfDays());
                String formattedPrice = String.format("%-17s", "RM " + String.format("%.2f", currentBooking.calculateTotal()));
                String formattedStaffName = currentBooking.getStaff().getName();

                filteredDisplayList.add(formattedBookingNumber + formattedBookingID + formattedGuestID + formattedRoomID + formattedRoomType + formattedDays + formattedPrice + formattedStaffName);
            }
        }

        String[] filteredArray = new String[filteredDisplayList.size()];

        for (int i = 0 ; i < filteredDisplayList.size() ; i++)
        {
            filteredArray[i] = filteredDisplayList.get(i);
        }


        bookingList.setListData(filteredArray);
    }

    public static void saveData(ArrayList<Guest> guests, ArrayList<Staff> staffs, ArrayList<Room> rooms, ArrayList<Booking> bookings)
    {
        try
        {
            BufferedWriter wrGuest = new BufferedWriter(new FileWriter("Guest.txt"));
            for (Guest g : guests)
            {
                wrGuest.write(g.getName() + "@_@" + g.getICNumber() + "@_@" + g.getPhoneNumber() + "@_@" + g.getEmail() + "@_@" + g.getGuestID());
                wrGuest.newLine();
            }
            wrGuest.close();

            BufferedWriter wrStaff = new BufferedWriter(new FileWriter("Staff.txt"));
            for (Staff s : staffs)
            {
                wrStaff.write(s.getName() + "@_@" + s.getICNumber() + "@_@" + s.getPhoneNumber() + "@_@" + s.getEmail() + "@_@" + s.getStaffID() + "@_@" + s.getPassword());
                wrStaff.newLine();
            }
            wrStaff.close();

            BufferedWriter wrRoom = new BufferedWriter(new FileWriter("Room.txt"));
            for (Room r : rooms)
            {
                wrRoom.write(r.getRoomID() + "@_@" + r.getRoomType() + "@_@" + r.getPricePerNight());
                wrRoom.newLine();
            }
            wrRoom.close();

            BufferedWriter wrBooking = new BufferedWriter(new FileWriter("Booking.txt"));
            for (Booking b : bookings)
            {
                wrBooking.write(b.getBookingID() + "@_@" + b.getGuest().getGuestID() + "@_@" + b.getRoom().getRoomID() + "@_@" + b.getNumberOfDays() + "@_@" + b.getStaff().getStaffID());
                wrBooking.newLine();
            }
            wrBooking.close();
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
