package domain.user;

public class CompanyUser extends User {
    private String companyName;
    private String taxId; //NIP

    public CompanyUser(String email, String displayName, String companyName, String taxId) {
        super(email, displayName);
        this.companyName = companyName;
        this.taxId = taxId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Override
    public String toString() {
        return super.toString() + " Company Name: " + companyName + " Tax ID: " + taxId;
    }
}
