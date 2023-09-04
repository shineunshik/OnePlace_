package oneplace.com;

public class Ob_Bus_Station_list {

        String terminalId;
        String terminalNm;
        String address;

    public Ob_Bus_Station_list(){
    }
    public Ob_Bus_Station_list(String terminalId, String terminalNm,String address){
        this.terminalId=terminalId;
        this.terminalNm=terminalNm;
        this.address=address;

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
}
