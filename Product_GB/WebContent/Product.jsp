<%@page import="model.Product"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css"> 
 
<script src="components/jquery-3.2.1.min.js"></script>
<script src="components/main.js"></script>




                   

</head>
<body>


<br>
<br>


<<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Product  Management</h1>    
				
				<form id="formProduct" name="formProduct" method="post" action="Product.jsp">  
					Product Date:  
					<input id="ProductDate" name="ProductDate" type="text" class="form-control form-control-sm">  
					
					<br> 
					Product Type:  
					<input id="ProductType" name="ProductType" type="text" class="form-control form-control-sm">  
					
					<br>
					 Description:  
					 <input id="Description" name="Description" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Product Number:  
					 <input id="ProductNumber" name="ProductNumber" type="text" class="form-control form-control-sm">  
					 
					
					 
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidProductIDSave" name="hidProductIDSave" value=""> 
					 
				</form> 
				
				  </div>
                </div>
            </div>
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>
					
				

                

                        
                    </div>
                    <br>
                   <div id="divItemsGrid">   
					<%    
					Product productObj = new Product();
						out.print(productObj.readProduct());   
					%>  
					
					
					
                   
                </div>
            </div>
				  
 			</div>
 		 
 		</div>   
 		 <br>
</body>

 <br>
 
</html>