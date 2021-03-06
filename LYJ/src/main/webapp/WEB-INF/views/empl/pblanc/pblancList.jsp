<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

</script>
</head>
<body>

<div class="content-body"><!-- Zero configuration table -->
<section id="configuration">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-header">
                    <h4 class="cad-title">공고게시판</h4>
                    <a class="heading-elements-toggle"><i class="fa fa-ellipsis-v font-medium-3"></i></a>
                    <div class="heading-elements">
                        <ul class="list-inline mb-0">
                            <li><a data-action="collapse"><i class="ft-minus"></i></a></li>
                            <li><a data-action="reload"><i class="ft-rotate-cw"></i></a></li>
                            <li><a data-action="expand"><i class="ft-maximize"></i></a></li>
                            <li><a data-action="close"><i class="ft-x"></i></a></li>
                        </ul>
                        
                    </div>
                </div>
                <div class="card-content collapse show">
                    <div class="card-body card-dashboard">
                          <form action="/admin/pblanc/pblancList.do" method="post" class="form-inline pull-right" >
		                        <select class="form-control" name="search_keycode" style="margin: 3px; height: 40px" >
		                           <option>검색조건</option>
		                           <option value="TOTAL">전체</option>
		                           <option value="TITLE">공고명</option>
		                           <option value="WRITER">공고기관</option>
		                        </select>
		                        <input id="search_keyword" name="search_keyword" type="text" placeholder="검색어 입력..." class="form-control" style="height: 40px"/>
		                         <button type="submit"  class="btn btn-warning btn-min-width mr-1 mb-1" style="margin-left:3px; height: 40px; margin-top: 12px">검색</button>
<!-- 		                         <button type="button" id="btnForm" class="btn btn-danger btn-min-width mr-1 mb-1" value="계획서등록" style="  margin-top: 12px; width: 100px;height: 40px " >게시글 작성</button> -->
		                  </form>
                      
                    <div class="table-responsive">
                        <table class="table mb-0">
                            <thead class="bg-teal bg-lighten-4">
                                <tr>
                                    <th>#</th>
                                    <th>공고명</th>
                                    <th>공고기관</th>
                                    <th>제안서 마감일</th>
                                </tr>
                            </thead>
                            <tbody>
                            	<c:forEach items="${pblancList }" var="pblanc">
	                                <tr>
	                                    <td><input type="hidden" name="pblanc_id" value="${ pblanc.pblanc_link}"/>${pblanc.rnum }</td>
	                                    <td><a href="${pblanc.pblanc_link}" target="_blank">${pblanc.pblanc_nm }</a></td>
	                                    <td>${pblanc.pblanc_instt }</td>
	                                    <td>${pblanc.pblanc_clos}</td>
	                                </tr>
	                            </c:forEach>
                                
                            </tbody>
                        </table>
                      ${paging }
<!--                         <div class="col-lg-6 col-md-12"  > -->
<!--                   <nav aria-label="Page navigation"> -->
<!--                      <ul class="pagination justify-content-center"> -->
<!--                         <li class="page-item"> -->
<!--                            <a class="page-link" href="#" aria-label="Previous"> -->
<!--                               <span aria-hidden="true">« Prev</span> -->
<!--                               <span class="sr-only">Previous</span> -->
<!--                            </a> -->
<!--                         </li> -->
<!--                         <li class="page-item"><a class="page-link" href="#">1</a></li> -->
<!--                         <li class="page-item"><a class="page-link" href="#">2</a></li> -->
<!--                         <li class="page-item active"><a class="page-link" href="#">3</a></li> -->
<!--                         <li class="page-item"><a class="page-link" href="#">4</a></li> -->
<!--                         <li class="page-item"><a class="page-link" href="#">5</a></li> -->
<!--                         <li class="page-item"> -->
<!--                            <a class="page-link" href="#" aria-label="Next"> -->
<!--                               <span aria-hidden="true">Next »</span> -->
<!--                               <span class="sr-only">Next</span> -->
<!--                            </a> -->
<!--                         </li> -->
<!--                      </ul> -->
                     
<!--                   </nav> -->
               
<!--                </div> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

    </div>
</body>
</html>