package com.example.myapplication;

public class Pertambahan {
    private String id;
    private Integer first;
    private Integer second;
    private String operator;
    private Float result;

    public Pertambahan() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }
}
