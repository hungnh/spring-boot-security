package uet.hungnh.security.dto.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) return false;

        List<Rule> rules = Arrays.asList(
                new LengthRule(8, 30),
                new WhitespaceRule()
        );
        final PasswordValidator validator = new PasswordValidator(rules);
        final RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
}
