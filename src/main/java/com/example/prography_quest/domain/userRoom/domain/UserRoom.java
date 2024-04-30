package com.example.prography_quest.domain.userRoom.domain;

import com.example.prography_quest.domain.room.domain.Room;
import com.example.prography_quest.domain.room.dto.UpdateRoomRequest;
import com.example.prography_quest.domain.user.domain.User;
import com.example.prography_quest.domain.userRoom.dto.UpdateTeamRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "userrooms")
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userroom_id")
    private Integer id;

    private Integer room_id;
    private Integer user_id;

    private Team team;

    @Builder
    public UserRoom(Room room, User user, Team team) {
        this.room_id = room.getId();
        this.user_id = user.getId();
        this.team = team;
    }

    public UserRoom updateTeam(UpdateTeamRequest request) {
        this.team = request.getTeam();
        return this;
    }
}
