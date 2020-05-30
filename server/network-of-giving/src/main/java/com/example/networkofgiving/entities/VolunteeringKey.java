package com.example.networkofgiving.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class VolunteeringKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "charity_id")
    private Long charityId;

    public VolunteeringKey() { }

    public VolunteeringKey(Long userId, Long charityId) {
        this.userId = userId;
        this.charityId = charityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolunteeringKey that = (VolunteeringKey) o;
        return userId.equals(that.userId) &&
                charityId.equals(that.charityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, charityId);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCharityId() {
        return charityId;
    }

    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }
}
