
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class newModules extends JFrame {
    private JPanel contentPane;
    private JTextField name;
    private JButton button;
    private JRadioButton b1, b2, b3, s1, s2;
    private JComboBox comb;
    String courseType;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    newModules frame = new newModules();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public newModules() {
        //initializing the new JFrame
        setTitle("Course Management System");
        ImageIcon image = new ImageIcon("Herald.png");
        setIconImage(image.getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 190, 1014, 597);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(255, 255, 255));
        setContentPane(contentPane);
        contentPane.setLayout(null);
//----------------------------------------------------------------------------------
        JLabel lbl = new JLabel("New Module");
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 32));
        lbl.setForeground(new Color(153, 224, 101));
        lbl.setBounds(362, 52, 325, 50);
        contentPane.add(lbl);
//--------------------------------------------------------------------------------
        JLabel courseName = new JLabel("Module Name");
        courseName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        courseName.setBounds(58, 152, 150, 43);
        contentPane.add(courseName);

//---------------------------------------------------------------------------------
        //text field for module name
        name = new JTextField();
        name.setFont(new Font("Tahoma", Font.PLAIN, 20));
        name.setBounds(214, 151, 290, 50);
        contentPane.add(name);
        name.setColumns(10);

//-----------------------------------------------------------------------------------
        JLabel course_type = new JLabel("Course Type");
        course_type.setFont(new Font("Tahoma", Font.PLAIN, 20));
        course_type.setBounds(542, 160, 150, 29);
        contentPane.add(course_type);
//-------------------------------------------------------------------------------------
        //here program select the available course type and add it to the array list
        ArrayList<String> course = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM course");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                course.add(rs.getString("coursename"));
            }
//---------------------------------------------------------------------------------------
        } catch (Exception d) {
            JOptionPane.showMessageDialog(null, d);

        }
        //here displays the array list inside the combobox
        comb = new JComboBox(new DefaultComboBoxModel<String>(course.toArray(new String[0])));
        comb.setBounds(680, 160, 100, 30);
        contentPane.add(comb);
        comb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //getting index of the selected value in combo box
                int index = Integer.parseInt(String.valueOf(comb.getSelectedIndex()));
                //also, now getting the value at index of course array and storing it in the string datatype variable
                courseType = course.get(index);
            }
        });

//---------------------------------------------------------------------------------
        JLabel level = new JLabel("Level");
        level.setFont(new Font("Tahoma", Font.PLAIN, 20));
        level.setBounds(542, 235, 99, 29);
        contentPane.add(level);
        //making radiobutton
        b1 = new JRadioButton("L4");
        b1.setBounds(680, 235, 50, 30);
        b2 = new JRadioButton("L5");
        b2.setBounds(730, 235, 50, 30);
        b3 = new JRadioButton("L6");
        b3.setBounds(780, 235, 50, 30);
        //combining radiobutton as one so tht user can't choose multiple button at once
        ButtonGroup b = new ButtonGroup();
        //adding all of them to the panel and inside the group
        b.add(b1);
        b.add(b2);
        b.add(b3);
        add(b1);
        add(b2);
        add(b3);
        setLayout(null);
        setVisible(true);
//---------------------------------------------------------------------------------
        //adding some more radio button as done previously
        JLabel semester = new JLabel("Semester");
        semester.setFont(new Font("Tahoma", Font.PLAIN, 20));
        semester.setBounds(58, 235, 99, 29);
        contentPane.add(semester);

        s1 = new JRadioButton("First");
        s1.setBounds(214, 235, 100, 30);
        s2 = new JRadioButton("Second");
        s2.setBounds(314, 235, 100, 30);
        //also grouping these buttons and then adding them all inside group and also adding them to the panel
        ButtonGroup g = new ButtonGroup();
        g.add(s1);
        g.add(s2);
        add(s1);
        add(s2);
        setLayout(null);
        setVisible(true);
//----------------------------------------------------------------------------------
        //this button just reloads the page so that all the field would be empty
        button = new JButton("Clear All");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                newModules frame = new newModules();
                frame.setVisible(true);
            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(58, 447, 259, 74);
        contentPane.add(button);
//----------------------------------------------------------------------------------
        //code below is for adding new module to the table
        button = new JButton("ADD");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lvl = null;
                String sem = null;
                String option = "FALSE";
                String modelCode = code();
                //-------------------------------------------------------------------------------
                //getting values from the text field and radio buttons
                String moduleName = name.getText();
                if (b1.isSelected()) lvl = "L4";
                if (b2.isSelected()) lvl = "L5";
                if (b3.isSelected()) lvl = "L6";
                if (s1.isSelected()) sem = "First";
                if (s2.isSelected()) sem = "Second";
                //--------------------------------------------------------------------------------
                //checking whether the module, that admin going to add is optional or regular module only if admin is going to add in level 6
                if(lvl == "L6"){
                    int a = JOptionPane.showConfirmDialog(button, "Is it optional module?");
                    if(a == JOptionPane.YES_OPTION) {
                        option = "True";
                    } else if(a == JOptionPane.NO_OPTION){
                        option = "FALSE";
                    }
                } else {

                }
                //now adding the module as new row in the table
                try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    String query = "INSERT INTO module (moduleName, moduleCode, courseName, Level, Semester, Optional) values(?,?,?,?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, moduleName);
                    pst.setString(2, modelCode);
                    pst.setString(3, courseType);
                    pst.setString(4, lvl);
                    pst.setString(5, sem);
                    pst.setString(6, option);
                    int x = pst.executeUpdate();
                    if (x == 0){

                    } else {
                        JOptionPane.showMessageDialog(button, "New course has been added");
                        dispose();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 22));
        button.setBounds(399, 447, 259, 74);
        contentPane.add(button);
    }

    //---------------------------------------------------------------------------------
    //this method creates code for module
    public String code() {
        String code = null;
        try {
            code = alphaNumeric(); // here the alphanumeric method is called to get a random code
            Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                    "root", "password");
            PreparedStatement st = connection.prepareStatement("Select moduleCode from module where moduleCode=?");
            st.setString(1, code);
            ResultSet rs = st.executeQuery();
            //if there is already a module name with the code name then it 'code()' method calls itself as recursion
            if (rs.next()) {
                code();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        //if code doesn't exist then it returns code when it is called
        return code;
    }
    //this method alphaNumeric() just creates a new code that has length of 4 and mixed with numbers and letters
    public String alphaNumeric() {
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String alphaNumeric = upperAlphabet + numbers;
        StringBuilder sb = new StringBuilder();
        // create an object of Random class
        Random random = new Random();
        // specify length of random string
        for (int i = 0; i < 4; i++) {
            // generate random index number
            int index = random.nextInt(alphaNumeric.length());
            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);
            // append the character to string builder
            sb.append(randomChar);
        }
        String randomString = sb.toString();
        return randomString;
    }
//--------------------------------------------------------------------------
}
