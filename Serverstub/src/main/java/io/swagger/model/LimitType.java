package io.swagger.model;

public enum LimitType {
    CUSTOMER_TRANSACTIONS(5000L),
    CUSTOMER_DAILY_TRANSACTIONS(100L),
    BANKACCOUNT_MIN(0L);

    private final Limit limit;

    LimitType(Long max){
        limit = new Limit().limit(max).type(this);
    }

    LimitType(Double max){
        limit = new Limit().limit(max).type(this);
    }

    public Limit getDefault(){
        return limit;
    }
}
