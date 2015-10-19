public class Employee extends Person{
    private int SIN;
    private float salary;
    private String deptCode;
    private String role;
    
    public int getSIN() { return SIN; }
    public float getSalary() { return salary; }
    public String getDeptCode() { return deptCode; }
    public String getRole() { return role; }
    
    public void setSIN(int SIN) { this.SIN = SIN; }
    public void setSalary(float salary) { this.salary = salary; }
    public void setDeptCode(String deptCode) { this.deptCode = deptCode; }
    public void setRole(String role) { this.role = role; }
}
