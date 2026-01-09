package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * This class manages the graphical user interface for displaying search results.
 * It shows a list of books that match the search criteria and allows users to view details of selected books.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see Book
 * @see GUI_BookDetails
 */
public class GUI_SearchResults extends JFrame {
    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;
    private JList<String> listResults;
    private DefaultListModel<String> listModel;
    private JButton btnViewDetails;
    private JButton btnBack;
    /**
     * Constructs a new GUI_SearchResults frame.
     * 
     * @param results The list of Book objects to display as search results.
     */
    public GUI_SearchResults(ArrayList<Book> results) {
        setTitle("Book Recommender - Risultati Ricerca");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        initComponents(results);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Initializes and sets up the components of the GUI.
     * This method creates and positions all UI elements including the results list and action buttons.
     * 
     * @param results The list of Book objects to display as search results.
     */
    private void initComponents(ArrayList<Book> results) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblResults = new JLabel("Risultati della ricerca:");
        lblResults.setFont(new Font("Cambria", Font.BOLD, 14));
        lblResults.setBounds(10, 11, 414, 20);
        contentPane.add(lblResults);

        listModel = new DefaultListModel<>();
        for (Book book : results) {
            listModel.addElement(book.getTitle() + " - " + book.getAuthor() + " (" + book.getYear() + ")");
        }

        listResults = new JList<>(listModel);
        listResults.setFont(new Font("Cambria", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(listResults);
        scrollPane.setBounds(10, 40, 414, 170);
        contentPane.add(scrollPane);

        btnViewDetails = new JButton("Visualizza Dettagli");
        btnViewDetails.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnViewDetails.setForeground(colorLightYellow);
        // btnViewDetails.setBackground(colorBtnBrown);
        btnViewDetails.setBounds(10, 220, 200, 30);
        btnViewDetails.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listResults.getSelectedIndex();
                if (selectedIndex != -1) {
                    Book selectedBook = results.get(selectedIndex);
                    new GUI_BookDetails(selectedBook);
                } else {
                    JOptionPane.showMessageDialog(GUI_SearchResults.this, "Seleziona un libro dalla lista.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        contentPane.add(btnViewDetails);

        btnBack = new JButton("Indietro");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnBack.setForeground(colorLightYellow);
        // btnBack.setBackground(colorBtnBrown);
        btnBack.setBounds(324, 220, 100, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnBack);
    }
}