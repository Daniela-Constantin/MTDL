package DAO;

import Domaine.Visits;
import java.util.List;
import java.util.Optional;

public interface Dao<Users> {
    Optional<Users> get(int id);
    Users getByUsr(String usr);
    List<Users> getAll();
    void save(Users u);
    void saveVisit(Visits v);
    void updatePassword(String u, String pass);
    void updateFirstName(String u, String first);
    void updateLastName(String u, String last);
    void updateUsername(Users u, String usr);
    void updateEmail(String u, String em);
    void updatePhone(String u, String tel);
    void delete(String usr);
    int get_login(String usr, String pass);
    void enrollVisit_stud(int code, int id);
    void enrollVisit_prof(int code, int id);
    void getAllStudentEnroll();
}
