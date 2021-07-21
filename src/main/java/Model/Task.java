package Model;

public class Task {
    String taskBusiId;
    String email;
    public Task (String taskBusiId, String email) {
        this.email = email;
        this.taskBusiId = taskBusiId;
    }

    public String getTaskBusiId() {
        return taskBusiId;
    }

    public String getEmail() {
        return email;
    }
}
