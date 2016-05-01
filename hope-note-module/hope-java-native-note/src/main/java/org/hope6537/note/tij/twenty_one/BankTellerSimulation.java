package org.hope6537.note.tij.twenty_one;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 银行出纳员仿真
 * @signdate 2014年8月10日下午7:41:28
 * @company Changchun University&SHXT
 */
public class BankTellerSimulation {

    static final int MAX_LINE_SIZE = 50;
    static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManaegr(exec, customers, ADJUSTMENT_PERIOD));
        if (args.length > 0) {
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        } else {
            System.out.println("按下回车键以退出");
            System.in.read();
        }
        exec.shutdownNow();
    }

}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 该类为顾客实体类，简化版 仅仅包含一个final int域 他是一个只读对象
 * 对于每个Teller任务在任何时刻都只从输入队列中移除一个Customer 并且在这个Customer对象工作直到完成
 * 因此在任何时刻只有一个对象能访问他
 * @signdate 2014年8月11日下午1:16:22
 * @company Changchun University&SHXT
 */
class Customer {
    private final int serviceTime;

    public Customer(int serviceTime) {
        super();
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 表示谷歌在等待被某个Teller服务时所排成的单一的行 这只是一个阻塞队列
 * @signdate 2014年8月11日下午1:18:14
 * @company Changchun University&SHXT
 */
class CustomerLine extends ArrayBlockingQueue<Customer> {

    /**
     * @describe
     */
    private static final long serialVersionUID = 3441359585380160156L;

    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "[空]";
        }
        StringBuilder result = new StringBuilder();
        for (Customer customer : this) {
            result.append(customer);
        }
        return result.toString();
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 它附着在CustomerLine之上，按照随机的时间向这个队列中添加Customer
 * @signdate 2014年8月11日下午1:18:47
 * @company Changchun University&SHXT
 */
class CustomerGenerator implements Runnable {
    private static Random rand = new Random(47);
    private CustomerLine customers;

    public CustomerGenerator(CustomerLine customers) {
        super();
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(300);
                customers.put(new Customer(rand.nextInt(1000)));

            }
        } catch (Exception e) {
            System.out.println("顾客生产器被打断");
        }
        System.out.println("顾客生产器已停止");
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 出纳员实体类 Teller從CustomerLine中取出Customer
 * 在任何時刻他只能處理一個Customer，並且跟蹤在這個特定班次中有他服務的Customer的數量
 * ，在沒有足夠多的顧客時，他會進入doSomeThingElse狀態，当出现很多顾客的时候，他又被重新补充回来
 * 而优先队列的特性会保证工作量最少的出纳员会被推向前台
 * @signdate 2014年8月11日下午1:19:32
 * @company Changchun University&SHXT
 */
class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    // Customers served during this shift:
    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine cq) {
        customers = cq;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {
                    customersServed++;
                    while (!servingCustomerLine) {
                        wait();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(this + " 被打断");
        }
        System.out.println(this + " 业务结束");
    }

    @Override
    public synchronized int compareTo(Teller other) {
        return customersServed < other.customersServed ? -1
                : (customersServed == other.customersServed ? 0 : 1);
    }

    public synchronized void doSomethingElse() {
        customersServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serveCustomerLine() {
        // 这句是啥意思？
        assert !servingCustomerLine : "already serving " + this;
        servingCustomerLine = true;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller [id=" + id + "]";
    }

    public String shortString() {
        return "T " + id;
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 出纳经理 控制系统，用来控制出纳员的数量
 * @signdate 2014年8月11日下午12:05:46
 * @company Changchun University&SHXT
 */
class TellerManaegr implements Runnable {
    private static Random rand = new Random(47);
    private ExecutorService exec;
    private CustomerLine customers;
    private PriorityBlockingQueue<Teller> workingTellers = new PriorityBlockingQueue<Teller>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<Teller>();
    private int adjustmentPeriod;

    public TellerManaegr(ExecutorService exec, CustomerLine customers,
                         int adjustmentPeriod) {
        super();
        this.exec = exec;
        this.customers = customers;
        this.adjustmentPeriod = adjustmentPeriod;
        Teller teller = new Teller(customers);
        exec.execute(teller);
        workingTellers.add(teller);
    }

    /**
     * @descirbe 闲置一个出纳员
     * @author Hope6537(赵鹏)
     * @signDate 2014年8月11日下午12:39:39
     * @version 0.9
     */
    public void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.offer(teller);
    }

    /**
     * @descirbe 可以用一个稳定的方式增加或移除出纳员
     * @author Hope6537(赵鹏)
     * @signDate 2014年8月11日下午1:48:02
     * @version 0.9
     */
    public void adjustTellerNumber() {
        // 如果排队的人数过多，就加一个窗口
        if (customers.size() / workingTellers.size() > 2) {
            // 如果有闲置的出纳员
            if (tellersDoingOtherThings.size() > 0) {
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.add(teller);
                return;
            }
            // 否则的话就再雇佣一个
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }
        if (workingTellers.size() > 1
                && customers.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }
        if (customers.size() == 0) {
            // 如果没有顾客，那么就流一个就够了
            while (workingTellers.size() > 1) {
                reassignOneTeller();
            }
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();
                System.out.print(customers + " {");
                for (Teller teller : workingTellers) {
                    System.out.print(teller.shortString() + " ");
                }
                System.out.println("}");
            }
        } catch (Exception e) {
            System.out.println(this + "被中断 ");
        }
        System.out.println(this + "已结束");
    }

    @Override
    public String toString() {
        return "TellerManager";
    }
}