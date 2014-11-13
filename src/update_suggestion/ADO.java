package update_suggestion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** 负责数据库数据读取 */
public class ADO {
	private String username = "suggest";
	private String pwd = "suggest";
	private String protocol = "mysql";
	private String hostname = "172.21.101.8:3306";
	private String database = "gcore";

	private Connection conn = null;
	private Statement statement = null;
	private String query = "select word, weight from suggest_table";

	public ADO() {
		initialize(protocol, hostname, database, username, pwd);
	}

	public ADO(String hostname, String database, String username, String pwd) {
		initialize(protocol, hostname, database, username, pwd);
	}

	public ADO(String protocol, String hostname, String database,
			String username, String password) {
		initialize(username, hostname, database, username, password);
	}

	/** 初始化参数 */
	private void initialize(String protocol, String hostname, String database,
			String username, String password) {
		if (!protocol.equals(this.protocol))
			this.protocol = protocol;
		if (!hostname.equals(this.hostname))
			this.hostname = hostname;
		if (!database.equals(this.database))
			this.database = database;
		if (!username.equals(this.username))
			this.username = username;
		if (!password.equals(this.pwd))
			this.pwd = password;
	}

	/** 连接数据库 */
	public void connectDatabase() throws SQLException {
		if (conn != null)
			throw new IllegalStateException(
					"Already has a connection, or you should close it first.");
		conn = DriverManager.getConnection(createUrl(), username, pwd);
		statement = conn.createStatement();
	}

	/** 关闭数据库连接 */
	public void closeDatabase() throws SQLException {
		statement.close();
		conn.close();
	}

	/** 提取数据 */
	public void getResultSet(Extract ext) throws SQLException {
		ResultSet rst = statement.executeQuery(query);
		while (rst.next()) {
			ext.extractItem(rst);
		}
	}

	/** 创建连接数据库的url */
	private String createUrl() {
		StringBuilder builder = new StringBuilder("jdbc");
		builder.append(":").append(protocol).append("://").append(hostname)
				.append("/").append(database);
		return builder.toString();
	}

	public void setUsername(String name) {
		username = name;
	}

	public void setPassword(String password) {
		pwd = password;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setHost(String host) {
		this.hostname = host;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
