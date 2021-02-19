package com.MyPage.MyPage.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BalanceConfirmations")
public class BalanceConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBalanceConfirmations")
    private int idBalanceConfirmations;

    @Column
    private float value;

    @Column
    private String comment;

    @Column
    private Date date;

    /**
     * User who add balance
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_idUsers1")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_idUsers2")
    private User user2;

    public int getIdBalanceConfirmations() {
        return idBalanceConfirmations;
    }

    public void setIdBalanceConfirmation(int idBalanceConfirmations) {
        this.idBalanceConfirmations = idBalanceConfirmations;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public void setIdBalanceConfirmations(int idBalanceConfirmations) {
        this.idBalanceConfirmations = idBalanceConfirmations;
    }

    public BalanceConfirmation(float value, String comment, Date date, User user1, User user2) {
        this.value = value;
        this.comment = comment;
        this.date = date;
        this.user1 = user1;
        this.user2 = user2;
    }

    public BalanceConfirmation(){

    }
}
