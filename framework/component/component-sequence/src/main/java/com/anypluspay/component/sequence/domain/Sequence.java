package com.anypluspay.component.sequence.domain;

/**
 * 序列
 * @author wxj
 * 2023/12/10
 */
public class Sequence {

    /**
     * 序列名称
     */
    private String name;

    /**
     * 当前值
     */
    private Long currentValue;

    /**
     * 步长
     */
    private Integer increment;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 缓存数
     */
    private Integer threshold;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public void setThreshold(Integer threshold) {
        this.threshold = threshold;
    }

}
