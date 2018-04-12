package wundERP.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

@Entity
public class CashCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Map<Integer, Integer> noteCount = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Map<Integer, Integer> getNoteCount() {
        return noteCount;
    }

    public void setNoteCount(Map<Integer, Integer> noteCount) {
        this.noteCount = noteCount;
    }
}

