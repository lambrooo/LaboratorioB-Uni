package bookrecommender;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException; // Added import for SQLException
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;
/**
 * This class manages the graphical user interface for creating a new bookshelf.
 * It allows users to name a new bookshelf and add books to it.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 * @see BookRecommender
 * @see GUI_LoggedHomePage
 */
public class GUI_CreateBookshelf extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtBookshelfName;
    private JTextField txtSearchBook;
    // Removed custom colors
    private DefaultComboBoxModel<Book> books;
    private DefaultComboBoxModel<Book> filteredBooks;
    private ArrayList<Book> bookshelfBooks = new ArrayList<>();
    private ItemListener itemListener;
    private JComboBox<Book> comboBox;
 /**
     * Constructs a new GUI_CreateBookshelf frame.
     * Initializes the GUI components for creating a new bookshelf.
     * 
     * @param userid The ID of the current user.
     */
    public GUI_CreateBookshelf(String userid) {
        setAutoRequestFocus(false);
        setTitle("Book Recommender - Crea Libreria");
        setIconImage(Toolkit.getDefaultToolkit().getImage("img\\\\Logo.png"));
        this.itemListener = null;
        try {
            books = BookRecommender.getBooks();
        } catch (java.rmi.RemoteException ex) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dei libri: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            // Handle the error, perhaps disable functionality or exit
        }
        initComponents(userid);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Initializes and configures all components of the graphical interface.
     * This method creates and positions all UI elements for creating a bookshelf.
     * 
     * @param userid The ID of the current user.
     */
    private void initComponents(String userid) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 564, 450); // Increased height to accommodate new components
        contentPane = new JPanel();
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Inserisci le informazioni richieste per creare una nuova libreria:");
        lblNewLabel.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel.setBounds(90, 11, 448, 35);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nome Libreria *");
        lblNewLabel_1.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setBounds(12, 114, 192, 19);
        contentPane.add(lblNewLabel_1);

        txtBookshelfName = new JTextField();
        txtBookshelfName.setFont(new Font("Cambria", Font.PLAIN, 12));
        // txtBookshelfName.setForeground(Color.BLACK);
        // txtBookshelfName.setBackground(Color.WHITE);
        txtBookshelfName.setBounds(214, 113, 246, 26);
        contentPane.add(txtBookshelfName);
        txtBookshelfName.setColumns(10);

        // Search book components
        JLabel lblSearchBook = new JLabel("Cerca libro:");
        lblSearchBook.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblSearchBook.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSearchBook.setBounds(10, 148, 192, 19);
        contentPane.add(lblSearchBook);

        txtSearchBook = new JTextField();
        txtSearchBook.setFont(new Font("Cambria", Font.PLAIN, 12));
        // txtSearchBook.setForeground(Color.BLACK);
        // txtSearchBook.setBackground(Color.WHITE);
        txtSearchBook.setBounds(214, 150, 139, 26);
        contentPane.add(txtSearchBook);

        // Add document listener for search functionality
        txtSearchBook.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterBooks();
            }

            private void filterBooks() {
                String searchText = txtSearchBook.getText().toLowerCase();
                filteredBooks.removeAllElements();
                
                for (int i = 0; i < books.getSize(); i++) {
                    Book book = books.getElementAt(i);
                    if (book.getTitle().toLowerCase().contains(searchText)) {
                        filteredBooks.addElement(book);
                    }
                }
                
                // Refresh combo box
                comboBox.setModel(filteredBooks);
            }
        });

        JLabel lblNewLabel_2 = new JLabel("Seleziona libro:");
        lblNewLabel_2.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_2.setBounds(10, 178, 192, 19);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel_4.setForeground(Color.RED);
        lblNewLabel_4.setBounds(118, 57, 342, 28);
        contentPane.add(lblNewLabel_4);

        DefaultListModel<Book> listModel = new DefaultListModel<>();
        JList<Book> list = new JList<>(listModel);
        list.setFont(new Font("Cambria", Font.PLAIN, 12));
        list.setBounds(40, 241, 155, 130);
        contentPane.add(list);

        // Initialize filtered books model
        filteredBooks = new DefaultComboBoxModel<>();
        for (int i = 0; i < books.getSize(); i++) {
            filteredBooks.addElement(books.getElementAt(i));
        }

        comboBox = new JComboBox<>(filteredBooks);
        comboBox.setFont(new Font("Cambria", Font.PLAIN, 12));
        comboBox.setMaximumRowCount(5);
        comboBox.setBounds(214, 176, 139, 22);
        contentPane.add(comboBox);
        itemListener = new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {}
        };
        comboBox.addItemListener(itemListener);

        JButton btnInsertBook = new JButton("INSERISCI");
        btnInsertBook.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Book chosenBook = (Book) comboBox.getSelectedItem();
                if (chosenBook != null) {
                    removeItem(chosenBook);
                    if(comboBox.getItemCount() == 0)
                        btnInsertBook.setEnabled(false);
                    
                    // Add book to list in alphabetical order
                    insertBookInOrder(chosenBook, listModel);
                }
            }

            private void removeItem(Book item) {
                comboBox.removeItemListener(itemListener);
                filteredBooks.removeElement(item);
                comboBox.removeItem(item);
                
                // Also remove from the main books model
                books.removeElement(item);
                comboBox.addItemListener(itemListener);
            }
            
            private void insertBookInOrder(Book book, DefaultListModel<Book> listModel) {
                // Add book to the bookshelfBooks list
                bookshelfBooks.add(book);
                
                // Sort the list
                Collections.sort(bookshelfBooks, Comparator.comparing(Book::getTitle));
                
                // Update the list model to reflect the sorted order
                listModel.clear();
                for (Book b : bookshelfBooks) {
                    listModel.addElement(b);
                }
            }
        });

        btnInsertBook.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnInsertBook.setForeground(colorLightYellow);
        // btnInsertBook.setBackground(colorBtnBrown);
        btnInsertBook.setBounds(363, 174, 97, 23);
        contentPane.add(btnInsertBook);

        JLabel lblNewLabel_3 = new JLabel("Anteprima libreria");
        lblNewLabel_3.setFont(new Font("Cambria", Font.PLAIN, 12));
        lblNewLabel_3.setBounds(40, 208, 134, 22);
        contentPane.add(lblNewLabel_3);

        JButton btnCreateBookshelf = new JButton("CREA LIBRERIA");
        btnCreateBookshelf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String bookshelfName = txtBookshelfName.getText();
                if(txtBookshelfName.getText().length() == 0) {
                    lblNewLabel_4.setText("*ATTENZIONE* Compilare tutti i campi obbligatori");
                } else {
                    try {
                        if(BookRecommender.searchBookshelf(bookshelfName, userid)){
                            lblNewLabel_4.setText("*ATTENZIONE* Una libreria con questo nome esiste già");
                        } else {
                            BookRecommender.appendBookshelf(bookshelfName, userid, bookshelfBooks);
                            setVisible(false);
                            new GUI_LoggedHomePage(userid);
                        }
                    } catch (java.rmi.RemoteException e1) {
                        lblNewLabel_4.setText("Errore durante la creazione della libreria: " + e1.getMessage());
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnCreateBookshelf.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnCreateBookshelf.setBackground(colorBtnBrown);
        // btnCreateBookshelf.setForeground(colorLightYellow);
        btnCreateBookshelf.setBounds(231, 385, 120, 23); // Adjusted position
        contentPane.add(btnCreateBookshelf);

        JButton btnBack = new JButton("INDIETRO");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new GUI_LoggedHomePage(userid);
            }
        });
        btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnBack.setBackground(colorBtnBrown);
        // btnBack.setForeground(colorLightYellow);
        btnBack.setBounds(12, 18, 68, 23);
        contentPane.add(btnBack);
    }
}
