import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class Teacher extends JFrame {
    private JPanel contentpane;
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel(new GridLayout(0, 1));
    private JPanel panel3 = new JPanel();
    private JTextField marks, Remarks;
    private JTable table1;
    private JTextArea test, test1;
    private String value, value1, moduleName, M, Grade, Remark;

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

    public Teacher(String userName) {
        //setting up new frame
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

        JLabel main = new JLabel(new ImageIcon("main.png"));
        main.setBounds(150, 0, 1535, 843);
        backgroundM.add(main);

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
        //for logout which disposes the current frame and opens login page when clicking on it
        JButton btnNewButton = new JButton(new ImageIcon("logout.png"));
        btnNewButton.setOpaque(false);
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
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
        //this button calls change password class and makes the frame inside that class visible
        JButton button = new JButton(new ImageIcon("changePW.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userName);
                bo.setTitle("Change Password");
                bo.setVisible(true);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(235, 80, 270, 50);
        background.add(button);


//-------------------------------------------------------------------------------------------------------------------
        //this button displays the marking panel(panel3) and closes all the other panels
        JButton scoring = new JButton(new ImageIcon("mark.png"));
        scoring.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel3.setVisible(true);
                main.setVisible(false);
                panel2.setVisible(false);
            }
        });
        scoring.setOpaque(false);
        scoring.setContentAreaFilled(false);
        scoring.setBorderPainted(false);
        scoring.setBounds(235, 140, 280, 50);
        background.add(scoring);

//-------------------------------------------------------------------------------------------------------------------
        //this button displays the profile of the teacher(panel2) and closes other panels
        JButton moduleDtl = new JButton(new ImageIcon("profile.png"));
        moduleDtl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel2.setVisible(true);
                main.setVisible(false);
                panel3.setVisible(false);
            }
        });
        moduleDtl.setOpaque(false);
        moduleDtl.setContentAreaFilled(false);
        moduleDtl.setBorderPainted(false);
        moduleDtl.setBounds(235, 20, 270, 50);
        background.add(moduleDtl);
//-----------------------------------------------------------------------------
        panel2.setBounds(300, 0, 1080, 840);
        backgroundM.add(panel2);
        panel2.setVisible(false);
//-----------------------------------------------------------------------------

        ArrayList<String> moduleDTL = new ArrayList<>();
        ArrayList<String> moduleCode = new ArrayList<>();
        //--------------------------------------------------------------------------------------------------
        //accessing details about the assigned modules to the user from module table
        try {
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                    "root", "password");
            PreparedStatement st = con.prepareStatement
                    ("Select moduleCode, moduleName, courseName, Level, Semester from module where " +
                            "moduleLead=? OR tutor1=? OR tutor2=? OR tutor3=? OR tutor4=?");
            st.setString(1, userName);
            st.setString(2, userName);
            st.setString(3, userName);
            st.setString(4, userName);
            st.setString(5, userName);
            ResultSet rs = st.executeQuery();
            //-----------------------------------------------------------
            while (rs.next()) {
                moduleDTL.add(rs.getString("moduleCode"));
                moduleDTL.add(rs.getString("moduleName"));
                moduleDTL.add(rs.getString("courseName"));
                moduleDTL.add(rs.getString("Level"));
                moduleDTL.add(rs.getString("Semester"));
            }
            //------------------------------------------------------------
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

//=========================================================================================
        //accesing moduledetails
        Iterator itr = moduleDTL.iterator();
        String DtlModule = "";
        while (itr.hasNext()) {
            String modelC = (String) itr.next();
            moduleCode.add(modelC);
            String modelN = (String) itr.next();
            String courseN = (String) itr.next();
            String L = (String) itr.next();
            String Sem = (String) itr.next() + "  ";

            DtlModule += modelC + " | " + modelN + "\n" +
                    "Course Name: " + courseN + "\n" +
                    "Level: " + L + "\n" +
                    "Semester:  " + Sem + "\n" + "" +
                    "+-------------------------------------------------------+\n";

        }
//=========================================================================================
        //displaying the module details taught by user and adding it to the panel2 which is the main profile panel
        test = new JTextArea();
        test.append(DtlModule);
        test.setEditable(false);
        test.setOpaque(false);
        test.setForeground(Color.BLACK);
        JScrollPane SC = new JScrollPane(test);
        SC.setOpaque(false);
        SC.getViewport().setOpaque(false);
        panel2.add(SC);
        test.setFont(new Font("Tacoma", Font.PLAIN, 20));

        //----------------------------------------------------
        // adding module assigned to the panel2(profile)
        JLabel moduleTeach = new JLabel("               " +
                "        Modules Assigned");
        moduleTeach.setFont(new Font("Tacoma", Font.PLAIN, 45));
        moduleTeach.setBounds(300, 30, 1000, 55);
        panel2.add(moduleTeach, 2, 0);

//------------------------------------------------------------------------------------------------
        panel3.setBounds(300, 0, 1080, 840);
        backgroundM.add(panel3);
        panel3.setVisible(false);
