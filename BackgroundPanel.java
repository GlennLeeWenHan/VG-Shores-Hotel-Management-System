import java.awt.*;
import javax.swing.*;


public class BackgroundPanel extends JPanel
{
    private Image backgroundImage;

    public BackgroundPanel(String imagePath)
    {
        backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
