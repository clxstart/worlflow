import java.sql.*;

public class init_flowable {
    public static void main(String[] args) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/workflow?useSSL=false&serverTimezone=GMT%2B8";
        String user = "root";
        String pass = "chang090011";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, null, "ACT_GE_PROPERTY", null);
            if (tables.next()) {
                System.out.println("Flowable tables already exist");
                return;
            }
            System.out.println("Creating Flowable base tables...");
            
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE ACT_GE_PROPERTY (NAME_ VARCHAR(64), VALUE_ VARCHAR(300), REV_ INT, PRIMARY KEY (NAME_)) ENGINE=InnoDB");
            stmt.execute("INSERT INTO ACT_GE_PROPERTY VALUES ('schema.version', '6.8.0.0', 1)");
            stmt.execute("INSERT INTO ACT_GE_PROPERTY VALUES ('schema.history', 'create(6.8.0.0)', 1)");
            System.out.println("Base tables created. Restart application to complete.");
        }
    }
}
