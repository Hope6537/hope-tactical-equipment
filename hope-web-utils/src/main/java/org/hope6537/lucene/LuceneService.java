package org.hope6537.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Describe: 基础的Lucene服务类
 * Created by Hope6537 on 14-11-27.
 */
public class LuceneService {

    private static final String DIRECTORY_PATH = "./luceneIndex";
    private static final Version DEFAULT_VERSION = Version.LUCENE_41;
    private FSDirectory dir = null;
    private Analyzer analyzer = null;
    private IndexWriter indexWriter = null;
    private IndexReader indexReader = null;

    public LuceneService() {
        this(DIRECTORY_PATH);
    }

    public LuceneService(String directoryPath) {
        try {
            File indexFiles = new File(directoryPath);
            // 索引文件的保存位置
            dir = FSDirectory.open(indexFiles);
            // 分析器
            analyzer = new SimpleAnalyzer(DEFAULT_VERSION);
            // 配置类
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IndexWriter getIndexWriter() throws IOException {
        if (indexWriter == null) {
            initWriter();
        }
        return indexWriter;
    }

    public IndexReader getIndexReader() throws IOException {
        if (indexReader == null) {
            initReader();
        }
        return indexReader;
    }

    public Analyzer getAnalyzer() {
        return analyzer;
    }

    protected void initWriter() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(DEFAULT_VERSION, analyzer);
        // 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        indexWriter = new IndexWriter(dir, iwc);
    }

    protected void initReader() throws IOException {
        indexReader = DirectoryReader.open(dir);
    }

    /**
     * 高亮被检索到的字段
     *
     * @param query 查询对象
     * @param value 存在高亮信息的字符串
     * @param left  左标签
     * @param right 右标签
     * @return 返回高亮完毕的字符串
     */
    public String setLightData(Query query, String value, String left, String right) {
        Formatter formatter = new SimpleHTMLFormatter(left, right);
        String result = null;
        Scorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new NullFragmenter());
        try {
            result = highlighter.getBestFragment(analyzer, null, value);
            if (result == null) {
                result = value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String setLightData(Query query, String value) {
        return this.setLightData(query, value, "<b>", "</b>");
    }

    /**
     * Describe 带有截取字符串的高亮，也就是说只显示高亮的那一部分
     * <pre>
     * 这里我要解释一下
     * 所谓的一段长度就是 例如这段文字
     * I resolved it by increasing the virtual memory available to the ES process. It started to fail when
     * I increased this to unlimited - 'ulimit -v unlimited'
     * - before starting the elasticsearch daemon and it is now loading in all my documents.
     * 我们要高亮 fail 这个单词 同时假设size为20
     * 那么我们就开始截取 第一个20个子是是否有fail? 没有就下一段 这一段 started to fail when I increased thi 出现了fail
     * 那么高亮如下
     * started to <b> fail </b> when I increased thi
     * 并且将这段字符串返回
     * </pre>
     *
     * @param query 查询对象
     * @param value 存在高亮信息的字符串
     * @param size  一段最多显示的字符串长度
     * @param left  左标签
     * @param right 右标签
     * @return 返回高亮完毕的字符串
     */
    public String setLightDataOnSubString(Query query, String value, int size, String left, String right) {
        Formatter formatter = new SimpleHTMLFormatter(left, right);
        String result = null;
        Scorer scorer = new QueryScorer(query);
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(new SimpleFragmenter(size));
        //TODO: 缺少多模截串高亮 过两天在补个AC自动机 让其能够多模匹配
        try {
            result = highlighter.getBestFragment(analyzer, null,
                    value);
            if (result == null) {
                if (value.length() > size) {
                    result = value.substring(0, size);
                } else {
                    result = value;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String setLightDataOnSubString(Query query, String value, int size) {
        return this.setLightDataOnSubString(query, value, size, "<b>", "</b>");
    }


    public void readerClose() {
        try {
            if (indexReader != null) {
                indexReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writerClose() {
        try {
            if (indexWriter != null) {
                indexWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        writerClose();
        readerClose();
    }


}