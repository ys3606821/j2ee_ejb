<%@ page import="phasebook.controller.*"%>
<%@ page import="javax.naming.*" %>
<%@ page import="phasebook.user.*" %>
<%@ page import="phasebook.post.*" %>
<%@ page import="phasebook.friendship.*" %>
<%@ page import="java.util.*" %>

<% 
	PhasebookUserRemote userBean = Utils.getUserBean();
	PhasebookUser user;
	Object userId;
	Friendship fs;
	PhasebookUser me;
	me=userBean.getUserById(session.getAttribute("id"));
	int relationshipType = -1;
	if(request.getParameter("id") == null){
		userId =  session.getAttribute("id");
		user = userBean.getUserById(userId);
	}
	else{
		userId =  request.getParameter("id");
		try {
			Utils.getUserBean().getUserById(request.getParameter("id")).getName();
			user = userBean.getUserById(userId);
			relationshipType = Utils.getFriendshipBean().friendshipStatus(me,user);
		} catch (Exception e) {
			userId =  session.getAttribute("id");
			user = userBean.getUserById(session.getAttribute("id"));
		}
	}
	
	
%>
<% if (user.getPhoto()!=null){ 
	String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+user.getPhoto().getName();
%>
	<br /> <%= Utils.a("user&id="+userId.toString(), Utils.img(photoURL)) %>
<%} %>

<form method="POST" action="CreateFriendshipForm">
	<input type="hidden" name="toUser" value="<%= userId.toString() %>"/>
	<input type="hidden" name="relationship" value="<%= relationshipType %>"/>
<%
	if(request.getParameter("id")!=null && request.getParameter("id") != session.getAttribute("id")){
   		if(relationshipType==0){%>
			<input type="submit" value="Add Friend" name="F0">
<%		}else if(relationshipType==1){%>
			<input type="submit" value="Friendship request sent" name="F1" disabled="disabled">
<%		}else if(relationshipType==2){ %>
			<input type="submit" value="Accept Friendship" name="F2">	
<%		}else if(relationshipType==3){}}%>

</form>

<h1><%= Utils.text(user.getName()) %></h1> 
<p class="tip"><%= Utils.text(user.getEmail()) %></p>

<%
	if (session.getAttribute("error") != null)
	{
%>
		<p style="color:red"><%= session.getAttribute("error") %></p>
<%
		session.removeAttribute("error");
	}
%>

<%
	String post = "";
	String privacy = "0";
	try {
		post = session.getAttribute("post").toString();
		session.removeAttribute("post");
		privacy = session.getAttribute("privacy").toString();
		session.removeAttribute("privacy");
	} catch (Exception e) {}
%>

<%
	List<Post> posts = null;
	if (Utils.getFriendshipBean().searchFriendship(me, user) != null || me.equals(user) )
		 posts = userBean.getUserReceivedPosts(userId);
	else
		posts = userBean.getUserPublicPosts(userId);
	for (int i=posts.size()-1; i>=0; i--) {
%>
	<p>
		<% if (posts.get(i).getPhoto()!=null){ 
			String photoURL = Utils.MAIN_PATH+userId.toString()+"/"+posts.get(i).getPhoto().getName();
		%>
			<br /> <%= Utils.aAbsolute(photoURL, Utils.img(photoURL)) %>
		<%} %>
	</p>
<%
	}
%>