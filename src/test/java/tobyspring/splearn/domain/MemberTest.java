package tobyspring.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    private static final String EMAIL = "kimdonghwan913@gmail.com";
    private static final String NICKNAME = "monkey";
    private static final String PASSWORD_HASH = "secret";

    @Test
    void createMember() {
        Member member = new Member(EMAIL, NICKNAME, PASSWORD_HASH);

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> new Member(null, NICKNAME, PASSWORD_HASH))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        Member member = new Member(EMAIL, NICKNAME, PASSWORD_HASH);

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        Member member = new Member(EMAIL, NICKNAME, PASSWORD_HASH);
        member.activate();

        assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate() {
        Member member = new Member(EMAIL, NICKNAME, PASSWORD_HASH);
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        Member member = new Member(EMAIL, NICKNAME, PASSWORD_HASH);

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
}
