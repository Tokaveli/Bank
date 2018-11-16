package bank2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/**
 *
 * @author Account
 */
public class LogowanieInterface extends javax.swing.JFrame {

    String password, login, reply, choice;

    int numberOfAccounts = 0;
    double balance = 0;

    Socket socket;
    OutputStream output;
    InputStream input;

    public LogowanieInterface() {
        initComponents();
        setLocationRelativeTo(null);
        password = getPassword();
        login = getLogin();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLLogin = new javax.swing.JLabel();
        jLHaslo = new javax.swing.JLabel();
        tFLogin = new javax.swing.JTextField();
        bZaloguj = new javax.swing.JButton();
        bRejestracja = new javax.swing.JButton();
        bZakoncz = new javax.swing.JButton();
        jPHaslo = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logowanie");
        setPreferredSize(new java.awt.Dimension(600, 400));
        setResizable(false);

        jLLogin.setText("Login :");

        jLHaslo.setText("PIN :");

        bZaloguj.setText("Zaloguj");
        bZaloguj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZalogujActionPerformed(evt);
            }
        });

        bRejestracja.setText("Rejestracja");
        bRejestracja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRejestracjaActionPerformed(evt);
            }
        });

        bZakoncz.setText("Zakoncz");
        bZakoncz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZakonczActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bZakoncz)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(188, 188, 188)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLHaslo)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLLogin)
                        .addGap(20, 20, 20)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPHaslo)
                    .addComponent(bRejestracja, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                    .addComponent(bZaloguj, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tFLogin))
                .addContainerGap(281, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLLogin)
                    .addComponent(tFLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLHaslo)
                    .addComponent(jPHaslo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bZaloguj, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bRejestracja, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addComponent(bZakoncz)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bRejestracjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRejestracjaActionPerformed

        RejestracjaInterface dialog = new RejestracjaInterface(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
    }//GEN-LAST:event_bRejestracjaActionPerformed

    private void bZakonczActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZakonczActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_bZakonczActionPerformed

    private void bZalogujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZalogujActionPerformed

        logIn();

    }

    public String getLogin() {
        return tFLogin.getText();
    }

    public String getPassword() {
        return jPHaslo.getText();
    }

    public void cleanDataLogIn() {
        tFLogin.setText("");
        jPHaslo.setText("");
    }

    public void logIn() {
        try {
            socket = new Socket("localhost", 7777);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException ex) {
            System.err.println(ex + "main 1");
        }

        password = getPassword();
        login = getLogin();
        boolean allGood = true;

        if (login.contains(" ") || login.contains("|") || tFLogin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole login nie może być puste\nLogin nie moze zawierac spacji\nLogin nie moze zawieracz naku '|'", "Login Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanDataLogIn();
        }

        if (password.contains(" ") || password.contains("|") || jPHaslo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole hasło nie może być puste\nHasło nie moze zawierac spacji\nHasło nie moze zawieracz znaku '|'", "PIN Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanDataLogIn();
        }

        if (allGood == true) {
            sendChoice();
            sendDataOnServer();
        }
    }

    public void sendChoice() {
        choice = "b";
        System.out.println("Logowanie...");
        try {
            output.write(choice.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void sendDataOnServer() {

        char x[] = password.toCharArray();

        for (int i = 0; i != x.length; i++) {
            int n = x[i];
            n += 3;
            x[i] = (char) n;
            password = String.valueOf(x);
        }

        System.out.println("Wysylam dane na serwer.");
        try {

            output.write(login.getBytes());
            output.write(" ".getBytes());
            output.write(password.getBytes());
            output.write("\r\n".getBytes());

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        receiveDataFromServer();
    }

    public void receiveDataFromServer() {
        System.out.println("Odbieram odpowiedz z serwera");
        try {
            int k = 0;
            StringBuffer sbReply = new StringBuffer();

            k = input.read();
            sbReply.append((char) k);

            reply = sbReply.toString().trim();

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        if (reply.equals("1")) {

            this.dispose();

            exit();
            KontoInterface k = new KontoInterface();
            k.perform();

        } else if (reply.equals("0")) {
            JOptionPane.showMessageDialog(null, "Podane konto nie istnieje lub wprowadzone hasło jest nieprawidłowe.", "LogIN Error", JOptionPane.INFORMATION_MESSAGE);
            cleanDataLogIn();
            exit();

        }
    }

    public void exit() {
        try {
            input.close();
            output.close();
            socket.close();
            System.out.println("Klient opuścił serwer.");
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }//GEN-LAST:event_bZalogujActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bRejestracja;
    private javax.swing.JButton bZakoncz;
    private javax.swing.JButton bZaloguj;
    private javax.swing.JLabel jLHaslo;
    private javax.swing.JLabel jLLogin;
    private javax.swing.JPasswordField jPHaslo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField tFLogin;
    // End of variables declaration//GEN-END:variables
}
