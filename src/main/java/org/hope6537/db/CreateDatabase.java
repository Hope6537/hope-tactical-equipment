package org.hope6537.db;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


/**
 * Hibernate框架初始化
 * @author ：赵鹏
 * @since ：2013-10-26下午08:54:37
 */
public class CreateDatabase {

    public static void CreateDB() {

        Configuration cfg = new Configuration().configure("hibernate/hibernate.cfg.xml");

        SchemaExport export = new SchemaExport(cfg);

        export.create(true, true);
    }

    public static void main(String[] args) {
        CreateDB();
    }


}
