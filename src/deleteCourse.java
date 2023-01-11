import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class deleteCourse extends JFrame {
    private JPanel contentPane;
    private String courseType;
    private JLabel lbl;
    private JButton btn;
    private JComboBox comb;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    deleteCourse frame = new deleteCourse();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * setting up a new frame
     * this frame just deletes the course from the option given to the user
     */

    public deleteCourse() {
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 360, 500, 234);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lbl = new JLabel("Select the course to delete: ");
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl.setBounds(45, 20, 400, 67);
        contentPane.add(lbl);
//-----------------------------------------------------------------------------
        //selecting courses available to add in the arraylist
        ArrayList<String> course = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM course where courseStatus = 1");
            ResultSet s = ps.executeQuery();
            while (s.next()) {
                course.add(s.getString("coursename"));
            }
        } catch (Exception d) {
            JOptionPane.showMessageDialog(null, d);
        }
        //now displaying the arraylist in the combobox
        comb = new JComboBox(new DefaultComboBoxModel<String>(course.toArray(new String[0])));
        comb.setBounds(100, 105, 100, 30);
        contentPane.add(comb);
        comb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting index of the selected content of combobox
                int index = Integer.parseInt(String.valueOf(comb.getSelectedIndex()));
                courseType = course.get(index);//storing the data available at the index of the arraylist in string
                System.out.println(courseType);
            }
        });
//=================================================================================================
        btn = new JButton(new ImageIcon("delete.png"));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("DELETE FROM course WHERE coursename=?");
                    st.setString(1, courseType);
                    //deletes the course
                    st.executeUpdate();
                } catch (SQLException sqlException) {
                    JOptionPane.showMessageDialog(null, sqlException);
                }
            }
        });
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBounds(180, 80, 259, 74);
        contentPane.add(btn);

    }
}
