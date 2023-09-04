package oneplace.com;

public class Ob_Bus_Station_Info_list {

        String arrPlaceNm; //도착지
        String arrPlandTime; //도착시간
        String charge; //운임
        String depPlaceNm; //출발지
        String depPlandTime; //출발시간
        String gradeNm; //버스등급
        String routeId; //노선ID

    public Ob_Bus_Station_Info_list(){
    }
    public Ob_Bus_Station_Info_list(String arrPlaceNm, String arrPlandTime, String charge, String depPlaceNm, String depPlandTime, String gradeNm, String routeId){
        this.arrPlaceNm=arrPlaceNm;
        this.arrPlandTime=arrPlandTime;
        this.charge=charge;
        this.depPlaceNm=depPlaceNm;
        this.depPlandTime=depPlandTime;
        this.gradeNm=gradeNm;
        this.routeId=routeId;

    }

    public String getArrPlaceNm() {
        return arrPlaceNm;
    }

    public void setArrPlaceNm(String arrPlaceNm) {
        this.arrPlaceNm = arrPlaceNm;
    }

    public String getArrPlandTime() {
        return arrPlandTime;
    }

    public void setArrPlandTime(String arrPlandTime) {
        this.arrPlandTime = arrPlandTime;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getDepPlaceNm() {
        return depPlaceNm;
    }

    public void setDepPlaceNm(String depPlaceNm) {
        this.depPlaceNm = depPlaceNm;
    }

    public String getDepPlandTime() {
        return depPlandTime;
    }

    public void setDepPlandTime(String depPlandTime) {
        this.depPlandTime = depPlandTime;
    }

    public String getGradeNm() {
        return gradeNm;
    }

    public void setGradeNm(String gradeNm) {
        this.gradeNm = gradeNm;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
}
