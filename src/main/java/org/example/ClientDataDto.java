package org.example;

public class ClientDataDto {
    private long id;
    private String name;

    public ClientDataDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClientDataDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
