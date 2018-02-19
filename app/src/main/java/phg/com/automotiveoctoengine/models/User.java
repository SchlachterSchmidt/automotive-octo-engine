package phg.com.automotiveoctoengine.models;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private String confirm_password;
    private boolean active;


    public User(int id, String firstname, String lastname, String username, String email, String password, boolean active) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirm_password;
    }

    public void setConfirmPassword(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String toString() {
        return "id: " + id + "\nfirstname: " + firstname + "\nlastname: " + lastname
                + "\nemail: " + email + "\nusername: " + username + "\nactive: " + active
                + "password\n" + password;
    }
}
