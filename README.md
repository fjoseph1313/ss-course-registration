### State Street Student Course Registration
This application performs student course registration and other regular operations described below.

###### To run this application do the following:
1. Run maven package command `mvn clean package`
2. Run `java -jar ss-course-registration-1.0.0.jar` command from the target directory of the built application
3. Access APIs using the documentation below
4. Access InMemory DB using this link: `http://localhost:8888/h2`

Provided APIs
1. ###### Student registration
   Request: POST: `localhost:8888/state-street/student/register/{student name}` where {studentName} is a parameter
   value for actual student name.
   Response: `{
   "studentId": "283a45f3-bfbf-433c-b230-b3275ba2b9d0",
   "studentName": "FRANCIS JOSEPH",
   "courses": []
   }`
   
2. ###### Delete a Student
   Request: POST: `localhost:8888/state-street/student/{studentID}/delete` where {studentID} is a parameter
   value for actual student ID. Can not delete a student by name.
   Response: `Successfully deleted a student with ID: studentID`
   
3. ###### Get Students Registered in a course, Sort by their name
   Request: GET: `localhost:8888/state-street/student/registered/in/course/{courseName}` where {courseName} is a 
   parameter value for actual Course name.
   Response: `[
   {
   "studentId": "283a45f3-bfbf-433c-b230-b3275ba2b9d0",
   "studentName": "FRANCIS JOSEPH"
   },
   {
   "studentId": "student2",
   "studentName": "STATE STREET"
   }
   ]`
   
4. ###### Find all students not registered for a course
   Request: GET: `localhost:8888/state-street/student/unregistered/in/course/{courseName}` where {courseName} is a
   parameter value for actual Course name.
   Response: `[
   {
   "studentId": "b3275ba2b9d0",
   "studentName": "EXTRA STUDENT"
   }
   ]`

