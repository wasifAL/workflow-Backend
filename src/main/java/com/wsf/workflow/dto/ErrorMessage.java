package com.wsf.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {

    Set<String> errorMessages = new HashSet<String>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        getErrorMessages().forEach((String error) -> {
            sb.append("* ");
            sb.append(error);
            sb.append("\n");
        });

        return sb.toString();
    }
}
