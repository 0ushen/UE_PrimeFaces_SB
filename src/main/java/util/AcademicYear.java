package util;


public class AcademicYear implements Comparable<AcademicYear> {
       
    private String name;
    private int year;
    
    @Override
    public boolean equals(Object o) {
        if(o != null){
            AcademicYear ay = (AcademicYear) o;
            if(name.equals(ay.getName()) && year == ay.getYear())
                return true;
             else
                return false;
        }
        else{
            return false;
        }
    }
    
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    
    @Override
    public int compareTo(AcademicYear ay){ 
        
        if(this.year > ay.getYear()) 
            return 1; 
        if(this.year < ay.getYear()) 
            return -1;
        else                   
            return 0;
        
    } 
    
    public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public Integer getYear() {
         return year;
     }

     public void setYear(Integer year) {
         this.year = year;
     }


}