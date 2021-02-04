package com.MyPage.MyPage.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Squads")
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSquads")
    private int idSquad;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @ManyToMany
    @JoinTable(name = "Users_has_Squads",
            joinColumns = @JoinColumn(name = "Squads_idSquads"),
            inverseJoinColumns = @JoinColumn(name = "Users_idUsers"))
    private Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Squad(int idSquad, String name, String code) {
        this.idSquad = idSquad;
        this.name = name;
        this.code = code;
    }

    public Squad(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Squad(){
    }

    public int getIdSquad() {
        return idSquad;
    }

    public void setIdSquad(int idSquad) {
        this.idSquad = idSquad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Squad{" +
                "idSquad=" + idSquad +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
