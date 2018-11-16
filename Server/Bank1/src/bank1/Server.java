package bank1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Account
 */
public class Server extends javax.swing.JFrame {

    String login, password, password2, exist, balance, choice, transferAmountString, transferAccount, transferSender, exist2, historyString, depositAmountString, depositAccount, withdrawAccount, wplataImie, withdrawAmountString, wyplataImie;

    String fileName = "Konta.txt";
    String[] loginsTable;
    String[] passwordsTable;
    String[] balanceTable;

    private int numberOfAccounts;
    private double transferAmount;
    private double depositAmount;
    private double withdrawAmount;

    ServerSocket server;
    Socket socket;
    InputStream in;
    OutputStream out;

    public Server() {

        initComponents();
        setLocationRelativeTo(null);
        tA.setEditable(false);

        loginsTable = new String[100];
        passwordsTable = new String[100];
        balanceTable = new String[100];

    }

    public void choiceSelect() {

        System.out.println("");
        try {
            int k = 0;
            StringBuffer sbchoice = new StringBuffer();

            k = in.read();
            sbchoice.append((char) k);
            choice = sbchoice.toString().trim();

            if (choice.equals("a")) {
                tA.append("Rejestracja ...\n");
                areaUpdate();
                rejestracja();
            }
            if (choice.equals("b")) {
                tA.append("Logowanie ...\n");
                areaUpdate();
                logowanie();
            }

            if (choice.equals("c")) {
                tA.append("Informacje o koncie ...\n");
                areaUpdate();
                stanKonta();
            }

            if (choice.equals("d")) {
                tA.append("Przelew ...\n");
                areaUpdate();
                przelew();
            }

            if (choice.equals("e")) {
                tA.append("Wpłata ...\n ");
                areaUpdate();
                wplata();
            }
            if (choice.equals("f")) {
                tA.append("Wypłata ...\n ");
                areaUpdate();
                wyplata();
            }

            if (choice.equals("g")) {
                tA.append("Historia ...\n ");
                areaUpdate();
                sendHistory();
            }

        } catch (UnknownHostException ex) {
            System.err.println(ex + " Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + " Blad WE/WY");
        }
    }

    public void areaUpdate() {
        tA.setCaretPosition(tA.getDocument().getLength());
    }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Rejestracja ++++++++++++++++++++++++++++
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
    public void rejestracja() {

        loadAccountToTable();
        getData();
        checkAccountExists();

    }

    public void loadAccountToTable() {

        tA.append("Wczytuje konta z pliku do tablicy\n");
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader buffReader = new BufferedReader(fileReader);

            String line;
            int i = 0;
            numberOfAccounts = 0;
            while ((line = buffReader.readLine()) != null) {

                StringTokenizer token = new StringTokenizer(line, "|");

                loginsTable[i] = token.nextToken();
                passwordsTable[i] = token.nextToken();
                balanceTable[i] = token.nextToken();

                i++;
                numberOfAccounts++;
            }

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void getData() {
        tA.append("Odbieranie danych potrzebnych do wykonania rejestracji\n");
        areaUpdate();
        try {

            int k = 0;
            StringBuffer sbLogin = new StringBuffer();
            StringBuffer sbPassword = new StringBuffer();

            boolean a = false;
            while ((k = in.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbLogin.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true) {
                    sbPassword.append((char) k);
                }
            }

            login = sbLogin.toString().trim();
            password = sbPassword.toString().trim();

            tA.append("Login to: " + login + "\n");
            tA.append("Haslo to: " + password + "\n");
            areaUpdate();

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void checkAccountExists() {

        for (int i = 0; i < numberOfAccounts; i++) {
            if (login.equals(loginsTable[i])) {
                exist = "1";
            } else {
                exist = "0";
            }
        }
        try {
            out.write(exist.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        if (exist.equals("0")) {
            tA.append("Rejestracja : Zarejestrowano pomyslnie\n");
            areaUpdate();
            saveAccountToFile();

        } else {
            tA.append("Rejestracja : Podane konto już istnieje w bazie\n");
            areaUpdate();
            choiceSelect();
        }
    }

    public void saveAccountToFile() {
        tA.append("Zapisywanie danych\n");
        areaUpdate();
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            for (int i = 0; i < numberOfAccounts; i++) {
                strb.append(loginsTable[i]);
                strb.append("|");
                strb.append(passwordsTable[i]);
                strb.append("|");
                strb.append(balanceTable[i]);
                strb.append("\n");
            }

            char x[] = password.toCharArray();

            for (int i = 0; i != x.length; i++) {
                int n = x[i];
                n += 3;
                x[i] = (char) n;
                password2 = String.valueOf(x);
            }

            strb.append(login);
            strb.append("|");
            strb.append(password2);
            strb.append("|0");
            strb.append("\n");

            numberOfAccounts++;
            tA.append("Ilosc kont na serwerze wynosi :" + numberOfAccounts + "\n");
            areaUpdate();

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();
            loginFile();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void loginFile() {
        try {
            FileWriter fileWriter = new FileWriter(login + ".txt");
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            buffWriter.close();
            fileWriter.close();
            choiceSelect();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Stan konta ++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public void stanKonta() {
        loadAccountToTable();
        sendBalanceInformation2();

    }

    public void sendBalanceInformation2() {
        tA.append("Wysylanie informacji o stanie konta\n");
        areaUpdate();
        try {
            out.write(balance.getBytes());
            out.write(" ".getBytes());
            out.write(login.getBytes());
            out.write("\n".getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Logowanie +++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void logowanie() {
        loadAccountToTable();
        getDataLogIn();
        checkAccountExistsLogin();
    }

    public void getDataLogIn() {
        tA.append("Odbieranie danych potrzebnych do wykonania logowania\n");
        areaUpdate();
        try {

            int k = 0;
            StringBuffer sbLogin = new StringBuffer();
            StringBuffer sbPassword = new StringBuffer();

            boolean a = false;
            while ((k = in.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbLogin.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true) {
                    sbPassword.append((char) k);
                }
            }

            login = sbLogin.toString().trim();
            password = sbPassword.toString().trim();
            char x[] = password.toCharArray();

            for (int i = 0; i != x.length; i++) {
                int n = x[i];
                n -= 3;
                x[i] = (char) n;
                password2 = String.valueOf(x);
            }

            tA.append("Login to: " + login + "\n");
            tA.append("Haslo to: " + password2 + "\n");
            areaUpdate();

        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void checkAccountExistsLogin() {

        for (int i = 0; i < numberOfAccounts; i++) {
            if (login.equals(loginsTable[i]) && password.equals(passwordsTable[i])) {
                exist = "1";
                balance = balanceTable[i];
                login = loginsTable[i];
                password = passwordsTable[i];
                break;
            } else {
                exist = "0";
            }
        }
        try {
            out.write(exist.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }

        if (exist.equals("0")) {
            tA.append("Logowanie : Konto nie istnieje\n");
            areaUpdate();
            choiceSelect();
        } else if (exist.equals("1")) {
            sendBalanceInformation();
            tA.append("Logowanie : Zalogowano pomyslnie\n");
            areaUpdate();
            choiceSelect();
        }
    }

    public void sendBalanceInformation() {
        tA.append("Wysylanie informacji o stanie konta\n");
        areaUpdate();
        try {
            out.write(balance.getBytes());
            out.write("\n".getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Przelew +++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void przelew() {
        loadAccountToTable();
        getDataTransfer();
        checkAccountExistsTransfer();
    }

    public void getDataTransfer() {
        tA.append("Odbieranie danych potrzebnych do wykonania przelewu\n");
        areaUpdate();
        try {

            int k = 0;
            StringBuffer sbAmountTransfer = new StringBuffer();
            StringBuffer sbAccountTransfer = new StringBuffer();
            StringBuffer sbTransferSender = new StringBuffer();

            boolean a = false;
            boolean b = false;
            while ((k = in.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbAmountTransfer.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true && k != '|' && b == false) {
                    sbAccountTransfer.append((char) k);
                }
                if (k == '|' && a == true) {
                    b = true;
                }
                if (b == true && k != '|') {
                    sbTransferSender.append((char) k);
                }
            }

            transferAmountString = sbAmountTransfer.toString().trim();
            transferAccount = sbAccountTransfer.toString().trim();
            transferSender = sbTransferSender.toString().trim();

            tA.append("Kwota przelewu to: " + transferAmountString + "\n");
            tA.append("Adresat przelewu to: " + transferAccount + "\n");
            tA.append("Nadawca wysylajace to: " + transferSender + "\n");
            areaUpdate();

            transferAmount = Double.parseDouble(transferAmountString);

        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void checkAccountExistsTransfer() {

        for (int i = 0; i < numberOfAccounts; i++) {
            if (transferAccount.equals(loginsTable[i])) {
                exist = "1";

                double task = Double.parseDouble(balanceTable[i]);
                task = task + transferAmount;
                String task2 = String.valueOf(task);
                balanceTable[i] = task2;

                break;
            } else {
                exist = "0";
            }
        }
        if (exist.equals("1")) {
            for (int i = 0; i < numberOfAccounts; i++) {
                if (transferSender.equals(loginsTable[i])) {
                    exist2 = "1";

                    double tsk = Double.parseDouble(balanceTable[i]);
                    tsk = tsk - transferAmount;
                    String tsk2 = String.valueOf(tsk);
                    balanceTable[i] = tsk2;
                    balance = balanceTable[i];
                    break;
                } else {
                    exist2 = "0";
                }
            }
        }
        saveHistory();
        saveAccountToFileTransfer();

    }

    public void saveHistory() {
        try {
            FileReader fileReader = new FileReader(transferSender + ".txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            StringBuffer sbHistory = new StringBuffer();

            String line;
            while ((line = buffReader.readLine()) != null) {
                sbHistory.append(line);
            }
            historyString = sbHistory.toString();

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        tA.append("Zapisywanie historii\n");
        areaUpdate();
        try {
            FileWriter fileWriter = new FileWriter(transferSender + ".txt");
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            strb.append("Przelew na kwote : " + transferAmount + " PLN" + " na konto : " + transferAccount);
            strb.append("|");
            strb.append(historyString);

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();
            saveHistory2();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

    }

    public void saveHistory2() {
        try {
            FileReader fileReader = new FileReader(transferAccount + ".txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            StringBuffer sbHistory = new StringBuffer();

            String line;
            while ((line = buffReader.readLine()) != null) {
                sbHistory.append(line);
            }
            historyString = sbHistory.toString();

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        try {
            FileWriter fileWriter = new FileWriter(transferAccount + ".txt");
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            strb.append("Otrzymano przelew na kwote : " + transferAmount + " PLN" + " od uzytykownika : " + transferSender);
            strb.append("|");
            strb.append(historyString);

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

    }

    public void saveAccountToFileTransfer() {

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            for (int i = 0; i < numberOfAccounts; i++) {
                strb.append(loginsTable[i]);
                strb.append("|");
                strb.append(passwordsTable[i]);
                strb.append("|");
                strb.append(balanceTable[i]);
                strb.append("\n");
            }

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
        try {
            out.write(exist.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        if (exist.equals("0")) {
            tA.append("Przelew : Brak konta w bazie\n");
            choiceSelect();
        } else if (exist.equals("1")) {
            sendBalanceInformation();
            tA.append("Przelew : Przelew został wykonany pomyślnie\n");
            choiceSelect();
        }
    }

    public void sendHistory() {

        boolean allGood = false;
        try {
            FileReader fileReader = new FileReader(login + ".txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            StringBuffer sbHistory = new StringBuffer();

            String line;
            while ((line = buffReader.readLine()) != null) {
                sbHistory.append(line);
            }
            historyString = sbHistory.toString();

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
            allGood = true;
        }
        if (allGood == false) {
            try {
                out.write(historyString.getBytes());
                out.write("\r\n".getBytes());
            } catch (UnknownHostException ex) {
                System.err.println(ex + "Blad Hosta");
            } catch (IOException ex) {
                System.err.println(ex + "Blad WE/WY");
            }
        }
    }
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Wplata ++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 

    public void wplata() {
        loadAccountToTable();
        getDataDeposit();
        checkAccountExistsDeposit();
    }

    public void getDataDeposit() {
        tA.append("Odbieranie danych potrzebnych do wplaty srodkow\n");
        try {

            int k = 0;
            StringBuffer sbDepositAmount = new StringBuffer();
            StringBuffer sbDepositAccount = new StringBuffer();

            boolean a = false;
            boolean b = false;
            while ((k = in.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbDepositAmount.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true && k != '0') {
                    sbDepositAccount.append((char) k);
                }
            }

            depositAmountString = sbDepositAmount.toString().trim();
            depositAccount = sbDepositAccount.toString().trim();

            tA.append("Kwota wplaty to: " + depositAmountString + "\n");
            tA.append("Konto wplaty to: " + depositAccount + "\n");

            depositAmount = Double.parseDouble(depositAmountString);

        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void checkAccountExistsDeposit() {

        for (int i = 0; i < numberOfAccounts; i++) {
            if (depositAccount.equals(loginsTable[i])) {
                exist = "1";

                double tsk = Double.parseDouble(balanceTable[i]);
                tsk = tsk + depositAmount;
                String tsk2 = String.valueOf(tsk);
                balanceTable[i] = tsk2;
                balance = balanceTable[i];

                break;
            } else {
                exist = "0";
            }
        }
        saveDepositHistory();
        saveAccountToFileTransfer();

    }

    public void saveDepositHistory() {
        try {
            FileReader fileReader = new FileReader(login + ".txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            StringBuffer sbHistory = new StringBuffer();

            String line;
            while ((line = buffReader.readLine()) != null) {
                sbHistory.append(line);
            }
            historyString = sbHistory.toString();

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        tA.append("Zapisywanie historii \n");
        try {
            FileWriter fileWriter = new FileWriter(login + ".txt");
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            strb.append("Wplata srodkow : " + depositAmount + " PLN");
            strb.append("|");
            strb.append(historyString);

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
        try {
            out.write(exist.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        if (exist.equals("0")) {
            tA.append("Wpłata : Brak konta w bazie\n");
            choiceSelect();
        } else if (exist.equals("1")) {
            sendBalanceInformation();
            tA.append("Wpłata : Przelew został wykonany pomyślnie\n");
            choiceSelect();
        }
    }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++ Wyplata +++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
    public void wyplata() {
        loadAccountToTable();
        getDataWithdraw();
        checkAccountExistsWithdraw();
    }

    public void getDataWithdraw() {
        tA.append("Odbieranie danych potrzebnych do wyplaty srodkow\n");
        try {

            int k = 0;
            StringBuffer sbWithdrawAmount = new StringBuffer();
            StringBuffer sbWithdrawAccount = new StringBuffer();

            boolean a = false;
            boolean b = false;
            while ((k = in.read()) != -1 && k != '\n') {
                if (a == false) {
                    sbWithdrawAmount.append((char) k);
                }
                if (k == ' ') {
                    a = true;
                }
                if (a == true && k != '0') {
                    sbWithdrawAccount.append((char) k);
                }
            }

            withdrawAmountString = sbWithdrawAmount.toString().trim();
            withdrawAccount = sbWithdrawAccount.toString().trim();

            tA.append("Kwota wypłaty to: " + withdrawAmountString + "\n");
            tA.append("Konto wypłaty to: " + withdrawAccount + "\n");

            withdrawAmount = Double.parseDouble(withdrawAmountString);

        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
    }

    public void checkAccountExistsWithdraw() {

        for (int i = 0; i < numberOfAccounts; i++) {
            if (withdrawAccount.equals(loginsTable[i])) {
                exist = "1";

                double task = Double.parseDouble(balanceTable[i]);
                task = task - withdrawAmount;
                String task2 = String.valueOf(task);
                balanceTable[i] = task2;
                balance = balanceTable[i];

                break;
            } else {
                exist = "0";
            }
        }
        saveWithdrawHistory();
        saveAccountToFileTransfer();

    }

    public void saveWithdrawHistory() {
        try {
            FileReader fileReader = new FileReader(login + ".txt");
            BufferedReader buffReader = new BufferedReader(fileReader);

            StringBuffer sbHistory = new StringBuffer();

            String line;
            while ((line = buffReader.readLine()) != null) {
                sbHistory.append(line);
            }
            historyString = sbHistory.toString();

            buffReader.close();
            fileReader.close();
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        tA.append("Zapisywanie historii\n");
        try {
            FileWriter fileWriter = new FileWriter(login + ".txt");
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            StringBuilder strb = new StringBuilder();

            strb.append("Wyplata srodkow : " + withdrawAmount + " PLN");
            strb.append("|");
            strb.append(historyString);

            buffWriter.write(strb.toString());

            buffWriter.close();
            fileWriter.close();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }
        try {
            out.write(exist.getBytes());
        } catch (UnknownHostException ex) {
            System.err.println(ex + "Blad Hosta");
        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

        if (exist.equals("0")) {
            tA.append("Wyplata : Brak konta w bazie\n");
            choiceSelect();
        } else if (exist.equals("1")) {
            sendBalanceInformation();
            tA.append("Wyplata : Przelew został wykonany pomyślnie\n");
            choiceSelect();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tA = new javax.swing.JTextArea();
        bZakoncz = new javax.swing.JButton();
        bStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        setAlwaysOnTop(true);

        tA.setColumns(20);
        tA.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        tA.setRows(5);
        tA.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(tA);

        bZakoncz.setText("Zakoncz");
        bZakoncz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bZakonczActionPerformed(evt);
            }
        });

        bStart.setText("Uruchom serwer");
        bStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(bZakoncz, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bStart, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bStart, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bZakoncz, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bStartActionPerformed
        try {
            server = new ServerSocket(7777);

            Thread thread = new Thread(() -> {
                tA.append("Server dziala ...\n");

                try {
                    while (true) {

                        socket = server.accept();

                        in = socket.getInputStream();
                        out = socket.getOutputStream();
                        tA.append("Klient IP: " + socket.getInetAddress().getHostAddress() + " Port: " + socket.getPort() + "\n");
                        choiceSelect();
                       

                        in = socket.getInputStream();
                        out = socket.getOutputStream();
                        choiceSelect();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);

                }
            });

            thread.start();

        } catch (IOException ex) {
            System.err.println(ex + "Blad WE/WY");
        }

    }//GEN-LAST:event_bStartActionPerformed

    private void bZakonczActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bZakonczActionPerformed
        Thread thread = new Thread();
        thread.interrupt();
        tA.append("Serwer zostal zamkniety\n");
        dispose();
        System.exit(0);
    }//GEN-LAST:event_bZakonczActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bStart;
    private javax.swing.JButton bZakoncz;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea tA;
    // End of variables declaration//GEN-END:variables
}
