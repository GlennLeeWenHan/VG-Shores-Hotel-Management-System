import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class GuestDetailsDialog extends JDialog
{
    private ArrayList<Guest> guests;
    public GuestDetailsDialog(JFrame parent, Guest guest, ArrayList<Guest> guests, ArrayList<Booking> bookings)
    {
        super(parent, "Guest Details", true);
        this.guests = guests;
        // -------------------------------------------------
        setSize(450, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        // -------------------------------------------------

        CardLayout infoCardLayout = new CardLayout();

        JPanel infoPanel = new JPanel(infoCardLayout);
        
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        JPanel viewMasterPanel = new JPanel();
        viewMasterPanel.setLayout(new BorderLayout());

        JPanel viewInfoPanel = new JPanel(new GridLayout(5,1));
        viewInfoPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel guestIDLabel = new JLabel("Guest ID:     " + guest.getGuestID());
        JLabel guestNameLabel = new JLabel("Name:         " + guest.getName());
        JLabel guestICNumberLabel = new JLabel("IC Number:    " + guest.getICNumber());
        JLabel guestPhoneNumberLabel = new JLabel("Phone Number: " + guest.getPhoneNumber());
        JLabel guestEmailLabel = new JLabel("Email:        " + guest.getEmail());

        guestIDLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        guestNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        guestICNumberLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        guestPhoneNumberLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        guestEmailLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));

        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        // Edit Details Button
        JButton editDetailsButton = new JButton("Edit Details");
        


        // Delete Guest Button
        JButton deleteGuestButton = new JButton("Delete Guest");
        deleteGuestButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                boolean isActive = false;
                for (Booking b : bookings)
                {
                    if ((b.getGuest().getGuestID()).equals(guest.getGuestID()))
                    {
                        isActive = true;
                    }
                }

                if (isActive != true)
                {
                    guests.remove(guest);
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "This current guest has an active booking. Delete the booking before deleting this guest.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        viewInfoPanel.add(guestIDLabel);
        viewInfoPanel.add(guestNameLabel);
        viewInfoPanel.add(guestICNumberLabel);
        viewInfoPanel.add(guestPhoneNumberLabel);
        viewInfoPanel.add(guestEmailLabel);

        //------------------------------------------------------------------------
        //------------------------------------------------------------------------




        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        JPanel editMasterPanel = new JPanel();
        editMasterPanel.setLayout(new BorderLayout());

        JPanel editInfoPanel = new JPanel(new GridLayout(5,2));
        editInfoPanel.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JLabel editGuestIDLabel = new JLabel("Guest ID:     " + guest.getGuestID());
        JLabel editNameLabel = new JLabel("Name: ");
        JLabel editICNumberLabel = new JLabel("IC Number: ");
        JLabel editPhoneNumber = new JLabel("Phone Number: ");
        JLabel editEmail = new JLabel("Email: ");

        JTextField nameField = new JTextField(guest.getName());
        JTextField ICNumberField = new JTextField(guest.getICNumber());
        JTextField phoneNumberField = new JTextField(guest.getPhoneNumber());
        JTextField emailField = new JTextField(guest.getEmail());

        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm");

        editGuestIDLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        editNameLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        editICNumberLabel.setFont(new Font("Monospaced", Font.PLAIN, 14));
        editPhoneNumber.setFont(new Font("Monospaced", Font.PLAIN, 14));
        editEmail.setFont(new Font("Monospaced", Font.PLAIN, 14));

        nameField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        ICNumberField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        phoneNumberField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        emailField.setFont(new Font("Monospaced", Font.PLAIN, 14));

        editInfoPanel.add(editGuestIDLabel);
        editInfoPanel.add(new JLabel());

        editInfoPanel.add(editNameLabel);
        editInfoPanel.add(nameField);

        editInfoPanel.add(editICNumberLabel);
        editInfoPanel.add(ICNumberField);

        editInfoPanel.add(editPhoneNumber);
        editInfoPanel.add(phoneNumberField);

        editInfoPanel.add(editEmail);
        editInfoPanel.add(emailField);
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------




        //------------------------------------------------------------------------
        //------------------------------------------------------------------------
        infoPanel.add(viewMasterPanel, "view");
        infoPanel.add(editMasterPanel, "edit");
        //------------------------------------------------------------------------
        //------------------------------------------------------------------------



        //------------------------------------------------------------------------
        //------------------------------------------------------------------------

        //--------------------
        JPanel viewButtonPanel = new JPanel();
        JPanel editButtonPanel = new JPanel();
        //--------------------


        //-------------------------------------------------------------
        editDetailsButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                infoCardLayout.show(infoPanel, "edit");
            }
        });
        //-------------------------------------------------------------
        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                infoCardLayout.show(infoPanel, "view");
            }
        });
        //-------------------------------------------------------------
        confirmButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String enteredName = nameField.getText();
                String enteredICNumber = ICNumberField.getText();
                String enteredPhoneNumber = phoneNumberField.getText();
                String enteredEmail = emailField.getText();
                String ownName = guest.getName();

                if ( isValidName(enteredName) && isNameAvailable(enteredName, ownName))
                {
                    if (isValidICNumber(enteredICNumber))
                    {
                        if (isValidPhoneNumber(enteredPhoneNumber))
                        {
                            if (isValidEmail(enteredEmail))
                            {
                                guest.setName(enteredName);
                                guest.setICNumber(enteredICNumber);
                                guest.setPhoneNumber(enteredPhoneNumber);
                                guest.setEmail(enteredEmail);
                                dispose();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Invalid email format! (example: glenn@gmail.com)", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "Invalid Phone Number format! (example: 01674678964)", "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid IC Number format!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if (isValidName(enteredName) == false)
                    {
                        JOptionPane.showMessageDialog(null, "Invalid name format!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    
                    else if (isNameAvailable(enteredName, ownName) == false)
                    {
                        JOptionPane.showMessageDialog(null, "Name has been taken, try another name.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //-------------------------------------------------------------


        //------------------------------------------------------------------------
        //------------------------------------------------------------------------

        viewButtonPanel.add(closeButton);
        viewButtonPanel.add(editDetailsButton);
        viewButtonPanel.add(deleteGuestButton);

        editButtonPanel.add(backButton);
        editButtonPanel.add(confirmButton);

        viewMasterPanel.add(viewButtonPanel, BorderLayout.SOUTH);
        viewMasterPanel.add(viewInfoPanel, BorderLayout.CENTER);

        editMasterPanel.add(editButtonPanel, BorderLayout.SOUTH);
        editMasterPanel.add(editInfoPanel, BorderLayout.CENTER);

        add(infoPanel, BorderLayout.CENTER);


        // -------------------------------------------------
        setResizable(false);
        setVisible(true);
        // -------------------------------------------------
    }

    public boolean isValidPhoneNumber(String phoneNumber)
    {
        return phoneNumber.matches("01[0-9]{8,9}");
    }

    public boolean isValidEmail(String email)
    {
        return email.matches("[a-zA-Z0-9]+@gmail\\.com");
    }

    public boolean isValidName(String name)
    {
        return name.matches("[a-zA-Z]+");
    }

    public boolean isNameAvailable(String name, String ownName)
    {
        for (Guest g : guests)
        {
            if ((g.getName().toLowerCase()).equals(name.toLowerCase()))
            {
                if ((g.getName().toLowerCase()).equals(ownName.toLowerCase()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidICNumber(String ICNumber)
    {
        return ICNumber.matches("[0-9]{6,12}");
    }
}