import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignupPage extends JFrame
{
    private ArrayList<Staff> staffs;

    public SignupPage(ArrayList<Guest> guests, ArrayList<Staff> staffs, ArrayList<Room> rooms, ArrayList<Booking> bookings)
    {
        this.staffs = staffs;

        int width = 1000;
        int height = 700;

        setTitle("VG Shores Hotel Staff Signup");
        setSize(width, height);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image logoImage = Toolkit.getDefaultToolkit().getImage("logo.png");
        setIconImage(logoImage);

        BackgroundPanel fullPage = new BackgroundPanel("Wallpaper.png");
        fullPage.setLayout(new BorderLayout());
        setContentPane(fullPage);
        fullPage.setBorder(BorderFactory.createEmptyBorder(230, 200, 230, 200));


        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new GridLayout(7, 2));
        informationPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JLabel signupLabel = new JLabel("[STAFF SIGNUP]");
        signupLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        signupLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameTextField = new JTextField(20);

        JLabel ICNumberLabel = new JLabel("IC Number:");
        JTextField ICNumberTextField = new JTextField(20);

        JLabel phoneNumberLabel = new JLabel("Phone Number:  ");
        JTextField phoneNumberTextField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:  ");
        JTextField emailTextField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Enter Password:");
        JPasswordField passwordPasswordField = new JPasswordField(20);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordPasswordField = new JPasswordField(20);

        JLabel verificationCodeLabel = new JLabel("Enter Verification Code:");
        JTextField verificationCodeTextField = new JTextField(20);

        informationPanel.add(nameLabel);
        informationPanel.add(nameTextField);
        informationPanel.add(ICNumberLabel);
        informationPanel.add(ICNumberTextField);
        informationPanel.add(phoneNumberLabel);
        informationPanel.add(phoneNumberTextField);
        informationPanel.add(emailLabel);
        informationPanel.add(emailTextField);
        informationPanel.add(passwordLabel);
        informationPanel.add(passwordPasswordField);
        informationPanel.add(confirmPasswordLabel);
        informationPanel.add(confirmPasswordPasswordField);
        informationPanel.add(verificationCodeLabel);
        informationPanel.add(verificationCodeTextField);

        JButton backButton = new JButton("Back");
        JButton signupButton = new JButton("Confirm Signup");

        buttonPanel.add(backButton);
        buttonPanel.add(signupButton);

        fullPage.add(signupLabel, BorderLayout.NORTH);
        fullPage.add(informationPanel, BorderLayout.CENTER);
        fullPage.add(buttonPanel, BorderLayout.SOUTH);

        

        signupButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String enteredName = nameTextField.getText();
                String enteredICNumber = ICNumberTextField.getText();
                String enteredPhoneNumber = phoneNumberTextField.getText();
                String enteredEmail = emailTextField.getText();
                String enteredPassword = new String(passwordPasswordField.getPassword());
                String enteredConfirmPassword = new String(confirmPasswordPasswordField.getPassword());
                String enteredVerificationCode = verificationCodeTextField.getText();

                boolean isTaken = false;

                for (Staff s : staffs)
                {
                    if ((s.getName().toLowerCase()).equals(enteredName.toLowerCase()))
                    {
                        isTaken = true;
                    }
                }

                if (enteredPassword.equals(enteredConfirmPassword))
                {
                    if (enteredVerificationCode.equals("DrAnis"))
                    {
                        if (isValidName(enteredName) && isTaken == false)
                        {
                            if (isValidICNumber(enteredICNumber))
                            {
                                if (isValidPhoneNumber(enteredPhoneNumber))
                                {
                                    if (isValidEmail(enteredEmail))
                                    {
                                        if (isValidPassword(enteredConfirmPassword))
                                        {
                                            staffs.add(new Staff(enteredName, enteredICNumber, enteredPhoneNumber, enteredEmail, generateStaffID(), enteredPassword));
                                            saveStaffDetails(staffs);
                                            new LoginPage(guests, staffs, rooms, bookings);
                                            JOptionPane.showMessageDialog(null, "Welcome to VG Hotel Family " + enteredName + ".", "Staff account created!", JOptionPane.INFORMATION_MESSAGE);
                                            dispose();
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null, "Are you trying to break my program?", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        }
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
                            
                            else if (isTaken)
                            {
                                JOptionPane.showMessageDialog(null, "Name has been taken, try another name.", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Invalid verification code!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Password not match!\nTry again.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Password not match");
                }
            }
        });

        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                new LoginPage(guests, staffs, rooms, bookings);
            }
        });


        setLocationRelativeTo(null);


        // fullPage.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
        setResizable(false);
    }

    public String generateStaffID()
    {
        int counter = 1;
        String staffID = " ";
        boolean isTaken = true;
        while (isTaken)
        {
            isTaken = false;
            staffID = "S" + String.format("%03d", counter);
            for (Staff s : staffs)
            {
                if (s.getStaffID().equals(staffID))
                {
                    isTaken = true;
                }
            }
            counter++;
        }
        return staffID;
    }

    //name
    //ic number
    // phone number
    //email
    //password
    //confirm password
    // verification code

    public void saveStaffDetails(ArrayList<Staff> staffs)
    {
        try
        {
            BufferedWriter wrStaff = new BufferedWriter(new FileWriter("Staff.txt"));
            for (Staff s : staffs)
            {
                wrStaff.write(s.getName() + "@_@" + s.getICNumber() + "@_@" + s.getPhoneNumber() + "@_@" + s.getEmail() + "@_@" + s.getStaffID() + "@_@" + s.getPassword());
                wrStaff.newLine();
            }
            wrStaff.close();
        }
        catch(IOException e)
        {
            //do nothing
        }
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

    public boolean isValidPassword(String password)
    {
        if (password.contains("@_@"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean isValidICNumber(String ICNumber)
    {
        return ICNumber.matches("[0-9]{6,12}");
    }
}
