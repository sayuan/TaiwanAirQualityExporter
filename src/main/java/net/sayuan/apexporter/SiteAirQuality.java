package net.sayuan.apexporter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

/*
[
  {
    "CO": "0.39",
    "County": "基隆市",
    "FPMI": "4",
    "MajorPollutant": "懸浮微粒",
    "NO": "4.22",
    "NO2": "19",
    "NOx": "23.48",
    "O3": "32",
    "PM10": "62",
    "PM2.5": "42",
    "PSI": "54",
    "PublishTime": "2016-04-05 15:00",
    "SiteName": "基隆",
    "SO2": "7.2",
    "Status": "普通",
    "WindDirec": "90",
    "WindSpeed": "1.2"
  },
  ...
]
 */
public class SiteAirQuality {
    private double co;
    private String county;
    private int fpmi;
    private String majorPollutant;
    private double no;
    private double no2;
    private double nox;
    private double o3;
    private int pm10;
    private int pm2_5;
    private int psi;
    private DateTime publishTime;
    private String siteName;
    private double so2;
    private String status;
    private double windDirec;
    private double windSpeed;

    public double getCO() {
        return co;
    }

    @JsonProperty("CO")
    public void setCO(double co) {
        this.co = co;
    }

    public String getCounty() {
        return county;
    }

    @JsonProperty("County")
    public void setCounty(String county) {
        this.county = county;
    }

    public int getFPMI() {
        return fpmi;
    }

    @JsonProperty("FPMI")
    public void setFPMI(int fpmi) {
        this.fpmi = fpmi;
    }

    public String getMajorPollutant() {
        return majorPollutant;
    }

    @JsonProperty("MajorPollutant")
    public void setMajorPollutant(String majorPollutant) {
        this.majorPollutant = majorPollutant;
    }

    public double getNO() {
        return no;
    }

    @JsonProperty("NO")
    public void setNO(double no) {
        this.no = no;
    }

    public double getNO2() {
        return no2;
    }

    @JsonProperty("NO2")
    public void setNo2(double no2) {
        this.no2 = no2;
    }

    public double getNOx() {
        return nox;
    }

    @JsonProperty("NOx")
    public void setNOx(double nox) {
        this.nox = nox;
    }

    public double getO3() {
        return o3;
    }

    @JsonProperty("O3")
    public void setO3(double o3) {
        this.o3 = o3;
    }

    public int getPM10() {
        return pm10;
    }

    @JsonProperty("PM10")
    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPM2_5() {
        return pm2_5;
    }

    @JsonProperty("PM2.5")
    public void setPM2_5(int pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public int getPSI() {
        return psi;
    }

    @JsonProperty("PSI")
    public void setPSI(int psi) {
        this.psi = psi;
    }

    public DateTime getPublishTime() {
        return publishTime;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm")
    @JsonProperty("PublishTime")
    public void setPublishTime(
            DateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getSiteName() {
        return siteName;
    }

    @JsonProperty("SiteName")
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public double getSO2() {
        return so2;
    }

    @JsonProperty("SO2")
    public void setSO2(double so2) {
        this.so2 = so2;
    }

    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    public double getWindDirec() {
        return windDirec;
    }

    @JsonProperty("WindDirec")
    public void setWindDirec(double windDirec) {
        this.windDirec = windDirec;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    @JsonProperty("WindSpeed")
    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
