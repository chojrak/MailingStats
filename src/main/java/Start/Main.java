package Start;


import oracle.jdbc.pool.OracleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
//-Doracle.jdbc.thinLogonCapability=o3
        String query = "select * from chacinr_sql.zrzut_new2 z where z.acco_busi_id = '10000395'";
        //  String connString = "jdbc:oracle:thin:@(description=(address_list=(address=(protocol=TCP)(port=1522)(host=clydbshd1.netia.org)))(connect_data=(SERVER=dedicated)(SERVICE_NAME=CLYSHD1)))";
        String connString = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=dssprd-oradb.netia.org)(PORT=1522))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=DSSPRD)))";
        OracleDataSource ods = null;

        try {
            ods = new OracleDataSource();
         // ods.setServiceName("CLYSHD1");
            ods.setURL(connString);
            ods.setUser("CHACINR_SQL");
            ods.setPassword("WIEM LEPIEJ!1");

            Connection conn = ods.getConnection();

            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) System.out.println(rs.getString("CUST_NAME_TX"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
