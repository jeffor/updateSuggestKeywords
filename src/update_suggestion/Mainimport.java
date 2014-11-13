package update_suggestion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;

public class Mainimport {

	public static void main(String[] args) throws SQLException,
			SolrServerException, IOException, InterruptedException {
		if (args.length == 5) {
			DataSet datas = new ElemSet(); // 定义数据容器
			while (true) {
				/** 读取数据 */
				ADO ado = new ADO(args[0], args[1], args[2], args[3]);
				ado.connectDatabase();
				ado.getResultSet((Extract) datas);
				ado.closeDatabase();
				if(args[4].equals("test"))
					break;
				/** 将数据导入检索引擎 */
				ImportData importor = new ImportData();
				importor.importData(datas, args[4]);
				datas.dict.clear();
				System.err.println("finish for sleeping: "+ new Date(System.currentTimeMillis()));
				Thread.sleep(1000 * 3600 * 24 * 7); // 七天以周期
			}
		}else{
			help();
		}
	}

	public static void help() {
		System.err
				.println("Usage: java -jar update-suggest-keywords.jar <database host> <database> <username> <password> <solrUrl>");
		System.err.println("    example: ");
		System.err
				.println("      java -jar update-suggest-keywords.jar 192.168.2.103:3306 test user pwd http://192.168.1.30:8080/solr/suggest");
	}
}
