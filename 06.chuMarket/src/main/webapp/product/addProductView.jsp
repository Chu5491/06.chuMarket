<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="../javascript/calendar.js">
</script>

<script type="text/javascript">
<!--
function fncAddProduct()
{
	//Form 유효성 검증
 	var name = document.detailForm.prodName.value;
	var detail = document.detailForm.prodDetail.value;
	var manuDate = document.detailForm.manuDate.value;
	var price = document.detailForm.price.value;
	var stock = document.detailForm.prodStock.value;

	if(name == null || name.length<1)
	{
		alert("상품명은 반드시 입력하여야 합니다.");
		document.detailForm.prodName.focus();
		return;
	}
	if(detail == null || detail.length<1)
	{
		alert("상품상세정보는 반드시 입력하여야 합니다.");
		document.detailForm.prodDetail.focus();
		return;
	}
	if(manuDate == null || manuDate.length<1)
	{
		alert("제조일자는 반드시 입력하셔야 합니다.");
		document.detailForm.manuDate.focus();
		return;
	}
	if(price == null || price.length<1)
	{
		alert("가격은 반드시 입력하셔야 합니다.");
		document.detailForm.price.focus();
		return;
	}
	if(stock == null || stock < 1)
	{
		alert("수량은 반드시 1개 이상 입력하셔야 합니다.");
		document.detailForm.prodStock.focus();
		return;
	}

	document.detailForm.action='/addProduct.do';
	document.detailForm.submit();
}

function resetData()
{
	document.detailForm.reset();
}

function checkStock(value)
{
	if(value > 101)
	{
		alert('최대 100개 까지 등록할 수 있습니다!!');	
		document.detailForm.prodStock.value= 100;
	}
	
}

-->
</script>

<!DOCTYPE html>
<html>
	<head>
	<title>상품등록</title>
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	</head>
	<body bgcolor="#ffffff" text="#000000">
		<form name="detailForm" method="post">
			<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
				<tr>
					<td width="15" height="37">
						<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
					</td>
					<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">상품등록</td>
								<td width="20%" align="right">&nbsp;</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37">
						<img src="/images/ct_ttl_img03.gif"	width="12" height="37"/>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">
						상품명 
						<imgsrc="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle">
					</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="105">
									<c:if test="${empty prod }">
										<input type="text" name="prodName" class="ct_input_g" style="width: 100px; height: 19px" maxLength="20">
									</c:if>
									<c:if test="${! empty prod }">
										${prod.prodName }
									</c:if>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">
						상품상세정보 
						<img	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
					</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<c:if test="${empty prod }">
							<input type="text" name="prodDetail" class="ct_input_g" style="width: 100px; height: 19px" maxLength="10" minLength="6"/>
						</c:if>
						<c:if test="${! empty prod }">
							${prod.prodDetail }
						</c:if>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">
						제조일자 
						<img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
					</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<c:if test="${empty prod }">
							<input type="text" name="manuDate" readonly="readonly" class="ct_input_g" style="width: 100px; height: 19px"	maxLength="10" minLength="6"/>
							&nbsp;
							<img src="../images/ct_icon_date.gif" width="15" height="15" onclick="show_calendar('document.detailForm.manuDate', document.detailForm.manuDate.value)"/>
						</c:if>
						<c:if test="${! empty prod }">
							${prod.manuDate }
						</c:if>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">가격 
						<img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
					</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<c:if test="${empty prod }">
							<input type="text" name="price" 	class="ct_input_g" style="width: 100px; height: 19px" maxLength="10">&nbsp;원
						</c:if>
						<c:if test="${! empty prod }">
							${prod.price }
						</c:if>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
				<tr>
					<td width="104" class="ct_write">상품이미지</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<c:if test="${empty prod }">
							<input type="text" name="fileName" class="ct_input_g" style="width: 200px; height: 19px" maxLength="13"/>
						</c:if>
						<c:if test="${! empty prod }">
							${prod.fileName }
						</c:if>
					</td>
				</tr>
				<tr>
					<td width="104" class="ct_write">수량</td>
					<td bgcolor="D6D6D6" width="1"></td>
					<td class="ct_write01">
						<c:if test="${empty prod }">
							<input type="text" name="prodStock" class="ct_input_g" style="width: 50px; height: 19px" maxLength="13"
								onkeyup="javascript:checkStock(this.value);"/> 
							개
						</c:if>
						<c:if test="${! empty prod }">
							${prod.prodStock } 개
						</c:if>
					</td>
				</tr>
				<tr>
					<td height="1" colspan="3" bgcolor="D6D6D6"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
				<tr>
					<td width="53%"></td>
					<td align="right">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="17" height="23">
								<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
							</td>
							<c:if test="${empty prod }">
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"  style="padding-top: 3px;">
									<a href="javascript:fncAddProduct();">등록</a>
								</td>
							</c:if>
							<c:if test="${! empty prod }">
								<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
									<a href="/listProduct.do?menu=manage">확인</a>
								</td>
							</c:if>
								<td width="14" height="23">
									<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
								</td>
								<td width="30"></td>
								<td width="17" height="23">
									<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
								</td>
							<c:if test="${empty prod }">
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"	 style="padding-top: 3px;">
									<a href="javascript:resetData();">취소</a>
								</td>
							</c:if>
							<c:if test="${! empty prod }">
								<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
									<a href="../product/addProductView.jsp;">추가등록</a>
								</td>
							</c:if>
							<td width="14" height="23">
								<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
							</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>