<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>フォロー申請者 一覧</h2>
        <table id="applicantUser_list">
            <tbody>
                <tr>
                    <th>申請者</th>
                    <th>申請日</th>
                    <th>許可</th>
                    <th>否認</th>
                </tr>
                <c:forEach var="applicant" items="${ applicants }">
                  <tr id="${ applicant.id }">
                      <td><c:out value="${ applicant.employeeApplicant.name }" /></td>
                      <td><c:out value="${ applicant.created_at }" /></td>
                      <td><button onClick="approve(${ applicant.id },'yes')" style="background-color:#0000FF; color:#FFF">許可</button></td>
                      <td><button onClick="approve(${ applicant.id },'no')" style="background-color:#FF0000; color:#FFF">否認</button></td>
                  </tr>
                </c:forEach>
            </tbody>
        </table>
        <script>
          function approve(approveList_id,status) {
              var approve_id = approveList_id;
              $.ajax({
                  url: "approvalUpdate",
                  type: "POST",
                  data: {
                      "approve_id": approve_id,
                      "status": status
                  }
              }).done(function(data){
                  $("#"+approve_id).hide();
                  if (status == "yes") {
                      window.alert("許可しました。");
                  } else {
                      window.alert("否認しました。");
                  }
              }).fail(function(){
                  window.alert("ステータス変更できませんでした。");
              });
          }
        </script>
    </c:param>
</c:import>