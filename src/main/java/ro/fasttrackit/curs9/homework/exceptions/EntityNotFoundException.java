package ro.fasttrackit.curs9.homework.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String id) {
        super("Entity with id %s doesn't exist".formatted(id));
    }
}
