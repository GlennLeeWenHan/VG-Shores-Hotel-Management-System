import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

public class AddBookingDialog extends JDialog
{
    private ArrayList<Booking> bookings;
    public AddBookingDialog(JFrame parent, ArrayList<Guest> guests, ArrayList<Room> rooms, ArrayList<Staff> staffs, Staff currentStaff, ArrayList<Booking> bookings)
    {
        super(parent, "Add New Booking", true);

        this.bookings = bookings;
        setSize(650, 190);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel masterPanel = new JPanel();
        masterPanel.setLayout(new BorderLayout());

        JPanel masterInformationPanel = new JPanel();
        masterInformationPanel.setLayout(new BorderLayout());

        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(3, 2));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        String[] guestList = new String[guests.size() + 1];
        guestList[0] = "Select Guest";
        for (int i = 1; i < guests.size() + 1; i ++ )
        {
            guestList[i] = String.format("%-6s", guests.get(i - 1).getGuestID()) + guests.get(i - 1).getName();
        }

        ArrayList<Room> roomArrayList = new ArrayList<>();
        for (Room r : rooms)
        {
            if (r.getAvailability() == true)
            {
                roomArrayList.add(r);
            }
        }

        String[] roomList = new String[roomArrayList.size() + 1];
        roomList[0] = "Select Room";
        for (int i = 1 ; i < roomArrayList.size() + 1; i++)
        {
            roomList[i] = String.format("%-6s", roomArrayList.get(i - 1).getRoomID()) + String.format("%-16s", roomArrayList.get(i - 1).getRoomType()) + "RM " + String.format("%.2f", roomArrayList.get(i - 1).getPricePerNight()) + "/day";
        }

        JLabel selectGuestLabel = new JLabel("Select Guest:");
        selectGuestLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JComboBox<String> guestComboBox = new JComboBox<>(guestList);
        guestComboBox.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JLabel selectRoomLabel = new JLabel("Select Room:");
        selectRoomLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        

        JComboBox<String> roomComboBox = new JComboBox<>(roomList);
        roomComboBox.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JLabel daysLabel = new JLabel("How many days?:");
        daysLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JTextField daysField = new JTextField();

        informationPanel.add(selectGuestLabel);
        informationPanel.add(guestComboBox);
        informationPanel.add(selectRoomLabel);
        informationPanel.add(roomComboBox);
        informationPanel.add(daysLabel);
        informationPanel.add(daysField);

        JLabel totalPriceLabel = new JLabel("Total Price: ");
        totalPriceLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        totalPriceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton closeButton = new JButton("Close");
        JButton confirmBookingButton = new JButton("Confirm Booking");
        

        buttonPanel.add(closeButton);
        buttonPanel.add(confirmBookingButton);
        
        
        masterInformationPanel.add(informationPanel, BorderLayout.CENTER);
        masterInformationPanel.add(totalPriceLabel, BorderLayout.SOUTH);

        masterPanel.add(masterInformationPanel, BorderLayout.CENTER);
        masterPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(masterPanel, BorderLayout.CENTER);

        confirmBookingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                

                if ((guestComboBox.getSelectedIndex() > 0) && (roomComboBox.getSelectedIndex() > 0))
                {
                    Guest selectedGuest = guests.get(guestComboBox.getSelectedIndex() - 1);
                    Room selectedRoom = roomArrayList.get(roomComboBox.getSelectedIndex() - 1);

                    try
                    {
                        int days = Integer.parseInt(daysField.getText());
                        if (days < 1)
                        {
                            JOptionPane.showMessageDialog(null, "Days must be greater or equal to 1!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (days > 30)
                        {
                            JOptionPane.showMessageDialog(null, "Maximum booking days is 30", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            bookings.add(new Booking(generateBookingID(), selectedGuest, selectedRoom, days, currentStaff));
                            selectedRoom.setAvailability(false);
                            new DataCreationSuccessDialog(AddBookingDialog.this, "Booking Notification", "Booking successfully created.");
                            dispose();
                        }
                    }
                    catch(NumberFormatException t)
                    {
                        JOptionPane.showMessageDialog(null, "Days has to be an integer value.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    catch(IndexOutOfBoundsException z)
                    {
                        JOptionPane.showMessageDialog(null, "Days has to be an integer value.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if (guestComboBox.getSelectedItem() == "Select Guest" || guestComboBox.getSelectedIndex() == -1)
                    {
                        JOptionPane.showMessageDialog(null, "Please select a guest first.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (roomComboBox.getSelectedItem() == "Select Room" || roomComboBox.getSelectedIndex() == -1)
                    {
                        JOptionPane.showMessageDialog(null, "Please select a room first.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        daysField.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(DocumentEvent e)
            {
                updateTotalPrice(daysField, totalPriceLabel, roomArrayList, roomComboBox);
            }

            public void changedUpdate(DocumentEvent e)
            {
                updateTotalPrice(daysField, totalPriceLabel, roomArrayList, roomComboBox);
            }

            public void removeUpdate(DocumentEvent e)
            {
                updateTotalPrice(daysField, totalPriceLabel, roomArrayList, roomComboBox);
            }
        });


        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        
        setResizable(true);
        setVisible(true);
    }

    public String generateBookingID()
    {
        int counter = 1;
        String bookingID = "";
        boolean isTaken = true;
        while (isTaken)
        {
            isTaken = false;
            bookingID = "B" + String.format("%03d", counter);
            for (Booking b : bookings)
            {
                if (b.getBookingID().equals(bookingID))
                {
                    isTaken = true;
                }
            }
            counter++;
        }
        return bookingID;
    }

    

    public void updateTotalPrice(JTextField daysField, JLabel totalPriceLabel, ArrayList<Room> roomArrayList, JComboBox<String> roomComboBox)
    {

        try
        {
            Room selectedRoom = roomArrayList.get(roomComboBox.getSelectedIndex() - 1);

            try
            {
                int days = Integer.parseInt(daysField.getText());
                if ((days > 0) && (days < 31))
                {
                    double pricePerNight = selectedRoom.getPricePerNight();
                    double totalPrice = days * pricePerNight;
                    totalPriceLabel.setText("Total Price: RM" + String.format("%.2f", totalPrice));
                }
                else
                {
                    totalPriceLabel.setText("Total Price:");
                }
            }
            catch(NumberFormatException e)
            {
                totalPriceLabel.setText("Total Price:");
            }
        }
        catch(IndexOutOfBoundsException z)
        {
            totalPriceLabel.setText("Total Price:");
        }
    }
}
