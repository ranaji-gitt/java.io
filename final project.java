
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

class Employee
{
    int id;
    String name;
    double basicSalary;
    String department;
    double allowance, deduction, netSalary;

    Employee(int id, String name, double basicSalary, String department)
    {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
        this.department = department;
    }

    void calculateSalary()
    {
        allowance = basicSalary * 0.20;
        deduction = basicSalary * 0.10;
        netSalary = basicSalary + allowance - deduction;
    }
}

public class EmployeePayrollSystem implements ActionListener
{
    JFrame jd = new JFrame();
    JTextField txtId, txtName, txtSalary;
    JComboBox<String> cmbDepartment;
    JTextArea area;
    JButton btnAdd, btnSave, btnLoad, btnView;

    ArrayList<Employee> employees = new ArrayList<Employee>();

    EmployeePayrollSystem()
    {
        jd.setTitle("Employee Payroll System");
        jd.setSize(600, 550);
        jd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jd.setLocationRelativeTo(null);
        jd.setLayout(null);

        JLabel lblId = new JLabel("Employee ID:");
        lblId.setBounds(35, 25, 120, 30);
        jd.add(lblId);
        txtId = new JTextField();
        txtId.setBounds(170, 25, 365, 30);
        jd.add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(35, 70, 120, 30);
        jd.add(lblName);
        txtName = new JTextField();
        txtName.setBounds(170, 70, 365, 30);
        jd.add(txtName);

        JLabel lblSalary = new JLabel("Basic Salary:");
        lblSalary.setBounds(35, 115, 120, 30);
        jd.add(lblSalary);
        txtSalary = new JTextField();
        txtSalary.setBounds(170, 115, 365, 30);
        jd.add(txtSalary);

        JLabel lblDepartment = new JLabel("Department:");
        lblDepartment.setBounds(35, 160, 120, 30);
        jd.add(lblDepartment);
        cmbDepartment = new JComboBox<String>(
            new String[]{"IT", "HR", "Finance", "Sales", "Marketing"});
        cmbDepartment.setBounds(170, 160, 365, 30);
        jd.add(cmbDepartment);

        btnAdd = new JButton("Add Employee");
        btnSave = new JButton("Save");
        btnLoad = new JButton("Load");
        btnView = new JButton("View All");

        btnAdd.setBounds(35, 210, 140, 35);
        btnSave.setBounds(185, 210, 105, 35);
        btnLoad.setBounds(300, 210, 105, 35);
        btnView.setBounds(415, 210, 120, 35);

        btnAdd.addActionListener(this);
        btnSave.addActionListener(this);
        btnLoad.addActionListener(this);
        btnView.addActionListener(this);

        jd.add(btnAdd);
        jd.add(btnSave);
        jd.add(btnLoad);
        jd.add(btnView);

        area = new JTextArea();
        area.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setBounds(35, 265, 500, 230);
        jd.add(scrollPane);

        jd.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnAdd)
        {
            try
            {
                int id = Integer.parseInt(txtId.getText().trim());
                String name = txtName.getText().trim();
                double salary = Double.parseDouble(txtSalary.getText().trim());

                if (id <= 0 || name.isEmpty() || salary <= 0 || name.contains("|"))
                {
                    JOptionPane.showMessageDialog(jd, "Enter valid employee details.");
                    return;
                }

                for (Employee emp : employees)
                {
                    if (emp.id == id)
                    {
                        JOptionPane.showMessageDialog(jd, "Employee ID already exists.");
                        return;
                    }
                }

                employees.add(new Employee(id, name, salary,
                    cmbDepartment.getSelectedItem().toString()));

                employees.get(employees.size() - 1).calculateSalary();

                JOptionPane.showMessageDialog(jd, "Employee Added Successfully!");

                txtId.setText("");
                txtName.setText("");
                txtSalary.setText("");
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(jd, "Enter a valid ID and salary.");
            }
        }
        else if (e.getSource() == btnSave)
        {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("employees.txt")))
            {
                for (Employee emp : employees)
                {
                    bw.write(emp.id + "|" + emp.name + "|" +
                        emp.basicSalary + "|" + emp.department);
                    bw.newLine();
                }

                JOptionPane.showMessageDialog(jd, "Records saved successfully!");
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(jd, "File error: " + ex.getMessage());
            }
        }
        else if (e.getSource() == btnLoad)
        {
            try (BufferedReader br = new BufferedReader(new FileReader("employees.txt")))
            {
                employees.clear();

                String line;

                while ((line = br.readLine()) != null)
                {
                    String data[] = line.split("\\|", -1);

                    if (data.length != 4)
                        continue;

                    try
                    {
                        int id = Integer.parseInt(data[0]);
                        double salary = Double.parseDouble(data[2]);

                        if (id <= 0 || salary <= 0 || data[1].isEmpty())
                            continue;

                        boolean exists = false;

                        for (Employee emp : employees)
                        {
                            if (emp.id == id)
                            {
                                exists = true;
                                break;
                            }
                        }

                        if (!exists)
                            employees.add(new Employee(id, data[1], salary, data[3]));
                    }
                    catch (NumberFormatException ex)
                    {
							continue;
                    }
                }

                JOptionPane.showMessageDialog(jd, "Records loaded successfully!");
            }
            catch (IOException ex)
            {
                JOptionPane.showMessageDialog(jd, "Unable to load file: " + ex.getMessage());
            }
        }
        else if (e.getSource() == btnView)
        {
            area.setText("");

            if (employees.isEmpty())
            {
                area.setText("No employee records found.");
            }
            else
            {
                for (Employee emp : employees)
                {
                    emp.calculateSalary();

                    area.append("Employee ID: " + emp.id + "\n");
                    area.append("Name: " + emp.name + "\n");
                    area.append("Department: " + emp.department + "\n");
                    area.append("Basic Salary: Rs. " + emp.basicSalary + "\n");
                    area.append("Allowance: Rs. " + emp.allowance + "\n");
                    area.append("Deduction: Rs. " + emp.deduction + "\n");
                    area.append("Net Salary: Rs. " + emp.netSalary + "\n");
                    area.append("--------------------------\n");
                }
            }
        }
    }

    public static void main(String args[])
    {
        new EmployeePayrollSystem();
    }
}