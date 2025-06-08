package application.project.repository;

import org.springframework.beans.factory.annotation.Autowired;

import application.project.util.AuditUtil.AuditUtil;

public abstract class AuditRepository {
    @Autowired protected AuditUtil auditUtil;
}
