package util;

import entity.OrganizedUe;
import org.omnifaces.util.Messages;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@ApplicationScoped
public class Utils implements Serializable {

    private static String datePattern = "dd/MM/yyyy";


    @PostConstruct
    public void init() {
    }
    
    public static void addDetailMessage(String message) {
        addDetailMessage(message, null);
    }

    public static void addDetailMessage(String message, FacesMessage.Severity severity) {

        FacesMessage facesMessage = Messages.create("").detail(message).get();
        if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
            facesMessage.setSeverity(severity);
        }
        Messages.add(null, facesMessage);
    }

   public static String customFormatDate(Date date) {
       
        if (date != null) {
            DateFormat format = new SimpleDateFormat(datePattern);
            return format.format(date);
         }
        return "";
   }
   
   public static AcademicYear findAcademicYear(Date d){
       
       LocalDate date = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       AcademicYear ay = new AcademicYear();
       switch (date.getMonth()) {
           case SEPTEMBER : 
           case OCTOBER :
           case NOVEMBER : 
           case DECEMBER :
               ay.setYear(date.getYear());
               ay.setName(date.getYear() + " - " + (date.getYear() + 1));
               break;
           case JANUARY :
           case FEBRUARY :
           case MARCH :
           case APRIL :
           case MAY :
           case JUNE :
           case JULY :
           case AUGUST :
               ay.setYear(date.getYear() -1);
               ay.setName((date.getYear() - 1) + " - " + date.getYear());
               break;
               
           default : 
               System.out.println("Error in findAcademicYear() --> in default case");
               
       }
       
       return ay;
   }
    
    public static TreeSet<AcademicYear> findExistingAcademicYears(List<OrganizedUe> organizedUeList) {
        
        TreeSet<AcademicYear> existingAcademicYearsSet = new TreeSet<>();
        for(OrganizedUe o : organizedUeList) {
            AcademicYear ay = findAcademicYear(o.getStartDate());
            existingAcademicYearsSet.add(ay);
        }
        
        return existingAcademicYearsSet;
    }
    
    public static Date combineDateAndTime(Date date, Date time) {
        
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        Calendar calTime = Calendar.getInstance();
        calTime.setTime(time);
        calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
        calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
        
        Date dateWithTime = calDate.getTime();
        
        return dateWithTime;
    }

}
