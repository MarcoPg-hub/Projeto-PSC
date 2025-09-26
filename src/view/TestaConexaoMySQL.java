package view;

import connection.ConexaoMySQL;

public class TestaConexaoMySQL {

	public static void main(String[] args) {


		ConexaoMySQL.getInstance();
		
		System.out.println(ConexaoMySQL.statusConection());
		
		ConexaoMySQL.FecharConexao();

	}

}