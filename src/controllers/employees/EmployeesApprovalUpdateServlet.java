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
 * Servlet implementation class EmployeesApprovalUpdateServlet
 */
@WebServlet("/employees/approvalUpdate")
public class EmployeesApprovalUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesApprovalUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //フォロー申請許可
        EntityManager em = DBUtil.createEntityManager();
        HttpSession session = ((HttpServletRequest)request).getSession();
        Employee e = (Employee)session.getAttribute("login_employee");
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());

        Approval approval = em.find(Approval.class, Integer.parseInt(request.getParameter("approve_id")));
        approval.setUpdated_at(currentTime);
        approval.setUpdateUser(e.getName());
        approval.setAnswerDate(currentTime);

        System.out.println(request.getParameter("status"));

        if (request.getParameter("status").contains("yes")) {
            approval.setAnswer("1");
        } else {
            approval.setAnswer("0");
        }

        em.getTransaction().begin();
        em.persist(approval);
        em.getTransaction().commit();
        em.close();
    }
}
