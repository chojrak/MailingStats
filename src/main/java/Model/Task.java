package Model;

import java.util.LinkedList;

public class Task {
    String taskBusiId;
    LinkedList<String> email;

    public Task(String taskBusiId, String mail) {
        this.email = new LinkedList<>();
        email.add(mail);
        this.taskBusiId = taskBusiId;
    }

    public String getTaskBusiId() {
        return taskBusiId;
    }

    public LinkedList<String> getEmails() {
        return email;
    }

    public void addEmail(String s) {
        email.add(s);
    }
}
