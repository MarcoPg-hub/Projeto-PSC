package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {
	public static String status = "Não conectou...";


	public ConexaoMySQL() {
	}


	public static java.sql.Connection getInstance() {
		Connection connection = null; 
		try {

			String driverName = "com.mysql.cj.jdbc.Driver";
			Class.forName(driverName);
			String serverName = "localhost"; 
			String mydatabase = "cadastro"; 
			String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
			String username = "root"; 
			String password = "Marco2024"; 
			connection = DriverManager.getConnection(url, username, password);

			if (connection != null) {
				status = ("STATUS--->Conectado com sucesso!");
			} else {
				status = ("STATUS--->Não foi possível realizar conexão");
			}
			return connection;
		} catch (ClassNotFoundException e) {
			System.out.println("O driver especificado não foi encontrado.");
			e.printStackTrace();
			return null;
			
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
			e.printStackTrace();
			return null;
		}
	}


	public static String statusConection() {
		return status;
	}


	public static boolean FecharConexao() {

		try {
			ConexaoMySQL.getInstance().close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public static java.sql.Connection ReiniciarConexao() {
		FecharConexao();
		return ConexaoMySQL.getInstance();
	}


	public static Connection getConexaoMySQL() {
		return getInstance();
	}
}