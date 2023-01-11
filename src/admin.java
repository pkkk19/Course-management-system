import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class admin extends JFrame {
    private JPanel contentpane;
    private JPanel panel1 = new JPanel();
    private JPanel teacherDtl = new JPanel();
    private JPanel courseDtl = new JPanel();
    private JPanel studentDtl = new JPanel();
    private JPanel resultDtl = new JPanel();
    private JTable table1, table2, table3, table4;
    private String value, value1, col, status, user;


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    admin ad = new admin("admin");
                    ad.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * setting up the new JFrame
     * this method is called to display the main admin dashboard and to access all
     * the power of an admin
     */

    public admin(String userName) {
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
        //==========================================================================================

        JLabel main = new JLabel(new ImageIcon("main.png"));
        main.setBounds(150, 0, 1535, 843);
        backgroundM.add(main);

        //-----------------------------------------------------------------------------------------
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
        //logs out of the admin and displays the login page
        JButton btnNewButton = new JButton(new ImageIcon("logout.png"));
        btnNewButton.setOpaque(false);
        btnNewButton.setContentAreaFilled(false);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setBorderPainted(false);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) { //if pressed yes disposes the current frame and displays the login frame
                    dispose();
                    UserLogin obj = new UserLogin();//creating the object for UserLogin method
                    obj.setVisible(true);
                } else {
                }
            }
        });
        btnNewButton.setBounds(1480, 770, 50, 50);
        backgroundM.add(btnNewButton);
//-------------------------------------------------------------------------------------------------------
        JButton button = new JButton(new ImageIcon("changePW.png"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(userName);//creating a new method for ChangePassword method
                bo.setTitle("Change Password");
                bo.setVisible(true);
            }
        });
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBounds(235, 430, 270, 50);
        background.add(button);
//------------------------------------------------------------------------------------
        studentDtl.setBounds(300, 0, 1080, 840);
        backgroundM.add(studentDtl);
        studentDtl.setVisible(false);
//================================================================================================
        /**
         * creating a table and adding the table to the scrollPane and adding scrollPane to the panel
         */
        DefaultTableModel model1 = new DefaultTableModel();
        table3 = new JTable();
        table3.setPreferredScrollableViewportSize(new Dimension(1050, 400));
        table3.setModel(model1);
        JScrollPane scroll8 = new JScrollPane(table3);
        scroll8.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll8.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        studentDtl.add(scroll8);

//--------------------------------------------------------------------------------------
        //creating text area
        JTextArea details = new JTextArea();
        details.setEditable(false);
        details.setOpaque(false);
        details.setForeground(Color.BLACK);
        JScrollPane SC = new JScrollPane(details);
        SC.setOpaque(false);
        SC.getViewport().setOpaque(false);
        studentDtl.add(SC);
        details.setFont(new Font("Tacoma", Font.PLAIN, 20));
        //----------------------------------------------------------------------
        JButton studentDTL = new JButton(new ImageIcon("studentDtl.png"));
        studentDTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String enrolled = "";
                    Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    //selects the students
                    PreparedStatement st = connect.prepareStatement
                            ("Select first_name, last_name, user_name, password, email_id, mobile_number from account where type = ? ");
                    st.setString(1, "student");
                    ResultSet rs = st.executeQuery();
                    //----------------------------------------------------------------------------------------------------------------
                    //displays the student details selected from the query and displays it in the table
                    table3.setModel(DbUtils.resultSetToTableModel(rs));
                    studentDtl.setVisible(true);//setting the required frame visible and turning other frames invisible
                    main.setVisible(false);
                    courseDtl.setVisible(false);
                    teacherDtl.setVisible(false);
                    resultDtl.setVisible(false);
//-------------------------------------------------------------------------------------------------
                    // selects the number of students with different username available in the marks table which will be the number of enrolled students tht i wanted to display
                    PreparedStatement sta = connect.prepareStatement("SELECT COUNT(DISTINCT userName) FROM marks;");
                    ResultSet Set = sta.executeQuery();
                    while (Set.next()) {
                        enrolled = Set.getString(1);
                    }
                    //displays the number
                    details.setText("Number of Enrolled Students: " + enrolled);
                } catch (Exception exp) {
                    JOptionPane.showMessageDialog(null, exp);
                }
                //------------------------------------------------------------------------------------------
            }
        });
        studentDTL.setOpaque(false);
        studentDTL.setContentAreaFilled(false);
        studentDTL.setBorderPainted(false);
        studentDTL.setBounds(235, 310, 270, 50);
        background.add(studentDTL);
