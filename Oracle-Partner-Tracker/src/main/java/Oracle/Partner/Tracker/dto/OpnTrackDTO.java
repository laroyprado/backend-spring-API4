package Oracle.Partner.Tracker.dto;

import Oracle.Partner.Tracker.entities.OpnTrack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OpnTrackDTO {

    @Schema(description = "ID da OPN Track", example = "123")
    private String id;

    @Schema(description = "Nome da OPN Track", example = "CLOUD BUILD")
    private String name;

    public OpnTrackDTO(OpnTrack entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }
    
}
