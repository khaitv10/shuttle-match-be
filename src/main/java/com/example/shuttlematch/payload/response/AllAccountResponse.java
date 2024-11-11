package com.example.shuttlematch.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllAccountResponse {
    private int totalAccount;
    private int totalPage;
    private List<UserSummaryResponse> listAccount;

    public AllAccountResponse (int totalAccount, int totalPage, List<UserSummaryResponse> listAccount){
        this.totalAccount = totalAccount;
        this.totalPage = totalPage;
        this.listAccount = listAccount;
    }
}
