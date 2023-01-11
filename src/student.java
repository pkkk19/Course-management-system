import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class student extends JFrame {
    private JPanel contentpane;
    private JPanel panel1 = new JPanel();
    private JPanel profile = new JPanel();
    private JButton btnProfile, btn, btnChoose;
    private JTable table;
    private JLabel lbl;
    private JComboBox comb;
    private String courseType = "";
    private String Level = "";
    private String Semester = "";
    private String moduleName = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    student ad = new student("roman");
                    ad.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     *
     *
     */

    public student(String Name) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);
        contentpane = new JPanel();
        setContentPane(contentpane);
        contentpane.setLayout(null);

        ImageIcon MB = new ImageIcon("backgroundp.jpg");
        JLabel backgroundM = new JLabel("", MB, JLabel.CENTER);
        backgroundM.setBounds(0, 0, 1535, 843);
        contentpane.add(backgroundM);

        panel1.setBounds(0, 0, 300, 840);
        backgroundM.add(panel1);
        //----------------------------------------------------------------------------------------
        ImageIcon img = new ImageIcon("sideB.jpg");
        JLabel background = new JLabel("", img, JLabel.LEFT);
        background.setBounds(0, 0, 0, 0);
        panel1.add(background);
//---------------------------------------------------------------------------------------------
        JLabel logout = new JLabel("logout");
        logout.setFont(new Font("Tahoma", Font.PLAIN, 20));
        logout.setBounds(1480, 806, 99, 43);
        backgroundM.add(logout);
        //------------------------------------------------------------------------------------
        JButton btnNewButton = new JButton(new ImageIcon("logout.png"));
        btnNewButton.setBackground(new Color(0, 255, 0, 0));
        btnNewButton.setBorderPainted(false);
        btnNewButton.setOpaque(false);
        btnNewButton.setContentAreaFilled(false);

//----------------------------------------------------------------------------------------------
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setVisible(true);
                } else {

                }
            }
        });
        btnNewButton.setBounds(1480, 770, 50, 50);
        backgroundM.add(btnNewButton);
//--------------------------------------------------------------------------------------------------------
        JButton button = new JButton(new ImageIcon("changePW.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(Name);
                bo.setTitle("Change Password");
                bo.setVisible(true);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(235, 250, 270, 50);
        background.add(button);

//-------------------------------------------------------------------------------------------------------------------
        profile.setBounds(300, 0, 1080, 840);
        profile.setLayout(null);
        backgroundM.add(profile);
        profile.setVisible(false);

        //------------------------------------------------------------------------


        ///------------------------------------------------------------------------------
        try {
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                    "root", "password");
            PreparedStatement st = con.prepareStatement("Select courseType from marks where userName=?");
            st.setString(1, Name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String type = rs.getString("courseType");
                //--------------------------------------------------------------------------------

                JLabel main = new JLabel(new ImageIcon("main.png"));
                main.setBounds(150, 0, 1535, 843);
                backgroundM.add(main);

                btn = new JButton(new ImageIcon("result.png"));
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        result r = new result(Name);
                        r.setVisible(true);
                    }
                });
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setBounds(235, 140, 270, 50);
                background.add(btn);

                //---------------------------------------------------------------------------------------------
                btnProfile = new JButton(new ImageIcon("profile.png"));
                btnProfile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        profile.setVisible(true);
                        main.setVisible(false);
                    }
                });
                btnProfile.setBounds(235, 30, 270, 50);
                btnProfile.setBackground(new Color(0, 0, 0, 0));
                btnProfile.setOpaque(false);
                btnProfile.setContentAreaFilled(false);
                btnProfile.setBorderPainted(false);
                background.add(btnProfile);

                //----------------------------------------------------------------------------------------------
                lbl = new JLabel();

                DefaultTableModel mdl = new DefaultTableModel();
                table = new JTable();
                table.setPreferredScrollableViewportSize(new Dimension(1024, 400));
                table.setModel(mdl);
                table.setRowHeight(34);
                table.setBounds(0, 345, 1024, 100);
                table.setTableHeader(null);
                JScrollPane scroll9 = new JScrollPane(table);
                scroll9.setHorizontalScrollBarPolicy(
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scroll9.setVerticalScrollBarPolicy(
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scroll9.setBounds(0, 465, 1080, 100);
                profile.add(scroll9);
                table(Name);

            } else {
                JLabel Welcome = new JLabel("Welcome To Herald");
                Welcome.setForeground(Color.BLACK);
                Welcome.setFont(new Font("Tacoma", Font.PLAIN, 40));
                Welcome.setBounds(730, 170, 400, 52);
                backgroundM.add(Welcome);

                JLabel lblCourseSelection = new JLabel("Enroll for a course");
                lblCourseSelection.setForeground(Color.BLACK);
                lblCourseSelection.setFont(new Font("Tacoma", Font.PLAIN, 26));
                lblCourseSelection.setBounds(790, 300, 210, 52);
                backgroundM.add(lblCourseSelection);

//------------------------------------------------------------------------------------------------------------------------

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
                comb = new JComboBox(new DefaultComboBoxModel<String>(course.toArray(new String[0])));
                comb.setBounds(840, 370, 100, 30);
                backgroundM.add(comb);
                comb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int index = Integer.parseInt(String.valueOf(comb.getSelectedIndex()));
                        courseType = course.get(index);
                        System.out.println(courseType);
                    }
                });
