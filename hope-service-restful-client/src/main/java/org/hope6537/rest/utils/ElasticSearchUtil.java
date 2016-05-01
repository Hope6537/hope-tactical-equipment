package org.hope6537.rest.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by dintama on 16/5/1.
 */
public class ElasticSearchUtil {

    private Client client = null;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String host;
    private Integer port;

    public void init() {
        try {
            client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(getHost()), getPort()));
        } catch (UnknownHostException e) {
            logger.error("Elasticsearch init failed");
            e.printStackTrace();
        }
    }

    public void destroy() {
        client.close();
    }

    public List<Integer> getIdList(String index, String type, String title, Integer pageNumber, Integer pageSize) {

        List<Integer> idList = new LinkedList<>();

        SearchResponse searchResponse = client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.matchPhraseQuery("title", title))
                .setFrom(pageNumber).setSize(pageSize).setExplain(true)
                .execute()
                .actionGet();

        SearchHits hits = searchResponse.getHits();
        logger.debug("response", JSON.toJSONString(searchResponse));
        for (SearchHit hit : hits) {
            Map<String, Object> source = hit.getSource();
            Integer id = Integer.parseInt(source.get("id").toString());
            idList.add(id);
        }

        return idList;

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
