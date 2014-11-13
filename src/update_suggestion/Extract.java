package update_suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

/**数据处理接口(处理数据库返回的一个row)*/
public interface Extract {
	public void extractItem(ResultSet result) throws SQLException;
}
