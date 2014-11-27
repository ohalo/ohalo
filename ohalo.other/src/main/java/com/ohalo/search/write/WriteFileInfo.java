package com.ohalo.search.write;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 
 * <pre>
 * 功能：WriteFileInfo 写入文件信息
 * 作者：赵辉亮
 * 日期：2013-5-6下午1:49:41
 * </pre>
 */
public class WriteFileInfo {

	private static Set<String> jsStore = new HashSet<String>(100000);

	private static Log logger = LogFactory.getLog(WriteFileInfo.class);

	/**
	 * 
	 * <pre>
	 * 方法体说明：查询文件路径
	 * 作者：赵辉亮
	 * 日期：2013-5-6. 下午3:02:33
	 * </pre>
	 * 
	 * @param file
	 */
	public void searchFilePath(File file) {
		if (file == null) {
			return;
		}

		if (!file.isDirectory()) {
			if (file.getName().indexOf(".log") > -1) {
				jsStore.add(file.getPath());
			} else {
				logger.debug("无效地址路径：" + file.getPath());
			}
			return;
		}

		File files[] = file.listFiles();

		for (File f : files) {
			searchFilePath(f);
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：创建文件索引
	 * 作者：赵辉亮
	 * 日期：2013-5-6. 下午3:02:52
	 * </pre>
	 * 
	 * @param jsFileStore
	 * @param indexDir
	 * @throws IOException
	 */
	public void createFileIndex(Set<String> jsFileStore, String indexDir)
			throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexWriterConfig cfg = new IndexWriterConfig(Version.LUCENE_36,
				new SimpleAnalyzer(Version.LUCENE_36));
		cfg.setOpenMode(OpenMode.CREATE);
		IndexWriter writer = new IndexWriter(dir, cfg);
		for (Iterator<String> iter = jsFileStore.iterator(); iter.hasNext();) {
			String filePath = iter.next();
			logger.debug("索引文件路径：" + filePath);
			readFile(filePath, writer);
		}
		writer.close();
	}

	@SuppressWarnings("resource")
	public void readFile(String path, IndexWriter writer) throws IOException {
		File file = new File(path);
		Document doc = new Document();
		doc.add(new Field("path", path, Field.Store.YES, Field.Index.ANALYZED));
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		String strline = br.readLine();
		int i = 1;
		while (strline != null) {
			doc.add(new Field("content" + i, strline, Field.Store.YES,
					Field.Index.ANALYZED));
			i++;
		}
		writer.addDocument(doc);
	}

	@SuppressWarnings("resource")
	public void readFileInfo(String indexDir, String keyword)
			throws IOException, ParseException {
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexReader reader = IndexReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		SimpleAnalyzer analyzer = new SimpleAnalyzer(Version.LUCENE_36);
		QueryParser queryParser = new QueryParser(Version.LUCENE_36, "content",
				analyzer);
		queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
		Query queryKey = queryParser.parse(keyword);
		TopDocs docs = searcher.search(queryKey, 100);
		ScoreDoc[] sd = docs.scoreDocs;
		for (int i = 0; i < sd.length; i++) {
			Document hitDoc = reader.document(sd[i].doc);
			System.out.println(hitDoc.get("path"));
		}
		reader.close();
	}

	public static void main(String[] args) throws ParseException {
		WriteFileInfo wfi = new WriteFileInfo();
		//File file = new File("G:\\360CloudUI\\");
		//wfi.searchFilePath(file);
		try {
			//wfi.createFileIndex(jsStore, "E:\\halo_index\\log");
			wfi.readFileInfo("E:\\halo_index\\log", "E");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
