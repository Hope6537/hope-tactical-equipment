package org.hope6537.note.thinking_in_java.twenty_one;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Meal {
    private final int orderNum;

    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    public String toString() {
        return "餐点序号 " + orderNum;
    }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;

    public WaitPerson(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    // 外部是锁，内部是循环 这样可以保证退出等待循环之前。 条件将得到满足
                    while (restaurant.meal == null) {
                        System.out.println("侍者：当前没有食物 等待大厨开工");
                        wait(); // ... for the chef to produce a meal
                    }
                }
                System.out.println("侍者：拿到了 " + restaurant.meal);
                // 调用解锁前必须声明同步
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll(); // Ready for another
                }
            }
        } catch (InterruptedException e) {
            System.out.println("侍者：被打断");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count = 0;

    public Chef(Restaurant r) {
        restaurant = r;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null)
                        wait(); // ... for the meal to be taken
                }
                if (++count == 10) {
                    System.out.println("大厨：没有食物了，关门");
                    // 向exec中的所有项发出中断
                    restaurant.exec.shutdownNow();
                }
                System.out.println("大厨：点餐完毕！准备制作！ ");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("大厨：被打断");
        }
    }
}

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 生产者-消费者实现，使用单一的地点来存放对象
 * @signdate 2014年8月10日上午10:56:48
 * @company Changchun University&SHXT
 */
public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}