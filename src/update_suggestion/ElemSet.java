package update_suggestion;

import java.sql.ResultSet;
import java.sql.SQLException;

/** 特定的数据处理逻辑 */
public class ElemSet extends DataSet implements Extract {

	@Override
	public void extractItem(ResultSet result) throws SQLException {
		String word = result.getString(1); // 提取关键字
		Float uv = result.getFloat(2); // 提取uv
		String key = word.replace(" ", "").toLowerCase(); // 计算key

		if (dict.containsKey(key)) {
			/* 已存在关键字, 则更新关键字数据 */
			final Element elem = dict.get(key);
			if (elem.word.length() > word.length())
				elem.word = word;
			elem.uv += uv;
		} else {// 不存在则创建关键字
			dict.put(key, new Element(word, uv));
		}
	}

}
