package Vista;

import Controlador.CUsuarios;

import javax.swing.*;
import java.awt.*;

public class VUsuarios extends JFrame implements IVUsuarios {
    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin, btnRegistrar;
    JLabel lblUsername, lblPassword, lblError;
    public VUsuarios(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10,10));

        JPanel datos = new JPanel();
        txtUsername = new JTextField(8);
        txtPassword = new JPasswordField(8);
        lblUsername = new JLabel("Username");
        lblPassword = new JLabel("Password");

        datos.setLayout(new GridLayout(2,1));
        datos.add(lblUsername);
        datos.add(txtUsername, BorderLayout.NORTH);
        datos.add(lblPassword);
        datos.add(txtPassword, BorderLayout.SOUTH);

        panel.add(datos,BorderLayout.CENTER);

        JPanel botones = new JPanel();
        btnLogin = new JButton("Login");
        btnLogin.setActionCommand(LOGIN);
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand(REGISTRAR);
        botones.setLayout(new FlowLayout());
        botones.add(btnLogin);
        botones.add(btnRegistrar);
        panel.add(botones,BorderLayout.SOUTH);
        lblError = new JLabel("");
        panel.add(lblError,BorderLayout.NORTH);
        setContentPane(panel);

    }

    @Override
    public void avisarError(String mensaje) {
        lblError = new JLabel(mensaje);
    }

    @Override
    public void arrancar() {
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void setController(CUsuarios controller) {
        btnLogin.addActionListener(controller);
        btnRegistrar.addActionListener(controller);
    }

    @Override
    public String getUsername() {
        return txtUsername.getText();
    }

    @Override
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
}
