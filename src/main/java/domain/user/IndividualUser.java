package domain.user;

public class IndividualUser extends User {
    private String studentId;

    public IndividualUser(String email, String displayName, String studentId) {
        super(email, displayName);
        this.studentId = studentId;
    }

    public IndividualUser(String email, String displayName) {
        super(email, displayName);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return super.toString() + " Student ID: " + studentId;
    }
}
