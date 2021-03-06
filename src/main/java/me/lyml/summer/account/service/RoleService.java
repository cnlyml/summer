/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.account.service;

import me.lyml.summer.account.entity.Permission;
import me.lyml.summer.account.entity.Role;
import me.lyml.summer.account.repository.RoleDao;
import me.lyml.summer.account.repository.mybatis.RoleMapperDao;
import me.lyml.summer.base.repository.BaseDao;
import me.lyml.summer.base.service.BaseService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleService
 * @author: cnlyml
 * @date: 2016/9/5 9:47
 */
@Component
@Transactional(readOnly = true)
public class RoleService extends BaseService<Role, Long> {
    @Resource
    private RoleDao dao;
    @Resource
    private RoleMapperDao mapperDao;

    @Override
    public BaseDao<Role, Long> dao() {
        return dao;
    }

    public List<Map<String,Object>> findRoleByUserID(Long userID) {
        return mapperDao.findRoleByUserID(userID);
    }

    public List<Permission> findPermissionsByRoleID(Long roleID) {
        return dao.findPermissionsByRoleID(roleID);
    }
}
