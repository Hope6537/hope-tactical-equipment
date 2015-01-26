package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @describe 分发工作 建造车辆
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:15:01
 * @version 0.9
 * @company Changchun University&SHXT
 */
public class CarBuilder {
	public static void main(String[] args) throws Exception {
		// 声明流水线
		CarQueue chassisQueue = new CarQueue(), finishingQueue = new CarQueue();
		// 建立线程任务
		ExecutorService exec = Executors.newCachedThreadPool();
		// 建立对象池
		RobotPool robotPool = new RobotPool();
		// 启动并建立3种机器人，让其待命wait()
		exec.execute(new EngineRobot(robotPool));
		exec.execute(new DriveTrainRobot(robotPool));
		exec.execute(new WheelRobot(robotPool));
		// 启动生产流水线，等待装配工作
		exec.execute(new Assembler(chassisQueue, finishingQueue, robotPool));
		// 启动报告器，等待报告装好了的车辆
		exec.execute(new Reporter(finishingQueue));
		// 通过启动底盘装配器 开始整个流水线工作
		exec.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(15);
		exec.shutdownNow();
	}
}

/**
 * @describe 一个车的模型
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:20:10
 * @version 0.9
 * @company Changchun University&SHXT
 */
class ModelCar {

	private final int id;
	private boolean engine = false, driveTrain = false, wheels = false;

	public ModelCar(int idn) {
		id = idn;
	}

	// Empty Car object:
	public ModelCar() {
		id = -1;
	}

	/**
	 * @descirbe 使用锁方法，在某一时刻，只有一个线程能获取到这个对象
	 * @author Hope6537(赵鹏)
	 * @return
	 * @signDate 2014年8月11日下午2:20:18
	 * @version 0.9
	 */
	public synchronized int getId() {
		return id;
	}

	/**
	 * @descirbe 添加引擎
	 * @author Hope6537(赵鹏)
	 * @signDate 2014年8月11日下午2:22:45
	 * @version 0.9
	 */
	public synchronized void addEngine() {
		engine = true;
	}

	/**
	 * @descirbe 安装车厢
	 * @author Hope6537(赵鹏)
	 * @signDate 2014年8月11日下午2:22:50
	 * @version 0.9
	 */
	public synchronized void addDriveTrain() {
		driveTrain = true;
	}

	/**
	 * @descirbe 安装轮子
	 * @author Hope6537(赵鹏)
	 * @signDate 2014年8月11日下午2:22:55
	 * @version 0.9
	 */
	public synchronized void addWheels() {
		wheels = true;
	}

	public synchronized String toString() {
		return "车辆 " + id + " [" + " 引擎: " + engine + " 车厢: " + driveTrain
				+ " 轮子: " + wheels + " ]";
	}
}

/**
 * @describe 一个车辆队列 用于装载被配置的车辆 由于是流水线作业 所以采用链式结构
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:21:54
 * @version 0.9
 * @company Changchun University&SHXT
 */
class CarQueue extends LinkedBlockingQueue<ModelCar> {

	private static final long serialVersionUID = 2998611961570480381L;

}

/**
 * @describe 一个底盘创建器
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:23:29
 * @version 0.9
 * @company Changchun University&SHXT
 */
class ChassisBuilder implements Runnable {
	/**
	 * @describe 准备往流水线上放已经装好底盘的车辆 获取到流水线对象
	 */
	private CarQueue carQueue;
	private int counter = 0;

	public ChassisBuilder(CarQueue cq) {
		carQueue = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(500);
				// 开始建造实体类 建造一个车辆模型，当然其他的属性还没有来得及配置
				ModelCar c = new ModelCar(counter++);
				System.out.println("底盘创建器: 刚刚新创建了" + c);
				// 然后将其放入队列
				carQueue.put(c);
			}
		} catch (InterruptedException e) {
			System.out.println("底盘创建器: 被打斷");
		}
		System.out.println("底盘创建器: 任务结束");
	}
}

