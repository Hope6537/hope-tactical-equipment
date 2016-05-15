
    package org.hope6537.dto;

    /**
     * 实体DTO
     * Created by hope6537 by Code Generator
     */
    public class FeedbackDto extends BasicDto {
    
            /**标题 */
            private String title;
            
            /**内容 */
            private String des;
            
            /**家长ID */
            private Integer parentId;
            
            /**回复 */
            private String reply;
            
    public FeedbackDto() {

    }
    
        public FeedbackDto(String title,String des,Integer parentId,String reply) {

           this.title = title;
this.des = des;
this.parentId = parentId;
this.reply = reply;


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
            
                public Integer getParentId() {
                    return parentId;
                }
                public void setParentId(Integer parentId) {
                    this.parentId = parentId;
                }
            
                public String getReply() {
                    return reply;
                }
                public void setReply(String reply) {
                    this.reply = reply;
                }
            
    }
    