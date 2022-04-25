package kr.co.hjsoft.service;

import kr.co.hjsoft.dto.ApiDTO;
import kr.co.hjsoft.dto.PageRequestDTO;
import kr.co.hjsoft.dto.PageResultDTO;
import kr.co.hjsoft.entity.Api;

import java.util.List;

public interface ApiService {

    PageResultDTO<ApiDTO, Object[]> getList(PageRequestDTO dto);

    List<ApiDTO> get();

    default Api dtoToEntity(ApiDTO dto){
        Api api = Api.builder()
                .apino(dto.getApino())
                .gubun(dto.getGubun())
                .maxclassnm(dto.getMaxclassnm())
                .minclassnm(dto.getMinclassnm())
                .svcstatnm(dto.getSvcstatnm())
                .svcnm(dto.getSvcnm())
                .payatnm(dto.getPayatnm())
                .placenm(dto.getPlacenm())
                .usetgtinfo(dto.getUsetgtinfo())
                .svcurl(dto.getSvcurl())
                .areanm(dto.getAreanm())
                .telno(dto.getTelno())
                .build();

        return api;
    }
    default ApiDTO entityToDTO(Api api){
        ApiDTO dto = ApiDTO .builder()
                .apino(api.getApino())
                .gubun(api.getGubun())
                .maxclassnm(api.getMaxclassnm())
                .minclassnm(api.getMinclassnm())
                .svcstatnm(api.getSvcstatnm())
                .svcnm(api.getSvcnm())
                .payatnm(api.getPayatnm())
                .placenm(api.getPlacenm())
                .usetgtinfo(api.getUsetgtinfo())
                .svcurl(api.getSvcurl())
                .areanm(api.getAreanm())
                .telno(api.getTelno())
                .build();
        return dto;
    }
}