/**
 * @describe 装配工序
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:31:21
 * @version 0.9
 * @company Changchun University&SHXT
 */
class Assembler implements Runnable {
	// 获取到两个生产线队列 ，一个是刚刚装好底盘的队列， 一个是已经完成的车辆队列 获取前者的对象做出处理然后放到后者里面去
	private CarQueue chassisQueue, finishingQueue;
	// 一个预设的实体车模型
	private ModelCar car;
	// 用于并行的执行动作，并在下一个步骤之前如果还没有全部完成的话保持挂起的触发事件
	private CyclicBarrier barrier = new CyclicBarrier(4);
	// 一个机器台
	private RobotPool robotPool;

	/**
	 * @describe 获取车辆队列和装配机器人
	 * @author Hope6537(赵鹏)
	 * @param cq
	 *            装配底盘队列
	 * @param fq
	 *            完成车辆队列
	 * @param rp
	 *            机器人装配工
	 */
	public Assembler(CarQueue cq, CarQueue fq, RobotPool rp) {
		chassisQueue = cq;
		finishingQueue = fq;
		robotPool = rp;
	}

	public ModelCar car() {
		return car;
	}

	/**
	 * @descirbe 获得并行计数器
	 * @author Hope6537(赵鹏)
	 * @return
	 * @signDate 2014年8月11日下午2:32:13
	 * @version 0.9
	 */
	public CyclicBarrier barrier() {
		return barrier;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				// 这个take操作将会阻塞 直到底盘队列里面出现了可用的车模型(即刚装底盘的车)
				car = chassisQueue.take();
				// 在机器人对象池里雇佣3种机器人
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);
				robotPool.hire(WheelRobot.class, this);
				barrier.await(); // 等待这三个同学完成工序，再执行下一步
				// 然后将装配好了的车辆放入到完成队列中
				finishingQueue.put(car);
			}
		} catch (InterruptedException e) {
			System.out.println("装配工序：被打断");
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
		System.out.println("装配工序：任务结束");
	}
}

/**
 * @describe 报告获取源 用于将所有的已完成车辆提取出来
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:35:06
 * @version 0.9
 * @company Changchun University&SHXT
 */
class Reporter implements Runnable {
	private CarQueue carQueue;

	/**
	 * @describe 获取到车辆队列
	 * @author Hope6537(赵鹏)
	 * @param cq
	 */
	public Reporter(CarQueue cq) {
		carQueue = cq;
	}

	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("车辆装配完成: " + carQueue.take());
			}
		} catch (InterruptedException e) {
			System.out.println("装配完成提示：被打断");
		}
		System.out.println("装配完成提示：结束任务");
	}
}

/**
 * @describe 一个泛用的装配机器人抽象类
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:36:19
 * @version 0.9
 * @company Changchun University&SHXT
 */
abstract class Robot implements Runnable {

	/**
	 * @describe 内置了一个机器人连接池
	 */
	private RobotPool pool;
	private final String name;

	public Robot(RobotPool p, String name) {
		pool = p;
		this.name = name;
	}

	/**
	 * @describe 所有实现了机器人的机器人派生类都可以获取到装配工序对象
	 */
	protected Assembler assembler;

