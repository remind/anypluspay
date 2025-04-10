package com.anypluspay.payment.domain.payorder.event;

import com.anypluspay.payment.domain.payorder.general.GeneralPayOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wxj
 * 2025/4/10
 */
@Data
@AllArgsConstructor
public class PayOrderResultEvent {

    private GeneralPayOrder payOrder;
}
