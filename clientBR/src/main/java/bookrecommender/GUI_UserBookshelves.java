package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
/**
 * This class manages the graphical user interface for displaying a user's bookshelves.
 * It shows a list of all bookshelves belonging to the user and the books contained in each.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see BookRecommender
 * @see Bookshelf
 * @see GUI_LoggedHomePage
 */
public class GUI_UserBookshelves extends JFrame {
    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;
    /**
     * Constructs a new GUI_UserBookshelves frame.
     * 
     * @param userid The ID of the current user.
     */
    public GUI_UserBookshelves(String userid) {
        setTitle("Book Recommender - Le tue Librerie");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        initComponents(userid);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Initializes and sets up the components of the GUI.
     * This method creates and positions all UI elements including labels for bookshelves and books,
     * and a back button to return to the home page.
     * 
     * @param userid The ID of the current user.
     */
    private void initComponents(String userid) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JLabel lblTitle = new JLabel("Le tue Librerie");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Cambria", Font.BOLD, 18));
        contentPane.add(lblTitle, BorderLayout.NORTH);

        JPanel panelBookshelves = new JPanel();
        panelBookshelves.setLayout(new BoxLayout(panelBookshelves, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelBookshelves);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        refreshBookshelves(panelBookshelves, userid);
    }

    /**
     * Refreshes the display of bookshelves and their contents.
     * Clears and rebuilds the panel with updated bookshelf data from the server.
     * 
     * @param panelBookshelves The panel to display bookshelves.
     * @param userid The ID of the current user.
     */
    private void refreshBookshelves(JPanel panelBookshelves, String userid) {
        panelBookshelves.removeAll();
        try {
            HashMap<Bookshelf_key, Bookshelf> bookshelves = BookRecommender.getBookshelves(userid);
            for (Map.Entry<Bookshelf_key, Bookshelf> entry : bookshelves.entrySet()) {
                Bookshelf bookshelf = entry.getValue();
                JPanel panelBookshelf = new JPanel();
                panelBookshelf.setLayout(new FlowLayout(FlowLayout.LEFT));
                
                JLabel lblBookshelfName = new JLabel(bookshelf.getName());
                lblBookshelfName.setFont(new Font("Cambria", Font.BOLD, 14));
                panelBookshelf.add(lblBookshelfName);

                JButton btnRename = new JButton("Rinomina");
                btnRename.addActionListener(e -> {
                    String newName = JOptionPane.showInputDialog(this, "Inserisci il nuovo nome per la libreria:", bookshelf.getName());
                    if (newName != null && !newName.trim().isEmpty() && !newName.equals(bookshelf.getName())) {
                        try {
                            boolean success = BookRecommender.renameBookshelf(userid, bookshelf.getName(), newName.trim());
                            if (success) {
                                JOptionPane.showMessageDialog(this, "Libreria rinominata con successo!");
                                refreshBookshelves(panelBookshelves, userid);
                            } else {
                                JOptionPane.showMessageDialog(this, "Errore: Nome già esistente o libreria non trovata.", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Errore durante la rinomina: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                panelBookshelf.add(btnRename);

                JButton btnDelete = new JButton("Elimina");
                btnDelete.addActionListener(e -> {
                    int confirm = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler eliminare la libreria '" + bookshelf.getName() + "' e tutti i suoi libri?", "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            boolean success = BookRecommender.deleteBookshelf(userid, bookshelf.getName());
                            if (success) {
                                JOptionPane.showMessageDialog(this, "Libreria eliminata con successo!");
                                refreshBookshelves(panelBookshelves, userid);
                            } else {
                                JOptionPane.showMessageDialog(this, "Errore: Libreria non trovata.", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Errore durante l'eliminazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                panelBookshelf.add(btnDelete);

                panelBookshelves.add(panelBookshelf);

                for (String book : bookshelf.getBooks()) {
                    JLabel lblBook = new JLabel("- " + book);
                    lblBook.setFont(new Font("Cambria", Font.PLAIN, 12));
                    // Indent books slightly
                    lblBook.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); 
                    panelBookshelves.add(lblBook);
                }
                panelBookshelves.add(Box.createVerticalStrut(10));
            }
            panelBookshelves.revalidate();
            panelBookshelves.repaint();
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle librerie: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }

        JButton btnBack = new JButton("Indietro");
        btnBack.addActionListener(e -> {
            setVisible(false);
            new GUI_LoggedHomePage(userid);
        });
        btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnBack.setForeground(colorLightYellow);
        // btnBack.setBackground(colorBtnBrown);
        contentPane.add(btnBack, BorderLayout.SOUTH);
    }
}
