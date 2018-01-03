package com.tora.producerconsumer.simple;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marius Adam
 */
public class Message {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Z][a-z]+$");
    private static final Pattern CNP_PATTERN = Pattern.compile("^[1-2][0-9]{12}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String PROPERTY_SEPARATOR = "~";
    private String firstName;
    private String middleName;
    private String lastName;
    private String cnp;
    private String email;

    public Message(String firstName, String middleName, String lastName, String cnp, String email) {
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        setCnp(cnp);
        setEmail(email);
    }

    public Message(String format) {
        String[] props = format.split(PROPERTY_SEPARATOR);
        List<Consumer<String>> setters = Stream.of(
                (Consumer<String>) this::setFirstName,
                this::setMiddleName,
                this::setLastName,
                this::setCnp,
                this::setEmail
        ).collect(Collectors.toList());

        for (int i = 0; i < setters.size(); ++i) {
            if (i >= props.length) {
                break;
            }

            setters.get(i).accept(props[i]);
        }
    }

    public void setFirstName(String firstName) {
        this.firstName = NAME_PATTERN.matcher(firstName).find() ? firstName : null;
    }

    public void setMiddleName(String middleName) {
        this.middleName = NAME_PATTERN.matcher(middleName).find() ? middleName : null;
    }

    public void setLastName(String lastName) {
        this.lastName = NAME_PATTERN.matcher(lastName).find() ? lastName : null;
    }

    public void setCnp(String cnp) {
        this.cnp = CNP_PATTERN.matcher(cnp).find() ? cnp : null;
    }

    public void setEmail(String email) {
        this.email = EMAIL_PATTERN.matcher(email).find() ? email : null;
    }


    @Override
    public String toString() {
        return format();
    }

    public String format() {
        List<Function<Void, String>> getters = Stream.of(
                (Function<Void, String>) aVoid -> firstName + PROPERTY_SEPARATOR,
                (v) -> middleName + PROPERTY_SEPARATOR,
                (v) -> lastName + PROPERTY_SEPARATOR,
                (v) -> cnp + PROPERTY_SEPARATOR,
                (v) -> email
        ).collect(Collectors.toList());


        StringBuilder sb = new StringBuilder();
        getters.forEach(f -> sb.append(f.apply(null)));

        return sb.toString();
    }
}
