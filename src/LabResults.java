
public class LabResults 
{

    String serviceCode;
    String rUID;
    String pUID;
    String reqDate;
    String reqTime;
    String results;
    Boolean isDeleted;
    String delReason;

    /*/
     *  Empty Constructor
    /*/
    public LabResults() {
    }

    /*/
     *  Constructor
    /*/
    public LabResults(String rUID, String pUID, String reqDate, String reqTime, String results, String serviceCode) {
        this.rUID = rUID;
        this.pUID = pUID;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
        this.results = results;
    }

    public LabResults(String rUID, String pUID, String reqDate, String reqTime, String results, Boolean isDeleted,
            String delReason, String serviceCode) {
        this.rUID = rUID;
        this.pUID = pUID;
        this.reqDate = reqDate;
        this.reqTime = reqTime;
        this.results = results;
        this.isDeleted = isDeleted;
        this.delReason = delReason;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getDelReason() {
        return delReason;
    }

    public void setDelReason(String delReason) {
        this.delReason = delReason;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

}