//------------------------------------------------------------------------------------------------------------
                btn = new JButton(new ImageIcon("enroll.png"));
                btn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (courseType == "") {
                            JOptionPane.showMessageDialog(btn, "Please Enroll for a Course First.");
                        } else {
                            try {
                                Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                                        "root", "password");
                                ArrayList<String> moduleName = new ArrayList<>();
                                ArrayList<String> moduleCode = new ArrayList<>();
                                PreparedStatement st = connect.prepareStatement
                                        ("Select moduleName, moduleCode from module where courseName = ? and Level = 'L4' and Semester = 'First' and status = 'ACTIVE'");
                                st.setString(1, courseType);
                                ResultSet rs = st.executeQuery();
                                while (rs.next()) {
                                    moduleName.add(rs.getString("moduleName"));
                                    moduleCode.add(rs.getString("moduleCode"));
                                }
                                Iterator itr = moduleName.iterator();//getting the Iterator
                                Iterator i = moduleCode.iterator();
                                while (itr.hasNext()) {//check if iterator has the elements
                                    String modelN = (String) itr.next();
                                    String modelC = (String) i.next();
                                    System.out.println(modelC + modelN);
                                    String query2 = "INSERT INTO marks (userName, moduleCode, moduleName, courseType, Level, Semester) values('"
                                            + Name + "','" + modelC + "','" + modelN + "','" + courseType + "','" + "L4" + "','" + "First" + "')";
                                    Statement stat = connect.createStatement();
                                    int y = stat.executeUpdate(query2);
                                    if (y == 0) {
                                    } else {

                                    }
                                }
                                JOptionPane.showMessageDialog(btn, "You have been enrolled");
                                dispose();
                                student stud = new student(Name);
                                stud.setVisible(true);

                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }

                    }
                });
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setBounds(760, 440, 270, 50);
                backgroundM.add(btn);


                //-----------------------------------------------------------------------

            }
        } catch (Exception d) {
            JOptionPane.showMessageDialog(null, d);
        }
        //------------------------------------------------------------------------

    }

    private void table(String Name) {
        try {
            ArrayList<String> moduleCode = new ArrayList<>();
            String marks = "";
            String Grade = "";
            String Remarks = "";


            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = connect.prepareStatement("Select moduleCode, Level, Semester from marks where userName = ? and status = 'ACTIVE'");
            ps.setString(1, Name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moduleCode.add(rs.getString("moduleCode"));
                Level = rs.getString("Level");
                Semester = rs.getString("Semester");

            }
            table(moduleCode);

            lbl = new JLabel("Profile");
            lbl.setFont(new Font("Arial", Font.PLAIN, 30));
            lbl.setBounds(430, 50, 400, 45);
            profile.add(lbl);

            lbl = new JLabel("--------------------------------------------------------------------------------------------------------------------------");
            lbl.setFont(new Font("Arial", Font.PLAIN, 26));
            lbl.setBounds(0, 130, 1080, 37);
            profile.add(lbl);

            result re = new result(Name);

            lbl = new JLabel("Student Name: ");
            lbl.setFont(new Font("Arial", Font.PLAIN, 19));
            lbl.setBounds(30, 270, 150, 22);
            profile.add(lbl);

            lbl = new JLabel(re.firstName + " " + re.lastName);
            lbl.setFont(new Font("courier new", Font.PLAIN, 15));
            lbl.setBounds(170, 273, 300, 22);
            profile.add(lbl);

            lbl = new JLabel("Course: ");
            lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
            lbl.setBounds(30, 300, 300, 22);
            profile.add(lbl);

            lbl = new JLabel(re.courseType);
            lbl.setFont(new Font("courier new", Font.PLAIN, 15));
            lbl.setBounds(170, 303, 300, 22);
            profile.add(lbl);

            lbl = new JLabel("Level: ");
            lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
            lbl.setBounds(510, 270, 300, 22);
            profile.add(lbl);

            lbl = new JLabel(Level);
            lbl.setFont(new Font("courier new", Font.PLAIN, 15));
            lbl.setBounds(640, 273, 150, 22);
            profile.add(lbl);

            lbl = new JLabel("Semester: ");
            lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
            lbl.setBounds(510, 300, 300, 22);
            profile.add(lbl);

            lbl = new JLabel(Semester);
            lbl.setFont(new Font("courier new", Font.PLAIN, 15));
            lbl.setBounds(640, 303, 150, 22);
            profile.add(lbl);

            lbl = new JLabel("--------------------------------------------------------------------------------------------------------------------------");
            lbl.setFont(new Font("Arial", Font.PLAIN, 26));
            lbl.setBounds(0, 420, 1080, 37);
            profile.add(lbl);

            lbl = new JLabel("  Module Code         | " +
                    "Module Name                     " +
                    "                  | Module Leader      " +
                    "|                                                            " +
                    "Tutors");
            lbl.setFont(new Font("Arial", Font.PLAIN, 15));
            lbl.setBounds(0, 435, 1024, 37);
            profile.add(lbl);

            lbl = new JLabel("--------------------------------------------------------------------------------------------------------------------------");
            lbl.setFont(new Font("Arial", Font.PLAIN, 26));
            lbl.setBounds(0, 444, 1080, 37);
            profile.add(lbl);

            lbl = new JLabel("--------------------------------------------------------------------------------------------------------------------------");
            lbl.setFont(new Font("Arial", Font.PLAIN, 26));
            lbl.setBounds(0, 546, 1080, 37);
            profile.add(lbl);


            switch (Level) {
                case "L6":
                    //--------------------------------------------------------------------------------------

                    JLabel OPTMdl = new JLabel("Optional Module:");
                    OPTMdl.setFont(new Font("Tahoma", Font.PLAIN, 20));
                    OPTMdl.setBounds(100, 650, 152, 29);
                    profile.add(OPTMdl);

                    ArrayList<String> module = new ArrayList<>();


                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
                        PreparedStatement s = conn.prepareStatement("SELECT * FROM module where optional = 'True' AND Semester = ?");
                        s.setString(1, Semester);
                        ResultSet rsa = s.executeQuery();
                        while (rsa.next()) {

                            module.add(rsa.getString("moduleName"));


                        }


                    } catch (Exception d) {
                        JOptionPane.showMessageDialog(null, d);

                    }
                    comb = new JComboBox(new DefaultComboBoxModel<String>(module.toArray(new String[0])));
                    comb.setBounds(300, 650, 300, 30);
                    profile.add(comb);
                    comb.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int index = Integer.parseInt(String.valueOf(comb.getSelectedIndex()));
                            moduleName = module.get(index);
                            System.out.println(moduleName);
                        }
                    });

                    //------------------------------------------------------------------------------


                    btnChoose = new JButton(new ImageIcon("enroll.png"));
                    btnChoose.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                                        "root", "password");
                                ArrayList<String> moduleCode = new ArrayList<>();
                                PreparedStatement st = connect.prepareStatement
                                        ("Select moduleCode from module where courseName = ? and moduleName = ? and status = 'ACTIVE'");
                                st.setString(1, re.courseType);
                                st.setString(2, moduleName);
                                ResultSet rs = st.executeQuery();
                                while (rs.next()) {
                                    moduleCode.add(rs.getString("moduleCode"));
                                }
                                Iterator itr = moduleCode.iterator();
                                while (itr.hasNext()) {//check if iterator has the elements
                                    String modelC = (String) itr.next();
                                    String query2 = "INSERT INTO marks (userName, moduleCode, moduleName, courseType, Level, Semester, Optional) values('"
                                            + Name + "','" + modelC + "','" + moduleName + "','" + re.courseType + "','" + "L6" + "','" + Semester + "','" + "1" + "')";
                                    Statement stat = connect.createStatement();
                                    int y = stat.executeUpdate(query2);
                                    if (y == 0) {
                                    } else {

                                    }
                                }
                                JOptionPane.showMessageDialog(btn, "You have been enrolled");
                                table(moduleCode);
                                table.revalidate();
                                table.repaint();
                                btnChoose.setVisible(false);
                                comb.setVisible(false);
                                OPTMdl.setVisible(false);

                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                        }
                    });
                    btnChoose.setOpaque(false);
                    btnChoose.setContentAreaFilled(false);
                    btnChoose.setBorderPainted(false);
                    btnChoose.setBounds(350, 700, 250, 50);
                    profile.add(btnChoose);

                    PreparedStatement st = connect.prepareStatement("Select courseType from marks where userName=? and Level = 'L6' and Semester = ? and Optional = '1'");
                    st.setString(1, Name);
                    st.setString(2, Semester);
                    ResultSet RSet = st.executeQuery();
                    if (RSet.next()) {
                        btnChoose.setVisible(false);
                        comb.setVisible(false);
                        OPTMdl.setVisible(false);
                    } else {

                    }
                    break;
                default:
                    System.out.println();
            }


        } catch (Exception exe) {
            JOptionPane.showMessageDialog(null, exe);
        }
    }

    private void table(ArrayList<String> moduleCode) {
        int j = 0;
        ArrayList<String> code = new ArrayList<>();
        Iterator itr = moduleCode.iterator();
        while (itr.hasNext()) {
            code.add((String) itr.next());

            j++;
            try {
                Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
                PreparedStatement st = connect.prepareStatement("Select moduleCode, moduleName, moduleLead, tutor1, tutor2, tutor3, tutor4 from module where moduleCode = ?" + "OR moduleCode = ?".repeat(j));
                st.setString(1, moduleCode.get(0));
                int l = 2;
                for (int k = 0; k < j; k++) {
                    st.setString(l, code.get(k));
                    l++;
                }
                ResultSet r = st.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(r));
                table.getColumnModel().getColumn(0).setPreferredWidth(80);
                table.getColumnModel().getColumn(1).setPreferredWidth(200);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}
