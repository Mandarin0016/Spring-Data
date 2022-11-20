package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastRootSeedDTO {
    @XmlElement(name = "forecast")
    private List<ForecastSeedDTO> forecasts;

    public List<ForecastSeedDTO> getForecasts() {
        return forecasts;
    }
}
