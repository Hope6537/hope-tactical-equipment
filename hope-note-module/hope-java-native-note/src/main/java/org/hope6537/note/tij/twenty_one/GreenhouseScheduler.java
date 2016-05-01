package org.hope6537.note.tij.twenty_one;

import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 预定时间，预订条件来执行任务
 * @signdate 2014年8月10日下午5:12:36
 * @company Changchun University&SHXT
 */
public class GreenhouseScheduler {

    // ??这是啥?时间表线程执行器?
    ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(10);
    List<DataPoint> data = Collections
            .synchronizedList(new ArrayList<DataPoint>());
    private volatile boolean light = false;
    private volatile boolean water = false;
    private String thermostat = "Day";
    private Calendar lastTime = Calendar.getInstance();
    private float lastTemp = 65.0f;
    private int tempDirection = +1;
    private float lastHumidity = 50.0f;
    private int humidityDirection = +1;
    private Random rand = new Random(47);

    {
        lastTime.set(Calendar.MINUTE, 30);
        lastTime.set(Calendar.SECOND, 00);
    }

    public static void main(String[] args) {
        GreenhouseScheduler gh = new GreenhouseScheduler();
        gh.schedule(gh.new Terminate(), 5000);
        // Former "Restart" class not necessary:
        gh.repeat(gh.new Bell(), 0, 1000);
        gh.repeat(gh.new ThermostatNight(), 0, 2000);
        gh.repeat(gh.new LightOn(), 0, 200);
        gh.repeat(gh.new LightOff(), 0, 400);
        gh.repeat(gh.new WaterOn(), 0, 600);
        gh.repeat(gh.new WaterOff(), 0, 800);
        gh.repeat(gh.new ThermostatDay(), 0, 1400);
        gh.repeat(gh.new CollectData(), 500, 500);
    }

    public synchronized String getThermostat() {
        return thermostat;
    }

    public void setThermostat(String thermostat) {
        this.thermostat = thermostat;
    }

    public void schedule(Runnable event, long delay) {
        scheduler.schedule(event, delay, TimeUnit.MILLISECONDS);
    }

    public void repeat(Runnable event, long initialDelay, long period) {
        scheduler.scheduleAtFixedRate(event, initialDelay, period,
                TimeUnit.MILLISECONDS);
    }

    static class DataPoint {
        final Calendar time;
        final float/* double */temperature;
        final float humidity;

        public DataPoint(Calendar time, float temperature, float humidity) {
            super();
            this.time = time;
            this.temperature = temperature;
            this.humidity = humidity;
        }

        @Override
        public String toString() {
            return time.getTime()
                    + String.format(" 溫度: %1$.1f 濕度: %2$.2f", temperature,
                    humidity);
        }
    }

    class LightOn implements Runnable {
        @Override
        public void run() {
            System.out.println("将灯打开");
            light = true;
        }
    }

    class LightOff implements Runnable {
        public void run() {
            System.out.println("关掉灯光");
            light = false;
        }
    }

    class WaterOn implements Runnable {
        public void run() {
            System.out.println("打开水流");
            water = true;
        }
    }

    class WaterOff implements Runnable {
        public void run() {
            System.out.println("关闭水流");
            water = false;
        }
    }

    class ThermostatNight implements Runnable {
        public void run() {
            System.out.println("天黑了");
            setThermostat("Night");
        }
    }

    class ThermostatDay implements Runnable {
        public void run() {
            System.out.println("天亮了");
            setThermostat("Day");
        }
    }

    class Bell implements Runnable {
        public void run() {
            System.out.println("Bing~!");
        }
    }

    class Terminate implements Runnable {
        @Override
        public void run() {
            System.out.println("Terminating");
            scheduler.shutdownNow();
            new Thread() {
                public void run() {
                    for (DataPoint d : data) {
                        System.out.println(d);
                    }
                }

                ;
            }.start();
        }
    }

    class CollectData implements Runnable {
        @Override
        public void run() {
            System.out.println("收集数据中");
            synchronized (this) {
                lastTime.set(Calendar.MINUTE,
                        lastTime.get(Calendar.MINUTE) + 30);
                if (rand.nextInt(5) == 4) {
                    tempDirection = -tempDirection;
                }
                lastTemp = lastTemp + tempDirection * (1.0f + rand.nextFloat());
                if (rand.nextInt(5) == 4) {
                    humidityDirection = -humidityDirection;
                }
                lastHumidity = lastHumidity + humidityDirection
                        * rand.nextFloat();
                data.add(new DataPoint((Calendar) lastTime.clone(), lastTemp,
                        lastHumidity));
            }
        }
    }
}
