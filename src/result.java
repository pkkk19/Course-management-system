import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

public class result extends JFrame {
    private JPanel contentPane;
    private JLabel lbl;
    private JTable table;
    protected ArrayList<String> moduleCode = new ArrayList<>();
    private ArrayList<String> moduleName = new ArrayList<>();
    private ArrayList<String> marks = new ArrayList<>();
    private ArrayList<String> Grade = new ArrayList<>();
    private ArrayList<String> Remarks = new ArrayList<>();
    private int total = 0;
    protected String finalGrade = "";
    protected String courseType = "";
    protected String level = "";
    protected String Semester = "";
    protected String firstName = "";
    protected String lastName = "";

    //---------------------------------------------------------------

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    result r = new result("milan");
                    r.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //storing value of username in the String username variable
    public result(String userName) {

        //---------------------------------------------------------------------
        //..setting up the frame

        setBounds(450, 0, 1024, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        //-------------------------------------------------------------------------------------
        //..labels for Mark sheet
        lbl = new JLabel("Mark Sheet");
        lbl.setFont(new Font("Arial", Font.PLAIN, 40));
        lbl.setBounds(380, 150, 400, 45);
        contentPane.add(lbl);

        lbl = new JLabel("  Module Code         | " +
                "Module Name                                 " +
                "                     | Marks                     " +
                "| Grade                                           | Remarks");
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setBounds(0, 315, 1024, 37);
        contentPane.add(lbl);


        lbl = new JLabel("------------------------------------------------------------------------------------------------------------------");
        lbl.setFont(new Font("Arial", Font.PLAIN, 26));
        lbl.setBounds(0, 300, 1024, 37);
        contentPane.add(lbl);

        lbl = new JLabel("------------------------------------------------------------------------------------------------------------------");
        lbl.setFont(new Font("Arial", Font.PLAIN, 26));
        lbl.setBounds(0, 425, 1024, 37);
        contentPane.add(lbl);
        //----------------------------------------------------------------------------
        //declaring new object table

        DefaultTableModel mdl = new DefaultTableModel();
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(1024, 400));
        table.setModel(mdl);
        table.setRowHeight(34);
        table.setBounds(0, 345, 1024, 100);
        table.setTableHeader(null);
        //adding table to scroll
        JScrollPane scroll9 = new JScrollPane(table);
        scroll9.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll9.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll9.setBounds(0, 345, 1024, 100);
        //adding scroll to the contentPane
        contentPane.add(scroll9);
        //--------------------------------------------------------------------------------
        //connecting mySQL to obtain details from the database
        //obtaining details about marks of specific student by using username
        try {
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cms",
                    "root", "password");
            PreparedStatement st = connect.prepareStatement
                    ("Select marks from marks where userName = ? and status = 'ACTIVE'");
            st.setString(1, userName);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                //fetching each data and adding it to the arraylist
                marks.add(rs.getString("marks"));
            }
            //using iterator instead of the classic loops because we can go next() and previous() too
            //it doesn't make code faster or saves space, but it was easier for me to understand and cope with
            Iterator c = marks.iterator();
            int count = 0; //initializing count
            while (c.hasNext()) {
                String condition = (String) c.next();
                //checking condition if there is any unmarked marks
                switch (condition) {
                    case "0":
                        count++;
                        System.out.println(count);
                        break;
                    default:
                        break;
                }
            }
            //clearing marks arraylist to fetch totally new marks in it
            marks.clear();
            String statement;
            //if count is more than one that means there is some unmarked subjects in the active subjects so in this case it will select the old INACTIIVE marks.
            if (count > 0) {
                statement = "Select moduleCode, moduleName, courseType, marks, Grade, Remarks, Level, Semester from marks where userName = ? and status = 'INACTIVE'";
            } else {
                statement = "Select moduleCode, moduleName, courseType, marks, Grade, Remarks, Level, Semester from marks where userName = ? and status = 'ACTIVE'";
            }
            //after selecting which statement to run now the required statement in stored in the variable, and it is run through prepared statement
            PreparedStatement stat = connect.prepareStatement(statement);
            stat.setString(1, userName);
            ResultSet RS = stat.executeQuery();
            while (RS.next()) {
                moduleCode.add(RS.getString("moduleCode"));
                moduleName.add(RS.getString("moduleName"));
                courseType = RS.getString("courseType");
                marks.add(RS.getString("marks"));
                Grade.add(RS.getString("Grade"));
                Remarks.add(RS.getString("Remarks"));
                level = RS.getString("Level");
                Semester = RS.getString("Semester");
            }

