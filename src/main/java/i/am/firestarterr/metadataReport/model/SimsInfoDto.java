package i.am.firestarterr.metadataReport.model;

import lombok.Data;

@Data
public class SimsInfoDto {

    private String id;
    private String simsCode;
    private String simsDescription;
    private String simsSerialNumber;
    private String simsQuantity;
    private String simsGroupNumber;
    private Boolean simsDurabilityIndicator;
    private String descriptionOfFailure;
    private String reasonOfFailure;
    private Boolean removedThePart;
    private String technician;

}