//--------------------------------------------------------------------------------------
        resultDtl.setBounds(300, 0, 1080, 840);
        backgroundM.add(resultDtl);
        resultDtl.setVisible(false);
/**
 * creating a new table and adding it to the scrollPane and then adding the scroll to the panel
 */
        DefaultTableModel mod = new DefaultTableModel();
        table4 = new JTable();
        table4.setPreferredScrollableViewportSize(new Dimension(1050, 610));
        table4.setModel(mod);
        table4.setFont(new Font("sheriff", Font.PLAIN, 26));
        table4.setRowHeight(34);
        JScrollPane scroll = new JScrollPane(table4);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultDtl.add(scroll);
//------------------------------------------------------------------------------------------------
        table1();//calling the table1() method to display the data's in the table

        //--------------------------------------------------------------------
        table4.addMouseListener(new MouseListener() { //
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int row = table4.getSelectedRow();
                user = table4.getModel().getValueAt(row, 0).toString();//selects the first column of the selected row by mouse
                System.out.println(user);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
//--------------------------------------------------------------------------------------------
        JButton results = new JButton(new ImageIcon("result.png"));
        results.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //views the resultDtl panel
                resultDtl.setVisible(true);
                main.setVisible(false);
                teacherDtl.setVisible(false);
                studentDtl.setVisible(false);
                courseDtl.setVisible(false);
            }
        });
        results.setOpaque(false);
        results.setContentAreaFilled(false);
        results.setBorderPainted(false);
        results.setBounds(235, 370, 270, 50);
        background.add(results);
        //------------------------------------------------------------------------
        //this button simply prints the result
        JButton printResult = new JButton(new ImageIcon("result.png"));
        printResult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result r = new result(user);//creating an object for result method and passing the username of the student to take out the result of
                r.setVisible(true);
                r.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (r.finalGrade != "Fail") {   //if the student passes
                            try {
                                String moduleName = "";
                                String moduleCode = "";
                                ArrayList<String> moduleN = new ArrayList<>();
                                ArrayList<String> moduleC = new ArrayList<>();
                                Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                                        "root", "password");
                                //getting marks
                                PreparedStatement s = connect.prepareStatement
                                        ("Select marks from marks where userName = ?");
                                s.setString(1, user);
                                ResultSet m = s.executeQuery();
                                ArrayList<String> Z = new ArrayList<>();
                                while (m.next()) {
                                    Z.add(m.getString("marks"));
                                }
                                Iterator c = Z.iterator();
                                int count = 0;
                                while (c.hasNext()) {
                                    String condition = (String) c.next();
                                    switch (condition) {
                                        case "0":
                                            count++; // if any marks is zero count will increase by one
                                            break;
                                        default:
                                    }
                                }
                                //asking the admin if he/she wants the student tot preogress for the next level
                                int x = JOptionPane.showConfirmDialog(btnNewButton, "Do you want this student to progress?");
                                if (x == JOptionPane.YES_OPTION) {
                                    PreparedStatement st = connect.prepareStatement // if selected yes then the next level active module names and codes will be selected
                                            ("Select moduleName, moduleCode from module where courseName = ? and Level = ? and Semester = ? and Optional = 'FALSE' and status = 'ACTIVE'");
                                    st.setString(1, r.courseType);
                                    st.setString(2, r.level);
                                    st.setString(3, r.Semester);
                                    ResultSet rs = st.executeQuery();
                                    while (rs.next()) {
                                        moduleN.add(rs.getString("moduleName"));
                                        moduleC.add(rs.getString("moduleCode"));
                                    }
                                    Iterator i = moduleN.iterator();
                                    Iterator itr = moduleC.iterator();
                                    while (i.hasNext()) {
                                        moduleCode = (String) itr.next();
                                        moduleName = (String) i.next();
                                        // if student is marked in all the subjects then only the student will be enrolled
                                        switch (count) {
                                            case 0:
                                                String query2 = "INSERT INTO marks (userName, moduleCode, moduleName, courseType, Level, Semester) values('"
                                                        + user + "','" + moduleCode + "','" + moduleName + "','" + r.courseType + "','" + r.level + "','" + r.Semester + "')";
                                                Statement sta = connect.createStatement();
                                                int y = sta.executeUpdate(query2);
                                                if (y == 0) {

                                                } else {

                                                }
                                                //also while enrolling for the new module previously enrolled module will be turned into inactive as he/she is not learning anymore
                                                Iterator loop = r.moduleCode.iterator();
                                                while (loop.hasNext()) {
                                                    String code = (String) loop.next();
                                                    PreparedStatement stat = (PreparedStatement) connect
                                                            .prepareStatement("Update marks set status = 'INACTIVE' where moduleCode = ?");
                                                    stat.setString(1, code);
                                                    stat.executeUpdate();
                                                }
                                                break;
                                            default:

                                        }
                                    }
                                    //Also, if the student already passed all the levels then there would be the message printed that the student is graduated
                                    if(r.level == "Graduated"){
                                        JOptionPane.showMessageDialog(null, "Student is Graduated!");
                                    }else {
                                        //and if student is not graduated, yet it will print out tht student has been enrolled
                                        JOptionPane.showMessageDialog(null, "Student has been enrolled");

                                    }
                                } else {
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }
                    }
                });

            }
        });
        printResult.setOpaque(false);
        printResult.setContentAreaFilled(false);
        printResult.setBorderPainted(false);
        printResult.setMargin(new Insets(25, 100, 25, 170));
        resultDtl.add(printResult);
