package fr.trophyquest.web.service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "psn_title")
@Data
public class PsnTitle {

    @Id
    private UUID id;

    private String psnId;

    private String name;

    private String category;

    private String imageUrl;

}