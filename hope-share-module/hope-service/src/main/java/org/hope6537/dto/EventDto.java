
    package org.hope6537.dto;

    /**
     * 实体DTO
     * Created by hope6537 by Code Generator
     */
    public class EventDto extends BasicDto {
    
            /**标题 */
            private String title;
            
            /**内容 */
            private String des;
            
    public EventDto() {

    }
    
        public EventDto(String title,String des) {

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
    