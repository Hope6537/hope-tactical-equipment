package org.hope6537.datastruct.graph;

import org.hope6537.datastruct.list.PairMap;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Hope6537 on 2015/3/4.
 */
public class SymbolGraph {

    /**
     * 数据->索引
     */
    private PairMap pair;

    /**
     * 索引->数据
     */
    private String[] keys;

    /**
     * 一个构造标准图
     */
    private BasicGraph graph;

    public SymbolGraph(String stream, String spilt, String encoding) throws UnsupportedEncodingException {
        this(Arrays.toString(stream.getBytes(encoding)), spilt);
    }

    public SymbolGraph(String stream, String spilt) {
        Scanner scanner = new Scanner(stream);
        pair = new PairMap();
        //构造数据->索引 结构
        while (scanner.hasNextLine()) {
            String[] dataArray = scanner.nextLine().split(spilt);
            for (int i = 0; i < dataArray.length; i++) {
                if (!pair.containsKey(dataArray[i])) {
                    //为不同的字符串构造新的索引
                    pair.put(dataArray[i], pair.size());
                }
            }
        }
        //反向索引，通过索引号取值
        keys = new String[pair.size()];
        for (String name : pair.keySet()) {
            keys[pair.get(name)] = name;
        }
        //构造图
        graph = new BasicGraph(pair.size());
        scanner = new Scanner(stream);
        while (scanner.hasNextLine()) {
            String[] dataArray = scanner.nextLine().split(spilt);
            int vetrex = pair.get(dataArray[0]);
            for (int i = 1; i < dataArray.length; i++) {
                //根据数据格式，每一行开头是v顶点 该行剩下的数据时v顶点相邻的w[]
                graph.addEdge(vetrex, pair.get(dataArray[i]));
            }
        }
    }

    public boolean contains(String s) {
        return pair.containsKey(s);
    }

    public int index(String s) {
        return pair.get(s);
    }

    public String name(int v) {
        return keys[v];
    }

    public BasicGraph getGraph() {
        return graph;
    }

    public void printDegreesOnConsole(String source, String target) {
        BasicGraph basicGraph = this.getGraph();
        if (!this.contains(source)) {
            System.out.println(source + "不存在于图中");
            return;
        }
        int start = this.index(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(basicGraph, start);
        Scanner scanner = new Scanner(target);
        while (scanner.hasNext()) {
            String sink = scanner.next();
            if (this.contains(sink)) {
                int tar = this.index(sink);
                if (bfs.hasPathTo(tar)) {
                    System.out.println(source + "->" + sink);
                    for (int vetrex : bfs.pathTo(tar)) {
                        System.out.println("\t ->" + this.name(vetrex));
                    }
                } else {
                    System.out.println(source + " <->" + sink + "两点未连接");
                }
            } else {
                System.out.println(sink + "不在图中");
            }
        }


    }
}


