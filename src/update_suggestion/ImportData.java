package update_suggestion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;

import update_suggestion.DataSet.Element;

/** 数据导入检索引擎 */
public class ImportData {
	// default hostname
	final private static String hostname = "http://suggest.wochacha.com/solr/new_suggest"; 
	private HttpSolrServer server = null;

	/** 连接 solr */
	public void connectSolr() {
		server = new HttpSolrServer(hostname);
	}

	public void connectSolr(String hostname) {
		server = new HttpSolrServer(hostname);
	}

	/** 导入数据（使用默认solr服务器） */
	public void importData(DataSet datas) throws SolrServerException,
			IOException {
		if (server == null)
			connectSolr();
		clearSolr();
		Collection<SolrInputDocument> docs = translation(datas);
		server.add(docs.iterator());
		server.commit();
	}

	/** 导入数据（使用特定solr服务器） */
	public void importData(DataSet datas, String hostname)
			throws SolrServerException, IOException {
		connectSolr(hostname);
		importData(datas);
	}

	/** 数据转换 */
	public Collection<SolrInputDocument> translation(DataSet datas) {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for (Element elem : datas.dict.values()) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("name", elem.word);
			doc.addField("pv", elem.uv);
			docs.add(doc);
		}
		return docs;
	}

	private void clearSolr() throws SolrServerException, IOException {
		if (server == null)
			throw new IllegalStateException("There is no connection to solr");
		server.deleteByQuery("*:*");
		server.commit();
	}
}
