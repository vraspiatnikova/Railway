package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.util.Set;

@NamedQuery(name="Train.findTrainByName" ,
        query="SELECT train FROM Train train WHERE train.name=:trainName")
@Entity
@Table(name = "train", schema = "railway")
public class Train {

    private int id;
    private int capacity;
    private String name;
    private Set<Board> boards;

    public Train() {
    }

    public Train(String name, Integer capacity) {
        this.capacity = capacity;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train that = (Train) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + capacity;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "train", fetch = FetchType.EAGER)
    public Set<Board> getBoards() {
        return boards;
    }

    public void setBoards(Set<Board> boards) {
        this.boards = boards;
    }
}
