package fr.aelion.streamer.dto;

import fr.aelion.streamer.enumFolder.MemberType;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMemberDto {
    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private MemberType role;
}
