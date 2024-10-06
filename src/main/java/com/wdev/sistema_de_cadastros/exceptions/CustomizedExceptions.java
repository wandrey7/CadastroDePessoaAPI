package com.wdev.sistema_de_cadastros.exceptions;

public class CustomizedExceptions extends Exception {
    public CustomizedExceptions(String msg) {
        super(msg);
    }

    public static CustomizedExceptions idReservedException(String msg) {
        return new CustomizedExceptions(msg);
    }

    public static CustomizedExceptions emailExistsException(String msg) {
        return new CustomizedExceptions(msg);
    }

    public static CustomizedExceptions idNotExistException(String msg) {
        return new CustomizedExceptions(msg);
    }
}
