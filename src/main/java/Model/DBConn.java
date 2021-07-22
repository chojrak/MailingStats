package Model;

import oracle.jdbc.pool.OracleDataSource;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DBConn implements Serializable {
    private static char[] url;
    private static char[] user;
    private static char[] password;
    private static HashMap<String, char[]> SQLdata;
    private static OracleDataSource ods;

    static {
        SQLdata = new HashMap<String, char[]>();
        try {
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\data.jpg");
            ObjectInputStream ois = new ObjectInputStream(fis);
            SQLdata = (HashMap<String, char[]>) ois.readObject();

            ois.close();
            fis.close();
            url = SQLdata.get("url");
            user = SQLdata.get("user");
            password = SQLdata.get("password");
        } catch (FileNotFoundException e) {
            newUser();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBConn(String user, String password) {
        this.url = Pass.encodeText("jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=dssprd-oradb.netia.org)(PORT=1522))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=DSSPRD)))");
        this.user = Pass.encodeText(user);
        this.password = Pass.encodeText(password);
        SQLdata.put("url", this.url);
        SQLdata.put("user", this.user);
        SQLdata.put("password", this.password);
        saveToFile(SQLdata);
    }

    public static void saveToFile(HashMap<String, char[]> data) {
        try {
            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\data.jpg");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();

        } catch (Exception ex) {
        }
    }

    public static String getUrl() {
        return Pass.decodeText(url);
    }

    public static String getUser() {
        return Pass.decodeText(user);
    }

    public static String getPassword() {
        return Pass.decodeText(password);
    }

    public static void newUser() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Podaj has³o:");
        JPasswordField passwordField = new JPasswordField(25);
        panel.add(label);
        panel.add(passwordField);
        String user = JOptionPane.showInputDialog(null, "Podaj login do SQL", "SQL", JOptionPane.INFORMATION_MESSAGE);
        JOptionPane.showConfirmDialog(null, panel, "SQL", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        char[] pass = passwordField.getPassword();
        StringBuilder sb = new StringBuilder();
        for (char a : pass) sb.append(a);
        String password = sb.toString();

        new DBConn(user, password);
    }

    public static ResultSet Execute(String query) {
        try {
            ods = new OracleDataSource();
            ods.setURL(Pass.decodeText(url));
            ods.setUser(Pass.decodeText(user));
            ods.setPassword(Pass.decodeText(password));
            Connection conn = ods.getConnection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1017) newUser();
        }
        return null;
    }
}