            //fetching for same table again but this time to display the required table not everything
            String state = "";
            if (count > 0) {
                state = "Select moduleCode, moduleName, marks, Grade, Remarks FROM marks where userName = ? and status ='INACTIVE'";
            } else {
                state = "Select moduleCode, moduleName, marks, Grade, Remarks FROM marks where userName = ? and status ='ACTIVE'";
            }
            PreparedStatement sta = connect.prepareStatement
                    (state);
            sta.setString(1, userName);
            ResultSet rsa = sta.executeQuery();
            //using DBUtils external libraries
            table.setModel(DbUtils.resultSetToTableModel(rsa));
            table.getColumnModel().getColumn(0).setPreferredWidth(5);
            table.getColumnModel().getColumn(1).setPreferredWidth(200);
            table.getColumnModel().getColumn(2).setPreferredWidth(5);
            table.getColumnModel().getColumn(3).setPreferredWidth(100);

            //----------------------------------------------------------------------------
            //obtaining the student full name from the account table in database
            PreparedStatement ps = connect.prepareStatement
                    ("Select first_name, last_name from account where " +
                            "user_name = ?");
            ps.setString(1, userName);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                firstName = r.getString("first_name");
                lastName = r.getString("last_name");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        //diplaying the current level and semester before changing it
        lbl = new JLabel(level);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        lbl.setBounds(630, 253, 300, 22);
        contentPane.add(lbl);
        //----------------------------------------------------------------------------------
        lbl = new JLabel(Semester);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        lbl.setBounds(630, 283, 300, 22);
        contentPane.add(lbl);
//-------------------------------------------------------------------------------------------
        //checking weather the student is passed or failed in all subject and making overall status fail if student fails in even one subject
        Iterator i = marks.iterator();
        Iterator itr = Grade.iterator();
        int c = 0;
        String overallStatus = "Pass";

        while (i.hasNext()) {
            total += Integer.valueOf((String) i.next());
            c++;
            switch ((String) itr.next()) {
                case "Fail":
                    overallStatus = "Fail";
            }

        }
        //----------------------------------------------------------------
        //calculating percentage
        int percentage = 0;
        /* if the total marks is zero then while calculating percentage if
           we divide 0 by the total number of subjects it will so error which
           is why setting percentage to zero if total is zero.*/
        if (total == 0) {
            percentage = 0;
        } else {
            percentage = total / c;
        }
//---------------------------------------------------------------------------
        //deciding grade of the student
        if (overallStatus == "Fail") {
            finalGrade = "Fail";
        } else if (percentage >= 70) {
            finalGrade = "First-Class Honours";
        } else if (percentage >= 60) {
            finalGrade = "Upper Second-Class Honours";
        } else if (percentage >= 50) {
            finalGrade = "Lower Second-Class Honours";
        } else if (percentage >= 40) {
            finalGrade = "Third Class Honours";
        } else {
            finalGrade = "Fail";
        }

//-------------------------------------------------------------------------
        /*This switch case will decide if the student is in first sem then
         tht student will go to second semester but will still remain in the
         same level but when the student is in the second sem then he/she will
         go to first sem of next level*/
        switch (Semester) {
            case "First":
                Semester = "Second";
                break;
            case "Second":
                Semester = "First";
                switch (level) {
                    case "L4":
                        level = "L5";
                        break;
                    case "L5":
                        level = "L6";
                        break;
                    case "L6":
                        level = "Graduated";
                        break;
                }
                break;
        }
//-------------------------------------------------------------------------------
//displaying the student details in proper format in frame
        lbl = new JLabel(new ImageIcon("wlv.png"));
        lbl.setBounds(70, 40, 455, 80);
        contentPane.add(lbl);

        lbl = new JLabel(new ImageIcon("hck.png"));
        lbl.setBounds(500, 40, 400, 80);
        contentPane.add(lbl);

        lbl = new JLabel("Student Name: ");
        lbl.setFont(new Font("Arial", Font.PLAIN, 19));
        lbl.setBounds(30, 250, 150, 22);
        contentPane.add(lbl);

        lbl = new JLabel(firstName + " " + lastName);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        lbl.setBounds(170, 253, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel("Course: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(30, 280, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel(String.valueOf(courseType));
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        lbl.setBounds(170, 283, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel("Level: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(510, 250, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel("Semester: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(510, 280, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel("Total: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(610, 500, 300, 22);
        contentPane.add(lbl);

        lbl = new JLabel(String.valueOf(total));
        lbl.setBounds(750, 503, 80, 20);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        contentPane.add(lbl);

        lbl = new JLabel("Percentage: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(610, 530, 300, 25);
        contentPane.add(lbl);

        lbl = new JLabel(String.valueOf(percentage) + "%");
        lbl.setBounds(750, 533, 80, 20);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        contentPane.add(lbl);

        lbl = new JLabel("Grade: ");
        lbl.setFont(new Font("typewriter", Font.PLAIN, 19));
        lbl.setBounds(610, 560, 300, 25);
        contentPane.add(lbl);

        lbl = new JLabel(finalGrade);
        lbl.setBounds(750, 563, 400, 20);
        lbl.setFont(new Font("courier new", Font.PLAIN, 15));
        contentPane.add(lbl);
    }
}
