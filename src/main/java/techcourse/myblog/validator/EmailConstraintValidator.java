package techcourse.myblog.validator;

import techcourse.myblog.domain.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailConstraintValidator implements ConstraintValidator<EmailConstraint, String> {
    private UserRepository userRepository;

    public EmailConstraintValidator(UserRepository userRepository) {
        System.out.println("Create");
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(EmailConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        boolean isExist = userRepository.existsByEmail(value);
        if (isExist) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("이메일 중복")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
