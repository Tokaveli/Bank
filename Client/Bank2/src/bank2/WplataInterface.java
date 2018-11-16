package bank2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class WplataInterface extends javax.swing.JDialog {

    String balanceString, choice, depositAmountString, reply, login;
    double balance, depositAmount;

    Socket socket;
    OutputStream output;
    InputStream input;

    public WplataInterface() {

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

    public void deposit() {
        setAmountDeposit();
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

        choice = "e";
        System.out.println("Wplata ...");
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
                depositAmount = Double.parseDouble(depositAmountString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Kwota musi by wyrazona liczba!", "Amount Error", JOptionPane.ERROR_MESSAGE);
                allGood = false;
                cleanData();
            }
        }
        if (depositAmount <= 0) {
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

            output.write(depositAmountString.getBytes());
            output.write(" ".getBytes());
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
            JOptionPane.showMessageDialog(null, "Wplata Zakonczona Sukcesem!", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
            KontoInterface k = new KontoInterface();
            exit();
            k.perform2();
            dispose();

        } else if (reply.equals("0")) {
            JOptionPane.showMessageDialog(null, "Cos poszlo nie tak!", "Deposit Error", JOptionPane.ERROR_MESSAGE);
            KontoInterface k = new KontoInterface();
            exit();
            k.perform2();
            dispose();
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

    public void cleanData() {

        tFKwota.setText("");
    }

    public String getAmountDeposit() {
        return tFKwota.getText();
    }

    public void setAmountDeposit() {

        depositAmountString = getAmountDeposit();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLKwota = new javax.swing.JLabel();
        tFKwota = new javax.swing.JTextField();
        bWplata = new javax.swing.JButton();
        bAnuluj = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wpłata");
        getContentPane().setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        jLKwota.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLKwota.setText("Kwota :");
        getContentPane().add(jLKwota);

        tFKwota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(tFKwota);

        bWplata.setText("Wpłata");
        bWplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWplataActionPerformed(evt);
            }
        });
        getContentPane().add(bWplata);

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

    private void bWplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWplataActionPerformed
        deposit();
    }//GEN-LAST:event_bWplataActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAnuluj;
    private javax.swing.JButton bWplata;
    private javax.swing.JLabel jLKwota;
    private javax.swing.JTextField tFKwota;
    // End of variables declaration//GEN-END:variables
}
