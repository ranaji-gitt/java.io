
import javax.swing.*; // Swing thi window, button, label ane text field banava mate
import java.awt.*; // GUI na components ni positioning mate
import java.awt.event.*; // Button click jeva events handle karva mate
import java.io.*; // File read ane write karva mate
import java.util.ArrayList; // Ghani Employee objects ni list banava mate

class Employee // Employee naamni class banavi
{
    int id; // Employee ni ID store karse
    String name; // Employee nu naam store karse
    double basicSalary; // Employee no basic salary store karse
    String department; // Employee no department store karse
    double allowance,deduction,netSalary; // Allowance, deduction ane final salary store karse

    Employee(int id,String name,double basicSalary,String department) // Constructor
    {
        this.id=id; // Current object ma ID store kare chhe
        this.name=name; // Current object ma naam store kare chhe
        this.basicSalary=basicSalary; // Basic salary store kare chhe
        this.department=department; // Department store kare chhe
    }

    void calculateSalary() // Salary calculate karva mate method
    {
        allowance=basicSalary*20/100; // Basic salary na 20% allowance
        deduction=basicSalary*10/100; // Basic salary na 10% deduction
        netSalary=basicSalary+allowance-deduction; // Final salary calculate kare chhe
    }

    String getDetails() // Employee ni details string ma aape chhe
    {
        calculateSalary(); // Pela salary calculate kare chhe

        return "ID: "+id+
               "\nName: "+name+
               "\nBasic Salary: "+basicSalary+
               "\nDepartment: "+department+
               "\nAllowance: "+allowance+
               "\nDeduction: "+deduction+
               "\nNet Salary: "+netSalary+
               "\n--------------------------";
    }
}

