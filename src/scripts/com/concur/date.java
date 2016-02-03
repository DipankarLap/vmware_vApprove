package scripts.com.concur;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class date {
	
//	Parameters :
//	period : no of days gap between the current and desired date
//	format : Format of the date (Ex : dd/MM/yyyy . yyyy MMM dd)
	public String getDate(int period,String format)
	{
	     Calendar currentDate = Calendar.getInstance();
	     SimpleDateFormat formatter= new SimpleDateFormat(format);
	     currentDate.add(Calendar.DAY_OF_MONTH, period);
	     String date = formatter.format(currentDate.getTime());
	     return date;
	}
	


}
