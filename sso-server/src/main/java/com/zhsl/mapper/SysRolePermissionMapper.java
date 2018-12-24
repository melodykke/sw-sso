package com.zhsl.mapper;

import com.zhsl.model.SysRolePermission;
import java.util.List;

public interface SysRolePermissionMapper {
    int insert(SysRolePermission record);

    List<SysRolePermission> selectAll();
}