	/**
	 * @descirbe 给当前的机器人对象注册装配工序
	 * @author Hope6537(赵鹏)
	 * @param assembler
	 * @return
	 * @signDate 2014年8月11日下午2:37:23
	 * @version 0.9
	 */
	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}

	/**
	 * @describe 是否正在工作
	 */
	private boolean engage = false;

	/**
	 * @descirbe 开始工作 同时将engage设为true 并且启动当前工序
	 * @author Hope6537(赵鹏)
	 * @signDate 2014年8月11日下午2:37:47
	 * @version 0.9
	 */
	public synchronized void engage() {
		engage = true;
		notifyAll();
	}

	// 这一部分是机器人的具体装配动作 也是最重要的地方
	abstract protected void performService();

	public void run() {
		try {
			// 在线程中挂起，直到被需要调用
			powerDown();
			while (!Thread.interrupted()) {
				// 进行工作
				performService();
				assembler.barrier().await(); // 同步步骤，等待其他装配步骤完成
				// 完成工作 接着挂起
				powerDown();
			}
		} catch (InterruptedException e) {
			System.out.println(this + " 被打断");
		} catch (BrokenBarrierException e) {
			// 这里会出现线程终止异常 不必理会
			throw new RuntimeException(e);
		}
		System.out.println(this + " 任务结束");
	}

	/**
	 * @descirbe [关闭电源] 即为挂起当前的工序
	 * @author Hope6537(赵鹏)
	 * @throws InterruptedException
	 * @signDate 2014年8月11日下午2:40:58
	 * @version 0.9
	 */
	private synchronized void powerDown() throws InterruptedException {
		// 是否工作 = false
		engage = false;
		// 和装配工序断线
		assembler = null;
		// 将其本身返回对象池
		pool.release(this);
		while (engage == false)
			// 循环检查条件挂起 因为engage是无锁的
			wait();
	}

	public String toString() {
		return name;
	}
}

class EngineRobot extends Robot {

	public EngineRobot(RobotPool pool) {
		super(pool, "[引擎装配机器人]");
	}

	protected void performService() {
		System.out.println(this + " 正在安装引擎");
		assembler.car().addEngine();
	}
}

class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool pool) {
		super(pool, "[车厢装配机器人]");
	}

	protected void performService() {
		System.out.println(this + " 正在安装车厢");
		assembler.car().addDriveTrain();
	}
}

class WheelRobot extends Robot {
	public WheelRobot(RobotPool pool) {
		super(pool, "[轮子装配机器人]");
	}

	protected void performService() {
		System.out.println(this + " 正在安装轮子");
		assembler.car().addWheels();
	}
}

/**
 * @describe 机器人对象池
 * @author Hope6537(赵鹏)
 * @signdate 2014年8月11日下午2:45:12
 * @version 0.9
 * @company Changchun University&SHXT
 */
class RobotPool {
	// 防止出现相同的实体
	private Set<Robot> pool = new HashSet<Robot>();

	/**
	 * @descirbe 向连接池中添加一个对象，并且将所有占用当前连接池的锁的线程唤醒
	 * @author Hope6537(赵鹏)
	 * @param r
	 * @signDate 2014年8月11日下午2:48:04
	 * @version 0.9
	 */
	public synchronized void add(Robot r) {
		pool.add(r);
		notifyAll();
	}

	/**
	 * @descirbe 雇佣一个机器人
	 * @author Hope6537(赵鹏)
	 * @param robotType
	 *            机器人类型
	 * @param d
	 *            装配线
	 * @throws InterruptedException
	 * @signDate 2014年8月11日下午2:48:52
	 * @version 0.9
	 */
	public synchronized void hire(Class<? extends Robot> robotType, Assembler d)
			throws InterruptedException {
		for (Robot r : pool)
			if (r.getClass().equals(robotType)) {
				// 遍历并寻找 然后弹出，送出去
				pool.remove(r);
				r.assignAssembler(d);
				r.engage(); // 然后激活他以准备工作
				return;
			}
		wait(); // 如果现在没有，那就挂起等待，直到add方法被调用，说明
		// 1、添加了新机器人 2、之前的机器人返回
		hire(robotType, d); // 然后再遍历查找一次 依次递归，直到出现一个
	}

	/**
	 * @descirbe 机器人回家
	 * @author Hope6537(赵鹏)
	 * @param r
	 * @signDate 2014年8月11日下午2:51:28
	 * @version 0.9
	 */
	public synchronized void release(Robot r) {
		add(r);
	}
}
