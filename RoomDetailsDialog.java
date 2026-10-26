import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;


public class RoomDetailsDialog extends JDialog
{
    public RoomDetailsDialog(JFrame parent, ArrayList<Room> rooms, Room selectedRoom, ArrayList<Booking> bookings)
    {
        super(parent, "Room Details", true);

        setSize(300, 150);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel roomDetailsPanel = new JPanel();
        roomDetailsPanel.setLayout(new BorderLayout());

        JPanel roomInformationPanel = new JPanel();
        roomInformationPanel.setLayout(new GridLayout(3,1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel roomIDLabel = new JLabel("Room ID:           " + selectedRoom.getRoomID());
        JLabel roomTypeLabel = new JLabel("Room Type:         " + selectedRoom.getRoomType());
        JLabel roomPriceLabel = new JLabel("Price per night:   RM " + String.format("%.2f", selectedRoom.getPricePerNight()));

        roomInformationPanel.add(roomIDLabel);
        roomInformationPanel.add(roomTypeLabel);
        roomInformationPanel.add(roomPriceLabel);
        roomInformationPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        roomIDLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        roomTypeLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        roomPriceLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton closeButton = new JButton("Close");
        JButton deleteRoomButton = new JButton("Delete Room");

        buttonPanel.add(closeButton);
        buttonPanel.add(deleteRoomButton);

        roomDetailsPanel.add(roomInformationPanel, BorderLayout.CENTER);
        roomDetailsPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(roomDetailsPanel, BorderLayout.CENTER);


        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        deleteRoomButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                

                boolean isActive = false;
                for (Booking b : bookings)
                {
                    if (b.getRoom().getRoomID().equals(selectedRoom.getRoomID()))
                    {
                        isActive = true;
                    }
                }

                if (isActive != true)
                {
                    rooms.remove(selectedRoom);
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "This current room has an active booking. Delete the booking before deleting this room.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setResizable(false);
        setVisible(true);
    }
}