//----------------------------------------------------------------------------------------------
        //setting up the new table and adding the table to the scroll pane to make it scrollable if there is lots of datas
        DefaultTableModel model = new DefaultTableModel();
        table1 = new JTable();
        table1.setPreferredScrollableViewportSize(new Dimension(1050, 300));
        table1.setModel(model);
        JScrollPane scroll9 = new JScrollPane(table1);
        scroll9.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll9.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel3.add(scroll9);
        table(moduleCode);


        //---------------------------------------------------------------------------------

        test1 = new JTextArea();
        test1.setEditable(false);
        test1.setOpaque(false);
        test1.setForeground(Color.BLACK);
        test1.setVisible(true);
        test1.setFont(new Font("Tacoma", Font.PLAIN, 28));
        panel3.add(test1);

        //======================================================================================
        //adding mouse listener so tht i can fetch data at the areas where the mouse is pressed
        table1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                // here program can fetch data's of column index 0 and 1 from the selected row
                int row = table1.getSelectedRow();
                value = table1.getModel().getValueAt(row, 0).toString(); //userName
                value1 = table1.getModel().getValueAt(row, 1).toString(); //moduleCode
                moduleName = table1.getModel().getValueAt(row, 2).toString();
                M = table1.getModel().getValueAt(row, 3).toString();
                Grade = table1.getModel().getValueAt(row, 4).toString();
                Remark = table1.getModel().getValueAt(row, 5).toString();

                //===============================================================
                ArrayList<String> studentDTL = new ArrayList<>();
                try {
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    //-----------------------------------------------------------
                    //selecting full name of the selected students in the table
                    PreparedStatement sta = con.prepareStatement("Select first_name, last_name from account where " +
                            "user_name = ?");
                    sta.setString(1, value);
                    ResultSet rsa = sta.executeQuery();
                    //-----------------------------------------------------------
                    while (rsa.next()) {
                        studentDTL.add(rsa.getString("first_name"));
                        studentDTL.add(rsa.getString("last_name"));
                    }
                    //------------------------------------------------------------
                    //setting strings to null for initialization
                    //------------------------------------------------------------
                    String firstName = "";
                    String lastName = " ";
                    String studentDtl = "";
                    //-------------------------------------------------------------------------
                    Iterator j = studentDTL.iterator();
                    while (j.hasNext()) {
                        firstName = (String) j.next();
                        lastName = (String) j.next();
                    }
                    //storing the student details in proper format
                    studentDtl = value1 + " | " + moduleName + "\n" +
                            "+------------------------------------------------------------------------------------------------------------+\n" +
                            "Student Name: " + firstName + " " + lastName + "\n" +
                            "Username: " + value + "\n" +
                            "Marks: " + M + "\n" +
                            "Grade:  " + Grade + "\n" +
                            "Remarks: " + Remark + "\n" +
                            "+=================================================================+\n";
                    test1.setText(studentDtl);
                    test1.revalidate();
                    test1.repaint();


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                //============================================================================
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });

        //--------------------------------------------------------------------------------
        marks = new JTextField();
        marks.setFont(new Font("Tacoma", Font.PLAIN, 26));
        marks.setMargin(new Insets(5, 5, 5, 5));
        panel3.add(marks);
        marks.setColumns(2);

        Remarks = new JTextField();
        Remarks.setFont(new Font("Tacoma", Font.PLAIN, 26));
        Remarks.setMargin(new Insets(5, 5, 5, 5));
        panel3.add(Remarks);
        Remarks.setColumns(10);

        //======================================================================================

        JButton mark = new JButton(new ImageIcon("mark.png"));
        mark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //fetching marks from text field which will be in string
                    String marking = marks.getText();
                    //now adding it to the integer variable by converting the string variable to the integer
                    int score = Integer.valueOf(marking);
                    String remarking = Remarks.getText();
                    marks.setText("");
                    Remarks.setText("");
                    String grade = "";
                    //total scored is 100 so if i divide it by the 10 then i can get the total marks between 0 and 10
                    switch (score / 10) {
                        // for >= 90
                        case 10:
                        case 9:
                        case 8:
                        case 7:
                            grade = "First-Class Honours";
                            break;
                        case 6:
                            grade = "Upper Second-Class Honours";
                            break;
                        // for >= 60 and <70
                        case 5:
                            grade = "Lower Second-Class Honours";
                            break;
                        // for >= 50 and <60
                        case 4:
                            grade = "Third Class Honours";
                            break;
                        // for < 50
                        default:
                            grade = "Fail";
                            break;
                    }
                    //-------------------------------------------------------------------------------------------------------
                    //now updating the marks, Grade and remarks of the student whose username already exists in the table named marks
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("Update marks set marks = ?, Grade = ?, Remarks = ? where moduleCode = ? and userName = ?");
                    st.setString(1, marking);
                    st.setString(2, grade);
                    st.setString(3, remarking);
                    st.setString(4, value1);
                    st.setString(5, value);
                    st.executeUpdate();
                    table(moduleCode);
                    table1.revalidate();
                    table1.repaint();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc);
                }
            }
        });
        mark.setOpaque(false);
        mark.setContentAreaFilled(false);
        mark.setBorderPainted(false);
        mark.setMargin(new Insets(18, 70, 18, 200));
        panel3.add(mark);

    }
    //=============================================================================================
    //defining new method to display the table also this method takes in the arraylist parameter
    private void table(ArrayList moduleCode) {
        int j = 0;
        ArrayList<String> code = new ArrayList<>();
        Iterator i = moduleCode.iterator();//getting the Iterator
        while (i.hasNext()) {
            String module = (String) i.next();
            code.add(module);
            //here I am increasing the value of the j so my program will be aware of numbers of module codes
            j++;
            try {
                Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                        "root", "password");
                /*here if the j is zero then the end of statement will not add but if it is one then end of statement will also be added and also if
                it is 2 or more that same statement will be repeated and added to the end of the statement*/
                PreparedStatement ps = connect.prepareStatement("SELECT userName, moduleCode, moduleName, marks, Grade, Remarks FROM marks where status  = 'ACTIVE' and (moduleCode = ?" + "OR moduleCode = ?".repeat(j) + ")");
                ps.setString(1, code.get(0));
                //here I have used a classic for loop instead of the iterator because I want to access the second element of the arraylist
                int l = 2;
                for (int k = 0; k < j; k++) {
                    ps.setString(l, code.get(k));
                    l++;
                }
                ResultSet rs = ps.executeQuery();
                table1.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}
