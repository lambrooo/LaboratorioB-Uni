package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This class manages the graphical user interface for inserting new book suggestions.
 * It allows users to select a source book and suggest up to three other books.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class GUI_NewSuggestion extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<Book> cmboxBook;
    private JComboBox<Book> cmboxSuggestion1;
    private JComboBox<Book> cmboxSuggestion2;
    private JComboBox<Book> cmboxSuggestion3;
    private JLabel lblInfo;
    private String userid;

    /**
     * Constructs a new GUI_NewSuggestion frame.
     * Initializes the UI components for inserting new book suggestions.
     * 
     * @param userid The ID of the current user.
     */
    public GUI_NewSuggestion(String userid) {
        this.userid = userid;
        setTitle("Book Recommender - Inserisci Suggerimento");
        // setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png")); // Assuming img folder is not directly accessible
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes and configures all components of the graphical interface.
     * This method creates and positions all UI elements for suggesting books.
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

        // Row 0: Select Book
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(new JLabel("Seleziona libro:"), gbc);
        gbc.gridx = 1;
        cmboxBook = new JComboBox<>(getAllBooks());
        contentPane.add(cmboxBook, gbc);

        // Row 1: Suggestion 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("Suggerisci libro 1:"), gbc);
        gbc.gridx = 1;
        cmboxSuggestion1 = new JComboBox<>(getAllBooks());
        contentPane.add(cmboxSuggestion1, gbc);

        // Row 2: Suggestion 2
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPane.add(new JLabel("Suggerisci libro 2:"), gbc);
        gbc.gridx = 1;
        cmboxSuggestion2 = new JComboBox<>(getAllBooks());
        contentPane.add(cmboxSuggestion2, gbc);

        // Row 3: Suggestion 3
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(new JLabel("Suggerisci libro 3:"), gbc);
        gbc.gridx = 1;
        cmboxSuggestion3 = new JComboBox<>(getAllBooks());
        contentPane.add(cmboxSuggestion3, gbc);

        // Row 4: Insert Button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JButton btnInsert = new JButton("INSERISCI");
        btnInsert.addActionListener(e -> insertSuggestion());
        contentPane.add(btnInsert, gbc);

        // Row 5: Info Label
        gbc.gridy = 5;
        lblInfo = new JLabel("");
        lblInfo.setForeground(Color.RED);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblInfo, gbc);

        // Row 6: Back Button
        gbc.gridy = 6;
        JButton btnBack = new JButton("INDIETRO");
        btnBack.addActionListener(e -> {
            setVisible(false);
            new GUI_LoggedHomePage(userid);
        });
        contentPane.add(btnBack, gbc);
    }

    /**
     * Retrieves a DefaultComboBoxModel containing all books available in the system.
     * @return A DefaultComboBoxModel of Book objects.
     */
    private DefaultComboBoxModel<Book> getAllBooks() {
        DefaultComboBoxModel<Book> bookModel = null;
        try {
            bookModel = BookRecommender.getBooks();
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dei libri: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return bookModel;
    }

    /**
     * Handles the insertion of a new book suggestion.
     * Validates user input and calls the remote service to save the suggestion.
     */
    private void insertSuggestion() {
        Book sourceBook = (Book) cmboxBook.getSelectedItem();
        if (sourceBook == null) {
            lblInfo.setText("Seleziona un libro di origine.");
            return;
        }

        ArrayList<Book> suggestedBooks = new ArrayList<>();
        // Add suggestions while avoiding duplicates
        Book suggestion1 = (Book) cmboxSuggestion1.getSelectedItem();
        Book suggestion2 = (Book) cmboxSuggestion2.getSelectedItem();
        Book suggestion3 = (Book) cmboxSuggestion3.getSelectedItem();
        
        if (suggestion1 != null && !suggestedBooks.contains(suggestion1)) {
            suggestedBooks.add(suggestion1);
        }
        if (suggestion2 != null && !suggestedBooks.contains(suggestion2)) {
            suggestedBooks.add(suggestion2);
        }
        if (suggestion3 != null && !suggestedBooks.contains(suggestion3)) {
            suggestedBooks.add(suggestion3);
        }

        if (suggestedBooks.isEmpty()) {
            lblInfo.setText("Seleziona almeno un libro da suggerire.");
            return;
        }

        // Check that source book is not in suggested books
        boolean sourceInSuggestions = false;
        for (Book book : suggestedBooks) {
            if (book.getTitle().equals(sourceBook.getTitle()) && 
                book.getAuthor().equals(sourceBook.getAuthor()) && 
                book.getYear().equals(sourceBook.getYear())) {
                sourceInSuggestions = true;
                break;
            }
        }
        
        if (sourceInSuggestions) {
            lblInfo.setForeground(Color.RED);
            lblInfo.setText("Il libro di origine non può essere tra i suggerimenti.");
            return;
        }

        try {
            if (BookRecommender.insertSuggestion(userid, sourceBook.getTitle(), sourceBook.getAuthor(), sourceBook.getYear(), suggestedBooks)) {
                lblInfo.setForeground(Color.GREEN);
                lblInfo.setText("Suggerimento inserito con successo");
            } else {
                lblInfo.setForeground(Color.RED);
                lblInfo.setText("Errore nell'inserimento del suggerimento");
            }
        } catch (java.rmi.RemoteException e) {
            lblInfo.setForeground(Color.RED);
            lblInfo.setText("Errore: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
