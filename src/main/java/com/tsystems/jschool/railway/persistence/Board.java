package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
@NamedQueries({
        @NamedQuery(name = "Board.findBoardsBetweenDates",
                query = "SELECT board FROM Board board WHERE board.dateTime BETWEEN :dateTimeFrom AND :dateTimeTo"),
        @NamedQuery(name = "Board.findBoardByRouteNumber",
                query = "SELECT board FROM Board board WHERE board.route.number = :routeNumber"),
        @NamedQuery(name = "Board.findBoardByTrainName", query = "SELECT board FROM Board board " +
                "WHERE board.train.name =:trainName"),
        @NamedQuery(name = "Board.findBoardByTrainNameAndRoute", query = "SELECT board FROM Board board " +
                "WHERE board.route.number =:routeNumber AND board.train.name =:trainName"),
})
@Entity
@Table(name = "board", schema = "railway")
public class Board implements Comparable<Board>{
    private int id;
    private Date dateTime;
    private Route route;
    private Train train;
    private Collection<Ticket> tickets;

    public Board() {
    }

    public Board(Date dateTime, Route route, Train train) {
        this.dateTime = dateTime;
        this.route = route;
        this.train = train;
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

    @Basic
    @Column(name = "date_time", nullable = false)
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board that = (Board) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id")
    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int compareTo(Board o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
