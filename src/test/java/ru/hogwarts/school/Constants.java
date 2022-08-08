package ru.hogwarts.school;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class Constants {
    public static void testConstants() {

        Faculty testFaculty = new Faculty();
        testFaculty.setColor("red");
        testFaculty.setName("testFac");
        testFaculty.setId(300L);

        Student testStudent = new Student();
        testStudent.setAge(10);
        testStudent.setName("testStu");
        testStudent.setFaculty(testFaculty);
        testStudent.setId(300L);
    }
    public void testStudent() throws JSONException {
        JSONObject studentObject = new JSONObject();
        studentObject.put("name", "testname");
        studentObject.put("age", 200);
    }

}
