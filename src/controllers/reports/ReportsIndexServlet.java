package controllers.reports;

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
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsIndexServlet
 */
@WebServlet("/reports/index")
public class ReportsIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();
        int page;
        HttpSession session = ((HttpServletRequest)request).getSession();

        if (request.getParameter("employee_id") == null || request.getParameter("employee_id").isEmpty()){
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch(Exception e) {
                page = 1;
            }

            List<Report> reports = em.createNamedQuery("getMyAllReports",Report.class).setParameter("employee", (Employee)session.getAttribute("login_employee")).setFirstResult(15*(page - 1)).setMaxResults(15).getResultList();
            long reports_count = (long)em.createNamedQuery("getMyReportsCount",Long.class).setParameter("employee", (Employee)session.getAttribute("login_employee")).getSingleResult();

            request.setAttribute("selectedUser", null);
            request.setAttribute("reports", reports);
            request.setAttribute("reports_count", reports_count);
        } else {
            int approvalUser_id = Integer.parseInt(request.getParameter("employee_id"));
            Employee employee = em.find(Employee.class,approvalUser_id );
            page = 1;

            List<Report> reports = em.createNamedQuery("getMyAllReports",Report.class).setParameter("employee", employee).setFirstResult(15*(page - 1)).setMaxResults(15).getResultList();
            long reports_count = (long)em.createNamedQuery("getMyReportsCount",Long.class).setParameter("employee", employee).getSingleResult();

            request.setAttribute("selectedUser", approvalUser_id);
            request.setAttribute("reports", reports);
            request.setAttribute("reports_count", reports_count);
        }

        if (request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }


        Employee e = (Employee)session.getAttribute("login_employee");
        List<Approval> results = em.createNamedQuery("getAllDiaryOfApprovalUsers",Approval.class).setParameter("applicantUser", e.getId()).getResultList();

        em.close();

        request.setAttribute("loginUser", e);
        request.setAttribute("page", page);
        request.setAttribute("results", results);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/index.jsp");
        rd.forward(request, response);
    }
}
