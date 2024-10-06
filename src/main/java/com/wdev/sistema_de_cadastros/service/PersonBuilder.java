package com.wdev.sistema_de_cadastros.service;

public final class PersonBuilder {
    private String name;
    private String email;
    private int age;
    private String height;

    private PersonBuilder() {
    }

    public static PersonBuilder aPerson() {
        return new PersonBuilder();
    }

    public PersonBuilder name(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder email(String email) {
        this.email = email;
        return this;
    }

    public PersonBuilder age(int age) {
        this.age = age;
        return this;
    }

    public PersonBuilder height(String height) {
        this.height = height;
        return this;
    }

    public Person build() {
        return new Person(name, email, age, height);
    }
}
