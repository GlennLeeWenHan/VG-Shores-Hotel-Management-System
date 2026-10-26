import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class DataCreationSuccessDialog extends JDialog
{
    public DataCreationSuccessDialog(JDialog parent, String title, String message)
    {
        super(parent, title, true);
        
        setSize(300, 150);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton closeButton = new JButton("Close");

        closeButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        setLocationRelativeTo(parent);
        
        buttonPanel.add(closeButton);

        add(messageLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setResizable(false);
        setVisible(true);
    }
}
