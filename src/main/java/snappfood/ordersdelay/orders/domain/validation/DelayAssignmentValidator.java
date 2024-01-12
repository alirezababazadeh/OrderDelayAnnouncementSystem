package snappfood.ordersdelay.orders.domain.validation;

import lombok.SneakyThrows;
import snappfood.ordersdelay.core.validations.Validator;
import snappfood.ordersdelay.orders.domain.exceptions.DelayAssigmentException;
import snappfood.ordersdelay.orders.domain.exceptions.DelayReportException;

public class DelayAssignmentValidator extends Validator<DelayAssigmentException> {

    @Override
    protected void throwException(String message) throws DelayAssigmentException {
        throw new DelayAssigmentException(message);
    }
}
