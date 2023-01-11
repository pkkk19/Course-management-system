import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class newCourse extends JFrame {
    private JPanel contentPane;
    private JTextField textField;
    private JLabel lbl;
    private JButton btn;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    newCourse frame = new newCourse();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public newCourse() {
        //------------------------------------------------------------
        //setting up new frame
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
        textField.setFont(new Font("Tahoma", Font.PLAIN, 34));
        textField.setBounds(373, 35, 609, 67);
        contentPane.add(textField);
        textField.setColumns(10);

        lbl = new JLabel("Enter Course Name:");
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl.setBounds(45, 37, 326, 67);
        contentPane.add(lbl);
//-----------------------------------------------------------------------------------
        btn = new JButton("Enter");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //fetches the course name from text field
                String course = textField.getText();
                try {
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    //simply using insert query to insert the new row into course table
                    String query = "INSERT INTO course (coursename)values('" + course + "')";
                    Statement sta = con.createStatement();
                    int x = sta.executeUpdate(query);
                    if (x == 0) {
                        JOptionPane.showMessageDialog(btn, "This is already exist");
                    } else {
                        JOptionPane.showMessageDialog(btn, "New Course has been added");
                    }
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, sqlException);
                }
            }
        });
        btn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        btn.setBounds(399, 110, 259, 74);
        contentPane.add(btn);

    }
}
