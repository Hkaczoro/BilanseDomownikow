package com.MyPage.MyPage.Models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "History")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idHistory;

    @Column
    private String comment;

    @Column
    private float value;

    @Column
    private Date date;

    @ManyToOne
    @JoinColumn(name = "Users_idUsers1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "Users_idUsers2")
    private User user2;

    @ManyToOne
    @JoinColumn(name = "Squads_idSquads")
    private Squad squad;

    public History(String comment, float value, Date date, User user1, User user2, Squad squad) {
        this.comment = comment;
        this.value = value;
        this.date = date;
        this.user1 = user1;
        this.user2 = user2;
        this.squad = squad;
    }

    public History(){

    }

    public int getIdHistory() {
        return idHistory;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
