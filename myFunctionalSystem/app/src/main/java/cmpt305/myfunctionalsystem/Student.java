package cmpt305.myfunctionalsystem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Student{

    private String name;
    private int id;
    private List<Course> coursesInPlanner;
    private List<Course> classSchedule;
    private List<Course> shoppingCartCourses;

    public Student(JSONObject studentInfo) throws JSONException {
        this.name = studentInfo.getString("lastName")+ ","+studentInfo.getString("firstName");

    }

}
