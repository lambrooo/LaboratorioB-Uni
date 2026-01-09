package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.awt.event.ActionEvent;

/**
 * This class manages the graphical user interface for searching books.
 * It allows users to search for books by title or author/year, and provides different options for logged-in users.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see BookRecommender
 * @see GUI_OverviewResearch
 * @see GUI_Home
 * @see GUI_LoggedHomePage
 */
public class GUI_SearchBooks extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;
    private JTextField txtSearch;
    private ButtonGroup rdbtnGroup = new ButtonGroup();
    private HashMap<Bookshelf_key, Bookshelf> bookshelves;
    private DefaultComboBoxModel<String> defaultComboBoxModel_bookshelves;
    private boolean visibility;

    /**
     * Constructs a new GUI_SearchBooks frame.
     * It allows users to search for books by title or author/year, and provides different options for logged-in users.
     * 
     * @param userid A String representing the user ID (null if not logged in).
     */
    public GUI_SearchBooks(String userid) {
        setTitle("Book Recommender - Ricerca Libri");

        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        try {
            defaultComboBoxModel_bookshelves = getComboBoxModel_Bookshelf_fromHashmap(userid);
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento delle librerie: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            defaultComboBoxModel_bookshelves = new DefaultComboBoxModel<>(); // Initialize to empty to avoid NullPointerException
        }
        visibility = (userid != null);
        initComponents(userid);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Creates a DefaultComboBoxModel containing the names of all bookshelves for the given user.
     * 
     * @param userid A String representing the user ID.
     * @return A DefaultComboBoxModel containing bookshelf names.
     * @throws java.rmi.RemoteException if a remote communication error occurs.
     */
    protected DefaultComboBoxModel<String> getComboBoxModel_Bookshelf_fromHashmap(String userid) throws java.rmi.RemoteException {
        bookshelves = BookRecommender.getBookshelves(userid);
        defaultComboBoxModel_bookshelves = new DefaultComboBoxModel<>();
        for(Bookshelf_key key : bookshelves.keySet()) {
            defaultComboBoxModel_bookshelves.addElement(bookshelves.get(key).getName());
        }
        return defaultComboBoxModel_bookshelves;
    }
    /**
     * Initializes and sets up the components of the GUI.
     * 
     * @param userid A String representing the user ID (null if not logged in).
     */
    void initComponents(String userid){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JComboBox<String> cmboxLogged = new JComboBox<>(defaultComboBoxModel_bookshelves);
        cmboxLogged.setVisible(visibility);
        cmboxLogged.setFont(new Font("Cambria", Font.PLAIN, 12));
        cmboxLogged.setBounds(223, 98, 169, 20);
        contentPane.add(cmboxLogged);
        
        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setForeground(Color.RED);
        lblNewLabel_2.setFont(new Font("Cambria", Font.BOLD, 12));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(106, 51, 274, 41);
        contentPane.add(lblNewLabel_2);
        
        JRadioButton rdbtnTitle = new JRadioButton("Titolo");
        rdbtnTitle.setFont(new Font("Cambria", Font.PLAIN, 12));
        // rdbtnTitle.setBackground(colorLightYellow);
        rdbtnTitle.setBounds(223, 22, 70, 23);
        rdbtnTitle.setSelected(true);
        contentPane.add(rdbtnTitle);
        
        JRadioButton rdbtnAuthor = new JRadioButton("Autore/Anno");
        rdbtnAuthor.setFont(new Font("Cambria", Font.PLAIN, 12));
        // rdbtnAuthor.setBackground(colorLightYellow);
        rdbtnAuthor.setBounds(303, 22, 110, 23);
        contentPane.add(rdbtnAuthor);
        
        rdbtnGroup.add(rdbtnTitle);
        rdbtnGroup.add(rdbtnAuthor);
        
        JLabel lblNewLabel = new JLabel("Cerca per:");
        lblNewLabel.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setBounds(78, 22, 112, 23);
        contentPane.add(lblNewLabel);
        
        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Cambria", Font.PLAIN, 12));
        txtSearch.setBounds(223, 129, 169, 20);
        contentPane.add(txtSearch);
        txtSearch.setColumns(10);
        
        JLabel label = new JLabel("Scegli la libreria:");
        label.setFont(new Font("Cambria", Font.PLAIN, 12));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setVisible(visibility);
        label.setBounds(10, 100, 203, 17);
        contentPane.add(label);
        
        JLabel lblNewLabel_1 = new JLabel("Inserisci il valore da cercare:");
        lblNewLabel_1.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(10, 131, 203, 17);
        contentPane.add(lblNewLabel_1);
        
        JLabel lblNewLabel_3 = new JLabel("NB: La ricerca per Autore/Anno deve essere nel formato \"Autore/Anno\"");
        lblNewLabel_3.setFont(new Font("Cambria", Font.PLAIN, 10));
        lblNewLabel_3.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_3.setBounds(10, 226, 414, 24);
        contentPane.add(lblNewLabel_3);
        
        JButton btnNewButton = new JButton("CERCA");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkDataFormat()){
                    String searchingType = rdbtnTitle.isSelected() ? "Title" : "Author";
                    String text = txtSearch.getText();

                    try {
                        if(!BookRecommender.cercaLibro(searchingType, text, null, null, null).isEmpty()){
                            String selectedBookshelf = visibility ? cmboxLogged.getSelectedItem().toString() : null;
                            setVisible(false);    
                            new GUI_OverviewResearch(searchingType, text, selectedBookshelf, userid);
                        } else {
                            lblNewLabel_2.setText("Nessun risultato trovato");
                        }
                    } catch (java.rmi.RemoteException ex) {
                        JOptionPane.showMessageDialog(null, "Errore nella ricerca dei libri: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    lblNewLabel_2.setText("Inserire tutti i campi richiesti");
                }
            }
        });
        btnNewButton.setBounds(188, 179, 89, 23);
        // btnNewButton.setForeground(colorLightYellow);
        // btnNewButton.setBackground(colorBtnBrown);
        contentPane.add(btnNewButton);
        
        JButton btnBack = new JButton("INDIETRO");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                if(userid == null) {
                    try {
                        new GUI_Home();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    new GUI_LoggedHomePage(userid);
                }
            }
        });
        btnBack.setBounds(10, 20, 68, 23);
        // btnBack.setForeground(colorLightYellow);
        // btnBack.setBackground(colorBtnBrown);
        contentPane.add(btnBack);
    }
    
    /**
     * Checks if the search input is valid.
     * 
     * @return true if the search input is not empty, false otherwise.
     */
    private boolean checkDataFormat() {
        return txtSearch.getText().length() != 0;
    }
}