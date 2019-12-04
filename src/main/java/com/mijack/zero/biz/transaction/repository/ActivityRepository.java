/*
 *     Copyright 2019 Mi&Jack
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.mijack.zero.biz.transaction.repository;

import static com.mijack.zero.common.exceptions.BaseBizException.createException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mijack.zero.biz.transaction.domain.Activity;
import com.mijack.zero.biz.transaction.domain.Transaction;
import com.mijack.zero.biz.transaction.infrastructure.dao.ActivityDao;
import com.mijack.zero.biz.transaction.infrastructure.dao.data.ActivityDO;
import com.mijack.zero.biz.user.domain.User;
import com.mijack.zero.biz.user.repository.UserRepository;
import com.mijack.zero.common.Assert;
import com.mijack.zero.common.base.BaseConverter;
import com.mijack.zero.framework.ddd.Repo;
import com.mijack.zero.utils.CollectionHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Mi&amp;Jack
 */
@Repo
public class ActivityRepository extends BaseConverter<ActivityDO, Activity> {
    public static final Logger logger = LoggerFactory.getLogger(ActivityRepository.class);
    @Resource
    ActivityDao activityDao;
    @Resource
    UserRepository userRepository;
    @Resource
    TransactionRepository transactionRepository;

    public List<Activity> listActivity(User user) {
        List<ActivityDO> db = activityDao.selectList(new QueryWrapper<ActivityDO>().lambda().eq(ActivityDO::getUserId, user.getId()));
        return db.stream().map(this::convert).collect(Collectors.toList());
    }

    public Activity findActivityByUserAndActivityId(User user, Long activityId) {
        ActivityDO db = activityDao.selectOne(new QueryWrapper<ActivityDO>().lambda()
                .eq(ActivityDO::getId, activityId)
                .eq(ActivityDO::getUserId, user.getId()));
        return convert(db);
    }

    @Override
    protected Activity doForward(@Nonnull ActivityDO activityDO) {
        Activity activity = new Activity();
        activity.setId(activityDO.getId());
        activity.setUser(userRepository.getUserById(activityDO.getUserId()));
        activity.setName(activityDO.getName());
        activity.setMark(activityDO.getMark());
        activity.setTags(CollectionHelper.toList(StringUtils.split(activityDO.getTags(), ",")));
        activity.setCreateTime(activityDO.getCreateTime());
        activity.setUpdateTime(activityDO.getUpdateTime());
        List<Transaction> transactions = transactionRepository.listTransactionByActivity(activity);
        activity.setTransactions(transactions);
        return activity;
    }


    @Transactional(rollbackFor = Exception.class)
    public long addActivity(Activity activity) {
        ActivityDO activityDO = reverse().convert(activity);
        Long activityId = activityDao.allocateId();
        Objects.requireNonNull(activityDO).setId(activityId);

        Assert.state(activityDao.insert(activityDO) > 0, () -> createException("创建activity记录失败"));
        Assert.state(transactionRepository.addTransaction(activity, activity.getTransactions()) > 0,
                () -> createException("创建activity的transaction记录失败"));
        return 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteActivity(Activity activity) {
        ActivityDO activityDO = reverse().convert(activity);
        assert activityDO != null;
        Long activityId = activityDO.getId();
        Assert.state(activityDao.deleteById(activityId) > 0, () -> createException("activity删除失败"));
        Assert.state(transactionRepository.deleteTransactions(activity, activity.getTransactions())
                        == activity.getTransactions().size(),
                () -> createException("删除activity的transaction记录失败"));
        return 1;
    }
}
