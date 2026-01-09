package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This class manages the graphical user interface for the application's home screen.
 * It provides options for user login, registration, and public book search.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class GUI_Home extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed custom colors to use FlatLaf defaults
    private JTextField txtUserId;
    private JPasswordField txtPsw;
    private JLabel lblWarning;

    /**
     * Constructs a new GUI_Home frame.
     * Initializes the UI components for the home screen, including login, registration, and public search options.
     * 
     * @throws IOException if an I/O error occurs.
     */
    public GUI_Home() throws IOException {
        // Using consistent color scheme instead of Nimbus L&F

        setTitle("Book Recommender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new GridBagLayout());
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // titlePanel.setBackground(colorLightYellow);
        JLabel lblTitle = new JLabel("Book Recommender");
        lblTitle.putClientProperty("FlatLaf.styleClass", "h1"); // Check if this is valid or just rely on font
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 36f)); // Keep size but use default font family
        titlePanel.add(lblTitle);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPane.add(titlePanel, gbc);

        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        // loginPanel.setBackground(colorLightYellow);
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.insets = new Insets(5, 5, 5, 5);
        loginGbc.fill = GridBagConstraints.HORIZONTAL;

        loginGbc.gridx = 0;
        loginGbc.gridy = 0;
        loginPanel.add(new JLabel("Username:"), loginGbc);

        txtUserId = new JTextField(20);
        loginGbc.gridx = 1;
        loginGbc.gridy = 0;
        loginPanel.add(txtUserId, loginGbc);

        loginGbc.gridx = 0;
        loginGbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), loginGbc);

        txtPsw = new JPasswordField(20);
        loginGbc.gridx = 1;
        loginGbc.gridy = 1;
        loginPanel.add(txtPsw, loginGbc);

        JButton btnLogin = new JButton("Login");
        // btnLogin.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnLogin.setForeground(Color.BLACK);
        // btnLogin.setBackground(colorBtnBrown);
        loginGbc.gridx = 0;
        loginGbc.gridy = 2;
        loginPanel.add(btnLogin, loginGbc);

        JButton btnRegister = new JButton("Registrati");
        // btnRegister.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnRegister.setForeground(Color.BLACK);
        // btnRegister.setBackground(colorBtnBrown);
        loginGbc.gridx = 1;
        loginGbc.gridy = 2;
        loginPanel.add(btnRegister, loginGbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        contentPane.add(loginPanel, gbc);

        // Search Panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        // searchPanel.setBackground(colorLightYellow);
        searchPanel.setBorder(BorderFactory.createTitledBorder("Ricerca Pubblica"));

        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(5, 5, 5, 5);
        searchGbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnSearchTitle = new JButton("Cerca per Titolo");
        // btnSearchTitle.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnSearchTitle.setForeground(Color.BLACK);
        // btnSearchTitle.setBackground(colorBtnBrown);
        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchPanel.add(btnSearchTitle, searchGbc);

        JButton btnSearchAuthor = new JButton("Cerca per Autore");
        // btnSearchAuthor.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnSearchAuthor.setForeground(Color.BLACK);
        // btnSearchAuthor.setBackground(colorBtnBrown);
        searchGbc.gridx = 1;
        searchGbc.gridy = 0;
        searchPanel.add(btnSearchAuthor, searchGbc);

        JButton btnSearchAuthorYear = new JButton("Cerca per Autore e Anno");
        // btnSearchAuthorYear.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnSearchAuthorYear.setForeground(Color.BLACK);
        // btnSearchAuthorYear.setBackground(colorBtnBrown);
        searchGbc.gridx = 0;
        searchGbc.gridy = 1;
        searchGbc.gridwidth = 2;
        searchPanel.add(btnSearchAuthorYear, searchGbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPane.add(searchPanel, gbc);

        // Warning Label
        lblWarning = new JLabel("Username o password non validi.");
        lblWarning.setForeground(Color.RED);
        lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
        lblWarning.setVisible(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        contentPane.add(lblWarning, gbc);

        // Action Listeners
        /**
         * Handles the login action when the login button is pressed.
         * Authenticates the user and navigates to the logged-in home page on success.
         */
        btnLogin.addActionListener(e -> {
            String userid = txtUserId.getText();
            String password = new String(txtPsw.getPassword());
            try {
                if (BookRecommender.login(userid, password)) {
                    dispose();
                    new GUI_LoggedHomePage(userid);
                } else {
                    lblWarning.setVisible(true);
                }
            } catch (java.rmi.RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il login: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        /**
         * Handles the registration action when the register button is pressed.
         * Disposes the current frame and opens the new user registration frame.
         */
        btnRegister.addActionListener(e -> {
            dispose();
            new GUI_NewUser();
        });

        /**
         * Handles the search by title action when the search by title button is pressed.
         * Opens the public search frame for title search.
         */
        btnSearchTitle.addActionListener(e -> {
            new GUI_PublicSearch("Titolo");
        });
        /**
         * Handles the search by author action when the search by author button is pressed.
         * Opens the public search frame for author search.
         */
        btnSearchAuthor.addActionListener(e -> {
            new GUI_PublicSearch("Autore");
        });
        /**
         * Handles the search by author and year action when the search by author and year button is pressed.
         * Opens the public search frame for author and year search.
         */
        btnSearchAuthorYear.addActionListener(e -> {
            new GUI_PublicSearch("AutoreAnno");
        });

        setVisible(true);
    }
}
