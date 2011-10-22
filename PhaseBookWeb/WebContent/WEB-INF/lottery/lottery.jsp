<%@ page import="phasebook.controller.*" %>

<img id="imgcharity" src="http://www.medicalaidfilms.org/images/10.jpg">

<h1>Charity Lottery</h1>

<p>
	By placing bets on our Lottery you are helping many charity associations raise funds for social projects.
	The more you bet, the more you are helping!
</p>
<p>
	To bet, simply guess a number between 1 and 100. You pay 1 L&euro; for every bet you do.
	If you guessed the right number you win 50 L&euro;.
</p>

<h2>Next Draw: 12h00 23/10/2011</h2>

<form method="POST" action="BetForm">

	<select name="number">
		<% for (int i=1; i<=100; i++) { %>
			<option value="<%= i %>"><%= i %></option>
		<% } %>
	</select>
	<input type="submit" value="Place bet">
	<p class="tip">
		(costs 1 L&euro;, you have <%= Utils.getUserBean().getUserById(session.getAttribute("id")).getMoney() %> L&euro;)
	</p>
	
</form>

<h2>Get more money</h2>

<form method="POST" action="GetMoneyForm">

	<input type="text" name="money">
	<input type="submit" value="Deposit L&euro;">

</form>

<h2>Historic</h2>

<p>
	You have never placed any bet.
</p>