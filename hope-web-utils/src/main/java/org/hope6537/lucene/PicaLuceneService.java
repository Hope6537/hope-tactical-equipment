package org.hope6537.lucene;/*
package org.hope6537.lucene;
 
import com.threegrand.pica.law.model.Law;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;
 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
*/
/**
 * Pica项目的Lucene服务类
 * Created by Hope6537 on 14-11-27.
 * <p>
 * 提交
 * <p>
 * 回滚
 * <p>
 * 将Law对象添加进入索引中
 *
 * @param law 待添加的law对象
 * @throws IOException 没找到索引位置
 * <p>
 * 更新Law对象在索引内的数据
 * 由Law的lawId字段进行识别
 * 所以需要先进行查询
 * @param law 待更新的law对象
 * @throws IOException 没找到索引位置
 * <p>
 * 删除Law对象在索引内的数据
 * 由Law的lawId字段进行识别
 * 所以需要先进行查询
 * @param law 待刪除的law对象
 * @throws IOException 没找到索引位置
 * <p>
 * 更懒的查询方法 默认查询域和分页大小
 * <p>
 * 只搜索标题和内容符合关键字的Law对象 默认不截串
 * <p>
 * 查询符合关键字keyWord的Law对象
 * @param keyWord           关键字
 * @param checkedProperties 查询的属性字符串
 * @param searchNum         搜索的个数(但是Lucene不给从哪个开搜，所以最后还是假分页)
 * @param firstResult       要查询的第一个Law在Document的位置 类似于Mysql的Limit中的第一个数字
 * @param maxResult         最大数量 Limit的第二个数字
 * @param isSubStr          是否截取字符串
 * @return 返回装有匹配的Law的List
 * @throws IOException                                          没有找到索引在磁盘上的位置
 * @throws org.apache.lucene.queryparser.classic.ParseException 因为查询关键字非法
 * @author 赵鹏
 *//*

public class PicaLuceneService extends LuceneService {
 
    public PicaLuceneService() {
        super();
    }
 
    */
/**
 * 提交
 *//*

    public void commit() {
        try {
            getIndexWriter().commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    */
/**
 * 回滚
 *//*

    public void rollback() {
        try {
            getIndexWriter().rollback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
 
    */
/**
 * 将Law对象添加进入索引中
 *
 * @param law 待添加的law对象
 * @throws IOException 没找到索引位置
 *//*

    public void addLaw2Index(Law law) throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Document document = PicaLuceneUtil.law2Document(law);
        indexWriter.addDocument(document);
    }
 
    */
/**
 * 更新Law对象在索引内的数据
 * 由Law的lawId字段进行识别
 * 所以需要先进行查询
 *
 * @param law 待更新的law对象
 * @throws IOException 没找到索引位置
 *//*

    public void updateLaw2Index(Law law) throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Term term = new Term("lawId", law.getLawId());
        indexWriter.updateDocument(term, PicaLuceneUtil.law2Document(law));
 
    }
 
    */
/**
 * 删除Law对象在索引内的数据
 * 由Law的lawId字段进行识别
 * 所以需要先进行查询
 *
 * @param law 待刪除的law对象
 * @throws IOException 没找到索引位置
 *//*

    public void deleteLaw4Index(Law law) throws IOException {
        IndexWriter indexWriter = getIndexWriter();
        Term term = new Term("lawId", law.getLawId());
        indexWriter.deleteDocuments(term);
    }
 
    */
/**
 * 更懒的查询方法 默认查询域和分页大小
 *//*

    public List<Law> getLaw4Index(String keyWord) throws IOException, ParseException {
        return getLaw4Index(keyWord, 0, 10);
    }
 
    */
/**
 * 只搜索标题和内容符合关键字的Law对象 默认不截串
 *//*

    public List<Law> getLaw4Index(String keyWord, int firstResult, int maxResult) throws IOException, ParseException {
        ArrayList<String> checkedProperties = new ArrayList<String>();
        checkedProperties.add("lawId");
        checkedProperties.add("title");
        checkedProperties.add("content");
        return getLaw4Index(keyWord, checkedProperties, 100, firstResult, maxResult, false);
    }
 
    */
/**
 * 查询符合关键字keyWord的Law对象
 *
 * @param keyWord           关键字
 * @param checkedProperties 查询的属性字符串
 * @param searchNum         搜索的个数(但是Lucene不给从哪个开搜，所以最后还是假分页)
 * @param firstResult       要查询的第一个Law在Document的位置 类似于Mysql的Limit中的第一个数字
 * @param maxResult         最大数量 Limit的第二个数字
 * @param isSubStr          是否截取字符串
 * @return 返回装有匹配的Law的List
 * @throws IOException                                          没有找到索引在磁盘上的位置
 * @throws org.apache.lucene.queryparser.classic.ParseException 因为查询关键字非法
 * @author 赵鹏
 *//*

    public List<Law> getLaw4Index(String keyWord, ArrayList<String> checkedProperties, int searchNum, int firstResult, int maxResult, boolean isSubStr)
            throws IOException, ParseException {
        //定义好搜索器 我们开始干活
        IndexReader indexReader = getIndexReader();
        if (indexReader == null) {
            initReader();
            indexReader = getIndexReader();
        }
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //确定搜索版本 确定搜索字段 确定分词器
        QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_41,
                checkedProperties.toArray(new String[checkedProperties.size()]), getAnalyzer());
        //关键字
        Query query = queryParser.parse(keyWord);
        //定义搜索匹配个数 但是最后还是假分页
        TopDocs topDocs = indexSearcher.search(query, searchNum);
        //得到得分集
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //定义Law对象表
        List<Law> laws = new ArrayList<Law>();
        for (int i = firstResult; i < maxResult + firstResult
                && i < scoreDocs.length; i++) {
            int index = scoreDocs[i].doc;
            Document document = indexSearcher.doc(index);
            //在这里设置高亮
            String title;
            String content;
            if (isSubStr) {
                title = setLightDataOnSubString(query, document.get("title"), 50);
                content = setLightDataOnSubString(query, document.get("content"), 50);
            } else {
                title = setLightData(query, document.get("title"));
                content = setLightData(query, document.get("content"));
            }
            //高亮设置完毕后装载
            Law temp = PicaLuceneUtil.document2Law(document);
            temp.setTitle(title);
            temp.setContent(content);
            laws.add(temp);
        }
        return laws;
    }
 
 */
