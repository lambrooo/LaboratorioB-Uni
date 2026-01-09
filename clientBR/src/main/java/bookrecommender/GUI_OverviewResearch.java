package bookrecommender;

import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class manages the graphical user interface for the overview of book ratings.
 * It displays aggregated ratings and book information based on search criteria or user selection.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see BookRecommender
 * @see Book
 * @see Book_key
 */
public class GUI_OverviewResearch extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JPanel contentPane;
    private int avgStile = 0, avgContenuto = 0, avgGradevolezza = 0, avgOriginalita = 0, avgEdizione = 0, avgVotoFinale = 0, occurencies = 0;
    private HashMap<Book_key, Book> books;

    /**
     * Creates the frame for displaying book ratings overview.
     * 
     * @param searchingType The type of search performed
     * @param text The search text
     * @param bookshelf The bookshelf name (if applicable)
     * @param userid The user ID (if applicable)
     */
    /**
     * Constructs a new GUI_OverviewResearch frame.
     * Displays aggregated ratings and book information based on search criteria or user selection.
     * 
     * @param searchingType The type of search performed.
     * @param text The search text.
     * @param bookshelf The bookshelf name (if applicable).
     * @param userid The user ID (if applicable).
     */
    public GUI_OverviewResearch(String searchingType, String text, String bookshelf, String userid) {
        setTitle("Book Recommender - Analisi");
        
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\Logo.png"));
        try {
            if(userid == null) {
                this.books = new HashMap<>();
                for (Book book : BookRecommender.cercaLibro(searchingType, text, null, null, null)) {
                    this.books.put(new Book_key(book.getTitle(), book.getAuthor(), book.getYear()), book);
                }
            } else {
                this.books = new HashMap<>();
                for (Book book : BookRecommender.cercaLibro(searchingType, text, null, bookshelf, userid)) {
                    this.books.put(new Book_key(book.getTitle(), book.getAuthor(), book.getYear()), book);
                }
            }
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore durante la ricerca del libro: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            this.books = new HashMap<>(); // Initialize to empty to avoid NullPointerException
        }
        occurencies = this.books.size();
        calculateAverageValues(userid);
        initComponents(userid);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Calculates the average values for book ratings.
     * This method retrieves rating values and computes averages for different rating criteria.
     * 
     * @param userid The user ID for which to calculate ratings (null for all users).
     */
    private void calculateAverageValues(String userid) {
        try {
            if(userid == null) {
                for(Book_key key : books.keySet()) {
                    avgStile += BookRecommender.getRatingValue(books.get(key).getTitle(), "Stile");
                    avgContenuto += BookRecommender.getRatingValue(books.get(key).getTitle(), "Contenuto");
                    avgGradevolezza += BookRecommender.getRatingValue(books.get(key).getTitle(), "Gradevolezza");
                    avgOriginalita += BookRecommender.getRatingValue(books.get(key).getTitle(), "Originalita");
                    avgEdizione += BookRecommender.getRatingValue(books.get(key).getTitle(), "Edizione");
                    avgVotoFinale += BookRecommender.getRatingValue(books.get(key).getTitle(), "VotoFinale");
                }   
            } else {
                for(Book_key key : books.keySet()) {
                    avgStile += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "Stile");
                    avgContenuto += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "Contenuto");
                    avgGradevolezza += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "Gradevolezza");
                    avgOriginalita += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "Originalita");
                    avgEdizione += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "Edizione");
                    avgVotoFinale += BookRecommender.getRatingValue(userid, books.get(key).getTitle(), "VotoFinale");
                }
            }
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel calcolo dei valori medi: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        
        int size = books.size();
        if(size == 0)
            size = 1;
        
        avgStile /= size;
        avgContenuto /= size; 
        avgGradevolezza /= size; 
        avgOriginalita /= size; 
        avgEdizione /= size;
        avgVotoFinale /= size;
    }
    /**
     * Initializes and sets up the components of the GUI.
     * This method creates and positions all UI elements including tables, labels, and progress bars.
     * 
     * @param userid The user ID (if applicable).
     */
    private void initComponents(String userid) {
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 733, 422);
    contentPane = new JPanel();
    // contentPane.setBackground(colorLightYellow);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    
    String[][] data = new String[books.keySet().size()][4];

    int i = 0;
    for(Book_key key : books.keySet()) {
        Book element = books.get(key);
        data[i][0] = element.getTitle();
        data[i][1] = element.getAuthor();
        data[i][2] = element.getYear();
        data[i][3] = element.getGenre();
        i++;
    }
    String[] columns = {"Titolo", "Autore", "Anno", "Genere"};
    
    JTable table = new JTable(data, columns);
    table.setBorder(new EmptyBorder(2, 2, 2, 2));
    table.setEditingColumn(0);
    table.setEditingRow(0);
    table.setGridColor(new Color(128, 128, 128));
    table.setColumnSelectionAllowed(true);
    table.setBounds(168, 20, 633, 200);
    table.setFont(new Font("Cambria", Font.PLAIN, 12));
    
    JScrollPane jp = new JScrollPane(table);
    jp.setBounds(168, 20, 533, 200);
    contentPane.add(jp);
    
    JLabel lblNewLabel = new JLabel("Risultati trovati:");
    lblNewLabel.setFont(new Font("Cambria", Font.BOLD, 13));
    lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
    lblNewLabel.setBounds(41, 88, 113, 23);
    contentPane.add(lblNewLabel);
    
    JProgressBar pBarStile = new JProgressBar();
    // pBarStile.setForeground(colorBtnBrown);
    pBarStile.setStringPainted(true);
    pBarStile.setMaximum(5);
    pBarStile.setValue(avgStile);
    pBarStile.setString(String.valueOf(avgStile));
    pBarStile.setOrientation(SwingConstants.VERTICAL);
    pBarStile.setBounds(41, 285, 20, 58);
    contentPane.add(pBarStile);
    
    JLabel lblStile = new JLabel("Stile");
    lblStile.setHorizontalAlignment(SwingConstants.CENTER);
    lblStile.setBounds(14, 344, 73, 28);
    contentPane.add(lblStile);
    
    JProgressBar pBarContenuto = new JProgressBar();
    pBarContenuto.setMaximum(5);
    pBarContenuto.setValue(avgContenuto);
    pBarContenuto.setStringPainted(true);
    pBarContenuto.setString(String.valueOf(avgContenuto));
    pBarContenuto.setOrientation(SwingConstants.VERTICAL);
    // pBarContenuto.setForeground(colorBtnBrown);
    pBarContenuto.setBounds(117, 285, 20, 58);
    contentPane.add(pBarContenuto);
    
    JLabel lblContenuto = new JLabel("Contenuto");
    lblContenuto.setHorizontalAlignment(SwingConstants.CENTER);
    lblContenuto.setBounds(97, 344, 66, 28);
    contentPane.add(lblContenuto);
    
    JProgressBar pBarGradevolezza = new JProgressBar();
    pBarGradevolezza.setValue(avgGradevolezza);
    pBarGradevolezza.setMaximum(5);
    pBarGradevolezza.setStringPainted(true);
    pBarGradevolezza.setString(String.valueOf(avgGradevolezza));
    pBarGradevolezza.setOrientation(SwingConstants.VERTICAL);
    // pBarGradevolezza.setForeground(colorBtnBrown);
    pBarGradevolezza.setBounds(193, 285, 20, 58);
    contentPane.add(pBarGradevolezza);
    
    JLabel lblGradevolezza = new JLabel("Gradevolezza");
    lblGradevolezza.setHorizontalAlignment(SwingConstants.CENTER);
    lblGradevolezza.setBounds(166, 344, 73, 28);
    contentPane.add(lblGradevolezza);
    
    JProgressBar pBarOriginalita = new JProgressBar();
    pBarOriginalita.setValue(avgOriginalita);
    pBarOriginalita.setMaximum(5);
    pBarOriginalita.setStringPainted(true);
    pBarOriginalita.setString(String.valueOf(avgOriginalita));
    pBarOriginalita.setOrientation(SwingConstants.VERTICAL);
    // pBarOriginalita.setForeground(colorBtnBrown);
    pBarOriginalita.setBounds(269, 285, 20, 58);
    contentPane.add(pBarOriginalita);
    
    JLabel lblOriginalita = new JLabel("Originalità");
    lblOriginalita.setHorizontalAlignment(SwingConstants.CENTER);
    lblOriginalita.setBounds(249, 344, 66, 28);
    contentPane.add(lblOriginalita);
    
    JProgressBar pBarEdizione = new JProgressBar();
    pBarEdizione.setValue(avgEdizione);
    pBarEdizione.setMaximum(5);
    pBarEdizione.setStringPainted(true);
    pBarEdizione.setString(String.valueOf(avgEdizione));
    pBarEdizione.setOrientation(SwingConstants.VERTICAL);
    // pBarEdizione.setForeground(colorBtnBrown);
    pBarEdizione.setBounds(345, 285, 20, 58);
    contentPane.add(pBarEdizione);

	JLabel lblEdizione = new JLabel("Edizione");
	lblEdizione.setHorizontalAlignment(SwingConstants.CENTER);
	lblEdizione.setBounds(318, 344, 73, 28);
	contentPane.add(lblEdizione);

	JProgressBar pBarVotoFinale = new JProgressBar();
	pBarVotoFinale.setValue(avgVotoFinale);
	pBarVotoFinale.setMaximum(5);
	pBarVotoFinale.setStringPainted(true);
	pBarVotoFinale.setString(String.valueOf(avgVotoFinale));
	pBarVotoFinale.setOrientation(SwingConstants.VERTICAL);
	// pBarVotoFinale.setForeground(colorBtnBrown);
	pBarVotoFinale.setBounds(421, 285, 20, 58);
	contentPane.add(pBarVotoFinale);

	JLabel lblVotoFinale = new JLabel("Voto Finale");
	lblVotoFinale.setHorizontalAlignment(SwingConstants.CENTER);
	lblVotoFinale.setBounds(401, 344, 66, 28);
	contentPane.add(lblVotoFinale);

	JButton btnBack = new JButton("INDIETRO");
	btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
	btnBack.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        setVisible(false);    
        new GUI_SearchBooks(userid);
    }
});
// btnBack.setForeground(colorLightYellow);
// btnBack.setBackground(colorBtnBrown);
btnBack.setBounds(10, 20, 68, 23);
contentPane.add(btnBack);

JButton btnHome = new JButton("HOME");
btnHome.setFont(new Font("Cambria", Font.BOLD, 12));
btnHome.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        setVisible(false);    
        if (userid == null) {
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
// btnHome.setForeground(colorLightYellow);
// btnHome.setBackground(colorBtnBrown);
btnHome.setBounds(10, 54, 68, 23);
contentPane.add(btnHome);

JLabel lblOccurrencies = new JLabel(String.valueOf(occurencies));
lblOccurrencies.setFont(new Font("Cambria", Font.BOLD, 13));
lblOccurrencies.setVerticalAlignment(SwingConstants.TOP);
lblOccurrencies.setHorizontalAlignment(SwingConstants.CENTER);
lblOccurrencies.setBounds(41, 122, 103, 28);
contentPane.add(lblOccurrencies);
}
}