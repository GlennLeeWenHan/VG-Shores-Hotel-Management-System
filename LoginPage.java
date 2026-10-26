import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent; // states that someone rang the door
import java.awt.event.ActionListener; // goes to open the door

import java.util.ArrayList;




public class LoginPage extends JFrame
{

    public LoginPage(ArrayList<Guest> guests, ArrayList<Staff> staffs, ArrayList<Room> rooms, ArrayList<Booking> bookings)
    {

        int width = 1000;
        int height = 700;
        setTitle("VG Shores Hotel Staff Login");
        setSize(width,height);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image logoImage = Toolkit.getDefaultToolkit().getImage("logo.png");
        setIconImage(logoImage);

        //****Start of center panel****
        BackgroundPanel fullPage = new BackgroundPanel("Wallpaper.png");
        fullPage.setLayout(new BorderLayout());
        setContentPane(fullPage);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        centerPanel.setOpaque(false);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(3,2));
        innerPanel.setOpaque(false);

        JLabel loginLabel = new JLabel("[STAFF LOGIN]");
        loginLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        constraints.gridx = 0;
        constraints.gridy = 0;
        centerPanel.add(loginLabel, constraints);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        innerPanel.add(usernameLabel);

        JTextField usernameTextField = new JTextField(15);
        innerPanel.add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        innerPanel.add(passwordLabel);

        JPasswordField passwordPasswordField = new JPasswordField(15);
        innerPanel.add(passwordPasswordField);

        JButton signupButton = new JButton("Sign Up");
        innerPanel.add(signupButton);

        JButton loginButton = new JButton("Login");
        innerPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String enteredID = usernameTextField.getText();
                String enteredPassword = new String(passwordPasswordField.getPassword()); // because this one returns an array of char values

                boolean loginSuccess = false;

                Staff staffUsing = null;

                for (Staff s : staffs)
                {
                    if (s.getName().equals(enteredID) && s.getPassword().equals(enteredPassword))
                    {
                        loginSuccess = true;
                        staffUsing = s;
                        break;
                    }
                }

                if (loginSuccess)
                {
                    JOptionPane.showMessageDialog(null, "Welcome back " + enteredID + ".", "Logged in", JOptionPane.INFORMATION_MESSAGE);

                    new Homepage(guests, staffs, rooms, bookings, staffUsing);
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Username or Password is invalid !!\nPlease Try again :)", "Error", JOptionPane.ERROR_MESSAGE);

                    System.out.println("Login Failed");
                }
            }
        });

        signupButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new SignupPage(guests, staffs, rooms, bookings);
                dispose();
            }
        });

        //inserting inner panel into the center panel
        constraints.gridx = 0;
        constraints.gridy = 1;
        centerPanel.add(innerPanel, constraints);

        // ****End of center panel****


        // so that user can't move the window
        // ----------------------------------
        setLocationRelativeTo(null);
        // Point fixedLocation = getLocation();
        // addComponentListener(new ComponentAdapter()
        // {
        //     public void componentMoved(ComponentEvent e)
        //     {
        //         setLocation(fixedLocation);
        //     }
        // });
        // ----------------------------------


        fullPage.add(centerPanel, BorderLayout.CENTER);

        

        setVisible(true);
        setResizable(false);
    }
}
