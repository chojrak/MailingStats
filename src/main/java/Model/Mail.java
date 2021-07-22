package Model;

import java.util.LinkedList;

public class Mail {

    String email;
    LinkedList<String> taskBusiId;

    public Mail(String email, String taskBusiId) {
        this.taskBusiId = new LinkedList<String>();
        this.taskBusiId.add(taskBusiId);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public LinkedList<String> getTaskBusiIds() {
        return taskBusiId;
    }

    public void addTaskBusiId(String s) {
        taskBusiId.add(s);
    }
}
