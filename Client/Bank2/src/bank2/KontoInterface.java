package bank2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class KontoInterface extends javax.swing.JFrame {

    int numberOfAccounts;
    double balance;
    String balanceString, loginString, login, password, lName, fName;
    String[] loginsTable;
    String[] fNameTable;
    String[] lNameTable;

    Socket socket;
    OutputStream output;
    InputStream input;
    String choice;

    public KontoInterface() {

        initComponents();
        setLocationRelativeTo(null);

        loginsTable = new String[100];
        fNameTable = new String[100];
        lNameTable = new String[100];

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        bPrzelew = new javax.swing.JButton();
        bWplata = new javax.swing.JButton();
        bWyplata = new javax.swing.JButton();
        bWyloguj = new javax.swing.JButton();
        bStanKonta = new javax.swing.JButton();
        jLLogin = new javax.swing.JLabel();
        jLWitaj2 = new javax.swing.JLabel();
        bHistoria = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank");
        setPreferredSize(new java.awt.Dimension(600, 400));
        setResizable(false);

        bPrzelew.setText("Przelew");
        bPrzelew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPrzelewActionPerformed(evt);
            }
        });

        bWplata.setText("Wpłata");
        bWplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWplataActionPerformed(evt);
            }
        });

        bWyplata.setText("Wypłata");
        bWyplata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWyplataActionPerformed(evt);
            }
        });

        bWyloguj.setText("Wyloguj");
        bWyloguj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWylogujActionPerformed(evt);
            }
        });

        bStanKonta.setText("Stan Konta");
        bStanKonta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStanKontaActionPerformed(evt);
            }
        });

        jLLogin.setText("Zalogowano jako :");

        bHistoria.setText("Historia");
        bHistoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHistoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bWyloguj))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(bStanKonta, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(bPrzelew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bWplata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bWyplata, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(bHistoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLLogin)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLWitaj2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(373, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLLogin)
                    .addComponent(jLWitaj2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(bStanKonta)
                .addGap(18, 18, 18)
                .addComponent(bPrzelew)
                .addGap(18, 18, 18)
                .addComponent(bWplata)
                .addGap(18, 18, 18)
                .addComponent(bWyplata)
                .addGap(18, 18, 18)
                .addComponent(bHistoria)
                .addGap(75, 75, 75)
                .addComponent(bWyloguj))
        );

        fileMenu.setText("Zakoncz");

        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bank2/Exit.png"))); // NOI18N
        exitMenuItem.setText("Wyjdz");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        setJMenuBar(jMenuBar1);

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

    private void bWylogujActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWylogujActionPerformed

        exit();
        dispose();
        LogowanieInterface l = new LogowanieInterface();
        l.setVisible(true);

    }//GEN-LAST:event_bWylogujActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void bStanKontaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStanKontaActionPerformed

        JOptionPane.showMessageDialog(null, "Twoje aktualne środki wynoszą " + balance + " PLN", "Stan konta", JOptionPane.INFORMATION_MESSAGE);


    }//GEN-LAST:event_bStanKontaActionPerformed

    private void bPrzelewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPrzelewActionPerformed
        exit();
        dispose();
        PrzelewInterface p = new PrzelewInterface();
        p.perform();

    }//GEN-LAST:event_bPrzelewActionPerformed

    private void bWplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWplataActionPerformed
        exit();
        dispose();
        WplataInterface wp = new WplataInterface();
        wp.perform();
    }//GEN-LAST:event_bWplataActionPerformed

    private void bWyplataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWyplataActionPerformed
        exit();
        dispose();
        WyplataInterface wy = new WyplataInterface();
        wy.perform();
    }//GEN-LAST:event_bWyplataActionPerformed

    private void bHistoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHistoriaActionPerformed
        exit();
        dispose();
        Historia h = new Historia();
        h.perform();
    }//GEN-LAST:event_bHistoriaActionPerformed
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
        jLWitaj2.setText(login);
        loadNamesToTables();
    }

    public void perform2() {

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
        jLWitaj2.setText(login);
    }

    public void sendChoice() {
        choice = "c";
        System.out.println("Pobieram Stan Konta...");
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

    public void loadNamesToTables() {

        try {
            FileReader fileReader = new FileReader(login);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line;
            int i = 0;
            numberOfAccounts = 0;
            while ((line = buffReader.readLine()) != null) {

                StringTokenizer token = new StringTokenizer(line, "|");

                fNameTable[i] = token.nextToken();

                lNameTable[i] = token.nextToken();

                i++;
                numberOfAccounts++;
            }
            Welcome();
            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void Welcome() {
        for (int i = 1; i <= 1; i++) {

            fName = fNameTable[0];
            lName = lNameTable[0];

            JOptionPane.showMessageDialog(null, "Witaj " + fName + " " + lName, "Powitanie", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bHistoria;
    private javax.swing.JButton bPrzelew;
    private javax.swing.JButton bStanKonta;
    private javax.swing.JButton bWplata;
    private javax.swing.JButton bWyloguj;
    private javax.swing.JButton bWyplata;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLLogin;
    private javax.swing.JLabel jLWitaj2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
