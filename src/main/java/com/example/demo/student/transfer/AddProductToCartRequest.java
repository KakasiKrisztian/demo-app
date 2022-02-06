package com.example.demo.student.transfer;

public class AddProductToCartRequest {
    private long studentId;
    private long enrollListId;

    public AddProductToCartRequest(long studentId, long enrollListId) {
        this.studentId = studentId;
        this.enrollListId = enrollListId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getEnrollListId() {
        return enrollListId;
    }

    public void setEnrollListId(long enrollListId) {
        this.enrollListId = enrollListId;
    }

    @Override
    public String toString() {
        return "AddProductToCartRequest{" +
                "studentId=" + studentId +
                ", enrollListId=" + enrollListId +
                '}';
    }
}
