import java.io.EOFException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        Empleat[] employees = getEmpleats();

        try (var conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            Database.deleteEMPUsers(conn);
            executePreparedStatements(employees, conn);

            Database.deleteEMPUsers(conn);
            executeStatements(employees, conn);
        }
    }

    /**
     * Executes a batch of statements to insert employees into the database using a Statement object.
     *
     * @param employees an array of Empleat objects representing the employees to be inserted
     * @param conn      the Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     */
    private static void executeStatements(Empleat[] employees, Connection conn) throws SQLException {
        var stmt = conn.createStatement();

        long timeStart = System.currentTimeMillis();

        for (Empleat employee : employees) {
            Database.insert(employee, stmt);
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("Temps d'inserció SENSE PREPARED : " + (timeEnd - timeStart) + "ms");
        conn.commit();
    }

    /**
     * Executes prepared statements to insert employee data into the EMP table.
     *
     * @param employees An array of Empleat objects representing the employees to be inserted.
     * @param conn      The Connection object representing the database connection.
     * @throws SQLException if an error occurs while executing the prepared statements.
     */
    private static void executePreparedStatements(Empleat[] employees, Connection conn) throws SQLException {
        var preStmt = conn.prepareStatement("INSERT INTO EMP VALUES (?,?,?,'IT',7839,?,null,null,20)");

        var timeStart = System.currentTimeMillis();

        for (Empleat employee : employees) {
            Database.insert(employee, preStmt);
        }

        var timeEnd = System.currentTimeMillis();

        System.out.println("Temps d'inserció AMB PREPARED: " + (timeEnd - timeStart) + "ms");
        conn.commit();
    }

    /**
     * Retrieves the Empleat objects from a data file.
     *
     * @return An array of Empleat objects representing the employees.
     * @throws Exception if an error occurs while reading the data file.
     */
    private static Empleat[] getEmpleats() throws Exception {
        ArrayList<Empleat> empleats = new ArrayList<>();

        System.out.println("Llegint empleats...");
        try (var in = new ObjectInputStream(new FileInputStream("empleats.dat"))) {
            while (true) empleats.add((Empleat) in.readObject());
        } catch (EOFException EOF) {
            System.out.println("Empleats llegits.");

            return empleats.toArray(new Empleat[0]);
        }
    }
}