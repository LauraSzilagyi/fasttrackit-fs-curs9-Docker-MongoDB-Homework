package ro.fasttrackit.curs9.homework.exceptions;

public class JsonPatchCannotBeAppliedException extends RuntimeException {
    public JsonPatchCannotBeAppliedException(Exception ex) {
        super(ex);
    }
}
