$(document).ready(function () {

$("#updateForm").on("submit", function(){
	
	$.ajax({
	    url: 'userInfo',
	    type: 'PUT',
	    dataType: "xml",
	    data:$("#updateForm").serialize(),
	    success: function(xml) {
	    	console.log(xml);
	    	var user="";
	    	$(xml).find('User').each(function(){
                $(this).find("empId").each(function(){
                    var id = $(this).text();
                    console.log(empId);
                    user=user+"ID: "+empId;
                });
                $(this).find("username").each(function(){
                    var name = $(this).text();
                    console.log(username);
                    user=user+" Name: "+username;
                });
                $(this).find("passwordHash").each(function(){
                    var age = $(this).text();
                    console.log(passwordHash);
                    user=user+" password: "+passwordHash;
                });
                
                $(this).find("role").each(function(){
                    var age = $(this).text();
                    console.log(role);
                    user=user+" role: "+role;
                });
            });
	    	alert(user);
	    }
	});
   return true;
 })
 
 $("#deleteForm").on("submit", function(){
	$.ajax({
	    url: 'rest/userInfo',
	    type: 'DELETE',
	    dataType: "xml",
	    data:$("#deleteForm").serialize(),
	    success: function(xml) {
	    	console.log(xml);
	    	$(xml).find('User').each(function(){
                $(this).find("id").each(function(){
                    var id = $(this).text();
                    console.log(id);
                    alert("Deleted the user with id "+id);
                });
            });
	    	
	    }
	});
   return true;
 })
});