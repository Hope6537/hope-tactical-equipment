package org.hope6537.note.design.bridge.example;

/**
 * 具体抽象化角色
 */
public class Poster extends AbstractPoster {
    public Poster(Post post) {
        super(post);
    }

    /**
     * 对父类的业务处理进行修正
     */
    @Override
    public void request() {
        super.request();
        super.getPost().method2();
    }
}
