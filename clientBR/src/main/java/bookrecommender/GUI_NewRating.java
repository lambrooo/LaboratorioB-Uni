package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * This class manages the graphical user interface for inserting new book ratings.
 * It allows users to select a book from their bookshelves and provide ratings
 * based on various criteria, along with optional comments.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class GUI_NewRating extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JFormattedTextField txtStile, txtContenuto, txtGradevolezza, txtOriginalita, txtEdizione, txtVotoFinale;
    private JTextField txtStileComment, txtContenutoComment, txtGradevolezzaComment, txtOriginalitaComment, txtEdizioneComment;
    private int stile, contenuto, gradevolezza, originalita, edizione, votoFinale;
    private HashMap<Bookshelf_key, Bookshelf> bookshelves;
    private DefaultComboBoxModel<String> defaultComboBoxModel_bookshelves;
    private DefaultComboBoxModel<Book> defaultComboBoxModel_books;
    private JComboBox<String> cmboxBookshelf;
    private JComboBox<Book> cmboxBook;

    /**
     * Constructs a new GUI_NewRating frame.
     * Initializes the UI components for inserting a new rating.
     * 
     * @param userid The ID of the current user.
     */
    public GUI_NewRating(String userid) {
        setTitle("Book Recommender - Inserisci Valutazione");
        // setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png")); // Assuming img folder is not directly accessible
        try {
            bookshelves = BookRecommender.getBookshelves(userid);
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle librerie: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            // Handle the error, perhaps disable functionality or exit
        }
        defaultComboBoxModel_bookshelves = getComboBoxModel_Bookshelf_fromHashmap();
        initComponents(userid);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Retrieves a DefaultComboBoxModel containing the names of all bookshelves for the current user.
     * @return A DefaultComboBoxModel containing bookshelf names.
     */
    protected DefaultComboBoxModel<String> getComboBoxModel_Bookshelf_fromHashmap() {
        defaultComboBoxModel_bookshelves = new DefaultComboBoxModel<>();
        for(Bookshelf_key key : bookshelves.keySet()) {
            defaultComboBoxModel_bookshelves.addElement(bookshelves.get(key).getName());
        }
        return defaultComboBoxModel_bookshelves;
    }

    /**
     * Retrieves a DefaultComboBoxModel containing Book objects from a specific bookshelf.
     * @param bookshelf The name of the bookshelf.
     * @param userid The ID of the user who owns the bookshelf.
     * @return A DefaultComboBoxModel containing Book objects.
     */
    protected DefaultComboBoxModel<Book> getComboBoxModel_books_fromHashmap(String bookshelf, String userid) {
        defaultComboBoxModel_books = new DefaultComboBoxModel<>();
        for(Bookshelf_key key : bookshelves.keySet()) {
            if(key.getName().equals(bookshelf) && key.getuserid().equals(userid)) {
                for(String bookTitle : bookshelves.get(key).getBooks()) {
                    Book book = null;
                    try {
                        book = BookRecommender.getBookByTitle(bookTitle);
                    } catch (java.rmi.RemoteException ex) {
                        JOptionPane.showMessageDialog(null, "Errore nel recupero dei dettagli del libro: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                    if (book != null) {
                        defaultComboBoxModel_books.addElement(book);
                    }
                }
            }
        }
        return defaultComboBoxModel_books;
    }

    /**
     * Parses a string value to an integer, returning a default value if parsing fails or the value is out of range.
     * @param value The string to parse.
     * @param defaultValue The default value to return.
     * @return The parsed integer value, or the default value.
     */
    public static int getValueOrDefault(String value, int defaultValue) {
        try {
            int parsedValue = Integer.parseInt(value);
            return parsedValue < 1 || parsedValue > 5 ? defaultValue : parsedValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Initializes and configures all components of the graphical interface.
     * This method creates and positions all UI elements for rating a book.
     * 
     * @param userid The ID of the current user.
     */
    private void initComponents(String userid) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 700);
        contentPane = new JPanel(new GridBagLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Select Bookshelf
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(new JLabel("Seleziona una libreria:"), gbc);
        gbc.gridx = 1;
        cmboxBookshelf = new JComboBox<>(defaultComboBoxModel_bookshelves);
        cmboxBookshelf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedBookshelfName = (String) cmboxBookshelf.getSelectedItem();
                defaultComboBoxModel_books = getComboBoxModel_books_fromHashmap(selectedBookshelfName, userid);
                cmboxBook.setModel(defaultComboBoxModel_books);
                cmboxBook.setEnabled(true);
            }
        });
        contentPane.add(cmboxBookshelf, gbc);

        // Row 1: Select Book
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(new JLabel("Seleziona un libro:"), gbc);
        gbc.gridx = 1;
        cmboxBook = new JComboBox<>();
        cmboxBook.setEnabled(false);
        cmboxBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Enable rating fields when a book is selected
                if (txtStile != null) txtStile.setEnabled(true);
                if (txtContenuto != null) txtContenuto.setEnabled(true);
                if (txtGradevolezza != null) txtGradevolezza.setEnabled(true);
                if (txtOriginalita != null) txtOriginalita.setEnabled(true);
                if (txtEdizione != null) txtEdizione.setEnabled(true);
                if (txtVotoFinale != null) txtVotoFinale.setEnabled(true);
                if (txtStileComment != null) txtStileComment.setEnabled(true);
                if (txtContenutoComment != null) txtContenutoComment.setEnabled(true);
                if (txtGradevolezzaComment != null) txtGradevolezzaComment.setEnabled(true);
                if (txtOriginalitaComment != null) txtOriginalitaComment.setEnabled(true);
                if (txtEdizioneComment != null) txtEdizioneComment.setEnabled(true);
            }
        });
        contentPane.add(cmboxBook, gbc);

        // Row 2: Info Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JLabel lblInfo = new JLabel("");
        lblInfo.setForeground(Color.RED);
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblInfo, gbc);

        // Row 3: Value Range Info
        gbc.gridy = 3;
        contentPane.add(new JLabel("* Intervallo valori: {1, 2, 3, 4, 5}"), gbc);

        // Row 4: Value Format Info
        gbc.gridy = 4;
        contentPane.add(new JLabel("* I valori fuori dall'intervallo saranno sostituiti con 0"), gbc);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(5);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        // Rating fields and comments
        String[] criteria = {"Stile", "Contenuto", "Gradevolezza", "Originalità", "Edizione", "Voto Finale"};
        JFormattedTextField[] txtRatings = new JFormattedTextField[criteria.length];
        JTextField[] txtComments = new JTextField[criteria.length];

        for (int i = 0; i < criteria.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = 5 + i;
            gbc.gridwidth = 1;
            contentPane.add(new JLabel(criteria[i]), gbc);

            gbc.gridx = 1;
            txtRatings[i] = new JFormattedTextField(formatter);
            txtRatings[i].setEnabled(false);
            contentPane.add(txtRatings[i], gbc);

            if (i < criteria.length - 1) { // Voto Finale has no comment
                gbc.gridx = 2;
                contentPane.add(new JLabel("Commento:"), gbc);
                gbc.gridx = 3;
                txtComments[i] = new JTextField(20);
                txtComments[i].setEnabled(false);
                contentPane.add(txtComments[i], gbc);
            }
        }

        txtStile = txtRatings[0];
        txtContenuto = txtRatings[1];
        txtGradevolezza = txtRatings[2];
        txtOriginalita = txtRatings[3];
        txtEdizione = txtRatings[4];
        txtVotoFinale = txtRatings[5];

        txtStileComment = txtComments[0];
        txtContenutoComment = txtComments[1];
        txtGradevolezzaComment = txtComments[2];
        txtOriginalitaComment = txtComments[3];
        txtEdizioneComment = txtComments[4];

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5 + criteria.length;
        gbc.gridwidth = 2;
        JButton btnInsertRating = new JButton("INSERISCI VALUTAZIONE");
        btnInsertRating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stile = getValueOrDefault(txtStile != null ? txtStile.getText() : "", 0);
                contenuto = getValueOrDefault(txtContenuto != null ? txtContenuto.getText() : "", 0);
                gradevolezza = getValueOrDefault(txtGradevolezza != null ? txtGradevolezza.getText() : "", 0);
                originalita = getValueOrDefault(txtOriginalita != null ? txtOriginalita.getText() : "", 0);
                edizione = getValueOrDefault(txtEdizione != null ? txtEdizione.getText() : "", 0);
                votoFinale = (stile + contenuto + gradevolezza + originalita + edizione) / 5;

                Book selectedBook = (Book) cmboxBook.getSelectedItem();
                if (selectedBook == null) {
                    lblInfo.setText("Seleziona un libro.");
                    return;
                }

                String bookTitle = selectedBook.getTitle();
                String bookAuthor = selectedBook.getAuthor();
                String bookYear = selectedBook.getYear();

                String styleComment = txtStileComment != null ? txtStileComment.getText() : "";
                String contentComment = txtContenutoComment != null ? txtContenutoComment.getText() : "";
                String pleasantnessComment = txtGradevolezzaComment != null ? txtGradevolezzaComment.getText() : "";
                String originalityComment = txtOriginalitaComment != null ? txtOriginalitaComment.getText() : "";
                String editionComment = txtEdizioneComment != null ? txtEdizioneComment.getText() : "";

                try {
                    if(BookRecommender.appendRating(userid, (String)cmboxBookshelf.getSelectedItem(), bookTitle, bookAuthor, bookYear, stile, contenuto, gradevolezza, originalita, edizione, votoFinale, styleComment, contentComment, pleasantnessComment, originalityComment, editionComment)) {
                        JOptionPane.showMessageDialog(GUI_NewRating.this, "Valutazione aggiunta con successo!");
                        setVisible(false);
                        new GUI_LoggedHomePage(userid);
                    } else {
                        lblInfo.setText("Hai già valutato questo libro. Non puoi valutarlo di nuovo.");
                    }
                } catch (java.rmi.RemoteException ex) {
                    ex.printStackTrace();
                    lblInfo.setText("Errore: " + ex.getMessage());
                }
            }
        });
        contentPane.add(btnInsertRating, gbc);

        gbc.gridy = 6 + criteria.length;
        JButton btnClear = new JButton("PULISCI");
        btnClear.addActionListener(e -> {
            if (txtStile != null) txtStile.setText("");
            if (txtContenuto != null) txtContenuto.setText("");
            if (txtGradevolezza != null) txtGradevolezza.setText("");
            if (txtOriginalita != null) txtOriginalita.setText("");
            if (txtEdizione != null) txtEdizione.setText("");
            if (txtVotoFinale != null) txtVotoFinale.setText("");
            if (txtStileComment != null) txtStileComment.setText("");
            if (txtContenutoComment != null) txtContenutoComment.setText("");
            if (txtGradevolezzaComment != null) txtGradevolezzaComment.setText("");
            if (txtOriginalitaComment != null) txtOriginalitaComment.setText("");
            if (txtEdizioneComment != null) txtEdizioneComment.setText("");
        });
        contentPane.add(btnClear, gbc);

        gbc.gridy = 7 + criteria.length;
        JButton btnBack = new JButton("INDIETRO");
        btnBack.addActionListener(e -> {
            setVisible(false);
            new GUI_LoggedHomePage(userid);
        });
        contentPane.add(btnBack, gbc);
    }
}