public class EmployeePayrollSystem extends JFrame implements ActionListener
// JFrame thi window bane chhe ane ActionListener button clicks handle kare chhe
{
    JLabel idLabel,nameLabel,salaryLabel,departmentLabel;
    // Aa labels input fields na naam batavse

    JTextField idField,nameField,salaryField,departmentField;
    // Aa text fields user pase thi information leshe

    JButton addButton,saveButton,loadButton,viewButton;
    // Aa char buttons alag-alag operation karse

    JTextArea outputArea;
    // Aa area ma employee ni details batavse

    ArrayList<Employee> employees=new ArrayList<Employee>();
    // Employee objects ne memory ma store karva mate list

    EmployeePayrollSystem() // GUI no constructor
    {
        setTitle("Employee Payroll System"); // Window nu title
        setSize(650,550); // Window ni width 650 ane height 550
        setLayout(null); // Components ni position manually set karva
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Window close karta program bandh thase
		
        idLabel=new JLabel("Employee ID:");
        idLabel.setBounds(30,30,120,25);
        add(idLabel);
        // ID label banavi, position aapi ane window ma add karyu

        idField=new JTextField();
        idField.setBounds(160,30,150,25);
        add(idField);
        // ID enter karva mate text field

        nameLabel=new JLabel("Employee Name:");
        nameLabel.setBounds(30,70,120,25);
        add(nameLabel);
        // Name label

        nameField=new JTextField();
        nameField.setBounds(160,70,150,25);
        add(nameField);
        // Name enter karva mate text field

        salaryLabel=new JLabel("Basic Salary:");
        salaryLabel.setBounds(30,110,120,25);
        add(salaryLabel);
        // Salary label

        salaryField=new JTextField();
        salaryField.setBounds(160,110,150,25);
        add(salaryField);
        // Basic salary enter karva mate text field

        departmentLabel=new JLabel("Department:");
        departmentLabel.setBounds(30,150,120,25);
        add(departmentLabel);
        // Department label

        departmentField=new JTextField();
        departmentField.setBounds(160,150,150,25);
        add(departmentField);
        // Department enter karva mate text field

        addButton=new JButton("Add Employee");
        addButton.setBounds(330,30,140,30);
        addButton.addActionListener(this);
        add(addButton);
        // Add Employee button banavyo ane click event sathe jodyo

        saveButton=new JButton("Save");
        saveButton.setBounds(330,70,140,30);
        saveButton.addActionListener(this);
        add(saveButton);
        // Save button file ma data save karse

        loadButton=new JButton("Load");
        loadButton.setBounds(330,110,140,30);
        loadButton.addActionListener(this);
        add(loadButton);
        // Load button file mathi data read karse

        viewButton=new JButton("View All");
        viewButton.setBounds(330,150,140,30);
        viewButton.addActionListener(this);
        add(viewButton);
        // View All button badha employees batavse

        outputArea=new JTextArea();
        outputArea.setEditable(false);
        // User output area ma direct lakhine data badli na shake

        JScrollPane scrollPane=new JScrollPane(outputArea);
        scrollPane.setBounds(30,210,570,270);
        add(scrollPane);
        // Output area ne scroll pane ma mukyu jethi scroll kari shakay

        setVisible(true);
        // Window screen par dekhadse
    }

    public void actionPerformed(ActionEvent e)
    // Koi pan connected button click thay tyare aa method chale
    {
        if(e.getSource()==addButton)
        // Check kare chhe ke Add Employee button click thayo chhe ke nahi
        {
            try
            {
                int id=Integer.parseInt(idField.getText());
                // ID text ne integer ma convert kare chhe

                String name=nameField.getText().trim();
                // Name field mathi naam le chhe ane aagal-pachhal ni spaces hatave chhe

                double basicSalary=Double.parseDouble(salaryField.getText());
                // Salary text ne double number ma convert kare chhe

                String department=departmentField.getText().trim();
                // Department field mathi department le chhe

                if(name.isEmpty()||department.isEmpty()||basicSalary<0)
                // Naam/department khali hoy athva salary negative hoy to
                {
                    JOptionPane.showMessageDialog(this,"Enter valid details");
                    return;
                    // Error batavi ne method mathi bahar nikle chhe
                }

                boolean found=false;
                // Sharu ma maanvama aave chhe ke ID duplicate nathi

                for(Employee employee:employees)
                // List na darek employee ne check kare chhe
                {
                    if(employee.id==id)
                    // Jo same ID mali jay
                    {
                        found=true;
                        break;
                        // Loop bandh kare chhe
                    }
                }

                if(found)
                {
                    JOptionPane.showMessageDialog(this,"Employee ID already exists");
                    // Duplicate ID hoy to message batave chhe
                }
                else
                {
                    Employee employee=new Employee(id,name,basicSalary,department);
                    // Navo Employee object banave chhe

                    employee.calculateSalary();
                    // Employee no allowance, deduction ane net salary calculate kare chhe

                    employees.add(employee);
                    // Navo employee list ma add kare chhe

                    JOptionPane.showMessageDialog(this,"Employee added successfully");
                    // Success message batave chhe

                    idField.setText("");
                    nameField.setText("");
                    salaryField.setText("");
                    departmentField.setText("");
                    // Badha input fields khali kare chhe
                }
            }
            catch(NumberFormatException ex)
            // ID ke salary ma number ni jagyae khoto input hoy to
            {
                JOptionPane.showMessageDialog(this,"Enter valid ID and salary");
                // Error message batave chhe
            }
        }

        else if(e.getSource()==saveButton)
        // Jo Save button click thayo hoy
        {
            try
            {
                FileWriter writer=new FileWriter("employees.txt");
                // employees.txt file open kare chhe; file na hoy to banave chhe

                for(Employee employee:employees)
                // List na darek employee mate
                {
                    writer.write(employee.id+"|"+
                                 employee.name+"|"+
                                 employee.basicSalary+"|"+
                                 employee.department+"\n");
                    // Employee ni details | separator sathe file ma lakhse
                }

                writer.close();
                // File writer bandh kare chhe

                JOptionPane.showMessageDialog(this,"Data saved successfully");
                // Save thai gaya no message
            }
            catch(IOException ex)
            // File write karta error aave to
            {
                JOptionPane.showMessageDialog(this,"Error saving file");
                // Error message batave chhe
            }
        }

        else if(e.getSource()==loadButton)
        // Jo Load button click thayo hoy
        {
            try
            {
                BufferedReader reader=new BufferedReader(new FileReader("employees.txt"));
                // employees.txt file mathi data read karva mate reader banave chhe

                employees.clear();
                // Pela thi memory ma rahelo data clear kare chhe

                String line;
                // Ek line store karva mate variable

                while((line=reader.readLine())!=null)
                // File ni darek line read kare chhe; file puri thay tyare loop bandh
                {
                    String[] data=line.split("\\|");
                    // Line ne | na aadhare alag-alag parts ma divide kare chhe

                    if(data.length==4)
                    // Line ma char parts hoy to j aagal process kare chhe
                    {
                        try
                        {
                            int id=Integer.parseInt(data[0]);
                            // Pela part mathi ID le chhe

                            String name=data[1];
                            // Bija part mathi name le chhe

                            double basicSalary=Double.parseDouble(data[2]);
                            // Trija part mathi salary le chhe

                            String department=data[3];
                            // Chotha part mathi department le chhe

                            boolean found=false;
                            // Duplicate ID check karva mate

                            for(Employee employee:employees)
                            {
                                if(employee.id==id)
                                {
                                    found=true;
                                    break;
                                }
                                // Same ID hoy to found true thai jay
                            }

                            if(!found)
                            // Duplicate ID na hoy to j add kare chhe
                            {
                                Employee employee=new Employee(id,name,basicSalary,department);
                                employee.calculateSalary();
                                employees.add(employee);
                                // Employee banavi list ma add kare chhe
                            }
                        }
                        catch(NumberFormatException ex)
                        {
                            // File ma ID ke salary khoti hoy to aa record skip thase
                        }
                    }
                    // Char parts na hoy to aa line pan skip thase
                }

                reader.close();
                // File reader bandh kare chhe

                JOptionPane.showMessageDialog(this,"Data loaded successfully");
                // Load thai gaya no message batave chhe
            }
            catch(IOException ex)
            // File na male athva read na thai shake to
            {
                JOptionPane.showMessageDialog(this,"File not found or cannot be read");
                // Error message batave chhe
            }
        }

        else if(e.getSource()==viewButton)
        // Jo View All button click thayo hoy
        {
            outputArea.setText("");
            // Output area pehla khali kare chhe

            if(employees.isEmpty())
            // List ma ek pan employee na hoy to
            {
                outputArea.setText("No employee records found.");
                // Aa message batave chhe
            }
            else
            {
                for(Employee employee:employees)
                // List na darek employee mate
                {
                    outputArea.append(employee.getDetails()+"\n");
                    // Employee ni details output area ma add kare chhe
                }
            }
        }
    }

    public static void main(String[] args)
    // Java program execution ahi thi sharu thay chhe
    {
        new EmployeePayrollSystem();
        // EmployeePayrollSystem no object banave chhe ane GUI open kare chhe
    }
}
