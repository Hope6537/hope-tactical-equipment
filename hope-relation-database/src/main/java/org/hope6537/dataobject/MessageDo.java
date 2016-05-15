
    package org.hope6537.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class MessageDo extends BasicDo {
    
            /**通知ID */
            private Integer noticeId;
            
            /**家长ID */
            private Integer parentId;
            
    public MessageDo() {

    }
    
        public MessageDo(Integer noticeId,Integer parentId) {

           this.noticeId = noticeId;
this.parentId = parentId;


        }
        
                public Integer getNoticeId() {
                    return noticeId;
                }
                public void setNoticeId(Integer noticeId) {
                    this.noticeId = noticeId;
                }
            
                public Integer getParentId() {
                    return parentId;
                }
                public void setParentId(Integer parentId) {
                    this.parentId = parentId;
                }
            
    }
    