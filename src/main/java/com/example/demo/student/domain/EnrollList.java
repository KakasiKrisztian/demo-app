package com.example.demo.student.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class EnrollList {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

    @NotNull
    private String listName;

    @NotNull
    private String description;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "enrolllist_student",
            joinColumns = @JoinColumn(name = "enrolllist_id"), inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new HashSet<>();

    public void addToEnrollList(Student student) {
        //adding student to the current enroll list
        students.add(student);

        //adding current enroll list to the enroll lists set of the received student
        student.getEnrollLists().add(this);

    }

    public void removeFromCart(Student student) {
        students.remove(student);

        student.getEnrollLists().remove(this);
    }

    public EnrollList() {
    }

    public EnrollList(String listName, String description){
        this.listName = listName;
        this.description = description;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EnrollList{" +
                "id=" + id +
                ", listName='" + listName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
