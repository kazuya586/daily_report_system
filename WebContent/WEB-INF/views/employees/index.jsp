<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${ flush != null }">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>従業員 一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                    <th>フォローボタン</th>
                </tr>
                <% Boolean approvalFlg = false; %>
                <c:forEach var="employee" items="${ employees }" varStatus="status">
                    <input type="hidden" id="userCd" value="<c:out value='${ employee.id }' />">
                    <tr class="row${ status.count % 2 }">
                        <td><c:out value="${ employee.code }" /></td>
                        <td><c:out value="${ employee.name }" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${ employee.delete_flag == 1 }">
                                    (削除済み)
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/employees/show?id=${ employee.id } ' />" >詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                              <c:when test="${ (loginUser_id != employee.id) && (employee.delete_flag == 0) }">
                                <c:forEach var="approval" items="${ approvals }">
                                  <c:choose>
                                    <c:when test="${ approval.employeeApproval.id == employee.id }">
                                      <button style="background-color:#ff4500; color: #FFF" disabled>フォロー済み</button>
                                      <% approvalFlg = true; %>
                                    </c:when>
                                  </c:choose>
                                </c:forEach>
                                <% if (!approvalFlg){ %>
                                  <button id="${ employee.id }" onClick="follow(${ employee.id })" style="background-color:#668ad8;color: #FFF;" >フォロー</button>
                                <% } else {
                                     approvalFlg = false;
                                   } %>
                              </c:when>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            (全 ${ employees_count } 件) <br />
            <c:forEach var="i" begin="1" end="${ ((employees_count - 1) / 15) + 1 }" step="1">
                <c:choose>
                    <c:when test="${ i == page }">
                        <c:out value="${ i }" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/employees/index?page=${i}' />"><c:out value="${ i }" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <c:if test="${sessionScope.login_employee.admin_flag == 1}">
            <p><a href="<c:url value='/employees/new' />">新規従業員の登録</a></p>
        </c:if>
        <script>
        function follow(num) {
            var userId = num;

            $.ajax({
                url: "approval",
                type: "POST",
                data: { "follow_user": userId },
              }
            ).done(function(data) {
                $("#"+userId).text("フォロー済み");
                $("#"+userId).prop("disabled",true);
                $("#"+userId).css({
                    'background-color': '#ff4500',
                    'color': '#FFF'
                });

              }).fail(function() {
                  window.alert("フォローできませんでした。");
              });
        }
        </script>
    </c:param>
</c:import>