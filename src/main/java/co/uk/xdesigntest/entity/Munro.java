package co.uk.xdesigntest.entity;

import co.uk.xdesigntest.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Munro extends BaseEntity {

    @JsonIgnore
    @CsvBindByName(column = "Running No")
    private int runningNo;

    @JsonIgnore
    @CsvBindByName(column = "DoBIH Number")
    private int dobihNumber;

    @JsonIgnore
    @CsvBindByName(column = "Streetmap")
    private String stretmap;

    @JsonIgnore
    @CsvBindByName(column = "Geograph")
    private String geograph;

    @JsonIgnore
    @CsvBindByName(column = "Hill-bagging")
    private String hillBagging;

    @CsvBindByName(column = "Name")
    private String name;

    @JsonIgnore
    @CsvBindByName(column = "SMC Section")
    private int smcSection;

    @JsonIgnore
    @CsvBindByName(column = "RHB Section")
    private String rhbSection;

    @JsonIgnore
    @CsvBindByName(column = "_Section")
    private String section;

    @CsvBindByName(column = "Height (m)")
    private double heightInMetre;

    @JsonIgnore
    @CsvBindByName(column = "Height (ft)")
    private double heightInFeet;

    @JsonIgnore
    @CsvBindByName(column = "Map 1:50")
    private String map150;

    @JsonIgnore
    @CsvBindByName(column = "Map 1:25")
    private String map125;

    @CsvBindByName(column = "Grid Ref")
    private String gridRef;

    @JsonIgnore
    @CsvBindByName(column = "GridRefXY")
    private String gridRefXY;

    @JsonIgnore
    @CsvBindByName(column = "xcoord")
    private int xcoord;

    @JsonIgnore
    @CsvBindByName(column = "ycoord")
    private int ycoord;

    @JsonIgnore
    @CsvBindByName(column = "1891")
    private String _1891;

    @JsonIgnore
    @CsvBindByName(column = "1921")
    private String _1921;

    @JsonIgnore
    @CsvBindByName(column = "1933")
    private String _1933;

    @JsonIgnore
    @CsvBindByName(column = "1953")
    private String _1953;

    @JsonIgnore
    @CsvBindByName(column = "1969")
    private String _1969;

    @JsonIgnore
    @CsvBindByName(column = "1974")
    private String _1974;

    @JsonIgnore
    @CsvBindByName(column = "1981")
    private String _1981;

    @JsonIgnore
    @CsvBindByName(column = "1984")
    private String _1984;

    @JsonIgnore
    @CsvBindByName(column = "1990")
    private String _1990;

    @JsonIgnore
    @CsvBindByName(column = "1997")
    private String _1997;

    @JsonProperty("hillCategory")
    @CsvBindByName(column = "Post 1997")
    private String post1997;

    @JsonIgnore
    @CsvBindByName(column = "Comments")
    private String comments;
}