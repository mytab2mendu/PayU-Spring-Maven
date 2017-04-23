<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<title>Payment Processing</title>
	<script>
    function submitForm() {
      var postForm = document.forms.postForm;
      postForm.submit();
    }
</script>
</head>
<body onload="submitForm();">

<div>
<h2>Payment Gateway Testing Sample</h2>
<table>
<tr>
<td>Transaction Id</td>
<td><strong>'txnid' </strong></td>
<td>Amount: </td>
<td><strong>Rs. 'amount'</strong></td>
</tr>
</table>
<div >
<p>In this page we will genrate hash and send it to payumoney.</p>
<br>
<p>Please be patient. this process might take some time,<br />please do not hit refresh or browser back button or close this window</p>
</div>
</div>

<div>${values.hash}
	<form name="postForm" action="${values.action}" method="POST" >
		<input type="hidden" name="key" value="${values.key}" />
		<input type="hidden" name="hash" value="${values.hash}" />
		<input type="hidden" name="txnid" value="${values.txnid}" />
		<input type="hidden" name="amount" value="${values.amount}" />
		<input type="hidden" name="firstname" value="${values.firstname}" />
		<input type="hidden" name="email" value="${values.email}" />
		<input type="hidden" name="phone" value="${values.phone}" />
		<input type="hidden" name="productinfo" value="${values.productinfo}" />
		<input type="hidden" name="service_provider" value="${values.service_provider}" size="64" />
		<input type="hidden" name="surl" value="${values.surl}" />
		<input type="hidden" name="furl" value="${values.furl}" />
	</form>
</div>
</body>
</html>