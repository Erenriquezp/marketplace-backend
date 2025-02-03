package ec.edu.uce.marketplace.dtos;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UserProfileDto {

    private String country;
    private String countryFlagUrl;
    private String presentation;
    private String language;
    private String description;
    private String experience;
    private String reference;
    private String education;
    private List<String> skills;
    private List<String> certifications;
    private String qualifications;
    private List<String> publications;
    private Map<String, String> socialLinks;
}
