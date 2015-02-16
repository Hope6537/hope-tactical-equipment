package org.hope6537.hadoop;

import org.hope6537.context.ApplicationConstant;
import org.hope6537.date.ChineseTimeString;
import org.hope6537.date.DateFormatCalculate;
import org.hope6537.date.TimeDemo;
import org.hope6537.db.BaseDaoUtil;
import org.hope6537.db.BaseDaoUtilImpl;
import org.junit.Test;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

/**
 * 目标数据产生器
 * 下面为Shell脚本和待查询语句
 * <p>
 * zkServer.sh start * 3
 * start-dfs.sh
 * start-yarn.sh
 * <p>
 * hadoop fs -mkdir /hive-data
 * <p>
 * 导入mysql数据库
 * <p>
 * sqoop export --connect jdbc:mysql://hadoop1:3306/hive_source --username root --password **** --export-dir "/hive-data/account" --table account --fields-terminated-by '\t'
 * <p>
 * sqoop export --connect jdbc:mysql://hadoop1:3306/hive_source --username root --password **** --export-dir "/hive-data/trade" --table trade --fields-terminated-by '\t'
 * <p>
 * <p>
 * mysql語句
 * <p>
 * GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '****' WITH GRANT OPTION
 * =================MYSQL===========================
 * SELECT a.username , t.income,t.expenses,t.leftCharge
 * FROM account AS a
 * JOIN (SELECT
 * account_id ,SUM(income) income ,
 * SUM(expenses) expenses , SUM(income - expenses) leftCharge
 * FROM trade  GROUP BY account_id)
 * AS t ON  t.account_id = a.account;
 * <p>
 * 创建兩个外部表用于装载数据
 * <p>
 * create external table account(id bigint, account string, username string) row format delimited fields terminated by '\t' location '/hive-data/account';
 * <p>
 * create external table trade(id bigint, account_id bigint, income double, expenses double, time string) row format delimited fields terminated by '\t' location '/hive-data/trade';
 * <p>
 * 然后创建查询，并将查询结果写入HDFS中
 * <p>
 * CURRENT=`/bin/date +%y%m%d`
 * <p>
 * 注意 create as 时候不允许创建外部表，所以下语句不可用
 * <br/>
 * #create external table result row format delimited fields terminated by '\t' location '/hive-data/result' as select a.username , t.income , t.expenses , t.leftCharge FROM account AS a JOIN (select account_id ,SUM(income) as income , SUM(expenses) as expenses , SUM(income - expenses) as leftCharge FROM trade GROUP BY account_id) AS t on (t.account_id = a.account);
 * <p>
 * 但是可以定义表的位置
 * <br/>
 * create table result row format delimited fields terminated by '\t' location '/hive-data/result' as select account_id ,SUM(income) as income , SUM(expenses) as expenses , SUM(income - expenses) as leftCharge FROM trade GROUP BY account_id;
 */
public class DataInit {

    @Test
    public void testInitAccountData() {
        TimeDemo timeDemo = new TimeDemo();
        AccountDriver accountDriver = new AccountDriver();
        int accountCount = 0;
        for (int i = 0; i < 9000; i++) {
            Account account = new Account();
            int res = accountDriver.saveByPrepared(account);
            accountCount += res;
            if (res != ApplicationConstant.EFFECTIVE_LINE_ONE) {
                break;
            }
        }
        assertEquals(accountCount, 9000);
        timeDemo.showEndTime(ChineseTimeString.MSECOND);
    }

    @Test
    public void testInitTradeData() {
        TimeDemo timeDemo = new TimeDemo();
        TradeDriver tradeDriver = new TradeDriver();
        int tradeCount = 0;
        for (int i = 0; i < 61850; i++) {
            Trade trade = new Trade();
            int res = tradeDriver.saveByPrepared(trade);
            tradeCount += res;
            if (res != ApplicationConstant.EFFECTIVE_LINE_ONE) {
                break;
            }
        }
        assertEquals(tradeCount, 61850);
        timeDemo.showEndTime(ChineseTimeString.SECOND);
    }
}

class AccountDriver extends BaseDaoUtilImpl<Account> implements BaseDaoUtil<Account> {

}

class TradeDriver extends BaseDaoUtilImpl<Trade> implements BaseDaoUtil<Trade> {

}


class Account implements Serializable {

    private Integer id;
    private String account;
    private String username;

    public Account() {
        String randomStr = UUID.randomUUID().toString();
        account = randomStr.substring(0, 8) + "@" + randomStr.substring(10, 12) + ".com";
        username = randomStr.substring(14, 18);

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

class Trade implements Serializable {

    private Integer id;

    private Integer account_id;

    private Double income;

    private Double expenses;

    private String time;

    public Trade() {
        Random random = new Random();
        account_id = random.nextInt(20000);
        income = random.nextInt(500000) * 1.0;
        expenses = random.nextInt(400000) * 1.0;
        time = DateFormatCalculate.createNowTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

