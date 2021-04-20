package io.wisoft.jpashop.domain.user;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
public class Email {

    @Column(name = "email")
    private String address;

    protected Email() {}

    private Email(String address) {
        checkArgument(StringUtils.hasText(address), "address must be required.");
        checkArgument(address.length() >= 4 && address.length() <= 40,
                "address's length must be between 4 and 40 characters.");
        checkArgument(isValidAddress(address), "Invalid email address : " + address);

        this.address = address;
    }

    public static Email from(String address) { return new Email(address); }

    private boolean isValidAddress(String address) {
        Pattern pattern = Pattern.compile("^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    @Override
    public String toString() {
        return "Email{" +
                "address='" + address + '\'' +
                '}';
    }
}
