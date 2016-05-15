
    package org.hope6537.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class NoticeDo extends BasicDo {
    
            /**标题 */
            private String title;
            
            /**内容 */
            private String des;
            
    public NoticeDo() {

    }
    
        public NoticeDo(String title,String des) {

           this.title = title;
this.des = des;


        }
        
                public String getTitle() {
                    return title;
                }
                public void setTitle(String title) {
                    this.title = title;
                }
            
                public String getDes() {
                    return des;
                }
                public void setDes(String des) {
                    this.des = des;
                }
            
    }
    