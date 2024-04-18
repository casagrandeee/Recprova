import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/SampleDB";
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "senha";

    public static void main(String[] args) {
        String criarTabelaPessoa = """
                CREATE TABLE IF NOT EXISTS pessoa (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(500),
                    rua VARCHAR(500),
                    cep INT
                );
                """;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            PreparedStatement createTableStatement = conn.prepareStatement(criarTabelaPessoa);
            createTableStatement.execute();

            Person pessoa = new Person("Maria", "Rua A", 12345);
            inserirPessoa(conn, pessoa);

            pessoa.setNome("João");
            atualizarPessoa(conn, pessoa);

            excluirPessoa(conn, 1);

            consultarPessoas(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirPessoa(Connection conn, Person pessoa) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, rua, cep) VALUES (?, ?, ?)";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getRua());
            statement.setInt(3, pessoa.getCep());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Registro inserido com sucesso!");
            }
        }
    }

    public static void atualizarPessoa(Connection conn, Person pessoa) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, rua = ?, cep = ? WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, pessoa.getNome());
            statement.setString(2, pessoa.getRua());
            statement.setInt(3, pessoa.getCep());
            statement.setInt(4, pessoa.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Registro atualizado com sucesso!");
            }
        }
    }

    public static void excluirPessoa(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro excluído com sucesso!");
            }
        }
    }

    public static void consultarPessoas(Connection conn) throws SQLException {
        String sql = "SELECT * FROM pessoa";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String rua = resultSet.getString("rua");
                int cep = resultSet.getInt("cep");
                System.out.println("ID: " + id + ", Nome: " + nome + ", Rua: " + rua + ", CEP: " + cep);
            }
        }
    }
}