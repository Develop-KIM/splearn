package tobyspring.splearn;

import org.springframework.context.annotation.Bean;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.domain.MemberFixture;
import tobyspring.splearn.domain.PasswordEncoder;

public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
