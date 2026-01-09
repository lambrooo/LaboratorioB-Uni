package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * This class manages the graphical user interface for displaying book details.
 * It shows detailed information about the book, including aggregated ratings, suggestions, and user comments.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see Book
 * @see BookRecommender
 */
public class GUI_BookDetails extends JFrame {
    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;
    private Book book;
    /**
     * Constructs a new GUI_BookDetails frame.
     * Initializes the graphical interface to display details of the selected book.
     * 
     * @param selectedBook The book whose details are to be displayed.
     */
    public GUI_BookDetails(Book selectedBook) {
        this.book = selectedBook;
        setTitle("Book Recommender - Dettagli Libro");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }
 /**
     * Initializes and configures all components of the graphical interface.
     * This method creates and positions all UI elements, including labels, buttons, and text areas.
     */
    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("Titolo: " + book.getTitle());
        lblTitle.setFont(new Font("Cambria", Font.BOLD, 16));
        lblTitle.setBounds(20, 20, 560, 25);
        contentPane.add(lblTitle);

        JLabel lblAuthor = new JLabel("Autore: " + book.getAuthor());
        lblAuthor.setFont(new Font("Cambria", Font.PLAIN, 14));
        lblAuthor.setBounds(20, 50, 560, 20);
        contentPane.add(lblAuthor);

        JLabel lblYear = new JLabel("Anno: " + book.getYear());
        lblYear.setFont(new Font("Cambria", Font.PLAIN, 14));
        lblYear.setBounds(20, 75, 560, 20);
        contentPane.add(lblYear);

        JLabel lblGenre = new JLabel("Genere: " + book.getGenre());
        lblGenre.setFont(new Font("Cambria", Font.PLAIN, 14));
        lblGenre.setBounds(20, 100, 560, 20);
        contentPane.add(lblGenre);

        int y = 130;

        // Prospetto riassuntivo delle valutazioni
        JLabel lblRatingSummary = new JLabel("Prospetto riassuntivo delle valutazioni:");
        lblRatingSummary.setFont(new Font("Cambria", Font.BOLD, 14));
        lblRatingSummary.setBounds(20, y, 560, 20);
        contentPane.add(lblRatingSummary);
        y += 25;

        String[] criteria = {"Stile", "Contenuto", "Gradevolezza", "Originalità", "Edizione", "Voto Finale"};
        AggregatedRating aggregatedRating = null;
        try {
            aggregatedRating = BookRecommender.getAggregatedRating(book.getTitle());
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle valutazioni: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        if (aggregatedRating != null) {
            for (String criterion : criteria) {
                JLabel lblAggregated = new JLabel(criterion + ": Media " +
                    String.format("%.2f", aggregatedRating.getAverage(criterion)) +
                    ", Numero valutazioni: " + aggregatedRating.getCount(criterion));
                lblAggregated.setFont(new Font("Cambria", Font.PLAIN, 12));
                lblAggregated.setBounds(40, y, 520, 20);
                contentPane.add(lblAggregated);
                y += 25;
            }
        }

        // Libri consigliati con conteggio
        y += 20;
        JLabel lblSuggestions = new JLabel("Libri consigliati:");
        lblSuggestions.setFont(new Font("Cambria", Font.BOLD, 14));
        lblSuggestions.setBounds(20, y, 560, 20);
        contentPane.add(lblSuggestions);
        y += 25;

        ArrayList<RecommendedBook> suggestedBooks = null;
        try {
            suggestedBooks = BookRecommender.getSuggestedBooksWithCount(book.getTitle());
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dei libri suggeriti: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        if (suggestedBooks == null || suggestedBooks.isEmpty()) {
            JLabel lblNoSuggestions = new JLabel("Nessun libro consigliato per questo titolo.");
            lblNoSuggestions.setFont(new Font("Cambria", Font.ITALIC, 12));
            lblNoSuggestions.setBounds(40, y, 560, 20);
            contentPane.add(lblNoSuggestions);
            y += 25;
        } else {
            for (RecommendedBook suggestedBook : suggestedBooks) {
                JLabel lblSuggestedBook = new JLabel(suggestedBook.getTitle() +
                                                     " (Suggerito da " + suggestedBook.getCount() + " utenti)");
                lblSuggestedBook.setFont(new Font("Cambria", Font.PLAIN, 12));
                lblSuggestedBook.setBounds(40, y, 520, 20);
                contentPane.add(lblSuggestedBook);
                y += 25;
            }
        }

        // Commenti degli utenti
        y += 20;
        JLabel lblComments = new JLabel("Commenti degli utenti:");
        lblComments.setFont(new Font("Cambria", Font.BOLD, 14));
        lblComments.setBounds(20, y, 560, 20);
        contentPane.add(lblComments);
        y += 25;

        ArrayList<UserComment> comments = null;
        try {
            comments = BookRecommender.getUserComments(book.getTitle());
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dei commenti: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        if (comments == null || comments.isEmpty()) {
            JLabel lblNoComments = new JLabel("Nessun commento per questo libro.");
            lblNoComments.setFont(new Font("Cambria", Font.ITALIC, 12));
            lblNoComments.setBounds(40, y, 560, 20);
            contentPane.add(lblNoComments);
            y += 25;
        } else {
            JTextArea txtComments = new JTextArea();
            txtComments.setEditable(false);
            txtComments.setFont(new Font("Cambria", Font.PLAIN, 12));
            for (UserComment comment : comments) {
                txtComments.append(comment.getUserId() + ": " + comment.getComment() + "\n\n");
            }
            JScrollPane scrollComments = new JScrollPane(txtComments);
            scrollComments.setBounds(40, y, 520, 100);
            contentPane.add(scrollComments);
            y += 120;
        }

        JButton btnClose = new JButton("Chiudi");
        btnClose.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnClose.setForeground(colorLightYellow);
        // btnClose.setBackground(colorBtnBrown);
        btnClose.setBounds(250, y, 100, 25);
        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnClose);

        
        setSize(600, y + 70);
    }
}