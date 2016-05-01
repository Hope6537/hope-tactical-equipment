package org.hope6537.note.tij.twenty_one;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Hope6537(赵鹏)
 * @version 0.9
 * @describe 使用Callable作为线程载体
 * @signdate 2014年7月25日下午7:32:26
 * @company Changchun University&SHXT
 */
public class CallableDemo {

    public static void main(String[] args) {
        // 声明一个连接池
        ExecutorService exec = Executors.newCachedThreadPool();
        // Future用Callable返回结果的特定类型进行了参数化
        // 可以使用isDone方法查询Future是否已经完成 当任务完成后 会产生一个可以用get()接收到的一个结果
        ArrayList<Future<String>> results = new ArrayList<Future<String>>();
        for (int i = 0; i < 10; i++) {
            results.add(exec.submit(new TaskResult(i)));
        }
        for (Future<String> fs : results) {
            try {
                System.out.println(fs.get());
            } catch (Exception e) {
                System.out.println(e);
                return;
            } finally {
                exec.shutdown();
            }
        }
    }

}

class TaskResult implements Callable<String> {

    private int id;

    public TaskResult(int id) {
        super();
        this.id = id;
    }

    // 从任务中产生返回值
    @Override
    public String call() throws Exception {
        return "result of TaskWithResult" + id;
    }

}

/*Output:
 * result of TaskWithResult0
result of TaskWithResult1
result of TaskWithResult2
result of TaskWithResult3
result of TaskWithResult4
result of TaskWithResult5
result of TaskWithResult6
result of TaskWithResult7
result of TaskWithResult8
result of TaskWithResult9
*/
