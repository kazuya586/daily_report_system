package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Approval;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesApprovalServlet
 */
@WebServlet("/employees/approval")
public class EmployeesApprovalServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesApprovalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //フォローボタンを押下時の処理
        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest)request).getSession();
        Employee e = (Employee)session.getAttribute("login_employee");
        Employee approvalUser = em.find(Employee.class, Integer.parseInt(request.getParameter("follow_user")));

        Approval approval = new Approval();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        approval.setCreated_at(currentTime);
        approval.setCreateUser(e.getName());
        approval.setUpdated_at(currentTime);
        approval.setUpdateUser(e.getName());
        approval.setDeleteFlg(false);
        approval.setEmployeeApplicant((Employee)request.getSession().getAttribute("login_employee"));
        approval.setEmployeeApproval(approvalUser);
        approval.setAnswer("0");
        approval.setAnswerDate(currentTime);
        em.getTransaction().begin();
        em.persist(approval);
        em.getTransaction().commit();
        em.close();
    }
}
