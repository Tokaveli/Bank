/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class PrzelewInterface extends javax.swing.JDialog {

    int numberOfAccounts;
    double balance, transferAmount;
    String transferAmountString, transferAccount, choice, balanceString, reply, login;

    Socket socket;
    OutputStream output;
    InputStream input;

    public PrzelewInterface() {

        setLocationRelativeTo(null);
        initComponents();

        try {
            socket = new Socket("localhost", 7777);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public void przelew() {
        setTransferData();
        validityOfData();

    }

    public void perform() {

        setVisible(true);
        sendChoice();
        balanceInformation();
    }

    public void sendChoice() {
        choice = "c";
        System.out.println("Pobieram Stan Konta ...");
        try {
            output.write(choice.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void sendChoice2() {
        choice = "d";
        System.out.println("Przelew ...");
        try {
            output.write(choice.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void balanceInformation() {
        try {
            int k = 0;
            StringBuffer sbBalance = new StringBuffer();
            StringBuffer sbLogin = new StringBuffer();

            boolean a = false;
            while ((k = input.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbBalance.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true && k != ' ') {
                    sbLogin.append((char) k);
                }
            }

            balanceString = sbBalance.toString().trim();
            login = sbLogin.toString().trim();

            System.out.println("Stan Konta to: " + balanceString);
            System.out.println("Login to: " + login);

            balance = Double.parseDouble(balanceString);

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    public void validityOfData() {
        boolean allGood = true;
        if (tFKwota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Musisz wpisac kwote do przelewu!", "Amount Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        } else {
            try {
                transferAmount = Double.parseDouble(transferAmountString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Kwota musi by wyrazona liczba!", "Amount Error", JOptionPane.ERROR_MESSAGE);
                allGood = false;
                cleanData();
            }
        }
        if (transferAmount <= 0) {
            JOptionPane.showMessageDialog(null, "Kwota musi byc wieksza od zera!", "Amount Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;

        }

        if (transferAmount > balance) {
            JOptionPane.showMessageDialog(null, "Nie masz tyle pieniedzy!", "Amount Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }

        if (transferAccount.equals(login)) {
            JOptionPane.showMessageDialog(null, "Nie mozesz zrobic przelewu do samego siebie!", "Account Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }

        if (allGood == true) {
            sendChoice2();
            sendDataTransfer();
        }
    }

    public void sendDataTransfer() {

        System.out.println("Wysylam Dane Przelewu Na Serwer Przelewow...");
        try {

            output.write(transferAmountString.getBytes());
            output.write(" ".getBytes());
            output.write(transferAccount.getBytes());
            output.write("|".getBytes());
            output.write(login.getBytes());
            output.write("\r\n".getBytes());

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        getData();
    }

    public void getData() {
        System.out.println("Odbieram Odpowiedz Z Serwera Przelewow...");
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
            JOptionPane.showMessageDialog(null, "Przelew Zakonczony Sukcesem!", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            KontoInterface k = new KontoInterface();
            exit();
            k.perform2();
            dispose();

        } else if (reply.equals("0")) {
            JOptionPane.showMessageDialog(null, "Prosimy o podanie poprawnego konta!", "Transfer Error", JOptionPane.ERROR_MESSAGE);
            cleanData();
        }
    }

    public void cleanData() {
        tFLogin.setText("");
        tFKwota.setText("");
    }

    public String getTransferAccount() {
        return tFLogin.getText();
    }

    public String getTransferAmount() {
        return tFKwota.getText();
    }

    public void setTransferData() {
        transferAccount = getTransferAccount();
        transferAmountString = getTransferAmount();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLLogin = new javax.swing.JLabel();
        tFLogin = new javax.swing.JTextField();
        jLKwota = new javax.swing.JLabel();
        tFKwota = new javax.swing.JTextField();
        bPrzelew = new javax.swing.JButton();
        bAnuluj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Przelew");
        getContentPane().setLayout(new java.awt.GridLayout(3, 2, 5, 5));

        jLLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLLogin.setText("Login Użytkownika :");
        getContentPane().add(jLLogin);

        tFLogin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        getContentPane().add(tFLogin);

        jLKwota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLKwota.setText("Kwota :");
        getContentPane().add(jLKwota);

        tFKwota.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        getContentPane().add(tFKwota);

        bPrzelew.setText("Wykonaj Przelew");
        bPrzelew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzelewActionPerformed(evt);
            }
        });
        getContentPane().add(bPrzelew);

        bAnuluj.setText("Anuluj");
        bAnuluj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAnulujActionPerformed(evt);
            }
        });
        getContentPane().add(bAnuluj);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bAnulujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAnulujActionPerformed

        exit();
        dispose();
        KontoInterface k = new KontoInterface();
        k.perform2();

    }//GEN-LAST:event_bAnulujActionPerformed

    private void bPrzelewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzelewActionPerformed
        przelew();
    }//GEN-LAST:event_bPrzelewActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnuluj;
    private javax.swing.JButton bPrzelew;
    private javax.swing.JLabel jLKwota;
    private javax.swing.JLabel jLLogin;
    private javax.swing.JTextField tFKwota;
    private javax.swing.JTextField tFLogin;
    // End of variables declaration//GEN-END:variables
}
