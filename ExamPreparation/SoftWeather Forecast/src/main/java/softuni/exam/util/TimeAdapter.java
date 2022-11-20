package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeAdapter extends XmlAdapter<String, Time> {
    @Override
    public Time unmarshal(String v) throws Exception {
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return new Time(formatter.parse(v).getTime());
    }

    @Override
    public String marshal(Time v) throws Exception {
        return v.toString();
    }
}
