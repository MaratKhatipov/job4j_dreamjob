package ru.job4j.dreamjob.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {
    private int idCandidate;
    private String name;
    private String desc;
    private LocalDateTime created;

    public Candidate() {
    }

    public Candidate(int idCandidate, String name) {
        this.idCandidate = idCandidate;
        this.name = name;
    }

    public Candidate(int idCandidate, String name, String desc, LocalDateTime created) {
        this.idCandidate = idCandidate;
        this.name = name;
        this.desc = desc;
        this.created = created;
    }


    public int getIdCandidate() {
        return idCandidate;
    }

    public void setIdCandidate(int idCandidate) {
        this.idCandidate = idCandidate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return idCandidate == candidate.idCandidate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCandidate);
    }

    @Override
    public String toString() {
        return "Candidate{"
                + "idCandidate=" + idCandidate
                + ", name='" + name + '\''
                + ", desc='" + desc + '\''
                + ", created=" + created
                + '}';
    }
}