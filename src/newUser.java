import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class newUser extends JFrame {

    private JPanel contentpane;
    private JTextField firstname;
    private JTextField lastname;
    private JTextField email;
    private JTextField username;
    private JPasswordField passwordField;
    private JTextField mobile;
    private JButton button;
    private JRadioButton rb1, rb2;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    newUser frame = new newUser();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     creating a new frame
     **/

    public newUser() {
        setTitle("Course Management System");
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);

        contentpane = new JPanel();
        contentpane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentpane.setBackground(new Color(255, 255, 255));
        setContentPane(contentpane);
        contentpane.setLayout(null);

        JLabel lbl = new JLabel("New User Signup");
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        lbl.setForeground(new Color(153, 224, 101));
        lbl.setBounds(362, 52, 325, 50);
        contentpane.add(lbl);
//-----------------------------------------------------------------------------------
        JLabel lblName = new JLabel("First name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblName.setBounds(58, 152, 99, 43);
        contentpane.add(lblName);

        JLabel lblLastName = new JLabel("Last name");
        lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblLastName.setBounds(58, 243, 110, 29);
        contentpane.add(lblLastName);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmail.setBounds(58, 324, 110, 29);
        contentpane.add(lblEmail);
//------------------------------------------------------------------------------
        firstname = new JTextField();
        firstname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        firstname.setBounds(214, 151, 228, 50);
        contentpane.add(firstname);
        firstname.setColumns(10);

        lastname = new JTextField();
        lastname.setFont(new Font("Tahoma", Font.PLAIN, 32));
        lastname.setBounds(214, 235, 228, 50);
        contentpane.add(lastname);
        lastname.setColumns(10);

        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(214, 320, 228, 50);
        contentpane.add(email);
        email.setColumns(10);

//------------------------------------------------------------------------

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(542, 159, 99, 29);
        contentpane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(542, 245, 99, 24);
        contentpane.add(lblPassword);

        JLabel lblMobile = new JLabel("Mobile number");
        lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblMobile.setBounds(542, 329, 139, 26);
        contentpane.add(lblMobile);
//--------------------------------------------------------------------------

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(707, 151, 228, 50);
        contentpane.add(username);
        username.setColumns(10);

        mobile = new JTextField();
        mobile.setFont(new Font("Tahoma", Font.PLAIN, 32));
        mobile.setBounds(707, 320, 228, 50);
        contentpane.add(mobile);
        mobile.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(707, 235, 228, 50);
        contentpane.add(passwordField);
//------------------------------------------------------------------------------
        JLabel AccType = new JLabel("Account Type");
        AccType.setFont(new Font("Tahoma", Font.PLAIN, 20));
        AccType.setBounds(58, 385, 150, 29);
        contentpane.add(AccType);

        rb1 = new JRadioButton("Teacher");
        rb1.setBounds(200, 385, 100, 30);
        rb2 = new JRadioButton("Student");
        rb2.setBounds(300, 385, 100, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        add(rb1);
        add(rb2);
        setLayout(null);
        setVisible(true);
//-----------------------------------------------------------------------------
        button = new JButton("Register");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = null;
                //fetching the values selected and given by user
                if (rb1.isSelected()) type = "teacher";
                if (rb2.isSelected()) type = "student";
                String firstName = firstname.getText();
                String lastName = lastname.getText();
                String emailID = email.getText();
                String user = username.getText();
                String mobileNo = mobile.getText();
                int len = mobileNo.length();
                String password = passwordField.getText();
                String msg = "" + firstName;
                System.out.println(type);
                if (len != 10) {
                    JOptionPane.showMessageDialog(button, "Enter a valid mobile number");
                }
                try {
                    //adding new user
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    String query = "INSERT INTO account values('" + firstName + "','" + lastName + "','" + user + "','" +
                            password + "','" + emailID + "','" + mobileNo + "','" + type + "')";
                    Statement sta = connection.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(button, "This is already exist");
                    } else {
                        JOptionPane.showMessageDialog(button, "Account is successfully created");
                        dispose();
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(399, 447, 259, 74);
        contentpane.add(button);
    }
}

