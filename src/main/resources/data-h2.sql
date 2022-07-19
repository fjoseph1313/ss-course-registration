--Initial Queries to populate sample Course, Student and student-course registration.
INSERT INTO course(course_id, course_name) VALUES ('course1', 'CS101'), ('course2', 'CS525'), ('course3', 'CS485');
INSERT INTO student(student_id, student_name) VALUES ('283a45f3-bfbf-433c-b230-b3275ba2b9d0', 'FRANCIS JOSEPH'),
('student2', 'STATE STREET'),
('b3275ba2b9d0', 'EXTRA STUDENT');
INSERT INTO student_course(student_id, course_id) VALUES ('student2', 'course1'),
('283a45f3-bfbf-433c-b230-b3275ba2b9d0', 'course1');