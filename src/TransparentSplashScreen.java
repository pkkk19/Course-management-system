import javax.swing.*;
import java.awt.*;


public class TransparentSplashScreen extends JWindow {
    //Get transparent image that will be use as splash screen image.
    Image bi = Toolkit.getDefaultToolkit().getImage("lode.png");
    //creating an object of ImageIcon()
    ImageIcon ii = new ImageIcon(bi);

    public TransparentSplashScreen() {
        try {
            setSize(ii.getIconWidth(), ii.getIconHeight());
            setBackground(new Color(0, 255, 0, 0));
            setLocationRelativeTo(null);
            show();
            Thread.sleep(4000);
            dispose();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Paint transparent image onto JWindow
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, this);

    }

    public static void main(String[] args) {
        TransparentSplashScreen tss = new TransparentSplashScreen();
    }
}