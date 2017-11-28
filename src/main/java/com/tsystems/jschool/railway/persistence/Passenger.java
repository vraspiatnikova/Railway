package com.tsystems.jschool.railway.persistence;

import javax.persistence.*;
import java.util.Date;
import java.util.Collection;
@NamedQuery(name = "Passenger.findPassengerByUserInfo",
        query = "SELECT passenger FROM Passenger passenger WHERE passenger.userInfo = :user")
@Entity
@Table(name = "passenger", schema = "railway")
public class Passenger {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String passport;
    private User userInfo;
    private Collection<Ticket> tickets;

    public Passenger() {
    }

    public Passenger(String firstName, String lastName, Date birthdate, String passport, User userInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.passport = passport;
        this.userInfo = userInfo;
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

    @Column(name = "passport", nullable = false)
    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "birthdate", nullable = false)
    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Passenger passenger = (Passenger) o;

        if (!passport.equals(passenger.passport)) return false;
        if (!firstName.equals(passenger.firstName)) return false;
        if (!lastName.equals(passenger.lastName)) return false;
        return birthdate.equals(passenger.birthdate);
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + birthdate.hashCode();
        result = 31 * result + passport.hashCode();
        return result;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }

    @OneToMany(mappedBy = "passenger")
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }
}
