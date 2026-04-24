package domain.user;

public class IndividualUser extends User {
    private String studentId;

    public IndividualUser(String email, String displayName, String studentId) {
        super(email, displayName);
        this.studentId = studentId;
    }

    // Konstruktor bez studentId jeśli opcjonalny
    public IndividualUser(String email, String displayName) {
        super(email, displayName);
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        if (studentId == null)
            return super.toString();
        return super.toString() + " Student ID: " + studentId;
    }
}
