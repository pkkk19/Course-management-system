import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangePassword extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lblEnterNewPassword;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ChangePassword(String name) {
        //creating new frame
        setBounds(450, 360, 1024, 234);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        textField.setBounds(373, 35, 609, 67);
        contentPane.add(textField);
        textField.setColumns(10);
        //-----------------------------------------------------------------------
        JButton btnSearch = new JButton("Enter");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //fetches the text from the text field and stores in the string variable
                String pstr = textField.getText();
                try {
                    //updating the password given by the user
                    System.out.println("update password name " + name);
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("Update user set password=? where name=?");
                    st.setString(1, pstr);
                    st.setString(2, name);
                    st.executeUpdate();
                    dispose();
                    JOptionPane.showMessageDialog(btnSearch, "Password has been successfully changed");
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 29));
        btnSearch.setBackground(new Color(240, 240, 240));
        btnSearch.setBounds(438, 127, 170, 59);
        contentPane.add(btnSearch);

        lblEnterNewPassword = new JLabel("Enter New Password :");
        lblEnterNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblEnterNewPassword.setBounds(45, 37, 326, 67);
        contentPane.add(lblEnterNewPassword);
    }
}