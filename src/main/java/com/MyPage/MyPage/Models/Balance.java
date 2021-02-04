package com.MyPage.MyPage.Models;

import javax.persistence.*;

@Entity
@Table(name = "Balances")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBalance;

    @Column(name = "value")
    private float value;

    @ManyToOne
    @JoinColumn(name = "Users_idUsers1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "Users_idUsers2")
    private User user2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Squads_idSquads")
    private Squad squad;

    public Balance(float value, User user1, User user2, Squad squad) {
        this.value = value;
        this.user1 = user1;
        this.user2 = user2;
        this.squad = squad;
    }

    public Balance(){

    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }
}
