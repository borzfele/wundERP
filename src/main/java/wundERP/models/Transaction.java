package wundERP.models;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Calendar date;
    private String description;
    private int value;
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    @ManyToOne(fetch = FetchType.EAGER)
    private DailyAccount dailyAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public DailyAccount getDailyAccount() {
        return dailyAccount;
    }

    public void setDailyAccount(DailyAccount dailyAccount) {
        this.dailyAccount = dailyAccount;
    }
}
