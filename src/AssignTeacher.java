import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AssignTeacher extends JFrame {
    private JPanel contentPane;
    private JLabel lbl;
    private JComboBox comb;
    private String teacher;
    private JButton button;
    private JRadioButton b1, b2, b3, b4, b5;

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
     * this method takes the string value as parameter
     * this method is called to assign teacher in a module
     */
    public AssignTeacher(String value) {
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 360, 1014, 234);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lbl = new JLabel("Position: ");
        lbl.setFont(new Font("Tacoma", Font.PLAIN, 30));
        lbl.setBounds(45, 20, 326, 67);
        contentPane.add(lbl);

        //--------------------------------------------------------------------------------
        b1 = new JRadioButton("Module leader");
        b1.setBounds(250, 40, 130, 30);
        b2 = new JRadioButton("Tutor1");
        b2.setBounds(380, 40, 90, 30);
        b3 = new JRadioButton("Tutor2");
        b3.setBounds(450, 40, 90, 30);
        b4 = new JRadioButton("Tutor3");
        b4.setBounds(540, 40, 90, 30);
        b5 = new JRadioButton("Tutor4");
        b5.setBounds(630, 40, 90, 30);
        ButtonGroup b = new ButtonGroup();
        b.add(b1);
        b.add(b2);
        b.add(b3);
        b.add(b4);
        b.add(b5);
        contentPane.add(b1);
        contentPane.add(b2);
        contentPane.add(b3);
        contentPane.add(b4);
        contentPane.add(b5);
        contentPane.setLayout(null);
        contentPane.setVisible(true);
        //--------------------------------------------------------------------------------

        lbl = new JLabel("Tutor Name:");
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lbl.setBounds(45, 60, 326, 67);
        contentPane.add(lbl);

        //---------------------------------------------------------------------------------------
        //usernames from teacher account type and adding it to the arraylist
        ArrayList<String> teacherName = new ArrayList<>();
        try {
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                    "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM account where type = ?");
            ps.setString(1, "teacher");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                teacherName.add(rs.getString("user_name"));
            }
            //now the values available in the arraylist are displayed in the combobox
            comb = new JComboBox(new DefaultComboBoxModel<String>(teacherName.toArray(new String[0])));
            comb.setBounds(250, 85, 100, 30);
            contentPane.add(comb);
            comb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int index = Integer.parseInt(String.valueOf(comb.getSelectedIndex()));
                    teacher = teacherName.get(index);
                    System.out.println(teacher);
                }
            });
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception);
        }
        //----------------------------------------------------------------------------------
        button = new JButton("Assign");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pos = null;
                if (b1.isSelected()) pos = "moduleLead";
                if (b2.isSelected()) pos = "tutor1";
                if (b3.isSelected()) pos = "tutor2";
                if (b4.isSelected()) pos = "tutor3";
                if (b5.isSelected()) pos = "tutor4";
                System.out.println(pos);
                try {
                    //updates the tutor at the selected position of the selected module
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("Update module set " + pos + " = ? where moduleCode=?");
                    st.setString(1, teacher);
                    st.setString(2, value);
                    st.executeUpdate();
                    dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
        button.setBounds(438, 127, 170, 59);
        contentPane.add(button);

    }
}
