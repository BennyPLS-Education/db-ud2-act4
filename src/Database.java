import java.sql.*;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("SqlSourceToSinkFlow")
public class Database {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String INSERT_EMP = "INSERT INTO EMP VALUES (%d, '%s' , '%s' ,'IT',7839,'%s',null,null,20)";

    /**
     * Inserts an employee into the database using the provided JDBC Statement object.
     *
     * @param employee The employee object to be inserted into the database.
     * @param stmt The JDBC Statement object to be used for executing the SQL statement.
     * @throws SQLException If there is an error executing the SQL statement.
     */
    public static void insert(Empleat employee, Statement stmt) throws SQLException {
        var sqlStmt = String.format(INSERT_EMP, employee.getNumero(), employee.getCognoms(), employee.getNom(), employee.getAlta().format(formatter));
        stmt.executeUpdate(sqlStmt);
    }

    /**
     * Inserts an employee into the database using the provided PreparedStatement.
     *
     * @param employee The.employee object to be inserted into the database.
     * @param preStmt The PreparedStatement object used to execute the insertion query.
     * @throws SQLException if an exception occurs while executing the insertion query.
     */
    public static void insert(Empleat employee, PreparedStatement preStmt) throws SQLException {
        preStmt.setInt(1, employee.getNumero());
        preStmt.setString(2, employee.getCognoms());
        preStmt.setString(3, employee.getNom());
        preStmt.setString(4, employee.getAlta().format(formatter));
        preStmt.executeUpdate();
    }

    /**
     * Deletes employee users from the database who belong to department 20, have an office of 'IT', and a Manager ID of 7839.
     *
     * @param conn The Connection object used to execute the deletion query.
     * @throws SQLException if an error occurs while deleting the employee users.
     */
    public static void deleteEMPUsers(Connection conn) throws SQLException {
        conn.createStatement().executeUpdate("DELETE FROM EMP WHERE DEPT_NO = 20 AND OFICI= 'IT' AND CAP = 7839");
        conn.commit();

    }

    /**
     * Returns a Connection object to establish a connection with the database.
     *
     * @return a Connection object to establish a connection with the database.
     * @throws SQLException if a database error occurs during the connection process.
     */
    public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/negoci", "root", "CalaClara21.");
    }
}
