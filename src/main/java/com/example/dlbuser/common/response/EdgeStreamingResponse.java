package com.example.dlbuser.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({ "data" })
public class EdgeStreamingResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    // 요청에 성공한 경우
    public EdgeStreamingResponse(T result) {
        this.data = result;
    }

}
