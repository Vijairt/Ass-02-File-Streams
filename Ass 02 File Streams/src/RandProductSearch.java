import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        setTitle("Rand Product Search");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        topPanel.add(new JLabel("Enter partial name:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchProducts());

        setSize(500, 400);
        setVisible(true);
    }

    private void searchProducts() {
        resultArea.setText("");
        String query = searchField.getText().trim().toLowerCase();

        try (RandomAccessFile raf = new RandomAccessFile("products.dat", "r")) {
            long fileLength = raf.length();
            int recordSize = Product.getRecordSize();

            for (long pos = 0; pos < fileLength; pos += recordSize) {
                raf.seek(pos);
                Product p = Product.readFromFile(raf);
                if (p.getName().toLowerCase().contains(query)) {
                    resultArea.append(p.toString() + "\n");
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading file.");
        }
    }

    public static void main(String[] args) {
        new RandProductSearch();
    }
}
