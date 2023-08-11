package ua.ithillel.dto;

public class CreateUserObject {

    private String name;
    private String job;

    public CreateUserObject(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public CreateUserObject() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
