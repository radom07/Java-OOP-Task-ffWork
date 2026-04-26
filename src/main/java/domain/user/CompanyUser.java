package domain.user;

public class CompanyUser extends User {
    private String companyName;
    private String taxId; //NIP

    public CompanyUser(String email, String displayName, String companyName, String taxId) {
        if (taxId == null || taxId.isBlank())
            throw new IllegalArgumentException("Tax ID cannot be empty.");
        super(email, displayName);
        this.companyName = companyName;
        this.taxId = taxId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTaxId() {
        return taxId;
    }

    @Override
    public String toString() {
        return super.toString() + " Company Name: " + companyName + " Tax ID: " + taxId;
    }
}
