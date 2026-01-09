package bookrecommender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException; // Added import for SQLException

/**
 * This class manages the graphical user interface for new user registration.
 * It allows users to create a new account by providing their personal details and login credentials.
 * 
 * @version 1.0.0 (30-06-2025)
 * @author Matteo Mantica (Mat. 758070, VA), Leonardo Lambruschi (Mat. 753579, VA)
 */
public class GUI_NewUser extends JFrame {

    private static final long serialVersionUID = 1L;
    // Removed custom colors
    private JTextField txtName, txtSurname, txtFiscalCode, txtAddress, txtMail, txtUserid;
    private JPasswordField pswPassword, pswRepeatPassword;
    private JLabel lblInfo;

    /**
     * Constructs a new GUI_NewUser frame.
     * Initializes the UI components for user registration.
     */
    public GUI_NewUser() {
        // Using consistent color scheme

        setTitle("Book Recommender - Registrazione");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new GridBagLayout());
        // contentPane.setBackground(colorLightYellow);
        contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel lblTitle = new JLabel("Crea Nuovo Account");
        lblTitle.setFont(new Font("Cambria", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(lblTitle, gbc);

        // Info Label
        lblInfo = new JLabel(" ");
        lblInfo.setForeground(Color.RED);
        gbc.gridy = 1;
        contentPane.add(lblInfo, gbc);

        // Form Fields
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 2;
        contentPane.add(new JLabel("Nome:"), gbc);
        txtName = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPane.add(new JLabel("Cognome:"), gbc);
        txtSurname = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtSurname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        contentPane.add(new JLabel("Codice Fiscale:"), gbc);
        txtFiscalCode = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtFiscalCode, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        contentPane.add(new JLabel("Indirizzo:"), gbc);
        txtAddress = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtAddress, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        contentPane.add(new JLabel("Email:"), gbc);
        txtMail = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtMail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        contentPane.add(new JLabel("Username:"), gbc);
        txtUserid = new JTextField(20);
        gbc.gridx = 1;
        contentPane.add(txtUserid, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        contentPane.add(new JLabel("Password:"), gbc);
        pswPassword = new JPasswordField(20);
        gbc.gridx = 1;
        contentPane.add(pswPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        contentPane.add(new JLabel("Ripeti Password:"), gbc);
        pswRepeatPassword = new JPasswordField(20);
        gbc.gridx = 1;
        contentPane.add(pswRepeatPassword, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // buttonPanel.setBackground(colorLightYellow);
        
        
        JButton btnRegister = new JButton("Registrati");
        // btnRegister.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnRegister.setForeground(Color.BLACK);
        // btnRegister.setBackground(colorBtnBrown);
        
        JButton btnBack = new JButton("Indietro");
        // btnBack.setFont(new Font("Cambria", Font.BOLD, 12));
        // btnBack.setForeground(Color.BLACK);
        // btnBack.setBackground(colorBtnBrown);
        
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnBack);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(buttonPanel, gbc);

        // Action Listeners
        btnRegister.addActionListener(e -> {
            try {
                registerUser();
            } catch (java.rmi.RemoteException ex) {
                JOptionPane.showMessageDialog(this, "Errore durante la registrazione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        btnBack.addActionListener(e -> {
            dispose();
            try {
                new GUI_Home();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Errore nel tornare alla home: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }

    /**
     * Handles the user registration process.
     * Validates input, checks for existing user ID, and creates a new user via the remote service.
     * 
     * @throws java.rmi.RemoteException if a remote communication error occurs.
     */
    private void registerUser() throws java.rmi.RemoteException {
        if (!validateInput()) {
            return;
        }

        String userid = txtUserid.getText();
        if (BookRecommender.useridExist(userid)) {
            lblInfo.setText("Username già esistente. Scegline un altro.");
            return;
        }

        try {
            BookRecommender.createUser(
                txtName.getText(),
                txtSurname.getText(),
                txtFiscalCode.getText(),
                txtAddress.getText(),
                txtMail.getText(),
                userid,
                new String(pswPassword.getPassword())
            );
            JOptionPane.showMessageDialog(this, "Registrazione completata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new GUI_Home();
        } catch (IOException ex) {
            lblInfo.setText("Errore durante la registrazione: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Validates the user input fields for registration.
     * Checks for empty fields and password matching.
     * 
     * @return true if all inputs are valid, false otherwise.
     */
    private boolean validateInput() {
        if (txtName.getText().isEmpty() || txtSurname.getText().isEmpty() || txtFiscalCode.getText().isEmpty()
            || txtAddress.getText().isEmpty() || txtMail.getText().isEmpty() || txtUserid.getText().isEmpty()
            || pswPassword.getPassword().length == 0 || pswRepeatPassword.getPassword().length == 0) {
            lblInfo.setText("Tutti i campi sono obbligatori.");
            return false;
        }

        // Email format validation
        String email = txtMail.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            lblInfo.setText("Formato email non valido. Esempio: utente@esempio.com");
            return false;
        }

        // Italian Codice Fiscale validation (16 alphanumeric characters)
        String codiceFiscale = txtFiscalCode.getText().trim().toUpperCase();
        // Pattern: 6 letters + 2 digits + 1 letter + 2 digits + 1 letter + 3 digits + 1 letter
        String cfRegex = "^[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]$";
        if (!codiceFiscale.matches(cfRegex)) {
            lblInfo.setText("Formato Codice Fiscale non valido. Deve essere di 16 caratteri (es. RSSMRA85M01H501Z)");
            return false;
        }

        if (!new String(pswPassword.getPassword()).equals(new String(pswRepeatPassword.getPassword()))) {
            lblInfo.setText("Le password non coincidono.");
            return false;
        }

        lblInfo.setText(" ");
        return true;
    }
}