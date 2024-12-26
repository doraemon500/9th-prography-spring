package com.example.prography_quest.domain.room.domain;

import com.example.prography_quest.domain.room.dto.UpdateRoomRequest;
import com.example.prography_quest.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Integer id;
    private String title;

    private Integer hostId;

    private Room_type roomType;
    private RoomStatus status;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    @Builder
    public Room(String title, User user, RoomStatus status, Room_type room_type) {
        this.hostId = user.getId();
        this.title = title;
        this.status = status;
        this.roomType = room_type;
    }

    public Room updateRoom(UpdateRoomRequest request) {
        this.status = request.getStatus();
        return this;
    }
}
