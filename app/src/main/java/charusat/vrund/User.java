package charusat.vrund;

/**
 * Created by Harsh Shah on 29/09/2017.
 */

public class User {

    public String email;
    public String pass;
    public String fname;
    public String phone;
    public String role;
    public String gender;
    public  boolean ioc;

    User(){

    }

    User(String email, String pass, String fname, String phone,String role, String gender, boolean ioc){
        this.email = email;
        this.pass = pass;
        this.fname = fname;
        this.phone = phone;
        this.role = role;
        this.gender = gender;
        this.ioc = ioc;
    }

}
