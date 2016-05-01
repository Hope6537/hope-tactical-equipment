###hope-battleBack

RPC Service Provider/Client and RESTFul API Template

### Sub Modules

####hope-python-script 
    
    useful python scripts , such as process , transform , catch , generate data or codes

####hope-dependencies  
	
	manage dependencies of whole project
	
####hope-service-restful-client

	provide fast/stable RESTFul API. Base on Spring-Boot

####hope-service-default-implement
	
	implement java service interfaces by spring bean 

####hope-service-rpc-deploy

    packaging/deploy rpc service module
 
####hope-share-module  

	common class module , such as classes,tools,entry,domain...etc
	
#####hope-relation-database
	
	base relation database (mysql) data object operation , such as crud elements 

#####hope-service
	
	data transform object and base service overriding daos' methods and logic operation.but not explose the service.
	In Business Object .Services has assembly as a component.return by ResultSupport Class.
	Then explose to the rpc or restful client

#####hope-tools
	
	Commons tools such like serialize,encoding ,page,exception,object convert...
	
#####hope-custom-datastruct
	
	Custom datastructs for some situations
	

###hope-note-module

    Personal Note 