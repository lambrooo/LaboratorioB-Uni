package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
/**
 * This class manages the graphical user interface for public book searches.
 * It allows users to search for books by title, author, or author and year without logging in.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see BookRecommender
 * @see GUI_SearchResults
 */
public class GUI_PublicSearch extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtYear;
    private JButton btnSearch;
    private JLabel lblResult;
    private String searchType;
    // Removed custom colors
    /**
     * Constructs a new GUI_PublicSearch frame.
     * 
     * @param searchType The type of search to be performed (Titolo, Autore, or AutoreAnno).
     */
    public GUI_PublicSearch(String searchType) {
        this.searchType = searchType;
        setTitle("Book Recommender - Ricerca Pubblica");
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Initializes and sets up the components of the GUI.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Titolo:");
        lblTitle.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(lblTitle, gbc);

        txtTitle = new JTextField();
        txtTitle.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(txtTitle, gbc);

        JLabel lblAuthor = new JLabel("Autore:");
        lblAuthor.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPane.add(lblAuthor, gbc);

        txtAuthor = new JTextField();
        txtAuthor.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        contentPane.add(txtAuthor, gbc);

        JLabel lblYear = new JLabel("Anno:");
        lblYear.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        contentPane.add(lblYear, gbc);

        txtYear = new JTextField();
        txtYear.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPane.add(txtYear, gbc);

        btnSearch = new JButton("Cerca");
        btnSearch.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnSearch.setForeground(colorLightYellow);
        // btnSearch.setBackground(colorBtnBrown);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        contentPane.add(btnSearch, gbc);

        JButton btnBack = new JButton("Indietro");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnBack.setForeground(colorLightYellow);
        // btnBack.setBackground(colorBtnBrown);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnBack, gbc);

        lblResult = new JLabel("");
        lblResult.setFont(new Font("Cambria", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        contentPane.add(lblResult, gbc);

        // Abilita/disabilita campi in base al tipo di ricerca
        switch (searchType) {
            case "Titolo":
                txtAuthor.setEnabled(false);
                txtYear.setEnabled(false);
                break;
            case "Autore":
                txtTitle.setEnabled(false);
                txtYear.setEnabled(false);
                break;
            case "AutoreAnno":
                txtTitle.setEnabled(false);
                break;
        }
    }
    /**
     * Performs the book search based on the user's input and search type.
     * Displays the results or a message if no results are found.
     */
    private void performSearch() {
        String title = txtTitle.getText().trim();
        String author = txtAuthor.getText().trim();
        String year = txtYear.getText().trim();

        ArrayList<Book> results = new ArrayList<>();

        try {
            switch (searchType) {
                case "Titolo":
                    results = BookRecommender.cercaLibro("titolo", title, "", null, null);
                    break;
                case "Autore":
                    results = BookRecommender.cercaLibro("autore", author, "", null, null);
                    break;
                case "AutoreAnno":
                    String authorYearQuery = author + (year.isEmpty() ? "" : "/" + year);
                    results = BookRecommender.cercaLibro("autoreanno", authorYearQuery, null, null, null);
                    break;
            }
        } catch (java.rmi.RemoteException e) {
            JOptionPane.showMessageDialog(this, "Errore durante la ricerca: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        if (results.isEmpty()) {
            lblResult.setText("Nessun risultato trovato.");
        } else {
            lblResult.setText("Trovati " + results.size() + " risultati.");
            displayResults(results);
        }
    }
    /**
     * Displays the search results in a new window.
     * 
     * @param results The list of Book objects that match the search criteria.
     */
    private void displayResults(ArrayList<Book> results) {
        new GUI_SearchResults(results);
    }
}