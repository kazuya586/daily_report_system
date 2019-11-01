package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Table(name = "approval")

@NamedQueries({
    @NamedQuery(
        name = "getAllApprovalUsers",
        query = "SELECT a FROM Approval AS a WHERE a.employeeApplicant.id = :applicantUser AND a.deleteFlg = :deleteFlg"
    ),
    @NamedQuery(
        name = "getAllApplicantUsers",
        query = "SELECT a FROM Approval AS a WHERE a.employeeApproval.id = :approvalUser AND a.answer = '0' AND a.deleteFlg = false"
    ),
    @NamedQuery(
        name = "getAllDiaryOfApprovalUsers",
        query = "SELECT a FROM Approval AS a WHERE a.employeeApplicant.id = :applicantUser AND a.answer = '1' AND a.deleteFlg = false"
    )
})

@Entity
public class Approval {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "createUser", nullable = false,length=255)
    private String createUser;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "updateUser", nullable = false)
    private String updateUser;

    @Column(name = "deleteFlg", nullable = false)
    private Boolean deleteFlg;

    @ManyToOne
    @JoinColumn(name = "applicantUser", nullable= false)
    private Employee employeeApplicant;

    @ManyToOne
    @JoinColumn(name = "approvalUser", nullable= false)
    private Employee employeeApproval;

    @Column(name = "answer",nullable = false)
    private String answer;

    @Column(name = "answerDate",nullable = false)
    private Timestamp answerDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Boolean getDeleteFlg() {
        return deleteFlg;
    }

    public void setDeleteFlg(Boolean deleteFlg) {
        this.deleteFlg = deleteFlg;
    }

    public Employee getEmployeeApplicant() {
        return employeeApplicant;
    }

    public void setEmployeeApplicant(Employee employeeApplicant) {
        this.employeeApplicant = employeeApplicant;
    }

    public Employee getEmployeeApproval() {
        return  employeeApproval;
    }

    public void setEmployeeApproval(Employee employeeApproval) {
        this.employeeApproval = employeeApproval;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Timestamp getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Timestamp answerDate) {
        this.answerDate = answerDate;
    }

}
