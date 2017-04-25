<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div>
		<h2>Payment Gateway Testing Sample</h2>
		<h3>Fill the form and submit it for starting the transaction...</h3>
	</div>

<div>
<table>
	<form name="postForm" action="payU_Process" method="POST" >
	<input type="hidden" name="key" value="" /><!--   -->
	<input type="hidden" name="service_provider" value="payu_paisa" />
	<tr><td>Environment</td><td><input type="radio" name="env" value="secure" />Live
	 &nbsp; &nbsp;<input type="radio" name="env" value="test" checked="checked" />DEV</td></tr>
	<tr><td>txnid</td><td>XXXXXXXXX<input type="hidden" name="txnid" value="" /></td></tr>
	<tr><td>amount</td><td><input type="text" name="amount" value="10.00" /></td></tr>
	<tr><td>firstname</td><td><input type="text" name="firstname" value="Shiva" /></td></tr>
	<tr><td>email</td><td><input type="text" name="email" value="gani247@gmail.com" /></td></tr>
	<tr><td>phone</td><td><input type="text" name="phone" value="9703362199" /></td></tr>
	<tr><td>productinfo</td><td><input type="text" name="productinfo" value="test product" /></td></tr>
	<tr><td>success url</td><td><input type="text" name="surl" value="http://localhost:8080/spring-guitar/payU_Success" size="64" /></td></tr>
	<tr><td>failure url</td><td><input type="text" name="furl" value="http://localhost:8080/spring-guitar/payU_Fail" size="64" /></td></tr>
	<tr><td><input type="submit" /></td><td><input type="reset" /></td></tr>
	</form>
</table>
</div>
</body>
</html>