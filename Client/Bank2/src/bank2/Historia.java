package bank2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Historia extends javax.swing.JFrame {

    String historyString, choice;
    Socket socket;
    OutputStream output;
    InputStream input;

    public Historia() {
        initComponents();
        setLocationRelativeTo(null);
        tAHistoria.setEditable(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tAHistoria = new javax.swing.JTextArea();
        bWyjdz = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Historia");
        setPreferredSize(new java.awt.Dimension(600, 400));

        tAHistoria.setColumns(20);
        tAHistoria.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tAHistoria.setRows(5);
        tAHistoria.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(tAHistoria);

        bWyjdz.setText("Wyjdź");
        bWyjdz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bWyjdzActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGap(225, 225, 225)
                .addComponent(bWyjdz, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(225, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(bWyjdz, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bWyjdzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bWyjdzActionPerformed
        KontoInterface k = new KontoInterface();
        this.dispose();
        exit();
        k.perform2();
    }//GEN-LAST:event_bWyjdzActionPerformed

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
        loadHistory();

    }

    public void sendChoice() {
        choice = "g";

        try {
            output.write(choice.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void loadHistory() {
        try {
            int k = 0;
            StringBuffer sbHistory = new StringBuffer();

            while ((k = input.read()) != -1 && k != '\n') {

                if (k != '|') {
                    sbHistory.append((char) k);
                }
                if (k == '|') {
                    sbHistory.append("\n");
                }
            }
            historyString = sbHistory.toString().trim();
            tAHistoria.setText(historyString);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bWyjdz;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea tAHistoria;
    // End of variables declaration//GEN-END:variables
}
