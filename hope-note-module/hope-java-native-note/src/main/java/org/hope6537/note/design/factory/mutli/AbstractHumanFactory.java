package org.hope6537.note.design.factory.mutli;

import org.hope6537.note.design.factory.human.Human;

/**
 * <p>Describe: 多工厂模式下的抽象工厂 </p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月9日下午2:32:41</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public abstract class AbstractHumanFactory {

    public abstract Human createHuman();

}
