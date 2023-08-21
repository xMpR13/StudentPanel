
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        new Student();
    }
}

class Student implements ActionListener {

    private final JTextField name;
    private final JTextField last_name;
    private final JTextField age;
    private final JComboBox course;
    private final JTextArea area;
    private final JMenuItem saveItem;
    private final JMenuItem loadItem;
    private final JMenuItem exitItem;
    //buttons
    private final JButton searchBtn;
    private final JButton deleteBtn;

    File file;
    private JFileChooser fileChooser;

    public Student() {

        JFrame frame = new JFrame("STUDENT INFO");
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        //==============================file menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("File");
        saveItem = new JMenuItem("Save");
        saveItem.addActionListener(this);
        loadItem = new JMenuItem("Load");
        loadItem.addActionListener(this);
        exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(this);

        menu.add(saveItem);
        menu.add(loadItem);
        menu.addSeparator();
        menu.add(exitItem);

        menuBar.add(menu);

        //==============================details
        JLabel name_Label = new JLabel("Name        ");
        name = new JTextField(15);
        JPanel name_panel = new JPanel();
        name_panel.add(name_Label);
        name_panel.add(name);
        name_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel lastName_Label = new JLabel("Last Name");
        last_name = new JTextField(15);
        JPanel lastname_panel = new JPanel();
        lastname_panel.add(lastName_Label);
        lastname_panel.add(last_name);
        lastname_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel age_Label = new JLabel("Age          ");
        age = new JTextField(15);
        JPanel age_panel = new JPanel();
        age_panel.add(age_Label);
        age_panel.add(age);
        age_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel course_Label = new JLabel("Course     ");
        String optionCourse[] = {"Computer Science", "Information Technology", "Multimedia"};
        course = new JComboBox(optionCourse);
        JPanel course_panel = new JPanel();
        course_panel.add(course_Label);
        course_panel.add(course);
        course_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        //==============================buttons
        searchBtn = new JButton("Search Student");
        searchBtn.addActionListener(this);
        deleteBtn = new JButton("Delete Student");
        deleteBtn.addActionListener(this);
        JPanel btnPanel = new JPanel();
        btnPanel.add(searchBtn);
        btnPanel.add(deleteBtn);

        //==============================dds to main panel
        JPanel mainDetails_panel = new JPanel();
        mainDetails_panel.setLayout(new GridLayout(5, 0));
        mainDetails_panel.add(name_panel);
        mainDetails_panel.add(lastname_panel);
        mainDetails_panel.add(age_panel);
        mainDetails_panel.add(course_panel);
        mainDetails_panel.add(btnPanel);
        mainDetails_panel.setBorder(new TitledBorder(new LineBorder(Color.BLUE), "Student Details"));

        //=========================display
        area = new JTextArea(15, 25);
        area.setEditable(false);
        JScrollPane xy = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JPanel textArea_panel = new JPanel();
        textArea_panel.add(xy);
        textArea_panel.setBorder(new TitledBorder(new LineBorder(Color.BLUE), "Students Information"));

        frame.setJMenuBar(menuBar);
        frame.add(mainDetails_panel, BorderLayout.WEST);
        frame.add(textArea_panel, BorderLayout.CENTER);

        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (saveItem == e.getSource()) {

            String studentInfo = new String();
            String studentCourse = new String();
            String studentName = name.getText();
            String studentLastName = last_name.getText();
            String studentAge = age.getText();

            if (course.getSelectedItem().equals("Computer Science")) {
                studentCourse = "Computer Science";
            } else if (course.getSelectedItem().equals("Multimedia")) {
                studentCourse = "Multimedia";
            } else if (course.getSelectedItem().equals("Information Technology")) {
                studentCourse = "Information Technology";
            }

            studentInfo += studentName + ", " + studentLastName + ", " + studentAge + ", " + studentCourse + "\n";

            fileChooser = new JFileChooser();
            file = new File("Students.txt");

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));

                    writer.write(studentInfo);

                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

        } else if (e.getSource() == loadItem) {
            file = new File("students.txt");
            fileChooser = new JFileChooser();
            String str = "", data = "";

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                    while ((str = reader.readLine()) != null) {
                        data += str + "\n";

                    }
                    reader.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                area.setText(data);
            }
        } else if (e.getSource() == exitItem) {
            System.exit(0);
        } else if (deleteBtn == e.getSource()) {

            fileChooser = new JFileChooser();
            file = new File("Students.txt");
            ArrayList<String> names = new ArrayList<>();
            String str = "", data = "", line = "";

            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {
                file = fileChooser.getSelectedFile();

                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    while ((str = reader.readLine()) != null) {
                        data = str + "\n";
                        names.add(data);
                    }

                    for (int i = 0; i < names.size(); i++) {
                        if (names.get(i).contains(last_name.getText())) {
                            names.remove(i);
                        }
                    }

                    reader.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                    for (int i = 0; i < names.size(); i++) {
                        writer.write(names.get(i));
                    }
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            for (int i = 0; i < names.size(); i++) {
                line = line + names.get(i);
            }

            System.out.println(names);

            area.setText(line);

        } else if (searchBtn == e.getSource()) {

        }
    }
}
