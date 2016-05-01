package org.hope6537.note.design.bridge.example;

/**
 * 桥梁模式场景类
 * 抽象和实现分离 -> 实现细节对客户透明
 */
public class Client {

    public static void main(String[] args) {
        Post post = new PostImpl1();

        AbstractPoster poster = new Poster(post);

        poster.request();

    }

}