//-------------------------------------------------------------------------------------------------------------------
        JButton newUser = new JButton(new ImageIcon("newUser.png"));
        newUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newUser NW = new newUser(); //creating an object for a method
                NW.setVisible(true); //setting the frame visible
            }
        });
        newUser.setOpaque(false);
        newUser.setContentAreaFilled(false);
        newUser.setBorderPainted(false);
        newUser.setBounds(235, 10, 270, 50);
        background.add(newUser);

//-----------------------------------------------------------------------------


//-----------------------------------------------------------------------------

        JButton courseDlt = new JButton(new ImageIcon("courseD.png"));
        courseDlt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse Dtl = new deleteCourse(); //creating object for method
                Dtl.setVisible(true); // displays the frame
            }
        });
        courseDlt.setOpaque(false);
        courseDlt.setContentAreaFilled(false);
        courseDlt.setBorderPainted(false);
        courseDlt.setBounds(235, 130, 270, 50);
        background.add(courseDlt);

//----------------------------------------------------------------------------------
        JButton newCourse = new JButton(new ImageIcon("newCourse.png"));
        newCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newCourse NW = new newCourse();//creating the object for method
                NW.setVisible(true);//displaying the frame
            }
        });
        newCourse.setOpaque(false);
        newCourse.setContentAreaFilled(false);
        newCourse.setBorderPainted(false);
        newCourse.setBounds(235, 70, 270, 50);
        background.add(newCourse);
//----------------------------------------------------------------------------------
        courseDtl.setBounds(300, 0, 1080, 840);
        backgroundM.add(courseDtl);
        courseDtl.setVisible(false);
//--------------------------------------------------------------------------------------

        //creating new JTable and adding it to the scroll pane and then adding scrollPane to the courseDTl panel
        DefaultTableModel model = new DefaultTableModel();
        table1 = new JTable();
        table1.setPreferredScrollableViewportSize(new Dimension(1050, 610));
        table1.setModel(model);
        JScrollPane scroll9 = new JScrollPane(table1);
        scroll9.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll9.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        courseDtl.add(scroll9);
