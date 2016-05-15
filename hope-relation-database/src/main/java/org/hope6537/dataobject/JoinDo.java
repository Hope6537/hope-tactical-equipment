
    package org.hope6537.dataobject;

    /**
     * 实体DO
     * Created by hope6537 by Code Generator
     */
    public class JoinDo extends BasicDo {
    
            /**活动ID */
            private Integer eventId;
            
            /**学生ID */
            private Integer studentId;
            
    public JoinDo() {

    }
    
        public JoinDo(Integer eventId,Integer studentId) {

           this.eventId = eventId;
this.studentId = studentId;


        }
        
                public Integer getEventId() {
                    return eventId;
                }
                public void setEventId(Integer eventId) {
                    this.eventId = eventId;
                }
            
                public Integer getStudentId() {
                    return studentId;
                }
                public void setStudentId(Integer studentId) {
                    this.studentId = studentId;
                }
            
    }
    