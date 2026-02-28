package tobyspring.splearn.domain.member;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import tobyspring.splearn.domain.AbstractEntity;

import static java.util.Objects.requireNonNull;

@ToString(callSuper = true)
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Entity
public class MemberDetail extends AbstractEntity {
    private Profile profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    public void activate() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt은 설정되었습니다.");

        this.activatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        Assert.isTrue(deactivatedAt == null, "이미 deactivatedAt은 설정되었습니다.");

        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = requireNonNull(updateRequest.introduction());
    }
}
