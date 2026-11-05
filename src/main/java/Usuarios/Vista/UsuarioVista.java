package Usuarios.Vista;

import Usuarios.Controlador.UsuarioControlador;

import javax.swing.*;
import java.awt.*;

public class UsuarioVista extends JFrame implements IUsuarios {
    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin, btnRegistrar;
    JLabel lblUsername, lblPassword, lblError;

    public UsuarioVista() {
        super("Usuarios");
        JPanel panel = new JPanel(new BorderLayout(12, 16));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 20, 16, 20));

        JPanel datos = new JPanel(new GridLayout(2, 2, 10, 12));
        lblUsername = new JLabel("Username");
        txtUsername = new JTextField(14);
        lblPassword = new JLabel("Password");
        txtPassword = new JPasswordField(14);
        lblUsername.setLabelFor(txtUsername);
        lblPassword.setLabelFor(txtPassword);

        datos.add(lblUsername);
        datos.add(txtUsername);
        datos.add(lblPassword);
        datos.add(txtPassword);

        panel.add(datos, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btnLogin = new JButton("Login");
        btnLogin.setActionCommand(LOGIN);
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setActionCommand(REGISTRAR);
        btnLogin.setFocusPainted(false);
        btnRegistrar.setFocusPainted(false);
        botones.add(btnLogin);
        botones.add(btnRegistrar);
        panel.add(botones, BorderLayout.SOUTH);

        lblError = new JLabel(" ");
        lblError.setForeground(Color.RED);
        panel.add(lblError, BorderLayout.NORTH);
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void setMensaje(String mensaje) {
        if (lblError != null) {
            lblError.setText(mensaje);
        }
    }

    @Override
    public void arrancar() {
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void setController(UsuarioControlador controller) {
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
