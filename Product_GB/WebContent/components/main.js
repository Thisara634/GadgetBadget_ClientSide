$(document).ready(function() 
{  
	if ($("#alertSuccess").text().trim() == "")  
	{   
		$("#alertSuccess").hide();  
	} 
	$("#alertError").hide(); 
}); 

//SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 

	// Form validation-------------------  
	var status = validateProductForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 

	// If valid------------------------  
	var t = ($("#hidProductIDSave").val() == "") ? "POST" : "PUT";
	
	$.ajax(
	{
		url : "ProductApi",
		type : t,
		data : $("#formProduct").serialize(),
		dataType : "text",
		complete : function(response, status)
		{
			onProductSaveComplete(response.responseText, status);
		}
	});
}); 

function onProductSaveComplete(response, status){
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Saved.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Saving.");
		$("#slertError").show();
	}else{
		$("#alertError").text("Unknown Error while Saving.");
		$("#alertError").show();
	}
	$("#hidProductIDSave").val("");
	$("#formProduct")[0].reset();
}

//UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
		{     
	$("#hidProductIDSave").val($(this).closest("tr").find('#hidProductIDUpdate').val());     
	$("#ProductDate").val($(this).closest("tr").find('td:eq(0)').text());    
	$("#ProductType").val($(this).closest("tr").find('td:eq(1)').text());     
	$("#Description").val($(this).closest("tr").find('td:eq(2)').text());     
	$("#ProductNumber").val($(this).closest("tr").find('td:eq(3)').text()); 
	

});


//Remove Operation
$(document).on("click", ".btnRemove", function(event){
	$.ajax(
	{
		url : "ProductApi",
		type : "DELETE",
		data : "ProductID=" + $(this).data("productid"),
		dataType : "text",
		complete : function(response, status)
		{
			onProductDeletedComplete(response.responseText, status);
		}
	});
});

function onProductDeletedComplete(response, status)
{
	if(status == "success")
	{
		var resultSet = JSON.parse(response);
			
		if(resultSet.status.trim() == "success")
		{
			$("#alertSuccess").text("Successfully Deleted.");
			$("#alertSuccess").show();
					
			$("#divItemsGrid").html(resultSet.data);
	
		}else if(resultSet.status.trim() == "error"){
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	}else if(status == "error"){
		$("#alertError").text("Error While Deleting.");
		$("#alertError").show();
	}else{
		$("#alertError").text("Unknown Error While Deleting.");
		$("#alertError").show();
	}
}

//CLIENTMODEL
function validateProductForm() {  
	// Date  
	if ($("#ProductDate").val().trim() == "")  {   
		return "Insert Date.";  
		
	} 
	
	 // Type 
	if ($("#ProductType").val().trim() == "")  {   
		return "Insert Type.";  
	} 
	
	
	// Amount  
	if ($("#Description").val().trim() == "")  {   
		return "Insert Amount."; 
		 
	}
	 
	 // is numerical value  
	var tmpMobile = $("#ProductNumber").val().trim();  
	if (!$.isNumeric(tmpMobile))  {   
		return "Insert a numerical value for CardNumber.";  
		
	}
	 
	
		

	 
	 return true; 
	 
}
/**
 * 
 */