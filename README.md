# workflow-Backend

The end points :
    
    ApplicationController =>>
    POST: /api/application/ --create new application
    POST: /api/application/{id} --stage acion application
    GET : /api/application/ -- get all application list
    GET : /api/application/{id} -- get application details by id
    
    AuthController =>>
    POST: /api/auth/login/ --log in the application
    POST: /api/auth/logout/ --log out from the application
    POST: /api/auth/register/ --register in the application as user
    
    StageActorController =>>
    POST: /api/stageActor/ --create new stage actor
    GET : /api/stageActor/ --get all stage actor list
    GET : /api/stageActor/{id} --get stage actor by id 
    
    StagesController =>>
    POST: /api/stage/ --create new stage
    GET : /api/stage/ --get all stage list
    GET : /api/stage/{id} --get stage by id  
    
    UserController =>>
    POST: /api/user/ --create new user
    GET : /api/user/ --get all user list
    GET : /api/user/{id} --get user by id
    
    