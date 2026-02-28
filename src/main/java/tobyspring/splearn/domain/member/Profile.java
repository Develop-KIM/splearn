package tobyspring.splearn.domain.member;

import java.util.regex.Pattern;

public record Profile(
        String address
) {
    public static final Pattern PROFILE_ADDRESS_PATTERN =
            Pattern.compile("[a-z0-9]+");
    private static final int MAX_LENGTH = 15;


    public Profile {
        validateProfile(address);
    }

    private static void validateProfile(String address) {
        if (address == null || (!address.isEmpty() && !PROFILE_ADDRESS_PATTERN.matcher(address).matches())) {
            throw new IllegalArgumentException("프로필 주소 형식이 바르지 않습니다: " + address);
        }

        if (address.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("프로필 주소는 최대 " + MAX_LENGTH + "자리를 넘을 수 없습니다");
        }
    }

    public String mention() {
        return "@" + address;
    }
}
