package bookrecommender;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.io.IOException;

/**
 * This class manages the graphical user interface for the logged-in user's home page.
 * It provides options for various actions such as searching and adding books,
 * creating bookshelves, inserting ratings and suggestions, viewing bookshelves, and logging out.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see GUI_SearchAndAddBook
 * @see GUI_CreateBookshelf
 * @see GUI_NewRating
 * @see GUI_NewSuggestion
 * @see GUI_UserBookshelves
 * @see GUI_Home
 */
public class GUI_LoggedHomePage extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;

    /**
     * Creates the logged-in user's home page frame.
     * 
     * @param userid The ID of the logged-in user
     */
    public GUI_LoggedHomePage(String userid) {
        setTitle("Book Recommender - Pagina Principale");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        initComponents(userid);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    /**
     * Initializes and configures all components of the logged-in user's home page.
     * This method creates and positions all UI elements including buttons for various actions.
     * 
     * @param userid The ID of the logged-in user
     */
    public void initComponents(String userid) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblWelcome = new JLabel("Benvenuto " + userid + " nella tua area riservata. Scegli un'opzione:");
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblWelcome.setBounds(10, 24, 414, 41);
        contentPane.add(lblWelcome);
        
        // Button for searching and adding books
        JButton btnSearchAndAddBook = new JButton("Cerca e Aggiungi Libro");
        btnSearchAndAddBook.addActionListener(e -> {
            setVisible(false);
            new GUI_SearchAndAddBook(userid);
        });
        btnSearchAndAddBook.setBounds(100, 80, 250, 25);
        contentPane.add(btnSearchAndAddBook);
        
        // Button for creating a new bookshelf
        JButton btnCreateBookshelf = new JButton("Crea Libreria");
        btnCreateBookshelf.addActionListener(e -> {
            setVisible(false);
            new GUI_CreateBookshelf(userid);
        });
        btnCreateBookshelf.setBounds(100, 115, 250, 25);
        contentPane.add(btnCreateBookshelf);
        
        // Button for inserting a new rating
        JButton btnInsertRating = new JButton("Inserisci Valutazione");
        btnInsertRating.addActionListener(e -> {
            setVisible(false);
            new GUI_NewRating(userid);
        });
        btnInsertRating.setBounds(100, 150, 250, 25);
        contentPane.add(btnInsertRating);
        
        // Button for inserting a new suggestion
        JButton btnInsertSuggestion = new JButton("Inserisci Suggerimento");
        btnInsertSuggestion.addActionListener(e -> {
            setVisible(false);
            new GUI_NewSuggestion(userid);
        });
        btnInsertSuggestion.setBounds(100, 185, 250, 25);
        contentPane.add(btnInsertSuggestion);

        // Button for viewing user's bookshelves
        JButton btnViewBookshelves = new JButton("Le mie Librerie");
        btnViewBookshelves.addActionListener(e -> {
            setVisible(false);
            new GUI_UserBookshelves(userid);
        });
        btnViewBookshelves.setBounds(100, 220, 250, 25);
        contentPane.add(btnViewBookshelves);
        
        // Button for logging out
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            setVisible(false);
            try {
                new GUI_Home();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il logout: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        btnLogout.setBounds(100, 255, 250, 25);
        contentPane.add(btnLogout);
    }
}