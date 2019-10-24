package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
		
	// classe para concectar e desconcetar com o banco de dados.
	
	private static Connection conn = null;
	
	// metodo para abrir conexao
	public static Connection getConnection() {
		if(conn == null) {
			try {
				Properties props = loadProperties();         // instancia chamando o metodo load... para carregar os dados do arquivo.
				String url = props.getProperty("dburl");       // pega o valor da propriedade definidade no arquivo db.properties
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
		
		return conn;
	}
	
	// método para fechar conexão
	public static void closeConnection() {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	
	
	private static Properties loadProperties() {
		// metodo realiza a leitura das variaveis dentro do arquivo e instancia dentro de props
		
		try (FileInputStream fs = new FileInputStream("db.properties")){
			
			Properties props = new Properties();
			props.load(fs);
			return props;
			
		}
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	
	
	// fecha o statement e evita inserções de try e catch nos metodos da classe principal 
	public static void closeStatement(Statement st) {
		
		if(st != null) {
			
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
	}
	
	// fecha o ResultSet e evita inserções de try e catch nos metodos da classe principal 
	public static void closeResultSet(ResultSet rs) {
			
			if(rs != null) {
				
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DbException(e.getMessage());
				}
				
			}
		}
	
	
	
}
