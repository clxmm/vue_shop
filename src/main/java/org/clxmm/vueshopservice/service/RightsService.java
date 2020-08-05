package org.clxmm.vueshopservice.service;

import org.clxmm.vueshopservice.domian.Rights;
import org.clxmm.vueshopservice.mapper.RightsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author clx
 * @date 2020-08-05 20:32
 */
@Service
public class RightsService {


    @Autowired
    private RightsMapper rightsMapper;


    public List<Rights> getAll() {
        return rightsMapper.selectAll();
    }


}
