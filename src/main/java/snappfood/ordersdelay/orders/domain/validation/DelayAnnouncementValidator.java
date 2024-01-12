package snappfood.ordersdelay.orders.domain.validation;

import snappfood.ordersdelay.core.validations.Validator;
import snappfood.ordersdelay.orders.domain.exceptions.DelayReportException;

public class DelayAnnouncementValidator extends Validator<DelayReportException> {

    @Override
    protected void throwException(String message) throws DelayReportException {
        throw new DelayReportException(message);
    }
}
