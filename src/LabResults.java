
public class LabResults 
{

    String rUID;
    String pUID;
    String reqDate;
    String reqTime;
    String results;

    /*/
     *  Empty Constructor
    /*/
    public LabResults() {
    }

    /*/
     *  Constructor
    /*/
    public LabResults(String rUID, String pUID, String reqDate, String reqTime, String results) {
        this.rUID = rUID;
        this.pUID = pUID;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
        this.results = results;
    }

    /*/
     *  Getters and Setters
    /*/

    public String getrUID() {
        return rUID;
    }

    public void setrUID(String rUID) {
        this.rUID = rUID;
    }

    public String getpUID() {
        return pUID;
    }

    public void setpUID(String pUID) {
        this.pUID = pUID;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

}
