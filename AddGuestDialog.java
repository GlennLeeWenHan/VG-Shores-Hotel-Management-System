import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;




public class AddGuestDialog extends JDialog
{
    private ArrayList<Guest> guests;

    public AddGuestDialog(JFrame parent, ArrayList<Guest> guests)
    {
        super(parent, "Add New Guest", true);
        this.guests = guests;

        setSize(400, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        //=====================================================
        JPanel addGuestPanel = new JPanel();
        addGuestPanel.setLayout(new GridLayout(4, 2));
        addGuestPanel.setBorder(BorderFactory.createEmptyBorder(20,15,20,15));

        JLabel nameLabel = new JLabel("Name: ");
        JLabel ICNumberLabel = new JLabel("IC Number: ");
        JLabel phoneNumberLabel = new JLabel("Phone Number: ");
        JLabel emailLabel = new JLabel("Email: ");

        JTextField nameField = new JTextField();
        JTextField ICNumberField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();

        addGuestPanel.add(nameLabel);
        addGuestPanel.add(nameField);
        
        addGuestPanel.add(ICNumberLabel);
        addGuestPanel.add(ICNumberField);

        addGuestPanel.add(phoneNumberLabel);
        addGuestPanel.add(phoneNumberField);

        addGuestPanel.add(emailLabel);
        addGuestPanel.add(emailField);
        //=====================================================

        JPanel addGuestButtonPanel = new JPanel();
        addGuestButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton backButton = new JButton("Back");
        JButton createGuestButton = new JButton("Create Guest");

        addGuestButtonPanel.add(backButton);
        addGuestButtonPanel.add(createGuestButton);

        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }    
        });

        //String name, String ICNumber, String phoneNumber, String email, String guestID

        createGuestButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String enteredName = nameField.getText();
                String enteredICNumber = ICNumberField.getText();
                String enteredPhoneNumber = phoneNumberField.getText();
                String enteredEmail = emailField.getText();
                String newGuestID = generateGuestID();

                if (isNameAvailable(enteredName) && isValidName(enteredName))
                {
                    if (isValidICNumber(enteredICNumber))
                    {
                        if (isValidPhoneNumber(enteredPhoneNumber))
                        {
                            if (isValidEmail(enteredEmail))
                            {
                                guests.add(new Guest(nameField.getText(), ICNumberField.getText(), phoneNumberField.getText(), emailField.getText(), newGuestID));
                                new DataCreationSuccessDialog(AddGuestDialog.this, "New Guest Notification", "Guest creation successful!");
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
                    
                    else if (isNameAvailable(enteredName) == false)
                    {
                        JOptionPane.showMessageDialog(null, "Name has been taken, try another name.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //=====================================================
        add(addGuestPanel, BorderLayout.CENTER);
        add(addGuestButtonPanel, BorderLayout.SOUTH);
        //=====================================================


        setResizable(false);
        setVisible(true);
    }

    public String generateGuestID()
    {
        int counter = 1;
        String guestID = " ";
        boolean isTaken = true;
        while (isTaken)
        {
            isTaken = false;
            guestID = "G" + String.format("%03d", counter);
            for (Guest g : guests)
            {
                if (g.getGuestID().equals(guestID))
                {
                    isTaken = true;
                }
            }
            counter++;
        }
        return guestID;
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

    public boolean isNameAvailable(String name)
    {
        for (Guest g : guests)
        {
            if ((g.getName().toLowerCase()).equals(name.toLowerCase()))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isValidICNumber(String ICNumber)
    {
        return ICNumber.matches("[0-9]{6,12}");
    }
}
