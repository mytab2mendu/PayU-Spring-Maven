<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div>
		<h2>Payment Details When Failed</h2>
	</div>
<a href='./payU_Home'>Retry Here</a>
	<div>
		
		
		<p>Txn Id: ${param.txnid}</p>
		<p>status:	${param.status}</p>
		<p>Name: ${param.firstname}</p>
		<p>Email: ${param.email}</p>
		<p>Amount: ${param.amount}</p>
		<p>Phone No: ${param.phone}</p>
		<p>Product Info: ${param.productinfo}</p>
		<%-- <p>encryptedPaymentId: ${param.encryptedPaymentId}</p> --%>
				
	</div>
</body>
</html>