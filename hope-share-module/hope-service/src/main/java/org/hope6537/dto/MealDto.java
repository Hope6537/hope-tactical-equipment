
    package org.hope6537.dto;

    /**
     * 实体DTO
     * Created by hope6537 by Code Generator
     */
    public class MealDto extends BasicDto {
    
            /**早餐 */
            private String breakfast;
            
            /**午餐 */
            private String lunch;
            
            /**晚餐 */
            private String dinner;
            
            /**星期几 */
            private Integer day;
            
    public MealDto() {

    }
    
        public MealDto(String breakfast,String lunch,String dinner,Integer day) {

           this.breakfast = breakfast;
this.lunch = lunch;
this.dinner = dinner;
this.day = day;


        }
        
                public String getBreakfast() {
                    return breakfast;
                }
                public void setBreakfast(String breakfast) {
                    this.breakfast = breakfast;
                }
            
                public String getLunch() {
                    return lunch;
                }
                public void setLunch(String lunch) {
                    this.lunch = lunch;
                }
            
                public String getDinner() {
                    return dinner;
                }
                public void setDinner(String dinner) {
                    this.dinner = dinner;
                }
            
                public Integer getDay() {
                    return day;
                }
                public void setDay(Integer day) {
                    this.day = day;
                }
            
    }
    