package com.qatang.team.manager.service.log.impl;


import com.qatang.team.manager.core.service.AbstractService;
import com.qatang.team.manager.entity.log.LogEntity;
import com.qatang.team.manager.service.log.LogService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-05-13 14:32
 */
@Service
@Transactional
public class LogServiceImpl extends AbstractService<LogEntity, Long> implements LogService {

}
