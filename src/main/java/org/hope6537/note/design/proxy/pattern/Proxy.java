package org.hope6537.note.design.proxy.pattern;

public class Proxy implements Subject {

    private Subject subject = null;

    public Proxy() {
        subject = new Proxy();
    }

    public Proxy(Object... objects) {
        System.out.println(objects);
    }

    public Proxy(Subject _subject) {
        super();
        this.subject = _subject;
    }

    @Override
    public void request() {
        before();
        subject.request();
        after();
    }

    /**
     * <p>Describe: 预处理</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年9月29日下午2:21:42 </p>
     * <p>Author:Hope6537</p>
     *
     * @see
     */
    private void before() {
        System.out.println("before");
    }

    /**
     * <p>Describe: 后处理</p>
     * <p>Using: </p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年9月29日下午2:21:50 </p>
     * <p>Author:Hope6537</p>
     *
     * @see
     */
    private void after() {
        System.out.println("after");
    }

}
