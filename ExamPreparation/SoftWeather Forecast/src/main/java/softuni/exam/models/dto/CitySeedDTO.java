package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CitySeedDTO {
    @Expose
    @NotNull
    @Size(min = 2, max = 60)
    private String cityName;
    @Expose
    @NotNull
    @Size(min = 2)
    private String description;
    @Expose
    @NotNull
    @Min(500)
    private Integer population;
    @Expose
    private Long country;

    public String getCityName() {
        return cityName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPopulation() {
        return population;
    }

    public Long getCountry() {
        return country;
    }
}
