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

package me.lyml.summer.account.repository;

import me.lyml.summer.account.entity.Permission;
import me.lyml.summer.account.entity.Role;
import me.lyml.summer.base.repository.BaseDao;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName: RoleDao
 * @author: cnlyml
 * @date: 2016/9/5 9:43
 */
public interface RoleDao extends BaseDao<Role, Long> {
    @Query(value = "select p from Permission p inner join p.roleList r where r.id = ?1")
    List<Permission> findPermissionsByRoleID(Long roleID);
}
