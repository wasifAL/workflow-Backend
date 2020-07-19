package com.wsf.workflow.dto.replica;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO{
    private Double reqfund;
    private Double appfund;
    private Double yearlyIncome;

    //    current job details
    private String officeName;
    private String officeAddress;
    private String designation;

}