//------------------------------------------------------------------------------------------
        //calling method
        table();

        table1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
                int column = 1;
                int row = table1.getSelectedRow();
                value = table1.getModel().getValueAt(row, column).toString();//getting values at the selected cell
                status = table1.getModel().getValueAt(row, 5).toString(); // getting vale at the 6th column of the selected row
                System.out.println(value);
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
//--------------------------------------------------------------------------------
        JButton courseD = new JButton(new ImageIcon("moduleDtl.png"));
        courseD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                courseDtl.setVisible(true);//displaying courseDtl panel while hiding others
                main.setVisible(false);
                teacherDtl.setVisible(false);
                studentDtl.setVisible(false);
                resultDtl.setVisible(false);
            }
        });
        courseD.setOpaque(false);
        courseD.setContentAreaFilled(false);
        courseD.setBorderPainted(false);
        courseD.setBounds(235, 190, 270, 50);
        background.add(courseD);
//-----------------------------------------------------------------------------
        //refreshes values at the table
        JButton refresh = new JButton(new ImageIcon("refresh.png"));
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table();
                table1.revalidate();
                table1.repaint();
            }
        });
        refresh.setOpaque(false);
        refresh.setContentAreaFilled(false);
        refresh.setBorderPainted(false);
        refresh.setMargin(new Insets(25, 25, 25, 25));
        courseDtl.add(refresh);
//--------------------------------------------------------------------------------------
        JButton newModule = new JButton(new ImageIcon("add.png"));
        newModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newModules NC = new newModules();//creating object for methods
                NC.setVisible(true);//displaying the frame
            }
        });
        newModule.setOpaque(false);
        newModule.setContentAreaFilled(false);
        newModule.setBorderPainted(false);
        newModule.setMargin(new Insets(25, 100, 25, 150));
        courseDtl.add(newModule, 1);
//--------------------------------------------------------------------------------------------
        //invert just changes the status of module by changing it from the active to inactive and vice-versa
        JButton cancelModule = new JButton(new ImageIcon("invert.png"));
        cancelModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    switch (status) {
                        case "ACTIVE":
                            status = "INACTIVE";
                            break;
                        case "INACTIVE":
                            status = "ACTIVE";
                            break;
                    }
                    PreparedStatement st = (PreparedStatement) connect
                            .prepareStatement("Update module set status = ? where moduleCode=?");
                    st.setString(1, status);
                    st.setString(2, value);
                    st.executeUpdate();
                    //also, after changing the status revalidating the table
                    table();
                    table1.revalidate();
                    table1.repaint();
                } catch (Exception exe) {
                    JOptionPane.showMessageDialog(null, exe);
                }
            }
        });
        cancelModule.setOpaque(false);
        cancelModule.setContentAreaFilled(false);
        cancelModule.setBorderPainted(false);
        cancelModule.setMargin(new Insets(25, 100, 25, 100));
        courseDtl.add(cancelModule, 3);
//-------------------------------------------------------------------------------------------

        JButton modifyM = new JButton(new ImageIcon("update.png"));
        modifyM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update u = new update(value); // creating object for update method
                u.setVisible(true);//displaying the frame
            }
        });
        modifyM.setOpaque(false);
        modifyM.setContentAreaFilled(false);
        modifyM.setBorderPainted(false);
        modifyM.setMargin(new Insets(25, 100, 25, 100));
        courseDtl.add(modifyM, 4);

//--------------------------------------------------------------------------------------------
        JButton dltCourse = new JButton(new ImageIcon("delete.png"));
        dltCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //deletes the selected module
                    Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) connect
                            .prepareStatement("DELETE FROM module WHERE moduleCode=?");
                    st.setString(1, value);
                    st.executeUpdate();
                    //also revalidating the table data after deleting the course
                    table();
                    table1.revalidate();
                    table1.repaint();
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception);
                }
            }
        });
        dltCourse.setOpaque(false);
        dltCourse.setContentAreaFilled(false);
        dltCourse.setBorderPainted(false);
        dltCourse.setMargin(new Insets(25, 100, 25, 50));
        courseDtl.add(dltCourse, 2);
//-----------------------------------------------------------------------------------------------
        teacherDtl.setBounds(300, 0, 1080, 840);
        backgroundM.add(teacherDtl);
        teacherDtl.setVisible(false);
//-----------------------------------------------------------------------------------------------
        //creating a new table and adding it to the scrollPane and then adding scrollPane to the panel
        DefaultTableModel model0 = new DefaultTableModel();
        table2 = new JTable();
        table2.setPreferredScrollableViewportSize(new Dimension(1050, 700));
        table2.setModel(model0);
        JScrollPane scroll0 = new JScrollPane(table2);
        scroll0.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll0.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        teacherDtl.add(scroll0);
