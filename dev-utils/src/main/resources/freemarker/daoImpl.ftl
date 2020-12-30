package org.neris.pfrs.riskMonitoringAutoConfig.infrastructure.dao.impl;

import org.neris.pfrs.riskMonitoringAutoConfig.domain.pk.${tableInfo.beanName}PK;
import org.neris.pfrs.riskMonitoringAutoConfig.domain.po.${tableInfo.beanName}PO;
import org.neris.pfrs.riskMonitoringAutoConfig.domain.qo.${tableInfo.beanName}QO;
import org.neris.pfrs.riskMonitoringAutoConfig.infrastructure.dao.${tableInfo.beanName}Dao;
import org.springframework.stereotype.Repository;

@Repository
public class ${tableInfo.beanName}DaoImpl extends AbstructPfrsDaoImpl<${tableInfo.beanName}PO, ${tableInfo.beanName}QO, ${tableInfo.beanName}PK> implements ${tableInfo.beanName}Dao {
}
