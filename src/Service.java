public class Service {
    private String serviceCode;
    private String description;
    private float price;
    private Boolean isDeleted;
    private String reason;
    
    public Service(String serviceCode, String description, float price, Boolean isDeleted, String reason) {
        this.serviceCode = serviceCode;
        this.description = description;
        this.price = price;
        this.isDeleted = isDeleted;
        this.reason = reason;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
}
