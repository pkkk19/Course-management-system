import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class update extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lbl;
    private JButton btn;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    update frame = new update("");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *setting up new frame
     *Also this method when called takes string parameter and updates a modulename
     */

    public update(String value) {
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 360, 1014, 234);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Tacoma", Font.PLAIN, 34));
        textField.setBounds(373, 35, 609, 67);
        contentPane.add(textField);
        textField.setColumns(10);

        lbl = new JLabel("Update module Name:");
        lbl.setFont(new Font("Tacoma", Font.PLAIN, 30));
        lbl.setBounds(45, 37, 326, 67);
        contentPane.add(lbl);
//-------------------------------------------------------------------------------------------
        btn = new JButton(new ImageIcon("Update.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String moduleName = textField.getText();
                try {
                    //updating new modulename to the selected modulecode and update it to the table
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con.prepareStatement("UPDATE module SET moduleName = ? WHERE moduleCode=?");
                    st.setString(1, moduleName);
                    st.setString(2, value);
                    st.executeUpdate();
                    dispose();
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, sqlException);
                }
            }
        });
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBounds(399, 110, 259, 74);
        contentPane.add(btn);

    }
}
