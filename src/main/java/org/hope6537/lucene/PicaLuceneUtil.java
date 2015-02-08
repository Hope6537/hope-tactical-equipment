/*
package org.hope6537.lucene;
 
import com.threegrand.pica.law.model.Law;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
 
 
*/
/**
 * Pica项目中Document索引对象和Law对象的互相转换
 * Created by Hope6537 on 14-11-27.
 *//*

public class PicaLuceneUtil {
 
    */
/**
 * 对象转换索引
 *
 * @param law 要转换的对象
 * @return 返回转换完毕的Document
 *//*

    public static Document law2Document(Law law) {
        Document document = new Document();
        Field idField = new StringField("lawId", law.getLawId(), Field.Store.YES);
        Field titleField = new TextField("title", law.getTitle(), Field.Store.YES);
        Field contentField = new TextField("content", law.getContent(), Field.Store.YES);
        document.add(idField);
        document.add(titleField);
        document.add(contentField);
        return document;
    }
 
    */
/**
 * 索引转换对象
 * 注意 该对象只有lawId title和content字段
 *
 * @param document 要还原的装载着law的document对象
 * @return 返回由Document封装好的Law对象
 *//*

    public static Law document2Law(Document document) {
        Law law = new Law();
        law.setLawId(document.get("lawId"));
        law.setTitle(document.get("title"));
        law.setContent(document.get("content"));
        return law;
    }
 
}*/
