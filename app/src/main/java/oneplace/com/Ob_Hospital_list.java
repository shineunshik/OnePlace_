package oneplace.com;

public class Ob_Hospital_list {

    String dutyAddr; //주소
    String dutyEmcls; //응급의료기관 분류
    String dutyEmclsName; //응급위료기관 분류명
    String dutyName; //기관명
    String dutyTel1; //대표전화
    String dutyTel3; //응급실전화
    String hpid; //기관 ID
    String phpid; //기관ID(OLD)
    String rnum; //일련번호
    String wgs84Lat; //위도
    String wgs84Lon; //경도

    Ob_Hospital_list(){

    }

    public String getDutyAddr() {
        return dutyAddr;
    }

    public void setDutyAddr(String dutyAddr) {
        this.dutyAddr = dutyAddr;
    }

    public String getDutyEmcls() {
        return dutyEmcls;
    }

    public void setDutyEmcls(String dutyEmcls) {
        this.dutyEmcls = dutyEmcls;
    }

    public String getDutyEmclsName() {
        return dutyEmclsName;
    }

    public void setDutyEmclsName(String dutyEmclsName) {
        this.dutyEmclsName = dutyEmclsName;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getDutyTel1() {
        return dutyTel1;
    }

    public void setDutyTel1(String dutyTel1) {
        this.dutyTel1 = dutyTel1;
    }

    public String getDutyTel3() {
        return dutyTel3;
    }

    public void setDutyTel3(String dutyTel3) {
        this.dutyTel3 = dutyTel3;
    }

    public String getHpid() {
        return hpid;
    }

    public void setHpid(String hpid) {
        this.hpid = hpid;
    }

    public String getPhpid() {
        return phpid;
    }

    public void setPhpid(String phpid) {
        this.phpid = phpid;
    }

    public String getRnum() {
        return rnum;
    }

    public void setRnum(String rnum) {
        this.rnum = rnum;
    }

    public String getWgs84Lat() {
        return wgs84Lat;
    }

    public void setWgs84Lat(String wgs84Lat) {
        this.wgs84Lat = wgs84Lat;
    }

    public String getWgs84Lon() {
        return wgs84Lon;
    }

    public void setWgs84Lon(String wgs84Lon) {
        this.wgs84Lon = wgs84Lon;
    }
}
