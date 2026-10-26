import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import java.util.ArrayList;


public class AddRoomDialog extends JDialog
{
    private ArrayList<Room> rooms;
    public AddRoomDialog(JFrame parent, ArrayList<Room> rooms)
    {
        super(parent, "Add New Room", true);
        this.rooms = rooms;

        setSize(400, 150);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);


        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new GridLayout(2,2));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel roomTypeLabel = new JLabel("Room Type: ");
        JLabel pricePerNightLabel = new JLabel("Price per night: ");

        String[] listOfRoomTypes = {"Select Room Type", "Single Room", "Double Room", "Twin Room", "Hollywood Twin", "Deluxe Room", "Suite", "Villa"};

        JComboBox<String> roomTypeComboBox = new JComboBox<>(listOfRoomTypes);
        JTextField pricePerNightField = new JTextField();

        fieldPanel.add(roomTypeLabel);
        fieldPanel.add(roomTypeComboBox);
        fieldPanel.add(pricePerNightLabel);
        fieldPanel.add(pricePerNightField);

        JButton backButton = new JButton("Back");
        JButton createNewRoomButton = new JButton("Create New Room");

        buttonPanel.add(backButton);
        buttonPanel.add(createNewRoomButton);

        add(fieldPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        roomTypeComboBox.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    String selectedItem = roomTypeComboBox.getSelectedItem().toString();
                    System.out.println(selectedItem);
                }
            }
        });

        createNewRoomButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if (roomTypeComboBox.getSelectedItem() == "Select Room Type")
                {
                    JOptionPane.showMessageDialog(null, "Please select a room type before proceeding", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    String selectedRoomType = roomTypeComboBox.getSelectedItem().toString();
                    //String roomID, String roomType, double pricePerNight
                    try
                    {
                        double selectedPricePerNight = Double.parseDouble(pricePerNightField.getText());

                        if (selectedPricePerNight > 960515.50)
                        {
                            JOptionPane.showMessageDialog(null, "The most expensive hotel room in the world is RM 960515.50, be realistic man!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else if (selectedPricePerNight < 1)
                        {
                            JOptionPane.showMessageDialog(null, "Price has to be greater or equal to RM 1.", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                        else
                        {
                            rooms.add(new Room(generateRoomID(), selectedRoomType, selectedPricePerNight));
                            new DataCreationSuccessDialog(AddRoomDialog.this, "New Room Notification", "New room created successfully");
                            dispose();
                        }
                        
                    }
                    catch(NumberFormatException i)
                    {
                        JOptionPane.showMessageDialog(null, "Price should be double value data type!\nExample: 20.20", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        setResizable(false);
        setVisible(true);
    }

    public String generateRoomID()
    {
        int counter = 1;
        String roomID = "";
        boolean isTaken = true;
        while (isTaken)
        {
            isTaken = false;
            roomID = "R" + String.format("%03d", counter);
            for (Room r : rooms)
            {
                if (r.getRoomID().equals(roomID))
                {
                    isTaken = true;
                }
            }
            counter++;
        }
        return roomID;
    }
}
