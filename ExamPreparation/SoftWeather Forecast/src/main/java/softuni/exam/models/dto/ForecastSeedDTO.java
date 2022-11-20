package softuni.exam.models.dto;

import softuni.exam.models.enums.DayOfWeek;
import softuni.exam.util.TimeAdapter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Time;

@XmlRootElement(name = "forecast")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedDTO {
    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @NotNull
    @Min(-20)
    @Max(60)
    @XmlElement(name = "max_temperature")
    private Double maxTemperature;
    @NotNull
    @Min(-50)
    @Max(40)
    @XmlElement(name = "min_temperature")
    private Double minTemperature;
    @NotNull
    @XmlElement(name = "sunrise")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private Time sunrise;
    @NotNull
    @XmlElement(name = "sunset")
    @XmlJavaTypeAdapter(TimeAdapter.class)
    private Time sunset;
    @XmlElement(name = "city")
    private Long cityId;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public Double getMinTemperature() {
        return minTemperature;
    }

    public Time getSunrise() {
        return sunrise;
    }

    public Time getSunset() {
        return sunset;
    }

    public Long getCityId() {
        return cityId;
    }
}
