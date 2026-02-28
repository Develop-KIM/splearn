package tobyspring.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import tobyspring.splearn.SplearnTestConfiguration;
import tobyspring.splearn.domain.member.DuplicateEmailException;
import tobyspring.splearn.domain.member.Member;
import tobyspring.splearn.domain.member.MemberFixture;
import tobyspring.splearn.domain.member.MemberInfoUpdateRequest;
import tobyspring.splearn.domain.member.MemberRegisterRequest;
import tobyspring.splearn.domain.member.MemberStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@Import(SplearnTestConfiguration.class)
@SpringBootTest
record MemberRegisterTest(
        MemberRegister memberRegister,
        EntityManager entityManager
) {
    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        System.out.println(member);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
            .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = resigterMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void deactivate() {
        Member member = resigterMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = resigterMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.updateInfo(member.getId(),
                new MemberInfoUpdateRequest("monkeygarden", "monkey", "secret")
        );

        assertThat(member.getDetail().getProfile().address()).isEqualTo("monkey");
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("kimdonghwan913@gmail.com", "monkeygardennnnnnnnnnnnnnnnnnnn", "secret"));
        checkValidation(new MemberRegisterRequest("kimdonghwan913@gmail.com", "monk", "longsecret"));
        checkValidation(new MemberRegisterRequest("kimdonghwan913gmail.com", "monkey", "secret"));

    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Member resigterMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }
}
