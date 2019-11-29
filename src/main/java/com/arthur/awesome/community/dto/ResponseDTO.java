package com.arthur.awesome.community.dto;

import com.arthur.awesome.community.exception.CustomizeException;
import com.arthur.awesome.community.exception.ICustomizeErrorCode;
import lombok.Data;

@Data
public class ResponseDTO {
    private Integer code;
    private String message;

    public static ResponseDTO errorOf(Integer code, String message) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        return responseDTO;
    }

    public static ResponseDTO errorOf(ICustomizeErrorCode error) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(error.getCode());
        responseDTO.setMessage(error.getMessage());
        return responseDTO;
    }

    public static ResponseDTO errorOf(CustomizeException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(e.getCode());
        responseDTO.setMessage(e.getMessage());
        return responseDTO;
    }

    public static ResponseDTO okOf() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(200);
        responseDTO.setMessage("ok");
        return responseDTO;
    }
}
