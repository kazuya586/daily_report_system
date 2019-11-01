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
 * Servlet implementation class EmployeesIndexServlet
 */
@WebServlet("/employees/index")
public class EmployeesIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        HttpSession session = ((HttpServletRequest)request).getSession();
        Employee loginUser = (Employee)session.getAttribute("login_employee");
        int loginUser_id = loginUser.getId();

        //フォローボタン制御に必要
        List<Approval> approvals = em.createNamedQuery("getAllApprovalUsers", Approval.class).setParameter("applicantUser", loginUser_id).setParameter("deleteFlg", false).getResultList();

        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch(NumberFormatException e) {}

        List<Employee> employees = em.createNamedQuery("getAllEmployees",Employee.class).setFirstResult(15 * (page -1)).setMaxResults(15).getResultList();

        long employees_count = (long)em.createNamedQuery("getEmployeesCount",Long.class).getSingleResult();

        em.close();

        request.setAttribute("employees", employees);
        request.setAttribute("employees_count", employees_count);
        request.setAttribute("page", page);
        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        request.setAttribute("loginUser_id",loginUser_id);
        request.setAttribute("approvals", approvals);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/index.jsp");
        rd.forward(request, response);
    }
}
