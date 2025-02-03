package ec.edu.uce.marketplace.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;
import java.util.List;

@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user; // Relación One-to-One con User

    @Column(nullable = false)
    private String country;

    @Column(name = "country_flag_url")
    private String countryFlagUrl;

    private String presentation;

    private String language;

    private String description;

    private String experience;

    private String reference;

    private String education;

    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "skill")
    private List<String> skills; // Lista de habilidades

    @ElementCollection
    @CollectionTable(name = "user_certifications", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "certification")
    private List<String> certifications; // Lista de certificaciones

    private String qualifications;

    @ElementCollection
    @CollectionTable(name = "user_publications", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "publication_url")
    private List<String> publications; // Lista de publicaciones

    @ElementCollection
    @CollectionTable(name = "user_social_links", joinColumns = @JoinColumn(name = "profile_id"))
    @MapKeyColumn(name = "platform")
    @Column(name = "link")
    private Map<String, String> socialLinks; // Enlaces sociales (mapa: plataforma -> enlace)
}
