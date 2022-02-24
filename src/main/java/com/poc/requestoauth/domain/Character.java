package com.poc.requestoauth.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Character {
    private int id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private Origin origin;
    private Location location;
    private String image;
    private List<String> episode;
    private String url;
    private LocalDateTime created;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public Origin getOrigin() {
        return origin;
    }

    public Location getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setEpisode(List<String> episode) {
        this.episode = episode;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private String name;
        private String status;
        private String species;
        private String type;
        private String gender;
        private Origin origin;
        private Location location;
        private String image;
        private List<String> episode;
        private String url;
        private LocalDateTime created;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withSpecies(String species) {
            this.species = species;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder withOrigin(Origin origin) {
            this.origin = origin;
            return this;
        }

        public Builder withLocation(Location location) {
            this.location = location;
            return this;
        }

        public Builder withImage(String image) {
            this.image = image;
            return this;
        }

        public Builder withEpisode(List<String> episode) {
            this.episode = episode;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withCreated(LocalDateTime created) {
            this.created = created;
            return this;
        }

        public Character build() {
            Character character = new Character();
            character.setId(id);
            character.setName(name);
            character.setStatus(status);
            character.setSpecies(species);
            character.setType(type);
            character.setGender(gender);
            character.setOrigin(origin);
            character.setLocation(location);
            character.setImage(image);
            character.setEpisode(episode);
            character.setUrl(url);
            character.setCreated(created);
            return character;
        }
    }
}
