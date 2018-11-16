package bank2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class WyplataInterface extends javax.swing.JDialog {

    String balanceString, choice, withdrawAmountString, reply, login;
    double balance, withdrawAmount;

    Socket socket;
    OutputStream output;
    InputStream input;

    public WyplataInterface() {

        initComponents();
        setLocationRelativeTo(null);
    }

    public void perform() {

        try {
            socket = new Socket("localhost", 7777);
            output = socket.getOutputStream();
            input = socket.getInputStream();
        } catch (IOException ex) {
            System.err.println(ex + "main 1");
        }
        setVisible(true);
        sendChoice();
        balanceInformation();
    }

    public void withdraw() {
        setWithdrawAmount();
        validityOfData();

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
        choice = "f";
        System.out.println("Wypłata ...");
        try {
            output.write(choice.getBytes());
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
                withdrawAmount = Double.parseDouble(withdrawAmountString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Kwota musi by wyrazona liczba!", "Amount Error", JOptionPane.ERROR_MESSAGE);
                allGood = false;
                cleanData();
            }
        }
        if (withdrawAmount > balance) {
            JOptionPane.showMessageDialog(null, "Nie masz tyle pieniedzy!", "Amount Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }
        if (withdrawAmount <= 0) {
            JOptionPane.showMessageDialog(null, "Kwota musi byc wieksza od zera!", "Amount Error", JOptionPane.ERROR_MESSAGE);
            allGood = false;
            cleanData();
        }

        if (allGood == true) {
            sendChoice2();
            sendData();
        }
    }

    public void sendData() {

        System.out.println("Wysylam Dane Wplaty Na Serwer...");
        try {

            output.write(withdrawAmountString.getBytes());
            output.write(" ".getBytes());
            output.write(login.getBytes());
            output.write("\r\n".getBytes());

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        receiveData();
    }

    public void receiveData() {
        System.out.println("Odbieram Odpowiedz Z Serwera O Wplacie...");
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
            JOptionPane.showMessageDialog(null, "Wypłata została wykonana pomyślnie", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            KontoInterface k = new KontoInterface();
            exit();
            k.perform2();
            dispose();

        } else if (reply.equals("0")) {
            JOptionPane.showMessageDialog(null, "Cos poszlo nie tak!", "Withdraw Error", JOptionPane.ERROR_MESSAGE);
            KontoInterface k = new KontoInterface();
            exit();
            k.perform2();
            dispose();
        }
    }

    public void balanceInformation() {
        try {
            int k = 0;
            StringBuffer sbStanKonta = new StringBuffer();
            StringBuffer sbLogin = new StringBuffer();

            boolean a = false;
            while ((k = input.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbStanKonta.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true && k != ' ') {
                    sbLogin.append((char) k);
                }
            }

            balanceString = sbStanKonta.toString().trim();
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

    public void cleanData() {

        tFKwota.setText("");
    }

    public String getWithdrawAmount() {
        return tFKwota.getText();
    }

    public void setWithdrawAmount() {

        withdrawAmountString = getWithdrawAmount();
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

        jLKwota = new javax.swing.JLabel();
        tFKwota = new javax.swing.JTextField();
        bWyplata = new javax.swing.JButton();
        bAnuluj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wypłata");
        getContentPane().setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLKwota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLKwota.setText("Kwota :");
        getContentPane().add(jLKwota);

        tFKwota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(tFKwota);

        bWyplata.setText("Wypłata");
        bWyplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWyplataActionPerformed(evt);
            }
        });
        getContentPane().add(bWyplata);

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
        KontoInterface k = new KontoInterface();
        this.dispose();
        exit();
        k.perform2();
    }//GEN-LAST:event_bAnulujActionPerformed

    private void bWyplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWyplataActionPerformed
        withdraw();
    }//GEN-LAST:event_bWyplataActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnuluj;
    private javax.swing.JButton bWyplata;
    private javax.swing.JLabel jLKwota;
    private javax.swing.JTextField tFKwota;
    // End of variables declaration//GEN-END:variables
}
