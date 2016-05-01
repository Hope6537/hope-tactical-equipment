package org.hope6537.note.design.bridge.example;

/**
 * 抽象化角色
 */
public class AbstractPoster {

    private Post post;

    public AbstractPoster(Post post) {
        this.post = post;
    }

    /**
     * 自身的行为和属性->配合业务逻辑
     */
    public void request() {
        this.post.method1();
    }

    public Post getPost() {
        return post;
    }
}
