package charusat.vrund17;

/**
 * Created by Harsh Shah on 29/09/2017.
 */

public class User
{
    public String email;
    public String name;
    public String phone;
    public String role;
    public String gender;
    public  boolean ioc;
    public  boolean organiser;
    public String p_id;

    User(){

    }

    User(String email, String name, String phone, String role, String gender, boolean ioc, String p_id, boolean organiser) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
        this.ioc = ioc;
        this.p_id = p_id;
        this.organiser = organiser;
    }

}
