import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.util.ArrayList;


public class BookingDetailsDialog extends JDialog
{
    public BookingDetailsDialog(JFrame parent, ArrayList<Booking> bookings, Booking selectedBooking)
    {
        super(parent, "Booking Details", true);

        setSize(400, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new BorderLayout());
        pagePanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(7, 1));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel bookingIDLabel = new JLabel("Booking ID:        " + selectedBooking.getBookingID());
        JLabel guestNameLabel = new JLabel("Guest name:        " + selectedBooking.getGuest().getName());
        JLabel roomIDLabel = new JLabel("Room ID:           " + selectedBooking.getRoom().getRoomID());
        JLabel roomTypeLabel = new JLabel("Room type:         " + selectedBooking.getRoom().getRoomType());
        JLabel pricePerNightLabel = new JLabel("Price per night:   RM " + selectedBooking.getRoom().getPricePerNight());
        JLabel daysBookedLabel = new JLabel("Days booked:       " + selectedBooking.getNumberOfDays());
        JLabel totalPriceLabel = new JLabel("Total price:       RM " + String.format("%.2f", selectedBooking.calculateTotal()));

        bookingIDLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        guestNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        roomIDLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        roomTypeLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        pricePerNightLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        daysBookedLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        totalPriceLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton closeButton = new JButton("Close");
        JButton extendDaysButton = new JButton("Extend Days");
        JButton deleteBookingButton = new JButton("Delete Booking");

        detailsPanel.add(bookingIDLabel);
        detailsPanel.add(guestNameLabel);
        detailsPanel.add(roomIDLabel);
        detailsPanel.add(roomTypeLabel);
        detailsPanel.add(pricePerNightLabel);
        detailsPanel.add(daysBookedLabel);
        detailsPanel.add(totalPriceLabel);

        buttonPanel.add(closeButton);
        buttonPanel.add(extendDaysButton);
        buttonPanel.add(deleteBookingButton);

        pagePanel.add(detailsPanel, BorderLayout.CENTER);
        pagePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(pagePanel, BorderLayout.CENTER);

        // ==============================================================================
        
        // ==============================================================================
        

        deleteBookingButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                selectedBooking.getRoom().setAvailability(true);
                bookings.remove(selectedBooking);
                dispose();
            }
        });

        extendDaysButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JDialog extendDaysDialog = new JDialog(BookingDetailsDialog.this, "Extend Days", true);
                extendDaysDialog.setSize(300, 130);
                extendDaysDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                extendDaysDialog.setLayout(new BorderLayout());
                extendDaysDialog.setLocationRelativeTo(BookingDetailsDialog.this);
                
                

                JPanel daysToExtendFieldPanel = new JPanel();
                daysToExtendFieldPanel.setLayout(new GridLayout(1, 2));
                daysToExtendFieldPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

                JPanel daysToExtendButtonPanel = new JPanel();
                daysToExtendButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

                JLabel daysToExtendLabel = new JLabel("Days to extend: ");
                JTextField daysToExtendField = new JTextField();
                
                JButton backButton = new JButton("Back");
                JButton addDaysButton = new JButton("Add Days");

                daysToExtendFieldPanel.add(daysToExtendLabel);
                daysToExtendFieldPanel.add(daysToExtendField);

                daysToExtendButtonPanel.add(backButton);
                daysToExtendButtonPanel.add(addDaysButton);

                extendDaysDialog.add(daysToExtendFieldPanel, BorderLayout.CENTER);
                extendDaysDialog.add(daysToExtendButtonPanel, BorderLayout.SOUTH);


                backButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        extendDaysDialog.dispose();
                    }
                });

                addDaysButton.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        try
                        {
                            int daysExtend = Integer.parseInt(daysToExtendField.getText());

                            if ((daysExtend > 0) && ((daysExtend + selectedBooking.getNumberOfDays()) < 31))
                            {
                                selectedBooking.setNumberOfDays(selectedBooking.getNumberOfDays() + daysExtend);
                                extendDaysDialog.dispose();
                                BookingDetailsDialog.this.dispose();
                            }
                            else if ((daysExtend + selectedBooking.getNumberOfDays()) > 30)
                            {
                                JOptionPane.showMessageDialog(null, "Over the booking days limit of 30 days", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Days must be greater or equals to 1", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            
                        }
                        catch(NumberFormatException t)
                        {
                            JOptionPane.showMessageDialog(null, "Days must be integer value", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });


                extendDaysDialog.setResizable(false);
                extendDaysDialog.setVisible(true);
            }
        });

        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        setResizable(false);
        setVisible(true);
    }
}
