package rest.api.constellationsreservations.Enums;

public enum Role {
    USER,
    ADMIN;

    public String getAuthority() {
        return name();
    }
}
