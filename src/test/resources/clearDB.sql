DELETE FROM tbl_course_students WHERE EXISTS (SELECT 1 FROM tbl_course_students);
Delete from tbl_course WHERE EXISTS (SELECT 1 FROM tbl_course);
DELETE FROM tbl_teacher WHERE EXISTS (SELECT 1 FROM tbl_teacher);
DELETE FROM tbl_student WHERE EXISTS (SELECT 1 FROM tbl_student);