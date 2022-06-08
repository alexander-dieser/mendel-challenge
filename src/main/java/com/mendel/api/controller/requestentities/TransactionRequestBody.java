package com.mendel.api.controller.requestentities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequestBody {
    private String id;
    private String type;
    private String amount;

    @JsonProperty("parent_id")
    private String parentId;
}
