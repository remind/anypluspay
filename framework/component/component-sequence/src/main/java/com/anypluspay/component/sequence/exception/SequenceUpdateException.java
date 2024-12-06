package com.anypluspay.component.sequence.exception;

/**
 * 序列更新异常
 * @author wxj
 * 2023/12/10
 */
public class SequenceUpdateException extends RuntimeException {

    public SequenceUpdateException(Exception e) {
        super(e);
    }
}
