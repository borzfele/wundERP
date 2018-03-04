package wundERP.models;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class DailyAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Calendar date;
    @ManyToOne(fetch = FetchType.EAGER)
    private User owner;
    @Column(nullable = false)
    private int openCash;
    private int dailyBalance;
    private int closeCash;
    private int cassaBalance;
    private int posBalance;
    private int terminalBalance;
    private String comments;

}
