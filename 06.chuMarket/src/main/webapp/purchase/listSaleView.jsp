<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
	<title>구매 목록조회</title>
	<meta charset="euc-kr">
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	</head>

	<body bgcolor="#ffffff" text="#000000">
		<div style="width: 98%; margin-left: 10px;">
			<form name="detailForm" action="/listPurchase.do" method="post">
				<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
					<tr>
						<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
						<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="93%" class="ct_ttl01">구매 목록조회</td>
								</tr>
							</table>
						</td>
						<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
					</tr>
				</table>
		
				<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
					<tr>
						<td class="ct_list_b" width="100">No</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="150">회원ID</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="150">회원명</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">전화번호</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">구매수량</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">배송현황</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b">정보수정</td>
					</tr>
					<tr>
						<td colspan="13" bgcolor="808285" height="1"></td>
					</tr>
					<c:if test="${! empty list }">
						<c:forEach var="pur" items="${list}">
						<c:set var="i" value="${i+1}"/>
							<tr class="ct_list_pop">
								<td align="center">
									<a href="/getPurchase.do?tranNo=${pur.tranNo}">
										${i}
									</a>
								</td>
								<td></td>
								<td align="left">
									<a href="/getUser.do?userId=${pur.buyer.userId}">
										${pur.buyer.userId}
									</a>
								</td>
								<td></td>
								<td align="left">
									${pur.buyer.userName}
								</td>
								<td></td>
								<td align="left">${pur.receiverPhone}</td>
								<td></td>
								<td align="left">${pur.tranStock} 개</td>
								<td></td>
								<td align="left">
									<c:if test="${pur.tranCode == '1'}">
										판매완료
										<a href="/updateTranCode.do?tranNo=${pur.tranNo}&tranCode=${pur.tranCode}&menu=sale">
								  			배송하기
								  		</a>
									</c:if>
									<c:if test="${pur.tranCode == '2'}">
										배송중
									</c:if>
									<c:if test="${pur.tranCode == '3'}">
										배송완료
									</c:if>
								</td>
								<td></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="13" bgcolor="D6D7D6" height="1"></td>
						</tr>
					</c:if>
					<c:if test="${empty list }">
						<tr>
							<td colspan="13" height="3" align="center"><h3>현재 상품은 구매 정보가 없습니다!</h3></td>
						</tr>
					</c:if>
				</table>
		</div>
	</body>
</html>