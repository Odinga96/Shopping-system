package users;

import Databse.DbConnect;

abstract class Person {
    private String Username;
    private String Email;
    protected DbConnect personData;

    public Person(String Name, String email){
        this.Username=Name;
        this.Email=email;

        personData=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
    }

    protected   String getUsername() {
        return Username;
    }

    protected String getEmail() {
        return Email;
    }

    abstract void AddPersonRecord();
}
