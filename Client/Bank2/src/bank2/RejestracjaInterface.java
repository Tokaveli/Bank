package bank2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class RejestracjaInterface extends javax.swing.JDialog {

    String password, login, reply, fName, lName;
    String fileName = "Dane.txt";
    int pass;

    Socket socket;
    OutputStream output;
    InputStream input;
    String choice;
    LogowanieInterface l = new LogowanieInterface();

    public RejestracjaInterface(java.awt.Frame parent, boolean modal) {

        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLLogin = new javax.swing.JLabel();
        tFLogin = new javax.swing.JTextField();
        jLHaslo = new javax.swing.JLabel();
        tFHaslo = new javax.swing.JTextField();
        jLImie = new javax.swing.JLabel();
        tFImie = new javax.swing.JTextField();
        jLNazwisko = new javax.swing.JLabel();
        tFNazwisko = new javax.swing.JTextField();
        bDodajKonto = new javax.swing.JButton();
        Powrot = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rejestracja");
        getContentPane().setLayout(new java.awt.GridLayout(5, 2, 5, 5));

        jLLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLogin.setText("Login :");
        getContentPane().add(jLLogin);
        getContentPane().add(tFLogin);

        jLHaslo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLHaslo.setText("PIN :");
        getContentPane().add(jLHaslo);
        getContentPane().add(tFHaslo);

        jLImie.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLImie.setText("Imie :");
        getContentPane().add(jLImie);

        tFImie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tFImieActionPerformed(evt);
            }
        });
        getContentPane().add(tFImie);

        jLNazwisko.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLNazwisko.setText("Nazwisko :");
        getContentPane().add(jLNazwisko);
        getContentPane().add(tFNazwisko);

        bDodajKonto.setText("Dodaj Konto");
        bDodajKonto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDodajKontoActionPerformed(evt);
            }
        });
        getContentPane().add(bDodajKonto);

        Powrot.setText("Powrot");
        Powrot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PowrotActionPerformed(evt);
            }
        });
        getContentPane().add(Powrot);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PowrotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PowrotActionPerformed

        
        this.dispose();


    }//GEN-LAST:event_PowrotActionPerformed

    private void bDodajKontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDodajKontoActionPerformed

        addAccount();


    }//GEN-LAST:event_bDodajKontoActionPerformed

    private void tFImieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tFImieActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tFImieActionPerformed

    public void addAccount() {

        try {
            socket = new Socket("localhost", 7777);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException ex) {
            System.err.println(ex + "main 1");
        }

        password = getPIN();
        login = getLogin();
        fName = getFName();
        lName = getLName();

        boolean allGood = true;

        if (login.contains(" ") || login.contains("|") || tFLogin.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole login nie może być puste\nLogin nie moze zawierac spacji\nLogin nie moze zawieracz naku '|'", "Login Error", JOptionPane.ERROR_MESSAGE);

            allGood = false;
            cleanData();
        } else if (password.contains(" ") || password.contains("|") || tFHaslo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole PIN nie może być puste\nPIN nie moze zawierac spacji\nPIN nie moze zawierac znaku '|'", "PIN Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        } else {
            try {
                int pass = Integer.parseInt(password);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "PIN musi byc liczba", "PIN Error", JOptionPane.ERROR_MESSAGE);
                allGood = false;
                cleanData();
            }
        }

        if (fName.contains(" ") || fName.contains("|") || tFImie.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole imie nie może być puste\nImie nie moze zawierac spacji", "First name Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }
        if (lName.contains(" ") || lName.contains("|") || tFNazwisko.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Pole nazwisko nie może być puste\nNazwisko nie moze zawierac spacji", "Last Name Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }
        if (password.length() < 4 || password.length() > 8) {
            JOptionPane.showMessageDialog(null, "PIN musi składać się od 4 do 8 cyfr", "PIN error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }

        if (allGood == true) {

            sendChoice();
            sendDataOnServer();
        }
    }

    public void sendChoice() {
        choice = "a";
        System.out.println("Rejestracja...");
        try {
            output.write(choice.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void sendDataOnServer() {
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
        receiveReplyFromServer();
    }

    public void receiveReplyFromServer() {
        System.out.println("Odbieram odpowiedz z serwera");
        try {
            int k = 0;
            StringBuffer sbReply = new StringBuffer();

            k = input.read();
            sbReply.append((char) k);

            reply = sbReply.toString().trim();
            System.out.print("0 - konto nie istnieje\n1 - konto już istnieje\n");                       //
            System.out.println("Odpowiedz : " + reply);

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        if (reply.equals("1")) {
            JOptionPane.showMessageDialog(null, "Konto o podanym loginie juz istnieje!", "Blad", JOptionPane.ERROR_MESSAGE);
            cleanData();
            exit();

        } else if (reply.equals("0")) {
            JOptionPane.showMessageDialog(null, "Konto zarejestrowane pomyslnie!", "Gratulacje", JOptionPane.INFORMATION_MESSAGE);
            saveData();
            cleanData();
            dispose();
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
    }

    public void saveData() {

        System.out.println("Zapisuje dane konta do pliku.");
        setFName();
        setLName();

        login = getLogin();
        try {
            FileWriter fileWriter = new FileWriter(login);
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

           
            strb.append(fName);
            strb.append("|");

            strb.append(lName);
            strb.append("\n");

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public String getLogin() {
        return tFLogin.getText();
    }

    public String getPIN() {
        return tFHaslo.getText();
    }

    public String getFName() {
        return tFImie.getText();
    }

    public String getLName() {
        return tFNazwisko.getText();
    }

    public void setFName() {
        fName = tFImie.getText();
    }

    public void setLName() {
        lName = tFNazwisko.getText();
    }

    public void cleanData() {
        tFLogin.setText("");
        tFHaslo.setText("");
        tFImie.setText("");
        tFNazwisko.setText("");

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Powrot;
    private javax.swing.JButton bDodajKonto;
    private javax.swing.JLabel jLHaslo;
    private javax.swing.JLabel jLImie;
    private javax.swing.JLabel jLLogin;
    private javax.swing.JLabel jLNazwisko;
    private javax.swing.JTextField tFHaslo;
    private javax.swing.JTextField tFImie;
    private javax.swing.JTextField tFLogin;
    private javax.swing.JTextField tFNazwisko;
    // End of variables declaration//GEN-END:variables
}
