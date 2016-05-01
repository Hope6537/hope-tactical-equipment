package org.hope6537.note.design.chain.example;

/**
 * 抽象责任处理者
 */
public abstract class Handler {

    private Handler nextHandler;

    public final Response handleMessage(Request request) {
        Response response = null;
        //如果请求的等级符合自己处理的等级，那么处理并响应
        if (this.getHandlerLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);
        }
        //不属于自己的处理级别
        else {
            if (this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                //没有适当的处理者
                System.out.println("no handler");
            }
        }
        return response;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected abstract Level getHandlerLevel();

    /**
     * 处理任务的方法
     */
    protected abstract Response echo(Request request);


}
