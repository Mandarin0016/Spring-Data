package com.example.football.models.entity;

import com.example.football.models.enums.Position;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "first_name")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    private String lastName;
    @Column(nullable = false, name = "email", unique = true)
    private String email;
    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;
    @Column(nullable = false, name = "position")
    private Position position;
    @ManyToOne(targetEntity = Town.class)
    @JoinColumn(nullable = false)
    private Town town;
    @ManyToOne(targetEntity = Team.class)
    @JoinColumn(nullable = false)
    private Team team;
    @ManyToOne(targetEntity = Stat.class)
    @JoinColumn(nullable = false)
    private Stat stat;

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!id.equals(player.id)) return false;
        return email.equals(player.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("Player - %s %s", this.getFirstName(), this.getLastName())).append(System.lineSeparator());
        sb.append("\t").append(String.format("Position - %s", this.getPosition().name())).append(System.lineSeparator());
        sb.append("\t").append(String.format("Team - %s", this.getTeam().getName())).append(System.lineSeparator());
        sb.append("\t").append(String.format("Stadium - %s", this.getTeam().getStadiumName())).append(System.lineSeparator());
        return sb.toString().trim();
    }
}
