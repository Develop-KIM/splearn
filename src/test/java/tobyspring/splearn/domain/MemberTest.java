package tobyspring.splearn.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @DisplayName("회원을 생성한다.")
    @Test
    void createMember() {
        // given
        String email = "kimdonghwan913@gmail.com";
        String nickname = "monkey";
        String passwordHash = "secret";

        // when
        Member member = new Member(email, nickname, passwordHash);

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
}
