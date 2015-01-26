package org.hope6537.note.design.factory.lazy;


import org.hope6537.note.design.factory.example.CurrentProduct1;
import org.hope6537.note.design.factory.example.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Describe: 延迟加载的工厂类</p>
 * <p>Using: 一个对象被使用之后，保持该对象的状态，等待再次使用</p>
 * <p>DevelopedTime: 2014年9月9日下午3:19:06</p>
 * <p>Company: ChangChun Unviersity JiChuang Team</p>
 *
 * @author Hope6537
 * @version 1.0
 * @see
 */
public class LazyProductFactory {

    private static final Map<String, Product> prMap = new HashMap<String, Product>();

    /**
     * <p>Describe: 进行对象类型判断</p>
     * <p>Using: 使用键值对关系数组进行判断</p>
     * <p>How To Work: </p>
     * <p>DevelopedTime: 2014年9月9日下午6:40:30 </p>
     * <p>Author:Hope6537</p>
     *
     * @param type
     * @return
     * @throws Exception
     * @see
     */
    public static synchronized Product createProduct(String type)
            throws Exception {
        Product product = null;
        if (prMap.containsKey(type)) {
            product = prMap.get(type);
        } else {
            if (type.equals("Product1")) {
                product = new CurrentProduct1();
            }
        }
        prMap.put(type, product);
        return product;
    }

}
