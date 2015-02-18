package org.hope6537.note.design.abstract_factory.human;

/**
 * <p>
 * 抽象工厂模式使用的场景:<br/>
 * 一个对象族都拥有相同的约束
 * ,族内的约束为非公开状态，开发者可以自由设定而不需要关注于业务
 * 而客户端程序员直接拿来用就可以了
 * <hr>
 * 但是抽象工厂模式的拓展性并不优秀
 * </p>
 * <p>Describe: 抽象工厂定义</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月10日下午2:25:55</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public interface HumanFactory {

    public Human createYellowHuman();

    public Human createWhiteHuman();

    public Human createBlackHuman();

}
