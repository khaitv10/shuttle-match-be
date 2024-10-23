package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllAccountResponse {
    private int total;
    private List<UserSummaryResponse> listAccount;

    public AllAccountResponse (int total, List<UserSummaryResponse> listAccount){
        this.total = total;
        this.listAccount = listAccount;
    }
}