//------------------------------------------------------------------------------------------
        //calling method to fill-up the table2 with datas
        table2();

        table2.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
                int column = 1;
                int column1 = table2.getSelectedColumn();
                int row = table2.getSelectedRow();
                col = table2.getColumnName(column1); // gets the column name selected
                value1 = table2.getModel().getValueAt(row, column).toString(); // gets the value at the selected cell
                System.out.println(value1);
                System.out.println(col);
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });

//---------------------------------------------------------------------------------------------
        JButton teachD = new JButton(new ImageIcon("teacherDtl.png"));
        teachD.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherDtl.setVisible(true); // displays the teacher detail panel and dimisses all panels if they are visible
                main.setVisible(false);
                courseDtl.setVisible(false);
                studentDtl.setVisible(false);
                resultDtl.setVisible(false);
            }
        });
        teachD.setOpaque(false);
        teachD.setContentAreaFilled(false);
        teachD.setBorderPainted(false);
        teachD.setBounds(235, 250, 270, 50);
        background.add(teachD);
//-----------------------------------------------------------------------------------------------------------------
        JButton refresh2 = new JButton(new ImageIcon("refresh.png"));
        refresh2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //re-evaluates the table value
                table2();
                table2.revalidate();
                table2.repaint();
            }
        });
        refresh2.setMargin(new Insets(25, 25, 25, 25));
        refresh2.setOpaque(false);
        refresh2.setContentAreaFilled(false);
        refresh2.setBorderPainted(false);
        teacherDtl.add(refresh2);

//-----------------------------------------------------------------------------------------------
        JButton assignTeach = new JButton(new ImageIcon("assign.png"));
        assignTeach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AssignTeacher AT = new AssignTeacher(value1); // creating an object for the method
                AT.setVisible(true);//displaying the panel
            }
        });
        assignTeach.setMargin(new Insets(18, 70, 18, 200));
        assignTeach.setOpaque(false);
        assignTeach.setContentAreaFilled(false);
        assignTeach.setBorderPainted(false);
        teacherDtl.add(assignTeach);

        JButton removeTeach = new JButton(new ImageIcon("remove.png"));
        removeTeach.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //removes the teacher name from the selected row and column of the table
                    Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                            "root", "password");
                    PreparedStatement st = (PreparedStatement) con
                            .prepareStatement("Update module set " + col + " = null where moduleCode=?");
                    st.setString(1, value1);
                    st.executeUpdate();
                    table2();
                    table2.revalidate();
                    table2.repaint();
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc);
                }
            }
        });
        removeTeach.setMargin(new Insets(18, 70, 18, 200));
        removeTeach.setOpaque(false);
        removeTeach.setContentAreaFilled(false);
        removeTeach.setBorderPainted(false);
        teacherDtl.add(removeTeach);


    }

    //---------------------------------------------------------------------------------------------------------------------------
    private void table2() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = conn.prepareStatement("SELECT moduleName, moduleCode, moduleLead, tutor1, tutor2, tutor3, tutor4 FROM module");
            ResultSet rs = ps.executeQuery();
            table2.setModel(DbUtils.resultSetToTableModel(rs));//sets the selected details about the teacher assigned to specific modules

        } catch (Exception de) {
            JOptionPane.showMessageDialog(null, de);

        }
    }

    //===========================================================================================================================
    private void table() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = conn.prepareStatement("SELECT moduleName, moduleCode, courseName, Level, Semester, status FROM module");
            ResultSet rs = ps.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));//sets the selected details from the module table int the JTable

        } catch (Exception d) {
            JOptionPane.showMessageDialog(null, d);

        }
    }

//===========================================================================================================================

    private void table1() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms", "root", "password");
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT userName, courseType from marks");
            ResultSet rs = ps.executeQuery();
            table4.setModel(DbUtils.resultSetToTableModel(rs));//sets the selected details from marks table to the JTable
        } catch (Exception d) {
            JOptionPane.showMessageDialog(null, d);
        }
    }
}
