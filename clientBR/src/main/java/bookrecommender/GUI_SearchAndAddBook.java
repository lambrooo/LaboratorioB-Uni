package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the graphical user interface for searching and adding books to a bookshelf.
 * It allows users to search for books and add selected books to their personal bookshelves.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class GUI_SearchAndAddBook extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed unused colors
    private JPanel contentPane;
    private JTextField txtSearch;
    private JComboBox<String> cmboxSearchType;
    private JComboBox<String> cmboxBookshelf;
    private JList<String> listResults;
    private DefaultListModel<String> listModel;
    private JLabel lblInfo;
    private String userid;

    /**
     * Constructs a new GUI_SearchAndAddBook frame.
     * Initializes the UI components for searching and adding books to a bookshelf.
     * 
     * @param userid The ID of the current user.
     */
    public GUI_SearchAndAddBook(String userid) {
        this.userid = userid;
        setTitle("Book Recommender - Cerca e Aggiungi Libro");
        // setIconImage(Toolkit.getDefaultToolkit().getImage("img\\\\Logo.png")); // Assuming img folder is not directly accessible
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes and configures all components of the graphical interface.
     * This method creates and positions all UI elements for searching and adding books.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Search Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(new JLabel("Cerca per:"), gbc);
        gbc.gridx = 1;
        String[] searchTypes = {"Titolo", "Autore", "AutoreAnno"};
        cmboxSearchType = new JComboBox<>(searchTypes);
        contentPane.add(cmboxSearchType, gbc);

        // Row 1: Search Text
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("Cerca:"), gbc);
        gbc.gridx = 1;
        txtSearch = new JTextField(20);
        contentPane.add(txtSearch, gbc);

        // Row 1, Column 2: Search Button
        gbc.gridx = 2;
        JButton btnSearch = new JButton("Cerca");
        btnSearch.addActionListener(e -> performSearch());
        contentPane.add(btnSearch, gbc);

        // Row 2: Results List
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        listModel = new DefaultListModel<>();
        listResults = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listResults);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        contentPane.add(scrollPane, gbc);

        // Row 3: Bookshelf Selection
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        contentPane.add(new JLabel("Libreria:"), gbc);
        gbc.gridx = 1;
        cmboxBookshelf = new JComboBox<>();
        populateBookshelfComboBox();
        contentPane.add(cmboxBookshelf, gbc);

        // Row 3, Column 2: Add to Bookshelf Button
        gbc.gridx = 2;
        JButton btnAddToBookshelf = new JButton("Aggiungi alla Libreria");
        btnAddToBookshelf.addActionListener(e -> addToBookshelf());
        contentPane.add(btnAddToBookshelf, gbc);

        // Row 4: Info Label
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        lblInfo = new JLabel("");
        lblInfo.setForeground(Color.RED);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblInfo, gbc);

        // Row 5: Back Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        JButton btnBack = new JButton("Indietro");
        btnBack.addActionListener(e -> {
            setVisible(false);
            new GUI_LoggedHomePage(userid);
        });
        contentPane.add(btnBack, gbc);
    }

    /**
     * Populates the bookshelf combo box with the user's existing bookshelves.
     */
    private void populateBookshelfComboBox() {
        try {
            HashMap<Bookshelf_key, Bookshelf> bookshelves = BookRecommender.getBookshelves(userid);
            for (Bookshelf bookshelf : bookshelves.values()) {
                cmboxBookshelf.addItem(bookshelf.getName());
            }
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle librerie: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Performs the book search based on the selected search type and text.
     * Updates the results list and displays relevant information messages.
     */
    private void performSearch() {
        String searchType = (String) cmboxSearchType.getSelectedItem();
        String searchText = txtSearch.getText().trim();

        if (searchText.isEmpty()) {
            lblInfo.setText("Inserisci un testo per la ricerca.");
            lblInfo.setForeground(Color.RED);
            return;
        }

        ArrayList<Book> results = new ArrayList<>();
        try {
            if (searchType.equals("AutoreAnno")) {
                String[] parts = searchText.split("/");
                if (parts.length == 2) {
                    results = BookRecommender.cercaLibro(searchType, parts[0].trim(), parts[1].trim(), null, null);
                } else {
                    lblInfo.setText("Formato non valido per la ricerca Autore/Anno");
                    lblInfo.setForeground(Color.RED);
                    return;
                }
            } else {
                results = BookRecommender.cercaLibro(searchType, searchText, null, null, null);
            }
        } catch (java.rmi.RemoteException ex) {
            lblInfo.setText("Errore durante la ricerca: " + ex.getMessage());
            lblInfo.setForeground(Color.RED);
            ex.printStackTrace();
            return;
        }

        listModel.clear();
        for (Book book : results) {
            listModel.addElement(book.getTitle() + " - " + book.getAuthor() + " (" + book.getYear() + ")");
        }

        if (results.isEmpty()) {
            lblInfo.setText("Nessun risultato trovato.");
            lblInfo.setForeground(Color.RED);
        } else {
            lblInfo.setText("Trovati " + results.size() + " risultati.");
            lblInfo.setForeground(Color.GREEN);
        }
    }

    /**
     * Adds the selected book from the search results to the chosen bookshelf.
     * Validates selections and updates the bookshelf via the remote service.
     */
    private void addToBookshelf() {
        String selectedBookString = listResults.getSelectedValue();
        String selectedBookshelfName = (String) cmboxBookshelf.getSelectedItem();

        if (selectedBookString == null) {
            lblInfo.setText("Seleziona un libro dalla lista.");
            lblInfo.setForeground(Color.RED);
            return;
        }

        if (selectedBookshelfName == null) {
            lblInfo.setText("Seleziona una libreria.");
            lblInfo.setForeground(Color.RED);
            return;
        }

        // Extract book title from the displayed string
        String bookTitle = selectedBookString.split(" - ")[0];
        Book bookToAdd = null;
        try {
            bookToAdd = BookRecommender.getBookByTitle(bookTitle);
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel recupero dei dettagli del libro: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        if (bookToAdd == null) {
            lblInfo.setText("Errore: Libro non trovato nel sistema.");
            lblInfo.setForeground(Color.RED);
            return;
        }

        try {
            HashMap<Bookshelf_key, Bookshelf> bookshelves = BookRecommender.getBookshelves(userid);
            for (Map.Entry<Bookshelf_key, Bookshelf> entry : bookshelves.entrySet()) {
                if (entry.getKey().getName().equals(selectedBookshelfName)) {
                    Bookshelf bookshelf = entry.getValue();
                    ArrayList<String> currentBookTitles = bookshelf.getBooks();

                    if (!currentBookTitles.contains(bookToAdd.getTitle())) {
                        // Use the new method to add a single book to the existing bookshelf
                        try {
                            boolean success = BookRecommender.addBookToBookshelf(selectedBookshelfName, userid, bookToAdd);
                            if (success) {
                                lblInfo.setText("Libro aggiunto con successo alla libreria.");
                                lblInfo.setForeground(Color.GREEN);
                                // Update the local in-memory bookshelf
                                bookshelf.getBooks().add(bookToAdd.getTitle());
                            } else {
                                // This could happen if the bookshelf doesn't exist on the server
                                // or if there was an issue with the operation
                                lblInfo.setText("Errore nell'aggiunta del libro alla libreria. La libreria potrebbe non esistere.");
                                lblInfo.setForeground(Color.RED);
                            }
                        } catch (java.rmi.RemoteException e) {
                            lblInfo.setText("Errore di connessione: " + e.getMessage());
                            lblInfo.setForeground(Color.RED);
                            e.printStackTrace();
                        }
                    } else {
                        lblInfo.setText("Il libro è già presente nella libreria.");
                        lblInfo.setForeground(Color.RED);
                    }
                    return;
                }
            }
            lblInfo.setText("Libreria non trovata.");
            lblInfo.setForeground(Color.RED);
        } catch (java.rmi.RemoteException e) {
            lblInfo.setText("Errore: " + e.getMessage());
            lblInfo.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}