import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, countField;
    private int recordCount = 0;
    private RandomAccessFile raf;

    public RandProductMaker() {
        setTitle("Rand Product Maker");
        setLayout(new GridLayout(7, 2, 5, 5));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            raf = new RandomAccessFile("products.dat", "rw");
            recordCount = (int)(raf.length() / Product.getRecordSize());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error opening file.");
        }

        nameField = new JTextField();
        descField = new JTextField();
        idField = new JTextField();
        costField = new JTextField();
        countField = new JTextField(String.valueOf(recordCount));
        countField.setEditable(false);

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Description:"));
        add(descField);
        add(new JLabel("ID:"));
        add(idField);
        add(new JLabel("Cost:"));
        add(costField);
        add(new JLabel("Record Count:"));
        add(countField);

        JButton addButton = new JButton("Add");
        JButton quitButton = new JButton("Quit");

        add(addButton);
        add(quitButton);

        addButton.addActionListener(e -> addProduct());
        quitButton.addActionListener(e -> {
            try { raf.close(); } catch (IOException ex) {}
            System.exit(0);
        });

        setSize(400, 300);
        setVisible(true);
    }

    private void addProduct() {
        String name = nameField.getText().trim();
        String desc = descField.getText().trim();
        String id = idField.getText().trim();
        double cost;

        try {
            cost = Double.parseDouble(costField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cost.");
            return;
        }

        if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        try {
            raf.seek(raf.length());
            Product p = new Product(name, desc, id, cost);
            p.writeToFile(raf);
            recordCount++;
            countField.setText(String.valueOf(recordCount));
            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file.");
        }
    }

    public static void main(String[] args) {
        new RandProductMaker();
    }
}
