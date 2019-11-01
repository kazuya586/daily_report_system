package controllers.employees;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class EmployeesApplicantListServlet
 */
@WebServlet("/employees/applicantList")
public class EmployeesApplicantListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesApplicantListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest)request).getSession();
        Employee e = (Employee)session.getAttribute("login_employee");

        List<Approval> applicants = em.createNamedQuery("getAllApplicantUsers",Approval.class).setParameter("approvalUser", e.getId()).getResultList();
        request.setAttribute("applicants", applicants);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/applicantList.jsp");
        rd.forward(request, response);
    }
}
