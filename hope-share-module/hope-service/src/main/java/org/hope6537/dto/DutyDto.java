
    package org.hope6537.dto;

    /**
     * 实体DTO
     * Created by hope6537 by Code Generator
     */
    public class DutyDto extends BasicDto {
    
            /**班级ID */
            private Integer classesId;
            
            /**教师ID */
            private Integer teacherId;
            
    public DutyDto() {

    }
    
        public DutyDto(Integer classesId,Integer teacherId) {

           this.classesId = classesId;
this.teacherId = teacherId;


        }
        
                public Integer getClassesId() {
                    return classesId;
                }
                public void setClassesId(Integer classesId) {
                    this.classesId = classesId;
                }
            
                public Integer getTeacherId() {
                    return teacherId;
                }
                public void setTeacherId(Integer teacherId) {
                    this.teacherId = teacherId;
                }
            
    }
    