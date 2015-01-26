package org.hope6537.note.design.factory.mutli;

import org.hope6537.note.design.factory.human.Human;
import org.hope6537.note.design.factory.human.YellowHuman;

/** 
 * <p>Describe: 人种的创建工厂实现</p>
 * <p>Using: </p>
 * <p>DevelopedTime: 2014年9月9日下午2:34:46</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class YellowHumanFactory extends AbstractHumanFactory {

	@Override
	public Human createHuman() {
		return new YellowHuman();
	}

}
