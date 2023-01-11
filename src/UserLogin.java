import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserLogin extends JFrame {

    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //------------------------------------------------------------
                    //calling transparent splash screen class and running it at first while opening the login page
                    TransparentSplashScreen tss = new TransparentSplashScreen();
                    //Running the UserLogin constructor in the main class
                    UserLogin frame = new UserLogin();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public UserLogin() {
        setTitle("Course management system");
        //image icon
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        //------------------------------------------------------------------

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
//---------------------------------------------------------------------------------------
        ImageIcon img = new ImageIcon("background.png");
        JLabel background = new JLabel("", img, JLabel.CENTER);
        background.setBounds(0, 0, 1014, 597);
        contentPane.add(background);
//-------------------------------------------------------------------------------------------
        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 40, 273, 93);
        background.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Comic Sans MS", Font.PLAIN, 23));
        textField.setBounds(460, 180, 270, 40);
        background.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tacoma", Font.PLAIN, 23));
        passwordField.setBounds(460, 295, 270, 40);
        background.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tacoma", Font.PLAIN, 26));
        lblUsername.setBounds(290, 170, 193, 52);
        background.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setFont(new Font("Tacoma", Font.PLAIN, 26));
        lblPassword.setBounds(290, 286, 193, 52);
        background.add(lblPassword);
//----------------------------------------------------------------------------------
        //setting up button for login
        btnNewButton = new JButton(new ImageIcon("login.png"));
        btnNewButton.setBackground(new Color(0, 255, 0, 0));
        btnNewButton.setBorderPainted(false);
        btnNewButton.setOpaque(false);
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBounds(440, 392, 162, 43);
        btnNewButton.addActionListener(new ActionListener() {
            //-----------------------------------------------------------------------------------------------------------------------------------------
            public void actionPerformed(ActionEvent e) {
                //fetching text written in the text field in string variable
                String userName = textField.getText();
                String password = passwordField.getText();
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    //this query helps to select the user from the database of specific username and password
                    PreparedStatement st = connection.prepareStatement("Select user_name, password, type from account where user_name=? and password=?");
                    st.setString(1, userName);
                    st.setString(2, password);
                    ResultSet rs = st.executeQuery();
                    /*if the required username and password doesn't match it doesn't fetch any data which means
                      rs.next won't run but if it matches then it will log the user into the specific type of account*/
                    if (rs.next()) {
                        dispose();
                        String type = rs.getString("type");
                        System.out.println(type);
                        //--------------------------------------------------------------------------------------------------
                        //I have used a switch case here to decide which dashboard to open by looking at the type of account
                        switch (type) {
                            case "student":
                                student ad = new student(userName);
                                ad.setTitle("student");
                                ad.setVisible(true);
                                JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
                                break;
                            case "teacher":
                                Teacher T = new Teacher(userName);
                                T.setVisible(true);
                                JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
                                break;
                            case "admin":
                                admin admin = new admin(userName);
                                admin.setVisible(true);
                                JOptionPane.showMessageDialog(btnNewButton, "You have successfully logged in");
                                break;
                        }
                        //-----------------------------------------------------------------------------------------------

                    } else {
                        JOptionPane.showMessageDialog(btnNewButton, "Wrong Username & Password");
                    }
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }
        });
        background.add(btnNewButton);
    }
}