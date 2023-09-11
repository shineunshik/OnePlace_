package oneplace.com;

public class Ob_Bus_Station_list {

        String terminalId;
        String terminalNm;
        String address;
        String station_use;

    public Ob_Bus_Station_list(){
    }
    public Ob_Bus_Station_list(String terminalId, String terminalNm,String address,String station_use){
        this.terminalId=terminalId;
        this.terminalNm=terminalNm;
        this.address=address;
        this.station_use=station_use;

    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalNm() {
        return terminalNm;
    }

    public void setTerminalNm(String terminalNm) {
        this.terminalNm = terminalNm;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation_use() {
        return station_use;
    }

    public void setStation_use(String station_use) {
        this.station_use = station_use;
    }
}
