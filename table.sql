CREATE TABLE task
     (	id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
     	taskId VARCHAR(50) NOT NULL,
     	taskDescription VARCHAR(500),
     	start DATE NOT NULL,
     	finish DATE,
     	CONSTRAINT primary_key PRIMARY KEY (id)
     	);
