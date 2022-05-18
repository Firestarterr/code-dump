package i.am.firestarterr.metadataReport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttachmentMetadataJsonDto {

    //metadataJson
    private boolean checkisWastesTaken;
    private String component_serial_number;
    private String coolantSamples;
    private String customerName;
    private String customerNumber;
    private String defective_component_serial_number_type;
    private String engineModel;
    private String engineSerialNumber;
    private String formTime;
    private String malfunction_result;
    private String offeredRepair;
    private String oilSamples;
    private String orgSimPhoneNumber;
    private String problemComment;
    private String productModel;
    private String related_person;
    private SimsInfoDto sims;
    private List<String> technicianList = new ArrayList<>();
    private String unitSerialNumber;
    private String workHours;
    private String workOrderNo;

}
