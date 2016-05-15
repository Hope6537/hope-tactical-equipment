
    package org.hope6537.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class PlanDo extends BasicDo {
    
            /**作息数据 json格式 */
            private String data;
            
            /**星期几 */
            private Integer day;
            
    public PlanDo() {

    }
    
        public PlanDo(String data,Integer day) {

           this.data = data;
this.day = day;


        }
        
                public String getData() {
                    return data;
                }
                public void setData(String data) {
                    this.data = data;
                }
            
                public Integer getDay() {
                    return day;
                }
                public void setDay(Integer day) {
                    this.day = day;
                }
            
    }
    